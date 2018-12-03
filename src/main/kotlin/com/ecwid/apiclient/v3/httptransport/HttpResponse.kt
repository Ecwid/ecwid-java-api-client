package com.ecwid.apiclient.v3.httptransport

internal sealed class HttpResponse(val responseBody: String) {
	class Success(responseBody: String): HttpResponse(responseBody)
	class Error(val statusCode: Int, val reasonPhrase: String, responseBody: String): HttpResponse(responseBody)
	class TransportError(val exception: Exception): HttpResponse("")
}