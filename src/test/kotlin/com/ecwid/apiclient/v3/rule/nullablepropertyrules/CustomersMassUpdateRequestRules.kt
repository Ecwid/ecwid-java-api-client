package com.ecwid.apiclient.v3.rule.nullablepropertyrules

import com.ecwid.apiclient.v3.dto.customer.request.CustomersRequestFields
import com.ecwid.apiclient.v3.dto.customer.request.MassUpdateCustomer
import com.ecwid.apiclient.v3.rule.NullablePropertyRule
import com.ecwid.apiclient.v3.rule.NullablePropertyRule.AllowNullable


val customersMassUpdatedRequestNullablePropertyRules: List<NullablePropertyRule<*, *>> = listOf(
	AllowNullable(CustomersRequestFields::keyword),
	AllowNullable(CustomersRequestFields::maxOrderCount),
	AllowNullable(CustomersRequestFields::minOrderCount),
	AllowNullable(CustomersRequestFields::maxSalesValue),
	AllowNullable(CustomersRequestFields::minSalesValue),
	AllowNullable(CustomersRequestFields::purchasedProductIds),
	AllowNullable(CustomersRequestFields::customerGroupIds),
	AllowNullable(CustomersRequestFields::countryCodes),
	AllowNullable(CustomersRequestFields::taxExempt),
	AllowNullable(CustomersRequestFields::acceptMarketing),
	AllowNullable(CustomersRequestFields::lang),

	AllowNullable(MassUpdateCustomer::ids),
	AllowNullable(MassUpdateCustomer::delete),
	AllowNullable(MassUpdateCustomer::customer),

	AllowNullable(MassUpdateCustomer.UpdatedCustomer::customerGroupId),
	AllowNullable(MassUpdateCustomer.UpdatedCustomer::taxExempt),
	AllowNullable(MassUpdateCustomer.UpdatedCustomer::acceptMarketing),
)
