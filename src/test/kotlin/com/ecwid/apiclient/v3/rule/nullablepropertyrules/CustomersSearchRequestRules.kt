package com.ecwid.apiclient.v3.rule.nullablepropertyrules

import com.ecwid.apiclient.v3.dto.customer.request.CustomersMassUpdateRequest
import com.ecwid.apiclient.v3.dto.customer.request.CustomersSearchRequest
import com.ecwid.apiclient.v3.rule.NullablePropertyRule
import com.ecwid.apiclient.v3.rule.NullablePropertyRule.AllowNullable

val customersSearchRequestNullablePropertyRules: List<NullablePropertyRule<*, *>> = listOf(
	AllowNullable(CustomersSearchRequest::createdFrom),
	AllowNullable(CustomersSearchRequest::createdTo),
	AllowNullable(CustomersSearchRequest::customerGroupId),
	AllowNullable(CustomersSearchRequest::email),
	AllowNullable(CustomersSearchRequest::keyword),
	AllowNullable(CustomersSearchRequest::maxOrderCount),
	AllowNullable(CustomersSearchRequest::minOrderCount),
	AllowNullable(CustomersSearchRequest::maxSalesValue),
	AllowNullable(CustomersSearchRequest::minSalesValue),
	AllowNullable(CustomersSearchRequest::name),
	AllowNullable(CustomersSearchRequest::sortBy),
	AllowNullable(CustomersSearchRequest::updatedFrom),
	AllowNullable(CustomersSearchRequest::updatedTo),
	AllowNullable(CustomersSearchRequest::purchasedProductId),
	AllowNullable(CustomersSearchRequest::countryCode),
	AllowNullable(CustomersSearchRequest::taxExempt),
	AllowNullable(CustomersSearchRequest::acceptMarketing),
	AllowNullable(CustomersSearchRequest::lang),

	AllowNullable(CustomersMassUpdateRequest::customerGroupId),
	AllowNullable(CustomersMassUpdateRequest::keyword),
	AllowNullable(CustomersMassUpdateRequest::maxOrderCount),
	AllowNullable(CustomersMassUpdateRequest::minOrderCount),
	AllowNullable(CustomersMassUpdateRequest::maxSalesValue),
	AllowNullable(CustomersMassUpdateRequest::minSalesValue),
	AllowNullable(CustomersMassUpdateRequest::purchasedProductId),
	AllowNullable(CustomersMassUpdateRequest::countryCode),
	AllowNullable(CustomersMassUpdateRequest::taxExempt),
	AllowNullable(CustomersMassUpdateRequest::acceptMarketing),
	AllowNullable(CustomersMassUpdateRequest::lang),
)
