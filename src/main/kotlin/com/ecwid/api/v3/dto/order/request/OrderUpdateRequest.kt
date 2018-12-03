package com.ecwid.api.v3.dto.order.request

data class OrderUpdateRequest(
		val orderNumber: Int = 0,
		val updatedOrder: UpdatedOrder = UpdatedOrder()
)