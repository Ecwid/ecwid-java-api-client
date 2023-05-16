package com.ecwid.apiclient.v3.dto.instantsite.redirects.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.httptransport.HttpBody
import com.ecwid.apiclient.v3.impl.RequestInfo

data class InstantSiteRedirectUpdateRequest (
	val redirectId: String = "",
	val updatedRedirect: UpdatedInstantSiteRedirect = UpdatedInstantSiteRedirect(),
) : ApiRequest {
	override fun toRequestInfo() = RequestInfo.createPutRequest(
		pathSegments = listOf(
			"instant-site",
			"redirects",
			redirectId,
		),
		httpBody = HttpBody.JsonBody(
			obj = updatedRedirect
		)
	)
}
