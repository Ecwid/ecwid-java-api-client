package com.ecwid.apiclient.v3.dto.coupon.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.impl.RequestInfo

data class CouponDeleteRequest(
		val couponIdentifier: String = ""
) : ApiRequest {
	override fun toRequestInfo() = RequestInfo.createDeleteRequest(
			pathSegments = listOf(
				"discount_coupons",
				couponIdentifier
			)
	)
}
