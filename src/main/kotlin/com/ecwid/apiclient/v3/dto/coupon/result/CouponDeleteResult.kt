package com.ecwid.apiclient.v3.dto.coupon.result

import com.ecwid.apiclient.v3.dto.common.ApiResultDTO

data class CouponDeleteResult(
	val deleteCount: Int = 0
) : ApiResultDTO
