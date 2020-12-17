package com.ecwid.apiclient.v3.dto.order.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.impl.RequestInfo

data class OrderItemOptionFilesDeleteRequest(
		val orderNumber: Int = 0,
		val orderIdentity: String = "",
		val orderItemId: Int = 0,
		val optionName: String = ""
) : ApiRequest {
	constructor(orderNumber: Int = 0,
				orderItemId: Int = 0,
				optionName: String = ""
	) : this(orderNumber, orderNumber.toString(), orderItemId, optionName)

	override fun toRequestInfo() = RequestInfo.createDeleteRequest(
			endpoint = "orders/$orderIdentity/items/$orderItemId/options/$optionName/files"
	)
}
