package com.ecwid.apiclient.v3.rule.nullablepropertyrules

import com.ecwid.apiclient.v3.dto.coupon.request.CouponSearchRequest
import com.ecwid.apiclient.v3.rule.NullablePropertyRule
import com.ecwid.apiclient.v3.rule.NullablePropertyRule.AllowNullable

val couponSearchRequestNullablePropertyRules: List<NullablePropertyRule<*, *>> = listOf(
	AllowNullable(CouponSearchRequest::availability),
	AllowNullable(CouponSearchRequest::code),
	AllowNullable(CouponSearchRequest::createdFrom),
	AllowNullable(CouponSearchRequest::createdTo),
	AllowNullable(CouponSearchRequest::discountType),
	AllowNullable(CouponSearchRequest::updatedFrom),
	AllowNullable(CouponSearchRequest::updatedTo)
)
