package com.ecwid.apiclient.v3.impl

import com.ecwid.apiclient.v3.httptransport.HttpBody

class RequestInfo private constructor(
	val pathSegments: List<String>,
	val method: HttpMethod,
	val params: Map<String, String>,
	val httpBody: HttpBody,
	val headers: Map<String, String>
) {
	companion object {

		fun createGetRequest(
			pathSegments: List<String>,
			params: Map<String, String> = mapOf(),
			headers: Map<String, String> = mapOf()
		) = RequestInfo(
			pathSegments = pathSegments,
			method = HttpMethod.GET,
			params = params,
			httpBody = HttpBody.EmptyBody,
			headers = headers
		)

		fun createDeleteRequest(
			pathSegments: List<String>,
			params: Map<String, String> = mapOf(),
			headers: Map<String, String> = mapOf()
		) = RequestInfo(
			pathSegments = pathSegments,
			method = HttpMethod.DELETE,
			params = params,
			httpBody = HttpBody.EmptyBody,
			headers = headers
		)

		fun createPostRequest(
			pathSegments: List<String>,
			params: Map<String, String> = mapOf(),
			httpBody: HttpBody,
			headers: Map<String, String> = mapOf()
		) = RequestInfo(
			pathSegments = pathSegments,
			method = HttpMethod.POST,
			params = params,
			httpBody = httpBody,
			headers = headers
		)

		fun createPutRequest(
			pathSegments: List<String>,
			params: Map<String, String> = mapOf(),
			httpBody: HttpBody,
			headers: Map<String, String> = mapOf()
		) = RequestInfo(
			pathSegments = pathSegments,
			method = HttpMethod.PUT,
			params = params,
			httpBody = httpBody,
			headers = headers
		)

	}

}

enum class HttpMethod {
	GET,
	POST,
	PUT,
	DELETE
}
