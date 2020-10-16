package com.ecwid.apiclient.v3.dto.order.result

import java.util.*

data class DeletedOrder(
		var id: Int = 0,
		var orderId: String? = null,
		var date: Date = Date()
)
