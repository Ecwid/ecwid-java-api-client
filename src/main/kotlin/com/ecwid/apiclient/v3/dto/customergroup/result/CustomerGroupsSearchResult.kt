package com.ecwid.apiclient.v3.dto.customergroup.result

import com.ecwid.apiclient.v3.dto.common.ApiResultDTO

data class CustomerGroupsSearchResult(
		val items: List<FetchedCustomerGroup> = listOf(),
		val count: Int = 0,
		val total: Int = 0,
		val limit: Int = 0,
		val offset: Int = 0
) : ApiResultDTO
