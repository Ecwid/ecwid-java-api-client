package com.ecwid.apiclient.v3.dto.extrafield.result

import com.ecwid.apiclient.v3.dto.common.ApiResultDTO

data class CustomersConfigsSearchResult(
	val items: List<FetchedCustomersConfig> = listOf(),
) : ApiResultDTO
