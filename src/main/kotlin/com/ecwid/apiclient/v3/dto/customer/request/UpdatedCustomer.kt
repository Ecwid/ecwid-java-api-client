package com.ecwid.apiclient.v3.dto.customer.request

data class UpdatedCustomer(
		var email: String = "",
		var password: String? = null,
		var customerGroupId: Int? = null,
		var billingPerson: BillingPerson? = null,
		var shippingAddresses: List<ShippingAddress>? = null,
		var taxId: String? = null,
		var taxIdValid: Boolean? = null,
		var taxExempt: Boolean? = null,
		val acceptMarketing: Boolean? = null) {

	data class BillingPerson(
			var name: String? = null,
			var companyName: String? = null,
			var street: String? = null,
			var city: String? = null,
			var countryCode: String? = null,
			var postalCode: String? = null,
			var stateOrProvinceCode: String? = null,
			var phone: String? = null
	)

	data class ShippingAddress(
			var id: Int? = null,
			var name: String? = null,
			var companyName: String? = null,
			var street: String? = null,
			var city: String? = null,
			var countryCode: String? = null,
			var postalCode: String? = null,
			var stateOrProvinceCode: String? = null,
			var phone: String? = null
	)

}
