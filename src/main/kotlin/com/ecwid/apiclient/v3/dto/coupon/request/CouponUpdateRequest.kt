package com.ecwid.apiclient.v3.dto.coupon.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.httptransport.HttpBody
import com.ecwid.apiclient.v3.impl.RequestInfo

data class CouponUpdateRequest(
		val couponIdentifier: String = "",
		val updatedCoupon: UpdatedCoupon = UpdatedCoupon()
) : ApiRequest {
	override fun toRequestInfo() = RequestInfo.createPutRequest(
			pathSegments = listOf(
					"discount_coupons",
					"$couponIdentifier"
			),
			httpBody = HttpBody.JsonBody(
					obj = updatedCoupon
			)
	)
}
