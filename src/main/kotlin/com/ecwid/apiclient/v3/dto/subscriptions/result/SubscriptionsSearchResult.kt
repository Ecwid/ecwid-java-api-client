package com.ecwid.apiclient.v3.dto.subscriptions.result

import com.ecwid.apiclient.v3.dto.common.ApiResultDTO

data class SubscriptionsSearchResult(
	val total: Int = 0,
	val count: Int = 0,
	val offset: Int = 0,
	val limit: Int = 0,
	val items: List<FetchedSubscription> = listOf()
) : ApiResultDTO
