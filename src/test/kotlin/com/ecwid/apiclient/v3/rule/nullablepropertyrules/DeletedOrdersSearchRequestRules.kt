package com.ecwid.apiclient.v3.rule.nullablepropertyrules

import com.ecwid.apiclient.v3.dto.order.request.DeletedOrdersSearchRequest
import com.ecwid.apiclient.v3.rule.NullablePropertyRule
import com.ecwid.apiclient.v3.rule.NullablePropertyRule.AllowNullable

val deletedOrdersSearchRequestNullablePropertyRules: List<NullablePropertyRule<*, *>> = listOf(
    AllowNullable(DeletedOrdersSearchRequest::deletedFrom),
	AllowNullable(DeletedOrdersSearchRequest::deletedTo)
)
