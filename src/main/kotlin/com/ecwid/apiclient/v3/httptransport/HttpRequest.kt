package com.ecwid.apiclient.v3.httptransport

sealed class HttpRequest(
	val method: String,
	open val uri: String,
	open val params: Map<String, String>,
	open val transportHttpBody: TransportHttpBody,
	open val headers: Map<String, String>,
) {
	data class HttpGetRequest(
		override val uri: String,
		override val params: Map<String, String>,
		override val headers: Map<String, String>
	) : HttpRequest("GET", uri, params, TransportHttpBody.EmptyBody, headers)

	data class HttpPostRequest(
		override val uri: String,
		override val params: Map<String, String>,
		override val transportHttpBody: TransportHttpBody,
		override val headers: Map<String, String>
	) : HttpRequest("POST", uri, params, transportHttpBody, headers)

	data class HttpPutRequest(
		override val uri: String,
		override val params: Map<String, String>,
		override val transportHttpBody: TransportHttpBody,
		override val headers: Map<String, String>
	) : HttpRequest("PUT", uri, params, transportHttpBody, headers)

	data class HttpDeleteRequest(
		override val uri: String,
		override val params: Map<String, String>,
		override val transportHttpBody: TransportHttpBody,
		override val headers: Map<String, String>
	) : HttpRequest("DELETE", uri, params, transportHttpBody, headers)
}
