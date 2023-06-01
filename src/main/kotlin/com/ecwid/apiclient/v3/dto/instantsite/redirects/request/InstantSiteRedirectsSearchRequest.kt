package com.ecwid.apiclient.v3.dto.instantsite.redirects.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.impl.RequestInfo
import com.ecwid.apiclient.v3.responsefields.ResponseFields

data class InstantSiteRedirectsSearchRequest(
	val keyword: String? = null,
	val offset: Int? = null,
	val limit: Int? = null,
	val responseFields: ResponseFields = ResponseFields.All,
) : ApiRequest {
	override fun toRequestInfo() = RequestInfo.createGetRequest(
		pathSegments = listOf(
			"instant-site",
			"redirects"
		),
		params = toParams(),
		responseFields = responseFields,
	)

	private fun toParams(): Map<String, String> {
		return mutableMapOf<String, String>().apply {
			keyword?.let { put("keyword", it) }
			offset?.let { put("offset", it.toString()) }
			limit?.let { put("limit", it.toString()) }
		}
	}
}
