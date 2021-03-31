package com.ecwid.apiclient.v3.dto.coupon.result

import com.ecwid.apiclient.v3.dto.common.ApiResultDTO

data class CouponCreateResult(
		val id: Int = 0,
		val code: String = ""
) : ApiResultDTO
