package com.ecwid.apiclient.v3.dto.profile.result

import com.ecwid.apiclient.v3.dto.common.ApiResultDTO

data class OrderStatusSettingsSearchResult(
	val items: List<FetchedOrderStatusSettings> = listOf(),
	val limit: Int = 0,
) : ApiResultDTO
