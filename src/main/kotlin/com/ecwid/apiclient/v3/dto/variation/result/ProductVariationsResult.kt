package com.ecwid.apiclient.v3.dto.variation.result

data class ProductVariationsResult(
		val items: List<FetchedVariation> = listOf(),
		val count: Int = 0,
		val total: Int = 0,
		val limit: Int = 0,
		val offset: Int = 0
)