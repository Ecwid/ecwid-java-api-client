package com.ecwid.apiclient.v3.dto.instantsite.redirects.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.impl.RequestInfo
import com.ecwid.apiclient.v3.responsefields.ResponseFields

data class InstantSiteRedirectGetRequest(
	val redirectId: String = "",
	val responseFields: ResponseFields = ResponseFields.All,
) : ApiRequest {
	override fun toRequestInfo() = RequestInfo.createGetRequest(
		pathSegments = listOf(
			"instant-site",
			"redirects",
			redirectId,
		),
		responseFields = responseFields,
	)
}
