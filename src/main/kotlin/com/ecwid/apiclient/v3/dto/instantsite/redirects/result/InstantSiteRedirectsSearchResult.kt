package com.ecwid.apiclient.v3.dto.instantsite.redirects.result

import com.ecwid.apiclient.v3.dto.common.ApiResultDTO

data class InstantSiteRedirectsSearchResult(
	val items: List<FetchedInstantSiteRedirect> = listOf(),
	val count: Int = 0,
	val total: Int = 0,
	val limit: Int = 0,
	val offset: Int = 0
) : ApiResultDTO
