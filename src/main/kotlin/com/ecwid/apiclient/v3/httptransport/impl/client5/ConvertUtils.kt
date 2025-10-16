package com.ecwid.apiclient.v3.httptransport.impl.client5

import com.ecwid.apiclient.v3.httptransport.HttpRequest
import com.ecwid.apiclient.v3.httptransport.HttpResponse
import com.ecwid.apiclient.v3.httptransport.TransportHttpBody
import org.apache.hc.core5.http.ClassicHttpRequest
import org.apache.hc.core5.http.ClassicHttpResponse
import org.apache.hc.core5.http.ContentType
import org.apache.hc.core5.http.HttpEntity
import org.apache.hc.core5.http.HttpStatus
import org.apache.hc.core5.http.io.entity.*
import org.apache.hc.core5.http.io.support.ClassicRequestBuilder

internal fun HttpRequest.toHttpUriRequest(): ClassicHttpRequest {
	val requestBuilder = when (this) {
		is HttpRequest.HttpGetRequest -> {
			ClassicRequestBuilder.get(uri)
		}
		is HttpRequest.HttpPostRequest -> {
			ClassicRequestBuilder
				.post(uri)
				.setEntity(transportHttpBody.toEntity())
		}
		is HttpRequest.HttpPutRequest -> {
			ClassicRequestBuilder
				.put(uri)
				.setEntity(transportHttpBody.toEntity())
		}
		is HttpRequest.HttpDeleteRequest -> {
			ClassicRequestBuilder.delete(uri)
		}
	}

	return requestBuilder
		.apply updated@{
			this@updated.charset = Charsets.UTF_8
			this@toHttpUriRequest.params.forEach(this@updated::addParameter)
			this@toHttpUriRequest.headers.forEach(this@updated::addHeader)
		}
		.build()
}

internal fun ClassicHttpResponse.toApiResponse(): HttpResponse {
	val responseBytes = EntityUtils.toByteArray(entity)
	return if (code == HttpStatus.SC_OK) {
		HttpResponse.Success(responseBytes)
	} else {
		HttpResponse.Error(code, reasonPhrase, responseBytes)
	}
}

private fun TransportHttpBody.toEntity(): HttpEntity? = when (this) {
	is TransportHttpBody.EmptyBody ->
		null
	is TransportHttpBody.InputStreamBody ->
		BufferedHttpEntity(InputStreamEntity(stream, mimeType.toContentType()))
	is TransportHttpBody.ByteArrayBody ->
		ByteArrayEntity(byteArray, mimeType.toContentType())
	is TransportHttpBody.LocalFileBody ->
		FileEntity(file, mimeType.toContentType())
}

private fun String.toContentType(): ContentType = ContentType.create(this, Charsets.UTF_8)
