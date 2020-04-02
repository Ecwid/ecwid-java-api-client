package com.ecwid.apiclient.v3.dto.cart.result

data class CartsSearchResult(
		var total: Int = 0,
		var count: Int = 0,
		var offset: Int = 0,
		var limit: Int = 0,
		var items: List<FetchedCart> = listOf()
)
