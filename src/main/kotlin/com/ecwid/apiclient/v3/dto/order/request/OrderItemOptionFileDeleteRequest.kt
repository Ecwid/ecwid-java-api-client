package com.ecwid.apiclient.v3.dto.order.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.impl.RequestInfo

data class OrderItemOptionFileDeleteRequest(
		var orderNumber: Int = 0,
		var orderItemId: Int = 0,
		var optionName: String = "",
		var fileId: Int = 0
) : ApiRequest {
	override fun toRequestInfo() = RequestInfo.createDeleteRequest(
			endpoint = "orders/$orderNumber/items/$orderItemId/options/$optionName/files/$fileId"
	)
}