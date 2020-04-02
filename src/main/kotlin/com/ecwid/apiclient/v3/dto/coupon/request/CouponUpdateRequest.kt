package com.ecwid.apiclient.v3.dto.coupon.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.httptransport.HttpBody
import com.ecwid.apiclient.v3.impl.RequestInfo

class CouponUpdateRequest(
		var couponIdentifier: String = "",
		var updatedCoupon: UpdatedCoupon = UpdatedCoupon()
) : ApiRequest {
	override fun toRequestInfo() = RequestInfo.createPutRequest(
			endpoint = "discount_coupons/$couponIdentifier",
			httpBody = HttpBody.JsonBody(
					obj = updatedCoupon
			)
	)
}
