package com.ecwid.apiclient.v3.dto.customer.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.impl.RequestInfo
import com.ecwid.apiclient.v3.responsefields.ResponseFields
import java.util.*
import java.util.concurrent.TimeUnit

data class CustomersSearchRequest(
	val keyword: String? = null,
	val name: String? = null,
	val email: String? = null,
	val customerGroupId: String? = null,
	val minOrderCount: Int? = null,
	val maxOrderCount: Int? = null,
	val minSalesValue: Int? = null,
	val maxSalesValue: Int? = null,
	val taxExempt: Boolean? = null,
	val acceptMarketing: Boolean? = null,
	val purchasedProductId: String? = null,
	val countryCode: String? = null,
	val createdFrom: Date? = null,
	val createdTo: Date? = null,
	val updatedFrom: Date? = null,
	val updatedTo: Date? = null,
	val lang: String? = null,
	val sortBy: SortOrder? = null,
	val offset: Int = 0,
	val limit: Int = 100,
	val responseFields: ResponseFields = ResponseFields.All,
) : ApiRequest {
	override fun toRequestInfo() = RequestInfo.createGetRequest(
		pathSegments = listOf(
			"customers"
		),
		params = toParams(),
		responseFields = responseFields,
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
		UPDATED_DATE_ASC,
		SALES_VALUE_ASC,
		SALES_VALUE_DESC,
		FIRST_ORDER_DATE_ASC,
		FIRST_ORDER_DATE_DESC,
		LAST_ORDER_DATE_ASC,
		LAST_ORDER_DATE_DESC,
	}

	private fun toParams(): Map<String, String> {
		val request = this
		return mutableMapOf<String, String>().apply {
			request.keyword?.let { put("keyword", it) }
			request.name?.let { put("name", it) }
			request.email?.let { put("email", it) }
			request.customerGroupId?.let { put("customerGroup", it) }
			request.minOrderCount?.let { put("minOrderCount", it.toString()) }
			request.maxOrderCount?.let { put("maxOrderCount", it.toString()) }
			request.minSalesValue?.let { put("minSalesValue", it.toString()) }
			request.maxSalesValue?.let { put("maxSalesValue", it.toString()) }
			request.createdFrom?.let { put("createdFrom", TimeUnit.MILLISECONDS.toSeconds(it.time).toString()) }
			request.createdTo?.let { put("createdTo", TimeUnit.MILLISECONDS.toSeconds(it.time).toString()) }
			request.updatedFrom?.let { put("updatedFrom", TimeUnit.MILLISECONDS.toSeconds(it.time).toString()) }
			request.updatedTo?.let { put("updatedTo", TimeUnit.MILLISECONDS.toSeconds(it.time).toString()) }
			request.taxExempt?.let { put("taxExempt", it.toString()) }
			request.acceptMarketing?.let { put("acceptMarketing", it.toString()) }
			request.purchasedProductId?.let { put("purchasedProductId", it) }
			request.countryCode?.let { put("countryCode", it) }
			request.lang?.let { put("lang", it) }
			request.sortBy?.let { put("sortBy", it.name) }
			put("offset", request.offset.toString())
			put("limit", request.limit.toString())
		}.toMap()
	}
}
