package com.ecwid.apiclient.v3.rule.nullablepropertyrules

import com.ecwid.apiclient.v3.dto.category.request.CategoriesSearchRequest
import com.ecwid.apiclient.v3.rule.NullablePropertyRule
import com.ecwid.apiclient.v3.rule.NullablePropertyRule.AllowNullable

val categoriesSearchRequestNullablePropertyRules: List<NullablePropertyRule<*, *>> = listOf(
	AllowNullable(CategoriesSearchRequest::baseUrl),
	AllowNullable(CategoriesSearchRequest::cleanUrls),
	AllowNullable(CategoriesSearchRequest::hiddenCategories),
	AllowNullable(CategoriesSearchRequest::lang),
	AllowNullable(CategoriesSearchRequest::returnProductIds)
)
