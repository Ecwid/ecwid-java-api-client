package com.ecwid.apiclient.v3.dto.cart.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.impl.RequestInfo
import java.util.*

data class CartsSearchRequest(
		val showHidden: Boolean? = null,
		val totalFrom: Double? = null,
		val totalTo: Double? = null,
		val createdFrom: Date? = null,
		val createdTo: Date? = null,
		val updatedFrom: Date? = null,
		val updatedTo: Date? = null,
		val couponCode: String? = null,
		val customer: String? = null,
		val offset: Int = 0,
		val limit: Int = 100
) : ApiRequest {
	override fun toRequestInfo() = RequestInfo.createGetRequest(
			endpoint = "carts",
			params = toParams()
	)

	private fun toParams(): Map<String, String> {
		val request = this
		return mutableMapOf<String, String>().apply {
			request.showHidden?.let { put("showHidden", it.toString()) }
			request.totalFrom?.let { put("totalFrom", it.toString()) }
			request.totalTo?.let { put("totalTo", it.toString()) }
			request.createdFrom?.let { put("createdFrom", (it.time / 1000).toString()) }
			request.createdTo?.let { put("createdTo", (it.time / 1000).toString()) }
			request.updatedFrom?.let { put("updatedFrom", (it.time / 1000).toString()) }
			request.updatedTo?.let { put("updatedTo", (it.time / 1000).toString()) }
			request.couponCode?.let { put("couponCode", it) }
			request.customer?.let { put("customer", it) }
			put("offset", request.offset.toString())
			put("limit", request.limit.toString())
		}.toMap()
	}
}
