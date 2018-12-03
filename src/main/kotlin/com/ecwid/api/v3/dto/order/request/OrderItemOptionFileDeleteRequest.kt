package com.ecwid.api.v3.dto.order.request

data class OrderItemOptionFileDeleteRequest(
		val orderNumber: Int = 0,
		val orderItemId: Int = 0,
		val optionName: String = "",
		val fileId: Int = 0
)