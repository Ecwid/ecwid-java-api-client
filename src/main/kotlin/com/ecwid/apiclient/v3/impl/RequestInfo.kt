package com.ecwid.apiclient.v3.impl

import com.ecwid.apiclient.v3.httptransport.HttpBody

class RequestInfo private constructor(
	val pathSegments: List<String>,
	val method: HttpMethod,
	val params: Map<String, String>,
	val httpBody: HttpBody
) {
	companion object {

		@Deprecated("You should use overload with pathSegments argument instead")
		fun createGetRequest(endpoint: String, params: Map<String, String> = mapOf()): RequestInfo {
			return RequestInfo(listOf(endpoint), HttpMethod.GET, params, HttpBody.EmptyBody)
		}

		fun createGetRequest(pathSegments: List<String>, params: Map<String, String> = mapOf()): RequestInfo {
			return RequestInfo(pathSegments, HttpMethod.GET, params, HttpBody.EmptyBody)
		}

		@Deprecated("You should use overload with pathSegments argument instead")
		fun createDeleteRequest(endpoint: String, params: Map<String, String> = mapOf()): RequestInfo {
			return RequestInfo(listOf(endpoint), HttpMethod.DELETE, params, HttpBody.EmptyBody)
		}

		fun createDeleteRequest(pathSegments: List<String>, params: Map<String, String> = mapOf()): RequestInfo {
			return RequestInfo(pathSegments, HttpMethod.DELETE, params, HttpBody.EmptyBody)
		}

		@Deprecated("You should use overload with pathSegments argument instead")
		fun createPostRequest(endpoint: String, params: Map<String, String> = mapOf(), httpBody: HttpBody): RequestInfo {
			return RequestInfo(listOf(endpoint), HttpMethod.POST, params, httpBody)
		}

		fun createPostRequest(pathSegments: List<String>, params: Map<String, String> = mapOf(), httpBody: HttpBody): RequestInfo {
			return RequestInfo(pathSegments, HttpMethod.POST, params, httpBody)
		}

		@Deprecated("You should use overload with pathSegments argument instead")
		fun createPutRequest(endpoint: String, params: Map<String, String> = mapOf(), httpBody: HttpBody): RequestInfo {
			return RequestInfo(listOf(endpoint), HttpMethod.PUT, params, httpBody)
		}

		fun createPutRequest(pathSegments: List<String>, params: Map<String, String> = mapOf(), httpBody: HttpBody): RequestInfo {
			return RequestInfo(pathSegments, HttpMethod.PUT, params, httpBody)
		}

	}

}

enum class HttpMethod {
	GET,
	POST,
	PUT,
	DELETE
}
