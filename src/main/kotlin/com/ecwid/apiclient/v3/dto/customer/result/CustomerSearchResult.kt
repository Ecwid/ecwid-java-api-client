package com.ecwid.apiclient.v3.dto.customer.result

data class CustomersSearchResult(
		var items: List<FetchedCustomer> = listOf(),
		var count: Int = 0,
		var total: Int = 0,
		var limit: Int = 0,
		var offset: Int = 0
)