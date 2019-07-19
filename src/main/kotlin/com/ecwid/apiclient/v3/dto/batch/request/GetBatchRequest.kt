package com.ecwid.apiclient.v3.dto.batch.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.impl.RequestInfo

data class GetBatchRequest(
		val ticket: String,
		val escapedJson: Boolean
) : ApiRequest {
	override fun toRequestInfo() = RequestInfo.createGetRequest(
			endpoint = "batch",
			params = mapOf(
					"ticket" to ticket,
					"escapedJson" to escapedJson.toString()
			)
	)
}
