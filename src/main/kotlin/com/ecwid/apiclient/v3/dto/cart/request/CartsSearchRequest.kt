package com.ecwid.apiclient.v3.dto.cart.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.impl.RequestInfo
import java.util.*

data class CartsSearchRequest(
		var showHidden: Boolean? = null,
		var totalFrom: Double? = null,
		var totalTo: Double? = null,
		var createdFrom: Date? = null,
		var createdTo: Date? = null,
		var updatedFrom: Date? = null,
		var updatedTo: Date? = null,
		var couponCode: String? = null,
		var customer: String? = null,
		var offset: Int = 0,
		var limit: Int = 100
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
