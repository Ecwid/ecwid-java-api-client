package com.ecwid.apiclient.v3.dto.product.result

data class ProductsSearchResult(
		val items: List<FetchedProduct> = listOf(),
		val count: Int = 0,
		val total: Int = 0,
		val limit: Int = 0,
		val offset: Int = 0
)