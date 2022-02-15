package com.ecwid.apiclient.v3.rule.nullablepropertyrules

import com.ecwid.apiclient.v3.dto.category.request.CategoriesByPathRequest
import com.ecwid.apiclient.v3.rule.NullablePropertyRule
import com.ecwid.apiclient.v3.rule.NullablePropertyRule.AllowNullable

val categoriesByPathRequestNullablePropertyRules: List<NullablePropertyRule<*, *>> = listOf(
    AllowNullable(CategoriesByPathRequest::lang)
)
