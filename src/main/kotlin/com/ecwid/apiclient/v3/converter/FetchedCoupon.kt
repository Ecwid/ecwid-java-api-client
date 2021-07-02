package com.ecwid.apiclient.v3.converter

import com.ecwid.apiclient.v3.dto.coupon.request.UpdatedCoupon
import com.ecwid.apiclient.v3.dto.coupon.result.FetchedCoupon

fun FetchedCoupon.toUpdated(): UpdatedCoupon {
	return UpdatedCoupon(
		name = name,
		code = code,
		discountType = discountType,
		status = status,
		discount = discount,
		launchDate = launchDate,
		expirationDate = expirationDate,
		totalLimit = totalLimit,
		usesLimit = usesLimit,
		repeatCustomerOnly = repeatCustomerOnly,
		applicationLimit = applicationLimit,
		orderCount = orderCount,
		catalogLimit = catalogLimit?.toUpdated()
	)
}

fun FetchedCoupon.DiscountCouponCatalogLimit.toUpdated(): UpdatedCoupon.DiscountCouponCatalogLimit {
	return UpdatedCoupon.DiscountCouponCatalogLimit(
		products = products,
		categories = categories
	)
}
