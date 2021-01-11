package com.ecwid.apiclient.v3.dto.coupon.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.httptransport.HttpBody
import com.ecwid.apiclient.v3.impl.RequestInfo

data class CouponCreateRequest(
		val newCoupon: UpdatedCoupon = UpdatedCoupon()
) : ApiRequest {
	override fun toRequestInfo() = RequestInfo.createPostRequest(
			endpoint = "discount_coupons",
			httpBody = HttpBody.JsonBody(
					obj = newCoupon
			)
	)
}
