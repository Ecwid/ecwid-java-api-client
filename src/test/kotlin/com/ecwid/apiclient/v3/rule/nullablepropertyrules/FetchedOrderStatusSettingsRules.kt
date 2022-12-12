package com.ecwid.apiclient.v3.rule.nullablepropertyrules

import com.ecwid.apiclient.v3.dto.profile.result.FetchedOrderStatusSettings
import com.ecwid.apiclient.v3.rule.NullablePropertyRule
import com.ecwid.apiclient.v3.rule.NullablePropertyRule.AllowNullable

val fetchedOrderStatusSettingsNullablePropertyRules: List<NullablePropertyRule<*, *>> = listOf(
	AllowNullable(FetchedOrderStatusSettings::defaultStatus),
	AllowNullable(FetchedOrderStatusSettings::name),
	AllowNullable(FetchedOrderStatusSettings::nameTranslations),
	AllowNullable(FetchedOrderStatusSettings::lastNameChangeDate),
)
