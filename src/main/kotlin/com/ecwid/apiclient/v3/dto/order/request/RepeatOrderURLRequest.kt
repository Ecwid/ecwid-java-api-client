package com.ecwid.apiclient.v3.dto.order.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.impl.RequestInfo

class RepeatOrderURLRequest (
	val orderNumber: Long = 0
) : ApiRequest {
	override fun toRequestInfo() = RequestInfo.createGetRequest(
		pathSegments = listOf(
			"orders",
			"$orderNumber",
			"repeat"
		)
	)
}
