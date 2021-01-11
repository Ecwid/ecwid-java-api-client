package com.ecwid.apiclient.v3.dto.customer.result

data class CustomersSearchResult(
		val items: List<FetchedCustomer> = listOf(),
		val count: Int = 0,
		val total: Int = 0,
		val limit: Int = 0,
		val offset: Int = 0
)
