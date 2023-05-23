package com.ecwid.apiclient.v3.dto.instantsite.redirects.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.impl.RequestInfo

data class InstantSiteRedirectDeleteRequest(
	val redirectId: String = "",
) : ApiRequest {
	override fun toRequestInfo() = RequestInfo.createDeleteRequest(
		pathSegments = listOf(
			"instant-site",
			"redirects",
			redirectId
		),
	)
}
