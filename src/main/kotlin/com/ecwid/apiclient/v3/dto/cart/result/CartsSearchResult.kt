package com.ecwid.apiclient.v3.dto.cart.result

import com.ecwid.apiclient.v3.dto.common.ApiResultDTO

data class CartsSearchResult(
		val total: Int = 0,
		val count: Int = 0,
		val offset: Int = 0,
		val limit: Int = 0,
		val items: List<FetchedCart> = listOf()
) : ApiResultDTO
