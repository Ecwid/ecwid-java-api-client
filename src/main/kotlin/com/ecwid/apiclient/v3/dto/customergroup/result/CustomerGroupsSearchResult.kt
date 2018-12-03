package com.ecwid.apiclient.v3.dto.customergroup.result

data class CustomerGroupsSearchResult(
		var items: List<FetchedCustomerGroup> = listOf(),
		var count: Int = 0,
		var total: Int = 0,
		var limit: Int = 0,
		var offset: Int = 0
)