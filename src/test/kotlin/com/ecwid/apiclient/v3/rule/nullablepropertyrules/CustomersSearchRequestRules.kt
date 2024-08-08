package com.ecwid.apiclient.v3.rule.nullablepropertyrules

import com.ecwid.apiclient.v3.dto.customer.request.CustomersSearchRequest
import com.ecwid.apiclient.v3.rule.NullablePropertyRule
import com.ecwid.apiclient.v3.rule.NullablePropertyRule.AllowNullable

val customersSearchRequestNullablePropertyRules: List<NullablePropertyRule<*, *>> = listOf(
	AllowNullable(CustomersSearchRequest::createdFrom),
	AllowNullable(CustomersSearchRequest::createdTo),
	AllowNullable(CustomersSearchRequest::customerGroupId),
	AllowNullable(CustomersSearchRequest::email),
	AllowNullable(CustomersSearchRequest::keyword),
	AllowNullable(CustomersSearchRequest::usePrecalculatedOrderCount),
	AllowNullable(CustomersSearchRequest::maxOrderCount),
	AllowNullable(CustomersSearchRequest::minOrderCount),
	AllowNullable(CustomersSearchRequest::maxSalesValue),
	AllowNullable(CustomersSearchRequest::minSalesValue),
	AllowNullable(CustomersSearchRequest::name),
	AllowNullable(CustomersSearchRequest::sortBy),
	AllowNullable(CustomersSearchRequest::updatedFrom),
	AllowNullable(CustomersSearchRequest::updatedTo),
	AllowNullable(CustomersSearchRequest::purchasedProductIds),
	AllowNullable(CustomersSearchRequest::customerGroupIds),
	AllowNullable(CustomersSearchRequest::companyName),
	AllowNullable(CustomersSearchRequest::countryCodes),
	AllowNullable(CustomersSearchRequest::city),
	AllowNullable(CustomersSearchRequest::postalCode),
	AllowNullable(CustomersSearchRequest::stateOrProvinceCode),
	AllowNullable(CustomersSearchRequest::phone),
	AllowNullable(CustomersSearchRequest::taxExempt),
	AllowNullable(CustomersSearchRequest::acceptMarketing),
	AllowNullable(CustomersSearchRequest::lang),
	AllowNullable(CustomersSearchRequest::checkOnlyCustomerId),
)
