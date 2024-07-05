package com.ecwid.apiclient.v3.rule.nullablepropertyrules

import com.ecwid.apiclient.v3.dto.category.request.CategoriesSearchRequest
import com.ecwid.apiclient.v3.rule.NullablePropertyRule
import com.ecwid.apiclient.v3.rule.NullablePropertyRule.AllowNullable

val categoriesSearchRequestNullablePropertyRules: List<NullablePropertyRule<*, *>> = listOf(
	AllowNullable(CategoriesSearchRequest::baseUrl),
	AllowNullable(CategoriesSearchRequest::categoryIds),
	AllowNullable(CategoriesSearchRequest::cleanUrls),
	AllowNullable(CategoriesSearchRequest::slugsWithoutIds),
	AllowNullable(CategoriesSearchRequest::hiddenCategories),
	AllowNullable(CategoriesSearchRequest::keyword),
	AllowNullable(CategoriesSearchRequest::lang),
	AllowNullable(CategoriesSearchRequest::returnProductIds)
)
