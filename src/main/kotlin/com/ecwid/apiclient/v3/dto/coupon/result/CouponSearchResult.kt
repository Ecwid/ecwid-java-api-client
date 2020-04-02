package com.ecwid.apiclient.v3.dto.coupon.result

data class CouponSearchResult(
		var items: List<FetchedCoupon> = listOf(),
		var count: Int = 0,
		var total: Int = 0,
		var limit: Int = 0,
		var offset: Int = 0
)
