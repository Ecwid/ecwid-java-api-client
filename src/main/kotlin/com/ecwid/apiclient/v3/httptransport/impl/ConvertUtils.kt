package com.ecwid.apiclient.v3.httptransport.impl

import com.ecwid.apiclient.v3.httptransport.HttpRequest
import com.ecwid.apiclient.v3.httptransport.HttpResponse
import com.ecwid.apiclient.v3.httptransport.TransportHttpBody
import org.apache.http.Consts
import org.apache.http.HttpEntity
import org.apache.http.HttpStatus
import org.apache.http.client.methods.HttpUriRequest
import org.apache.http.client.methods.RequestBuilder
import org.apache.http.entity.*
import org.apache.http.message.BasicNameValuePair
import org.apache.http.util.EntityUtils

internal fun HttpRequest.toHttpUriRequest(): HttpUriRequest {
	val requestBuilder = when (this) {
		is HttpRequest.HttpGetRequest -> {
			RequestBuilder.get(uri)
		}
		is HttpRequest.HttpPostRequest -> {
			RequestBuilder
				.post(uri)
				.setEntity(transportHttpBody.toEntity())
		}
		is HttpRequest.HttpPutRequest -> {
			RequestBuilder
				.put(uri)
				.setEntity(transportHttpBody.toEntity())
		}
		is HttpRequest.HttpDeleteRequest -> {
			RequestBuilder.delete(uri)
		}
	}
	return requestBuilder
		.apply {
			charset = Charsets.UTF_8
			params.map { (name, value) ->
				BasicNameValuePair(name, value)
			}.forEach(this::addParameter)
		}
		.build()
}

internal fun org.apache.http.HttpResponse.toApiResponse(): HttpResponse {
	val statusLine = statusLine
	val responseBytes = EntityUtils.toByteArray(entity)
	return if (statusLine.statusCode == HttpStatus.SC_OK) {
		HttpResponse.Success(responseBytes)
	} else {
		HttpResponse.Error(statusLine.statusCode, statusLine.reasonPhrase, responseBytes)
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

private fun String.toContentType(): ContentType = ContentType.create(this, Consts.UTF_8)
