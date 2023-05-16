package com.ecwid.apiclient.v3.dto.instantsite.redirects.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.httptransport.HttpBody
import com.ecwid.apiclient.v3.impl.RequestInfo

data class InstantSiteRedirectListCreateRequest (
	val updatedRedirects: List<UpdatedInstantSiteRedirect> = emptyList(),
) : ApiRequest {
	override fun toRequestInfo() = RequestInfo.createPostRequest(
		pathSegments = listOf(
			"instant-site",
			"redirects",
		),
		httpBody = HttpBody.JsonBody(
			obj = updatedRedirects
		)
	)
}
