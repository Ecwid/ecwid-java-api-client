package com.ecwid.api.v3.dto.order.request

data class OrderCreateRequest(
		val newOrder: UpdatedOrder = UpdatedOrder()
)