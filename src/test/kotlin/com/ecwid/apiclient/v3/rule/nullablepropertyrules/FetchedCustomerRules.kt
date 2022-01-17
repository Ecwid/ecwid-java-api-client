package com.ecwid.apiclient.v3.rule.nullablepropertyrules

import com.ecwid.apiclient.v3.dto.customer.result.FetchedCustomer
import com.ecwid.apiclient.v3.rule.NullablePropertyRule
import com.ecwid.apiclient.v3.rule.NullablePropertyRule.IgnoreNullable

val fetchedCustomerNullablePropertyRules: List<NullablePropertyRule<*, *>> = listOf(
	IgnoreNullable(FetchedCustomer::acceptMarketing),
	IgnoreNullable(FetchedCustomer::billingPerson),
	IgnoreNullable(FetchedCustomer::customerGroupId),
	IgnoreNullable(FetchedCustomer::customerGroupName),
	IgnoreNullable(FetchedCustomer::registered),
	IgnoreNullable(FetchedCustomer::shippingAddresses),
	IgnoreNullable(FetchedCustomer::taxExempt),
	IgnoreNullable(FetchedCustomer::taxId),
	IgnoreNullable(FetchedCustomer::taxIdValid),
	IgnoreNullable(FetchedCustomer::updated),
	IgnoreNullable(FetchedCustomer.BillingPerson::city),
	IgnoreNullable(FetchedCustomer.BillingPerson::companyName),
	IgnoreNullable(FetchedCustomer.BillingPerson::countryCode),
	IgnoreNullable(FetchedCustomer.BillingPerson::countryName),
	IgnoreNullable(FetchedCustomer.BillingPerson::firstName),
	IgnoreNullable(FetchedCustomer.BillingPerson::lastName),
	IgnoreNullable(FetchedCustomer.BillingPerson::name),
	IgnoreNullable(FetchedCustomer.BillingPerson::phone),
	IgnoreNullable(FetchedCustomer.BillingPerson::postalCode),
	IgnoreNullable(FetchedCustomer.BillingPerson::stateOrProvinceCode),
	IgnoreNullable(FetchedCustomer.BillingPerson::stateOrProvinceName),
	IgnoreNullable(FetchedCustomer.BillingPerson::street),
	IgnoreNullable(FetchedCustomer.ShippingAddress::city),
	IgnoreNullable(FetchedCustomer.ShippingAddress::companyName),
	IgnoreNullable(FetchedCustomer.ShippingAddress::countryCode),
	IgnoreNullable(FetchedCustomer.ShippingAddress::countryName),
	IgnoreNullable(FetchedCustomer.ShippingAddress::id),
	IgnoreNullable(FetchedCustomer.ShippingAddress::name),
	IgnoreNullable(FetchedCustomer.ShippingAddress::phone),
	IgnoreNullable(FetchedCustomer.ShippingAddress::postalCode),
	IgnoreNullable(FetchedCustomer.ShippingAddress::stateOrProvinceCode),
	IgnoreNullable(FetchedCustomer.ShippingAddress::stateOrProvinceName),
	IgnoreNullable(FetchedCustomer.ShippingAddress::street)
)
