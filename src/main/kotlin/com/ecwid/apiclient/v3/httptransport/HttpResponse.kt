package com.ecwid.apiclient.v3.httptransport

import org.apache.http.HttpStatus
import org.apache.http.util.EntityUtils

sealed class HttpResponse(val responseBytes: ByteArray) {
	class Success(responseBytes: ByteArray) : HttpResponse(responseBytes)
	class Error(val statusCode: Int, val reasonPhrase: String, responseBytes: ByteArray) : HttpResponse(responseBytes)
	class TransportError(val exception: Exception) : HttpResponse(ByteArray(0))
}

fun org.apache.http.HttpResponse.toApiResponse(): HttpResponse {
	val statusLine = statusLine
	val responseBytes = EntityUtils.toByteArray(entity)
	return if (statusLine.statusCode == HttpStatus.SC_OK) {
		HttpResponse.Success(responseBytes)
	} else {
		HttpResponse.Error(statusLine.statusCode, statusLine.reasonPhrase, responseBytes)
	}
}
