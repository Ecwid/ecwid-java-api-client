package com.ecwid.apiclient.v3.dto.order.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.httptransport.HttpBody
import com.ecwid.apiclient.v3.impl.RequestInfo

data class OrderUpdateRequest(
		var orderNumber: Int = 0,
		var updatedOrder: UpdatedOrder = UpdatedOrder()
) : ApiRequest {
	override fun toRequestInfo() = RequestInfo.createPutRequest(
			endpoint = "orders/$orderNumber",
			httpBody = HttpBody.JsonBody(
					obj = updatedOrder
			)
	)
}