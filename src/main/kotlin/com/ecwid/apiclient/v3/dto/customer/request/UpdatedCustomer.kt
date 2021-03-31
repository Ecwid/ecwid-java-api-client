package com.ecwid.apiclient.v3.dto.customer.request

import com.ecwid.apiclient.v3.dto.common.ApiUpdatedDTO
import com.ecwid.apiclient.v3.dto.common.ApiUpdatedDTO.ModifyKind
import com.ecwid.apiclient.v3.dto.customer.result.FetchedCustomer

data class UpdatedCustomer(
		val email: String = "",
		val password: String? = null,
		val customerGroupId: Int? = null,
		val billingPerson: BillingPerson? = null,
		val shippingAddresses: List<ShippingAddress>? = null,
		val taxId: String? = null,
		val taxIdValid: Boolean? = null,
		val taxExempt: Boolean? = null,
		val acceptMarketing: Boolean? = null
) : ApiUpdatedDTO {

	data class BillingPerson(
			val name: String? = null,
			val companyName: String? = null,
			val street: String? = null,
			val city: String? = null,
			val countryCode: String? = null,
			val postalCode: String? = null,
			val stateOrProvinceCode: String? = null,
			val phone: String? = null
	)

	data class ShippingAddress(
			val id: Int? = null,
			val name: String? = null,
			val companyName: String? = null,
			val street: String? = null,
			val city: String? = null,
			val countryCode: String? = null,
			val postalCode: String? = null,
			val stateOrProvinceCode: String? = null,
			val phone: String? = null
	)

	override fun getModifyKind() = ModifyKind.ReadWrite(FetchedCustomer::class)

}
