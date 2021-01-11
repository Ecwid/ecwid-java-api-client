package com.ecwid.apiclient.v3.dto.coupon.result

import com.ecwid.apiclient.v3.dto.order.enums.DiscountCouponApplicationLimit
import com.ecwid.apiclient.v3.dto.order.enums.DiscountCouponStatus
import com.ecwid.apiclient.v3.dto.order.enums.DiscountCouponType
import com.ecwid.apiclient.v3.dto.order.enums.DiscountCouponUsesLimit
import java.util.*

data class FetchedCoupon(
		val id: Int = 0,
		val name: String = "",
		val code: String = "",
		val discountType: DiscountCouponType? = null,
		val status: DiscountCouponStatus? = null,
		val discount: Double? = null,
		val launchDate: Date? = null,
		val expirationDate: Date? = null,
		val totalLimit: Double? = null,
		val usesLimit: DiscountCouponUsesLimit? = null,
		val repeatCustomerOnly: Boolean? = null,
		val applicationLimit: DiscountCouponApplicationLimit? = null,
		val creationDate: Date? = null,
		val updateDate: Date? = null,
		val orderCount: Int? = null,
		val catalogLimit: DiscountCouponCatalogLimit? = null
) {
	data class DiscountCouponCatalogLimit(
			val products: List<Int>? = null,
			val categories: List<Int>? = null
	)
}
