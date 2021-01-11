package com.ecwid.apiclient.v3.dto.order.result

data class OrdersSearchResult(
		val items: List<FetchedOrder> = listOf(),
		val count: Int = 0,
		val total: Int = 0,
		val limit: Int = 0,
		val offset: Int = 0
)
