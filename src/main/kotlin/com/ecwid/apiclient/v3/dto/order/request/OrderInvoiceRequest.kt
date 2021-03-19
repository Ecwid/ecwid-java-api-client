package com.ecwid.apiclient.v3.dto.order.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.impl.RequestInfo

data class OrderInvoiceRequest(
		val orderNumber: Int = 0,
		val orderIdentity: String = ""
) : ApiRequest {
	constructor(orderNumber: Int = 0) : this(orderNumber, orderNumber.toString())

	override fun toRequestInfo() = RequestInfo.createGetRequest(
			pathSegments = listOf(
				"orders",
				orderIdentity,
				"invoice"
			)
	)
}
