package com.ecwid.apiclient.v3.dto.instantsite.redirects.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.httptransport.HttpBody
import com.ecwid.apiclient.v3.impl.RequestInfo

data class InstantSiteRedirectListDeleteRequest(
	val redirectListDeleteData: RedirectListDeleteData = RedirectListDeleteData()
) : ApiRequest {
	data class RedirectListDeleteData(
		val ids: List<String> = emptyList()
	)

	override fun toRequestInfo() = RequestInfo.createDeleteRequest(
		pathSegments = listOf(
			"instant-site",
			"redirects",
		),
		httpBody = HttpBody.JsonBody(
			obj = redirectListDeleteData
		)
	)
}
