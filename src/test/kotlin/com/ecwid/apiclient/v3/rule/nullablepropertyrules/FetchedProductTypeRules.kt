package com.ecwid.apiclient.v3.rule.nullablepropertyrules

import com.ecwid.apiclient.v3.dto.producttype.result.FetchedProductType
import com.ecwid.apiclient.v3.rule.NullablePropertyRule
import com.ecwid.apiclient.v3.rule.NullablePropertyRule.IgnoreNullable

val fetchedProductTypeNullablePropertyRules: List<NullablePropertyRule<*, *>> = listOf(
    IgnoreNullable(FetchedProductType::attributes),
	IgnoreNullable(FetchedProductType::googleTaxonomy),
	IgnoreNullable(FetchedProductType::name),
	IgnoreNullable(FetchedProductType.Attribute::name),
	IgnoreNullable(FetchedProductType.Attribute::show),
	IgnoreNullable(FetchedProductType.Attribute::type)
)
