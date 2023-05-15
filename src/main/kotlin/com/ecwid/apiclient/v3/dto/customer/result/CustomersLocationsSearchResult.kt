package com.ecwid.apiclient.v3.dto.customer.result

import com.ecwid.apiclient.v3.dto.common.ApiResultDTO

data class CustomersLocationsSearchResult(
	val items: List<FetchedCustomersLocations> = listOf()
) : ApiResultDTO
