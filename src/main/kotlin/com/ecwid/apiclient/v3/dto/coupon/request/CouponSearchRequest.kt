package com.ecwid.apiclient.v3.dto.coupon.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.impl.RequestInfo
import java.util.*

data class CouponSearchRequest(
		var offset: Int = 0,
		var limit: Int = 100,
		var code: String? = null,
		var discount_type: String? = null,
		var availability: String? = null,
		var createdFrom: Date? = null,
		var createdTo: Date? = null,
		var updatedFrom: Date? = null,
		var updatedTo: Date? = null
) : ApiRequest {
	override fun toRequestInfo() = RequestInfo.createGetRequest(
			endpoint = "discount_coupons",
			params = toParams()
	)

	private fun toParams(): Map<String, String> {
		val request = this
		return mutableMapOf<String, String>().apply {
			put("offset", request.offset.toString())
			put("limit", request.limit.toString())
			request.code?.let { put("code", it) }
			request.discount_type?.let { put("discount_type", it) }
			request.availability?.let { put("availability", it) }
			request.createdFrom?.let { put("createdFrom", (it.time / 1000).toString()) }
			request.createdTo?.let { put("createdTo", (it.time / 1000).toString()) }
			request.updatedFrom?.let { put("updatedFrom", (it.time / 1000).toString()) }
			request.updatedTo?.let { put("updatedTo", (it.time / 1000).toString()) }
		}.toMap()
	}
}
