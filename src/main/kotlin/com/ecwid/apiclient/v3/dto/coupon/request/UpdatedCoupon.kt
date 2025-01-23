package com.ecwid.apiclient.v3.dto.coupon.request

import com.ecwid.apiclient.v3.dto.common.ApiUpdatedDTO
import com.ecwid.apiclient.v3.dto.common.ApiUpdatedDTO.ModifyKind
import com.ecwid.apiclient.v3.dto.coupon.result.FetchedCoupon
import com.ecwid.apiclient.v3.dto.order.enums.DiscountCouponApplicationLimit
import com.ecwid.apiclient.v3.dto.order.enums.DiscountCouponStatus
import com.ecwid.apiclient.v3.dto.order.enums.DiscountCouponType
import com.ecwid.apiclient.v3.dto.order.enums.DiscountCouponUsesLimit
import java.util.*

data class UpdatedCoupon(
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
	val orderCount: Int? = null,
	val catalogLimit: DiscountCouponCatalogLimit? = null
) : ApiUpdatedDTO {

	data class DiscountCouponCatalogLimit(
		val products: List<Int>? = null,
		val categories: List<Long>? = null
	)

	override fun getModifyKind() = ModifyKind.ReadWrite(FetchedCoupon::class)
}
