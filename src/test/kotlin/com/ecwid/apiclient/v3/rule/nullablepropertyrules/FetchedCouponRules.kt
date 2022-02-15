package com.ecwid.apiclient.v3.rule.nullablepropertyrules

import com.ecwid.apiclient.v3.dto.coupon.result.FetchedCoupon
import com.ecwid.apiclient.v3.rule.NullablePropertyRule
import com.ecwid.apiclient.v3.rule.NullablePropertyRule.IgnoreNullable

val fetchedCouponNullablePropertyRules: List<NullablePropertyRule<*, *>> = listOf(
	IgnoreNullable(FetchedCoupon::applicationLimit),
	IgnoreNullable(FetchedCoupon::catalogLimit),
	IgnoreNullable(FetchedCoupon::creationDate),
	IgnoreNullable(FetchedCoupon::discount),
	IgnoreNullable(FetchedCoupon::discountType),
	IgnoreNullable(FetchedCoupon::expirationDate),
	IgnoreNullable(FetchedCoupon::launchDate),
	IgnoreNullable(FetchedCoupon::orderCount),
	IgnoreNullable(FetchedCoupon::repeatCustomerOnly),
	IgnoreNullable(FetchedCoupon::status),
	IgnoreNullable(FetchedCoupon::totalLimit),
	IgnoreNullable(FetchedCoupon::updateDate),
	IgnoreNullable(FetchedCoupon::usesLimit),
	IgnoreNullable(FetchedCoupon.DiscountCouponCatalogLimit::categories),
	IgnoreNullable(FetchedCoupon.DiscountCouponCatalogLimit::products)
)
