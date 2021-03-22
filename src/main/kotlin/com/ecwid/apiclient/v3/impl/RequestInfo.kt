package com.ecwid.apiclient.v3.impl

import com.ecwid.apiclient.v3.httptransport.HttpBody

class RequestInfo private constructor(
	@Deprecated("You should use parameter pathSegments instead")
	val endpoint: String? = null,
	val pathSegments: List<String>? = null,
	val method: HttpMethod,
	val params: Map<String, String>,
	val httpBody: HttpBody
) {
	companion object {

		@Deprecated("You should use overload with pathSegments argument instead")
		fun createGetRequest(endpoint: String, params: Map<String, String> = mapOf()): RequestInfo {
			return RequestInfo(endpoint, null, HttpMethod.GET, params, HttpBody.EmptyBody)
		}

		fun createGetRequest(pathSegments: List<String>, params: Map<String, String> = mapOf()): RequestInfo {
			return RequestInfo(null, pathSegments, HttpMethod.GET, params, HttpBody.EmptyBody)
		}

		@Deprecated("You should use overload with pathSegments argument instead")
		fun createDeleteRequest(endpoint: String, params: Map<String, String> = mapOf()): RequestInfo {
			return RequestInfo(endpoint, null, HttpMethod.DELETE, params, HttpBody.EmptyBody)
		}

		fun createDeleteRequest(pathSegments: List<String>, params: Map<String, String> = mapOf()): RequestInfo {
			return RequestInfo(null, pathSegments, HttpMethod.DELETE, params, HttpBody.EmptyBody)
		}

		@Deprecated("You should use overload with pathSegments argument instead")
		fun createPostRequest(endpoint: String, params: Map<String, String> = mapOf(), httpBody: HttpBody): RequestInfo {
			return RequestInfo(endpoint, null, HttpMethod.POST, params, httpBody)
		}

		fun createPostRequest(pathSegments: List<String>, params: Map<String, String> = mapOf(), httpBody: HttpBody): RequestInfo {
			return RequestInfo(null, pathSegments, HttpMethod.POST, params, httpBody)
		}

		@Deprecated("You should use overload with pathSegments argument instead")
		fun createPutRequest(endpoint: String, params: Map<String, String> = mapOf(), httpBody: HttpBody): RequestInfo {
			return RequestInfo(endpoint, null, HttpMethod.PUT, params, httpBody)
		}

		fun createPutRequest(pathSegments: List<String>, params: Map<String, String> = mapOf(), httpBody: HttpBody): RequestInfo {
			return RequestInfo(null, pathSegments, HttpMethod.PUT, params, httpBody)
		}

	}

}

enum class HttpMethod {
	GET,
	POST,
	PUT,
	DELETE
}
