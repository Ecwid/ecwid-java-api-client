package com.ecwid.apiclient.v3.dto.customer.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.impl.RequestInfo
import java.util.*

data class CustomersSearchRequest(
		var keyword: String? = null,
		var name: String? = null,
		var email: String? = null,
		var customerGroupId: Int? = null,
		var minOrderCount: Int? = null,
		var maxOrderCount: Int? = null,
		var createdFrom: Date? = null,
		var createdTo: Date? = null,
		var updatedFrom: Date? = null,
		var updatedTo: Date? = null,
		var sortBy: SortOrder? = null,
		var offset: Int = 0,
		var limit: Int = 100
) : ApiRequest {
	override fun toRequestInfo() = RequestInfo.createGetRequest(
			endpoint = "customers",
			params = toParams()
	)

	enum class SortOrder {
		NAME_ASC,
		NAME_DESC,
		EMAIL_ASC,
		EMAIL_DESC,
		ORDER_COUNT_ASC,
		ORDER_COUNT_DESC,
		REGISTERED_DATE_DESC,
		REGISTERED_DATE_ASC,
		UPDATED_DATE_DESC,
		UPDATED_DATE_ASC
	}

	private fun toParams(): Map<String, String> {
		val request = this
		return mutableMapOf<String, String>().apply {
			request.keyword?.let { put("keyword", it) }
			request.name?.let { put("name", it) }
			request.email?.let { put("email", it) }
			request.customerGroupId?.let { put("customerGroup", it.toString()) }
			request.minOrderCount?.let { put("minOrderCount", it.toString()) }
			request.maxOrderCount?.let { put("maxOrderCount", it.toString()) }
			request.createdFrom?.let { put("createdFrom", (it.time / 1000).toString()) }
			request.createdTo?.let { put("createdTo", (it.time / 1000).toString()) }
			request.updatedFrom?.let { put("updatedFrom", (it.time / 1000).toString()) }
			request.updatedTo?.let { put("updatedTo", (it.time / 1000).toString()) }
			request.sortBy?.let { put("sortBy", it.name) }
			put("offset", request.offset.toString())
			put("limit", request.limit.toString())
		}.toMap()
	}
}
