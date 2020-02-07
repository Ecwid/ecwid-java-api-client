package com.ecwid.apiclient.v3.dto.coupon.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.impl.RequestInfo

data class CouponDetailsRequest(
		var couponIdentifier: String = ""
) : ApiRequest {
	override fun toRequestInfo() = RequestInfo.createGetRequest(
			endpoint = "discount_coupons/$couponIdentifier"
	)
}
