package com.ecwid.apiclient.v3.rule.nullablepropertyrules

import com.ecwid.apiclient.v3.dto.customer.request.CustomersMassUpdateRequest
import com.ecwid.apiclient.v3.dto.customer.request.MassUpdateCustomer
import com.ecwid.apiclient.v3.rule.NullablePropertyRule
import com.ecwid.apiclient.v3.rule.NullablePropertyRule.AllowNullable


val customersMassUpdatedRequestNullablePropertyRules: List<NullablePropertyRule<*, *>> = listOf(

	AllowNullable(CustomersMassUpdateRequest::keyword),
	AllowNullable(CustomersMassUpdateRequest::maxOrderCount),
	AllowNullable(CustomersMassUpdateRequest::minOrderCount),
	AllowNullable(CustomersMassUpdateRequest::maxSalesValue),
	AllowNullable(CustomersMassUpdateRequest::minSalesValue),
	AllowNullable(CustomersMassUpdateRequest::purchasedProductIds),
	AllowNullable(CustomersMassUpdateRequest::customerGroupIds),
	AllowNullable(CustomersMassUpdateRequest::countryCodes),
	AllowNullable(CustomersMassUpdateRequest::taxExempt),
	AllowNullable(CustomersMassUpdateRequest::acceptMarketing),
	AllowNullable(CustomersMassUpdateRequest::lang),

	AllowNullable(MassUpdateCustomer::ids),
	AllowNullable(MassUpdateCustomer::delete),
	AllowNullable(MassUpdateCustomer::customer),

	AllowNullable(MassUpdateCustomer.UpdatedCustomer::customerGroupId),
	AllowNullable(MassUpdateCustomer.UpdatedCustomer::taxExempt),
	AllowNullable(MassUpdateCustomer.UpdatedCustomer::acceptMarketing),
)
