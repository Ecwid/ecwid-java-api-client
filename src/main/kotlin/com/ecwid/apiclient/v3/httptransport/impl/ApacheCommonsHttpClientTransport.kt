package com.ecwid.apiclient.v3.httptransport.impl

import com.ecwid.apiclient.v3.httptransport.HttpRequest
import com.ecwid.apiclient.v3.httptransport.HttpResponse
import com.ecwid.apiclient.v3.httptransport.HttpTransport
import com.ecwid.apiclient.v3.httptransport.TransportHttpBody
import org.apache.http.Consts
import org.apache.http.HttpStatus
import org.apache.http.client.HttpClient
import org.apache.http.client.config.RequestConfig
import org.apache.http.client.methods.HttpUriRequest
import org.apache.http.client.methods.RequestBuilder
import org.apache.http.entity.AbstractHttpEntity
import org.apache.http.entity.ContentType
import org.apache.http.entity.InputStreamEntity
import org.apache.http.impl.client.HttpClientBuilder
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager
import org.apache.http.message.BasicNameValuePair
import org.apache.http.util.EntityUtils
import java.io.IOException

private const val DEFAULT_CONNECTION_TIMEOUT = 10_000 // 10 sec
private const val DEFAULT_READ_TIMEOUT = 60_000 // 1 min

private const val DEFAULT_MAX_CONNECTIONS = 10


class ApacheCommonsHttpClientTransport : HttpTransport {

	private val httpClient: HttpClient

	init {
		val connectionManager = PoolingHttpClientConnectionManager().apply {
			maxTotal = DEFAULT_MAX_CONNECTIONS
			defaultMaxPerRoute = DEFAULT_MAX_CONNECTIONS
		}

		val requestConfig = RequestConfig.custom()
				.setConnectTimeout(DEFAULT_CONNECTION_TIMEOUT)
				.setConnectionRequestTimeout(DEFAULT_CONNECTION_TIMEOUT)
				.setSocketTimeout(DEFAULT_READ_TIMEOUT)
				.build()

		httpClient = HttpClientBuilder.create()
				.setConnectionManager(connectionManager)
				.setDefaultRequestConfig(requestConfig)
				// TODO .setRetryHandler()
				// TODO .setServiceUnavailableRetryStrategy()
				.build()
	}

	override fun makeHttpRequest(httpRequest: HttpRequest): HttpResponse {
		val request = toHttpUriRequest(httpRequest)
		return try {
			httpClient.execute(request) { response ->
				val statusLine = response.statusLine
				val responseBytes = EntityUtils.toByteArray(response.entity)
				if (statusLine.statusCode != HttpStatus.SC_OK) {
					HttpResponse.Error(statusLine.statusCode, statusLine.reasonPhrase, responseBytes)
				} else {
					HttpResponse.Success(responseBytes)
				}
			}
		} catch (e: IOException) {
			HttpResponse.TransportError(e)
		}
	}

	private fun toHttpUriRequest(httpRequest: HttpRequest): HttpUriRequest {
		val requestBuilder = when (httpRequest) {
			is HttpRequest.HttpGetRequest -> {
				RequestBuilder.get(httpRequest.uri)
			}
			is HttpRequest.HttpPostRequest -> {
				RequestBuilder
						.post(httpRequest.uri)
						.setEntity(httpRequest.transportHttpBody.toEntity())
			}
			is HttpRequest.HttpPutRequest -> {
				RequestBuilder
						.put(httpRequest.uri)
						.setEntity(httpRequest.transportHttpBody.toEntity())
			}
			is HttpRequest.HttpDeleteRequest -> {
				RequestBuilder.delete(httpRequest.uri)
			}
		}
		return requestBuilder
				.addParameters(*createNameValuePairs(httpRequest.params))
				.build()
	}

}

private fun createNameValuePairs(params: Map<String, String>): Array<BasicNameValuePair> {
	return params
			.map { (name, value) -> BasicNameValuePair(name, value) }
			.toTypedArray()
}

private fun String.toContentType(): ContentType = ContentType.create(this, Consts.UTF_8)

private fun TransportHttpBody.toEntity(): AbstractHttpEntity? = when (this) {
	is TransportHttpBody.EmptyBody ->
		null
	is TransportHttpBody.InputStreamBody ->
		InputStreamEntity(stream, mimeType.toContentType())
}
