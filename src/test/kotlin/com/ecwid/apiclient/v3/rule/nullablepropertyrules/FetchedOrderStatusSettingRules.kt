package com.ecwid.apiclient.v3.rule.nullablepropertyrules

import com.ecwid.apiclient.v3.dto.profile.result.FetchedOrderStatusSetting
import com.ecwid.apiclient.v3.rule.NullablePropertyRule
import com.ecwid.apiclient.v3.rule.NullablePropertyRule.AllowNullable

val fetchedOrderStatusSettingNullablePropertyRules: List<NullablePropertyRule<*, *>> = listOf(
	AllowNullable(FetchedOrderStatusSetting::defaultStatus),
	AllowNullable(FetchedOrderStatusSetting::name),
	AllowNullable(FetchedOrderStatusSetting::nameTranslations),
)
