package com.ecwid.api.v3.dto.order.result

data class OrdersSearchResult(
		var items: List<FetchedOrder> = listOf(),
		var count: Int = 0,
		var total: Int = 0,
		var limit: Int = 0,
		var offset: Int = 0
)
