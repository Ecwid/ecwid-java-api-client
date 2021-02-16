package com.ecwid.apiclient.v3.httptransport.impl

import com.ecwid.apiclient.v3.httptransport.HttpRequest
import com.ecwid.apiclient.v3.httptransport.HttpResponse
import com.ecwid.apiclient.v3.httptransport.HttpTransport
import com.ecwid.apiclient.v3.httptransport.TransportHttpBody
import org.apache.http.Consts
import org.apache.http.Header
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
import java.util.concurrent.TimeUnit

private const val DEFAULT_CONNECTION_TIMEOUT = 10_000 // 10 sec
private const val DEFAULT_READ_TIMEOUT = 60_000 // 1 min

private const val DEFAULT_MAX_CONNECTIONS = 10

/**
 * Number of attempts to retry request if server responded with 429
 */
private const val DEFAULT_RATE_LIMIT_ATTEMPTS = 5
/**
 * Number of seconds to wait until next attempt, if server didn't send Retry-After header
 */
private const val DEFAULT_RATE_LIMIT_RETRY_INTERVAL_SECONDS = 60L
/**
 * Maximal delay in seconds bereore next attempt
 */
private const val MAX_RATE_LIMIT_RETRY_INTERVAL_SECONDS = 120L

class ApacheCommonsHttpClientTransport(
	private val httpClient: HttpClient,
	private val defaultRateLimitAttempts: Int = DEFAULT_RATE_LIMIT_ATTEMPTS,
	private val defaultRateLimitRetryInterval: Long = DEFAULT_RATE_LIMIT_RETRY_INTERVAL_SECONDS,
	private val maxRateLimitRetryInterval: Long = MAX_RATE_LIMIT_RETRY_INTERVAL_SECONDS
) : HttpTransport {

	constructor(
		defaultConnectionTimeout: Int = DEFAULT_CONNECTION_TIMEOUT,
		defaultReadTimeout: Int = DEFAULT_READ_TIMEOUT,
		defaultMaxConnections: Int = DEFAULT_MAX_CONNECTIONS,
		defaultRateLimitAttempts: Int = DEFAULT_RATE_LIMIT_ATTEMPTS,
		defaultRateLimitRetryInterval: Long = DEFAULT_RATE_LIMIT_RETRY_INTERVAL_SECONDS,
		maxRateLimitRetryInterval: Long = MAX_RATE_LIMIT_RETRY_INTERVAL_SECONDS,
		defaultHeaders: List<Header> = emptyList()
	) : this(
		httpClient = buildHttpClient(
			defaultConnectionTimeout = defaultConnectionTimeout,
			defaultReadTimeout = defaultReadTimeout,
			defaulMaxConnections = defaultMaxConnections,
			defaultHeaders = defaultHeaders
		),
		defaultRateLimitAttempts = defaultRateLimitAttempts,
		defaultRateLimitRetryInterval = defaultRateLimitRetryInterval,
		maxRateLimitRetryInterval = maxRateLimitRetryInterval
	)

	override fun makeHttpRequest(httpRequest: HttpRequest): HttpResponse {
		val request = toHttpUriRequest(httpRequest)
		return try {
			doExecute(request)
		} catch (e: IOException) {
			HttpResponse.TransportError(e)
		}
	}

	private fun doExecute(request: HttpUriRequest, attemptsLeft: Int = defaultRateLimitAttempts): HttpResponse {
		return httpClient.execute(request) { response ->
			val statusLine = response.statusLine
			val responseBytes = EntityUtils.toByteArray(response.entity)
			if (statusLine.statusCode == HttpStatus.SC_OK) {
				HttpResponse.Success(responseBytes)
			} else if (statusLine.statusCode == 429 && attemptsLeft > 0) {
				// server should reply, how long to wait before retry
				val waitInterval = response.getFirstHeader("Retry-After")?.value?.toLong()
					?: defaultRateLimitRetryInterval
				if (waitInterval <= maxRateLimitRetryInterval) {
					// if servers says to wait acceptable time - we'll wait and retry
					TimeUnit.SECONDS.sleep(waitInterval)
					doExecute(request, attemptsLeft - 1) // рекурсивно декрементим остаток попыток
				} else {
					// if server says we need to wait too long, we just don't
					HttpResponse.Error(statusLine.statusCode, statusLine.reasonPhrase, responseBytes)
				}
			} else {
				HttpResponse.Error(statusLine.statusCode, statusLine.reasonPhrase, responseBytes)
			}
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

	companion object {
		private fun buildHttpClient(
			defaultConnectionTimeout: Int,
			defaultReadTimeout: Int,
			defaulMaxConnections: Int,
			defaultHeaders: List<Header>
		): HttpClient {
			val connectionManager = PoolingHttpClientConnectionManager().apply {
				maxTotal = defaulMaxConnections
				defaultMaxPerRoute = defaulMaxConnections
			}

			val requestConfig = RequestConfig.custom()
				.setConnectTimeout(defaultConnectionTimeout)
				.setConnectionRequestTimeout(defaultConnectionTimeout)
				.setSocketTimeout(defaultReadTimeout)
				.build()

			val httpClientBuilder = HttpClientBuilder.create()
				.setConnectionManager(connectionManager)
				.setDefaultRequestConfig(requestConfig)
			// TODO .setRetryHandler()
			// TODO .setServiceUnavailableRetryStrategy()
			if (defaultHeaders.isNotEmpty()) {
				httpClientBuilder.setDefaultHeaders(defaultHeaders)
			}
			return httpClientBuilder.build()
		}
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
