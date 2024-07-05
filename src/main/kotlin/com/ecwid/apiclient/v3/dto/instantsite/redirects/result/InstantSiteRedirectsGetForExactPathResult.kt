package com.ecwid.apiclient.v3.dto.instantsite.redirects.result

import com.ecwid.apiclient.v3.dto.common.ApiResultDTO

data class InstantSiteRedirectsGetForExactPathResult(
	val items: List<FetchedInstantSiteRedirect> = listOf(),
	val hasAnyRedirects: Boolean = true,
) : ApiResultDTO
