package com.ecwid.apiclient.v3.rule.nullablepropertyrules

import com.ecwid.apiclient.v3.dto.category.request.CategoryDetailsRequest
import com.ecwid.apiclient.v3.rule.NullablePropertyRule
import com.ecwid.apiclient.v3.rule.NullablePropertyRule.AllowNullable

val categoryDetailsRequestNullablePropertyRules: List<NullablePropertyRule<*, *>> = listOf(
	AllowNullable(CategoryDetailsRequest::baseUrl),
	AllowNullable(CategoryDetailsRequest::cleanUrls),
	AllowNullable(CategoryDetailsRequest::slugsWithoutIds),
	AllowNullable(CategoryDetailsRequest::lang)
)
