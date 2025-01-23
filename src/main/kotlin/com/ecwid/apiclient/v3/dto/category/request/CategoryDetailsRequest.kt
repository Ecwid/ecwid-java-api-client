package com.ecwid.apiclient.v3.dto.category.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.impl.RequestInfo
import com.ecwid.apiclient.v3.responsefields.ResponseFields

data class CategoryDetailsRequest(
	val categoryId: Long = 0L,
	val baseUrl: String? = null,
	val cleanUrls: Boolean? = null,
	val slugsWithoutIds: Boolean? = null,
	val lang: String? = null,
	val responseFields: ResponseFields = ResponseFields.All,
) : ApiRequest {
	override fun toRequestInfo() = RequestInfo.createGetRequest(
		pathSegments = listOf(
			"categories",
			"$categoryId"
		),
		params = toParams(),
		responseFields = responseFields,
	)

	private fun toParams(): Map<String, String> {
		val request = this
		return mutableMapOf<String, String>().apply {
			request.baseUrl?.let { put("baseUrl", it) }
			request.cleanUrls?.let { put("cleanUrls", it.toString()) }
			request.slugsWithoutIds?.let { put("slugsWithoutIds", it.toString()) }
			request.lang?.let { put("lang", it) }
		}.toMap()
	}
}
