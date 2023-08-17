package com.ecwid.apiclient.v3.dto.customer.result

import com.ecwid.apiclient.v3.dto.common.ApiResultDTO

data class CustomersFiltersDataSearchResult(
	val total: Int = 0,
	val customerGroups: List<CustomerFilterGroup> = listOf(),
	val shippingAddresses: List<CustomerFilterShippingAddress> = listOf(),
	val languages: List<String> = listOf(),
) : ApiResultDTO
