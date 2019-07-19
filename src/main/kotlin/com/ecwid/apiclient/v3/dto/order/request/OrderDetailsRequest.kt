package com.ecwid.apiclient.v3.dto.order.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.impl.RequestInfo

data class OrderDetailsRequest(
		var orderNumber: Int = 0
) : ApiRequest {
	override fun toRequestInfo() = RequestInfo.createGetRequest(
			endpoint = "orders/$orderNumber"
	)
}