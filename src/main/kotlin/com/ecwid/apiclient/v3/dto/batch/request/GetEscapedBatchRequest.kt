package com.ecwid.apiclient.v3.dto.batch.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.impl.RequestInfo
import com.ecwid.apiclient.v3.responsefields.ResponseFields

data class GetEscapedBatchRequest(
	val ticket: String = ""
) : ApiRequest {
	override fun toRequestInfo() = RequestInfo.createGetRequest(
		pathSegments = listOf(
			"batch"
		),
		params = mapOf(
			"ticket" to ticket,
			"escapedJson" to "true"
		),
		// No responseFields support in batch requests
		responseFields = ResponseFields.All
	)
}
