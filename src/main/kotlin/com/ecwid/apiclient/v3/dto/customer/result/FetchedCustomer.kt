package com.ecwid.apiclient.v3.dto.customer.result

import java.util.*

data class FetchedCustomer(
		var id: Int = 0,
		var email: String = "",
		var registered: Date? = null,
		var updated: Date? = null,
		var customerGroupId: Int? = null,
		var customerGroupName: String? = null,
		var billingPerson: BillingPerson? = null,
		var shippingAddresses: List<ShippingAddress>? = null,
		var taxId: String? = null,
		var taxIdValid: Boolean? = null,
		var taxExempt: Boolean? = null,
		var acceptMarketing: Boolean? = null) {

	data class BillingPerson(
			var name: String? = null,
			var firstName: String? = null,
			var lastName: String? = null,
			var companyName: String? = null,
			var street: String? = null,
			var city: String? = null,
			var countryCode: String? = null,
			var countryName: String? = null,
			var postalCode: String? = null,
			var stateOrProvinceCode: String? = null,
			var stateOrProvinceName: String? = null,
			var phone: String? = null
	)

	data class ShippingAddress(
			var id: Int? = null,
			var name: String? = null,
			var companyName: String? = null,
			var street: String? = null,
			var city: String? = null,
			var countryCode: String? = null,
			var countryName: String? = null,
			var postalCode: String? = null,
			var stateOrProvinceCode: String? = null,
			var stateOrProvinceName: String? = null,
			var phone: String? = null
	)

}
