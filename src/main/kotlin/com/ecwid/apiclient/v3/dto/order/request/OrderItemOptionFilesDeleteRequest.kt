package com.ecwid.apiclient.v3.dto.order.request

data class OrderItemOptionFilesDeleteRequest(
		val orderNumber: Int = 0,
		val orderItemId: Int = 0,
		val optionName: String = ""
)