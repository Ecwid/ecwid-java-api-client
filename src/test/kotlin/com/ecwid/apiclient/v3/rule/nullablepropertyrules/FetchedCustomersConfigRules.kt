package com.ecwid.apiclient.v3.rule.nullablepropertyrules

import com.ecwid.apiclient.v3.dto.extrafield.result.FetchedCustomersConfig
import com.ecwid.apiclient.v3.rule.NullablePropertyRule
import com.ecwid.apiclient.v3.rule.NullablePropertyRule.AllowNullable

val fetchedCustomersConfigNullablePropertyRules: List<NullablePropertyRule<*, *>> = listOf(
	AllowNullable(FetchedCustomersConfig::key),
	AllowNullable(FetchedCustomersConfig::title),
	AllowNullable(FetchedCustomersConfig::entityTypes),
	AllowNullable(FetchedCustomersConfig::type),
	AllowNullable(FetchedCustomersConfig::shownOnOrderDetails),
	AllowNullable(FetchedCustomersConfig::createdDate),
	AllowNullable(FetchedCustomersConfig::lastModifiedDate),
)
