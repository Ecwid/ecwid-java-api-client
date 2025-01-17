package com.ecwid.apiclient.v3.rule.nullablepropertyrules

import com.ecwid.apiclient.v3.dto.brand.request.BrandsSearchRequest
import com.ecwid.apiclient.v3.rule.NullablePropertyRule
import com.ecwid.apiclient.v3.rule.NullablePropertyRule.AllowNullable

val brandsSearchRequestNullablePropertyRules: List<NullablePropertyRule<*, *>> = listOf(
	AllowNullable(BrandsSearchRequest.ByFilters::lang),
	AllowNullable(BrandsSearchRequest.ByFilters::hiddenBrands),
	AllowNullable(BrandsSearchRequest.ByFilters::baseUrl),
	AllowNullable(BrandsSearchRequest.ByFilters::cleanUrls),
	AllowNullable(BrandsSearchRequest.ByFilters::sortBy),
)
