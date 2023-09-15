package com.ecwid.apiclient.v3.dto.batch.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.httptransport.HttpBody
import com.ecwid.apiclient.v3.impl.RequestInfo

data class CancelBatchGroupRequest(
	val groupId: String = ""
) : ApiRequest {
	override fun toRequestInfo() = RequestInfo.createPostRequest(
		pathSegments = listOf(
			"batch",
			"cancel-group",
		),
		params = mapOf(
			"groupId" to groupId,
		),
		httpBody = HttpBody.EmptyBody,
	)
}
