package com.ecwid.apiclient.v3.httptransport

sealed class HttpRequest(
	val method: String,
	val uri: String,
	val params: Map<String, String>,
	val transportHttpBody: TransportHttpBody,
	val headers: Map<String, String>,
) {
	class HttpGetRequest(uri: String, params: Map<String, String>, headers: Map<String, String>) :
		HttpRequest("GET", uri, params, TransportHttpBody.EmptyBody, headers)

	class HttpPostRequest(uri: String, params: Map<String, String>, transportHttpBody: TransportHttpBody, headers: Map<String, String>) :
		HttpRequest("POST", uri, params, transportHttpBody, headers)

	class HttpPutRequest(uri: String, params: Map<String, String>, transportHttpBody: TransportHttpBody, headers: Map<String, String>) :
		HttpRequest("PUT", uri, params, transportHttpBody, headers)

	class HttpDeleteRequest(uri: String, params: Map<String, String>, headers: Map<String, String>) :
		HttpRequest("DELETE", uri, params, TransportHttpBody.EmptyBody, headers)
}
