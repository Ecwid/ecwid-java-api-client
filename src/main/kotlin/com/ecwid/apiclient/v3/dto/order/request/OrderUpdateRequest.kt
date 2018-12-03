package com.ecwid.apiclient.v3.dto.order.request

data class OrderUpdateRequest(
		var orderNumber: Int = 0,
		var updatedOrder: UpdatedOrder = UpdatedOrder()
)