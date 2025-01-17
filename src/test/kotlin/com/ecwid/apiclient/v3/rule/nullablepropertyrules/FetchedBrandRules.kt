package com.ecwid.apiclient.v3.rule.nullablepropertyrules

import com.ecwid.apiclient.v3.dto.brand.result.FetchedBrand
import com.ecwid.apiclient.v3.rule.NullablePropertyRule
import com.ecwid.apiclient.v3.rule.NullablePropertyRule.AllowNullable

val fetchedBrandNullablePropertyRules: List<NullablePropertyRule<*, *>> = listOf(
	AllowNullable(FetchedBrand::nameTranslated),
	AllowNullable(FetchedBrand::productsFilteredByBrandUrl),
)
