package com.ecwid.apiclient.v3.rule.nullablepropertyrules

import com.ecwid.apiclient.v3.dto.customer.request.CustomersIdsRequest
import com.ecwid.apiclient.v3.rule.NullablePropertyRule

val customersIdsRequestNullablePropertyRules: List<NullablePropertyRule<*, *>> = listOf(
	NullablePropertyRule.AllowNullable(CustomersIdsRequest::keyword),
	NullablePropertyRule.AllowNullable(CustomersIdsRequest::maxOrderCount),
	NullablePropertyRule.AllowNullable(CustomersIdsRequest::minOrderCount),
	NullablePropertyRule.AllowNullable(CustomersIdsRequest::maxSalesValue),
	NullablePropertyRule.AllowNullable(CustomersIdsRequest::minSalesValue),
	NullablePropertyRule.AllowNullable(CustomersIdsRequest::purchasedProductIds),
	NullablePropertyRule.AllowNullable(CustomersIdsRequest::customerGroupIds),
	NullablePropertyRule.AllowNullable(CustomersIdsRequest::countryCodes),
	NullablePropertyRule.AllowNullable(CustomersIdsRequest::taxExempt),
	NullablePropertyRule.AllowNullable(CustomersIdsRequest::acceptMarketing),
	NullablePropertyRule.AllowNullable(CustomersIdsRequest::lang),
)
