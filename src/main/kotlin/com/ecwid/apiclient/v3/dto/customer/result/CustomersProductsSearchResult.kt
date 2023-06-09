package com.ecwid.apiclient.v3.dto.customer.result

import com.ecwid.apiclient.v3.dto.common.ApiResultDTO

data class CustomersProductsSearchResult(
	val items: List<FetchedCustomersProducts> = listOf(),
) : ApiResultDTO
