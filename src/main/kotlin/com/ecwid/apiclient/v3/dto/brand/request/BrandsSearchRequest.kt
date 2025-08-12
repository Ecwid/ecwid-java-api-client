package com.ecwid.apiclient.v3.dto.brand.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.dto.common.PagingRequest
import com.ecwid.apiclient.v3.impl.RequestInfo
import com.ecwid.apiclient.v3.responsefields.ResponseFields

sealed class BrandsSearchRequest : ApiRequest {

	data class ByFilters(
		val limit: Int = 100,
		override val offset: Int = 0,
		val lang: String? = null,
		val hiddenBrands: Boolean? = null,
		val baseUrl: String? = null,
		val cleanUrls: Boolean? = null,
		val sortBy: SortOrder? = null,
		val responseFields: ResponseFields = ResponseFields.All,
	) : BrandsSearchRequest(), PagingRequest<ByFilters> {
		override fun toRequestInfo() = RequestInfo.createGetRequest(
			pathSegments = listOf(
				"brands",
			),
			params = toParams(),
			responseFields = responseFields,
		)

		private fun toParams(): Map<String, String> {
			val request = this
			return mutableMapOf<String, String>().apply {
				put("limit", request.limit.toString())
				put("offset", request.offset.toString())
				request.lang?.let { put("lang", it) }
				request.hiddenBrands?.let { put("hiddenBrands", it.toString()) }
				request.baseUrl?.let { put("baseUrl", it) }
				request.cleanUrls?.let { put("cleanUrls", it.toString()) }
				request.sortBy?.let { put("sortBy", it.name) }
			}.toMap()
		}

		override fun copyWithOffset(offset: Int) = copy(offset = offset)
	}

	@Suppress("unused")
	enum class SortOrder {
		PRODUCT_COUNT_DESC,
		NAME_ASC,
	}
}
