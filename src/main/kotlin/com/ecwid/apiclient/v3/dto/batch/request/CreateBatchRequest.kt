package com.ecwid.apiclient.v3.dto.batch.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.dto.common.ApiRequestDTO
import com.ecwid.apiclient.v3.httptransport.HttpBody
import com.ecwid.apiclient.v3.impl.RequestInfo
import com.ecwid.apiclient.v3.util.buildEndpointPath
import com.ecwid.apiclient.v3.util.buildQueryString
import java.lang.IllegalArgumentException

data class CreateBatchRequestWithIds(
		val requests: Map<String, ApiRequest> = emptyMap(),
		val stopOnFirstFailure: Boolean = true,
		val extraParams: Map<String, String> = emptyMap()
) : ApiRequest {

	override fun toRequestInfo() = RequestInfo.createPostRequest(
			pathSegments = listOf(
					"batch"
			),
			params = extraParams + mapOf(
					"stopOnFirstFailure" to stopOnFirstFailure.toString()
			),
			httpBody = HttpBody.JsonBody(
					obj = requests.map { (id, request) ->
						SingleBatchRequest.create(id, request)
					}
			)
	)
}

data class CreateBatchRequest(
		val requests: List<ApiRequest> = emptyList(),
		val stopOnFirstFailure: Boolean = true,
		val extraParams: Map<String, String> = emptyMap()
) : ApiRequest {

	override fun toRequestInfo() = RequestInfo.createPostRequest(
			pathSegments = listOf(
					"batch"
			),
			params = extraParams + mapOf(
					"stopOnFirstFailure" to stopOnFirstFailure.toString()
			),
			httpBody = HttpBody.JsonBody(
					obj = requests.map(SingleBatchRequest.Companion::create)
			)
	)
}

private data class SingleBatchRequest(
		val id: String? = null,
		val path: String = "",
		val method: String = "",
		val body: Any? = null
) : ApiRequestDTO {

	companion object {

		internal fun create(apiRequest: ApiRequest): SingleBatchRequest {
			return create(null, apiRequest)
		}

		internal fun create(id: String?, apiRequest: ApiRequest): SingleBatchRequest {
			val requestInfo = apiRequest.toRequestInfo()
			val path = buildEndpointPath(requestInfo.pathSegments)
			val queryString = buildQueryString(requestInfo.params)
			return SingleBatchRequest(
					id = id,
					method = requestInfo.method.name,
					path = path + queryString,
					body = when (requestInfo.httpBody) {
						is HttpBody.EmptyBody -> {
							null
						}
						is HttpBody.JsonBody -> {
							requestInfo.httpBody.obj
						}
						is HttpBody.ByteArrayBody,
						is HttpBody.InputStreamBody,
						is HttpBody.LocalFileBody -> {
							throw IllegalStateException("Request type ${requestInfo.httpBody.javaClass.simpleName} is not allowed in batch requests")
						}
					}
			)
		}

	}

}

