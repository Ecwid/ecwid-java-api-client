package com.ecwid.apiclient.v3.httptransport

sealed class HttpRequest(val method: String, val uri: String, val params: Map<String, String>, val transportHttpBody: TransportHttpBody) {
	class HttpGetRequest(uri: String, params: Map<String, String>) : HttpRequest("GET", uri, params, TransportHttpBody.EmptyBody)
	class HttpPostRequest(uri: String, params: Map<String, String>, transportHttpBody: TransportHttpBody) : HttpRequest("POST", uri, params, transportHttpBody)
	class HttpPutRequest(uri: String, params: Map<String, String>, transportHttpBody: TransportHttpBody) : HttpRequest("PUT", uri, params, transportHttpBody)
	class HttpDeleteRequest(uri: String, params: Map<String, String>) : HttpRequest("DELETE", uri, params, TransportHttpBody.EmptyBody)
}
