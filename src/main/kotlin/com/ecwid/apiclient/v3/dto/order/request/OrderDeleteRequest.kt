package com.ecwid.apiclient.v3.dto.order.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.impl.RequestInfo

data class OrderDeleteRequest(
		var orderNumber: Int = 0,
		var orderIdentity: String = ""
) : ApiRequest {
	constructor(orderNumber: Int = 0) : this(orderNumber, orderIdentity = orderNumber.toString())

	override fun toRequestInfo() = RequestInfo.createDeleteRequest(
			endpoint = "orders/$orderIdentity"
	)
}
