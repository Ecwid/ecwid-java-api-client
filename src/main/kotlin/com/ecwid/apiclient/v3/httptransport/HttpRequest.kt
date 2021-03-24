package com.ecwid.apiclient.v3.httptransport

import org.apache.http.client.methods.HttpUriRequest
import org.apache.http.client.methods.RequestBuilder
import org.apache.http.message.BasicNameValuePair

sealed class HttpRequest(val method: String, val uri: String, val params: Map<String, String>, val transportHttpBody: TransportHttpBody) {
	class HttpGetRequest(uri: String, params: Map<String, String>) : HttpRequest("GET", uri, params, TransportHttpBody.EmptyBody)
	class HttpPostRequest(uri: String, params: Map<String, String>, transportHttpBody: TransportHttpBody) : HttpRequest("POST", uri, params, transportHttpBody)
	class HttpPutRequest(uri: String, params: Map<String, String>, transportHttpBody: TransportHttpBody) : HttpRequest("PUT", uri, params, transportHttpBody)
	class HttpDeleteRequest(uri: String, params: Map<String, String>) : HttpRequest("DELETE", uri, params, TransportHttpBody.EmptyBody)

	fun toHttpUriRequest(): HttpUriRequest {
		val requestBuilder = when (this) {
			is HttpGetRequest -> {
				RequestBuilder.get(uri)
			}
			is HttpPostRequest -> {
				RequestBuilder
					.post(uri)
					.setEntity(transportHttpBody.toEntity())
			}
			is HttpPutRequest -> {
				RequestBuilder
					.put(uri)
					.setEntity(transportHttpBody.toEntity())
			}
			is HttpDeleteRequest -> {
				RequestBuilder.delete(uri)
			}
		}
		return requestBuilder
			.addParameters(*createNameValuePairs(params))
			.build()
	}

	private fun createNameValuePairs(params: Map<String, String>): Array<BasicNameValuePair> {
		return params
			.map { (name, value) -> BasicNameValuePair(name, value) }
			.toTypedArray()
	}

}
