package com.ecwid.apiclient.v3.rule.nullablepropertyrules

import com.ecwid.apiclient.v3.dto.sluginfo.SlugInfoRequest
import com.ecwid.apiclient.v3.rule.NullablePropertyRule
import com.ecwid.apiclient.v3.rule.NullablePropertyRule.AllowNullable

val slugInfoRequestNullablePropertyRules: List<NullablePropertyRule<*, *>> = listOf(
	AllowNullable(SlugInfoRequest::degeneratorParams),
)
