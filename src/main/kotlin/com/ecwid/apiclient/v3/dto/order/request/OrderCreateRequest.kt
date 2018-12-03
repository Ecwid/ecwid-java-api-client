package com.ecwid.apiclient.v3.dto.order.request

data class OrderCreateRequest(
		val newOrder: UpdatedOrder = UpdatedOrder()
)