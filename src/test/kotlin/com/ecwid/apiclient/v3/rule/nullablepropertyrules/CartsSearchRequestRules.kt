package com.ecwid.apiclient.v3.rule.nullablepropertyrules

import com.ecwid.apiclient.v3.dto.cart.request.CartsSearchRequest
import com.ecwid.apiclient.v3.rule.NullablePropertyRule
import com.ecwid.apiclient.v3.rule.NullablePropertyRule.IgnoreNullable

val cartsSearchRequestNullablePropertyRules: List<NullablePropertyRule<*, *>> = listOf(
	IgnoreNullable(CartsSearchRequest::couponCode),
	IgnoreNullable(CartsSearchRequest::createdFrom),
	IgnoreNullable(CartsSearchRequest::createdTo),
	IgnoreNullable(CartsSearchRequest::customer),
	IgnoreNullable(CartsSearchRequest::showHidden),
	IgnoreNullable(CartsSearchRequest::totalFrom),
	IgnoreNullable(CartsSearchRequest::totalTo),
	IgnoreNullable(CartsSearchRequest::updatedFrom),
	IgnoreNullable(CartsSearchRequest::updatedTo)
)
