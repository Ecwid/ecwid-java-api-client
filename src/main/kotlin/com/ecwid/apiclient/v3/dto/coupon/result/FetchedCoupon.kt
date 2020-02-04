package com.ecwid.apiclient.v3.dto.coupon.result

import java.util.*

data class FetchedCoupon (
        var id: Int = 0,
        var name: String? = null,
        var code: String? = null,
        var discountType: String? = null,
        var status: String? = null,
        var discount: Double? = null,
        var launchDate: Date? = null,
        var expirationDate: Date? = null,
        var totalLimit: Double? = null,
        var usesLimit: String? = null,
        var repeatCustomerOnly: Boolean? = null,
        var applicationLimit: String? = null,
        var creationDate: Date? = null,
        var updateDate: Date? = null,
        var orderCount: Int? = null,
        var catalogLimit: DiscountCouponCatalogLimit? = null
) {
    data class DiscountCouponCatalogLimit(
            var products: List<Int>? = null,
            var categories: List<Int>? = null
    )
}