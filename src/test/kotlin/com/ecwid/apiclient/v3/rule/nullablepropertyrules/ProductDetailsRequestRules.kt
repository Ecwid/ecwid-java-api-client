package com.ecwid.apiclient.v3.rule.nullablepropertyrules

import com.ecwid.apiclient.v3.dto.product.request.ProductDetailsRequest
import com.ecwid.apiclient.v3.rule.NullablePropertyRule
import com.ecwid.apiclient.v3.rule.NullablePropertyRule.AllowNullable

val productDetailsRequestNullablePropertyRules: List<NullablePropertyRule<*, *>> = listOf(
    AllowNullable(ProductDetailsRequest::baseUrl),
	AllowNullable(ProductDetailsRequest::cleanUrls),
	AllowNullable(ProductDetailsRequest::slugsWithoutIds),
	AllowNullable(ProductDetailsRequest::lang)
)
