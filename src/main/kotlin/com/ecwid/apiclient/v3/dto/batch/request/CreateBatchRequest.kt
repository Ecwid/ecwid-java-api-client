package com.ecwid.apiclient.v3.dto.batch.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.httptransport.HttpBody
import com.ecwid.apiclient.v3.impl.RequestInfo

data class CreateBatchRequest(
		val requests: Map<String, ApiRequest>
) : ApiRequest {

	override fun toRequestInfo() = RequestInfo.createPostRequest(
			endpoint = "batch",
			httpBody = HttpBody.JsonBody(
					obj = requests.map { (id, request) ->
						SingleBatchRequest.create(request, id)
					}
			)
	)
}

@Suppress("unused")
private class SingleBatchRequest private constructor(
		val id: String?,
		val path: String,
		val method: String,
		val body: Any?
) {

	companion object {
		internal fun create(apiRequest: ApiRequest, id: String): SingleBatchRequest {
			val requestInfo = apiRequest.toRequestInfo()
			return SingleBatchRequest(
					id = id,
					method = requestInfo.method.name,
					path = requestInfo.endpoint,
					body = when (requestInfo.httpBody) {
						is HttpBody.EmptyBody -> null
						is HttpBody.JsonBody -> requestInfo.httpBody.obj
						is HttpBody.ByteArrayBody -> TODO()
						is HttpBody.InputStreamBody -> TODO()
						is HttpBody.LocalFileBody -> TODO()
					}
			)
		}
	}

}
