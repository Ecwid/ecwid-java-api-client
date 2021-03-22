package com.ecwid.apiclient.v3.dto.coupon.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.dto.order.enums.DiscountCouponStatus
import com.ecwid.apiclient.v3.dto.order.enums.DiscountCouponType
import com.ecwid.apiclient.v3.impl.RequestInfo
import com.google.gson.annotations.SerializedName
import java.util.*

data class CouponSearchRequest(
		val offset: Int = 0,
		val limit: Int = 100,
		val code: String? = null,
		@SerializedName("discount_type")
		val discountType: Set<DiscountCouponType>? = null,
		val availability: DiscountCouponStatus? = null,
		val createdFrom: Date? = null,
		val createdTo: Date? = null,
		val updatedFrom: Date? = null,
		val updatedTo: Date? = null
) : ApiRequest {
	override fun toRequestInfo() = RequestInfo.createGetRequest(
			pathSegments = listOf(
				"discount_coupons"
			),
			params = toParams()
	)

	private fun toParams(): Map<String, String> {
		val request = this
		return mutableMapOf<String, String>().apply {
			put("offset", request.offset.toString())
			put("limit", request.limit.toString())
			request.code?.let { put("code", it) }
			request.discountType?.let { put("discount_type", it.joinToString(separator = ",")) }
			request.availability?.let { put("availability", it.name) }
			request.createdFrom?.let { put("createdFrom", (it.time / 1000).toString()) }
			request.createdTo?.let { put("createdTo", (it.time / 1000).toString()) }
			request.updatedFrom?.let { put("updatedFrom", (it.time / 1000).toString()) }
			request.updatedTo?.let { put("updatedTo", (it.time / 1000).toString()) }
		}.toMap()
	}
}
