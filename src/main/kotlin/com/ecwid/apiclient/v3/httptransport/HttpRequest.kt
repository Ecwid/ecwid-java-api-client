package com.ecwid.apiclient.v3.httptransport

sealed class HttpRequest(val method: String, val uri: String, val params: Map<String, String>, val httpBody: HttpBody) {
	class HttpGetRequest(uri: String, params: Map<String, String>): HttpRequest("GET", uri, params, HttpBody.EmptyBody())
	class HttpPostRequest(uri: String, params: Map<String, String>, httpBody: HttpBody): HttpRequest("POST", uri, params, httpBody)
	class HttpPutRequest(uri: String, params: Map<String, String>, httpBody: HttpBody): HttpRequest("PUT", uri, params, httpBody)
	class HttpDeleteRequest(uri: String, params: Map<String, String>): HttpRequest("DELETE", uri, params, HttpBody.EmptyBody())
}