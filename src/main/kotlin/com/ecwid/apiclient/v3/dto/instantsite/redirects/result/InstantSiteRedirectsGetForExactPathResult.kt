package com.ecwid.apiclient.v3.dto.instantsite.redirects.result

data class InstantSiteRedirectsGetForExactPathResult(
	val items: List<FetchedInstantSiteRedirect> = listOf(),
	var hasAnyRedirects: Boolean = true,
)
