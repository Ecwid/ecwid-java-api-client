package com.ecwid.apiclient.v3.httptransport

internal sealed class HttpResponse(val responseBytes: ByteArray) {
	class Success(responseBytes: ByteArray): HttpResponse(responseBytes)
	class Error(val statusCode: Int, val reasonPhrase: String, responseBytes: ByteArray): HttpResponse(responseBytes)
	class TransportError(val exception: Exception): HttpResponse(ByteArray(0))
}