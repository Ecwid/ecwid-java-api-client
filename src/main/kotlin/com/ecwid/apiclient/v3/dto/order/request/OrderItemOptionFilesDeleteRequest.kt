package com.ecwid.apiclient.v3.dto.order.request

data class OrderItemOptionFilesDeleteRequest(
		var orderNumber: Int = 0,
		var orderItemId: Int = 0,
		var optionName: String = ""
)