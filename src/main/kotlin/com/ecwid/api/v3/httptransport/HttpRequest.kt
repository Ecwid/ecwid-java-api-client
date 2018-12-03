package com.ecwid.api.v3.httptransport

sealed class HttpRequest(val uri: String, val params: Map<String, String>, val httpBody: HttpBody) {
	class HttpGetRequest(uri: String, params: Map<String, String>): HttpRequest(uri, params, HttpBody.EmptyBody())
	class HttpPostRequest(uri: String, params: Map<String, String>, httpBody: HttpBody): HttpRequest(uri, params, httpBody)
	class HttpPutRequest(uri: String, params: Map<String, String>, httpBody: HttpBody): HttpRequest(uri, params, httpBody)
	class HttpDeleteRequest(uri: String, params: Map<String, String>): HttpRequest(uri, params, HttpBody.EmptyBody())
}