package com.ecwid.apiclient.v3.dto.coupon.request

import com.ecwid.apiclient.v3.dto.order.enums.DiscountCouponApplicationLimit
import com.ecwid.apiclient.v3.dto.order.enums.DiscountCouponStatus
import com.ecwid.apiclient.v3.dto.order.enums.DiscountCouponType
import com.ecwid.apiclient.v3.dto.order.enums.DiscountCouponUsesLimit
import java.util.*

data class UpdatedCoupon(
		var name: String = "",
		var code: String = "",
		var discountType: DiscountCouponType? = null,
		var status: DiscountCouponStatus? = null,
		var discount: Double? = null,
		var launchDate: Date? = null,
		var expirationDate: Date? = null,
		var totalLimit: Double? = null,
		var usesLimit: DiscountCouponUsesLimit? = null,
		var repeatCustomerOnly: Boolean? = null,
		var applicationLimit: DiscountCouponApplicationLimit? = null,
		var orderCount: Int? = null,
		var catalogLimit: DiscountCouponCatalogLimit? = null
) {
	data class DiscountCouponCatalogLimit(
			var products: List<Int>? = null,
			var categories: List<Int>? = null
	)
}
