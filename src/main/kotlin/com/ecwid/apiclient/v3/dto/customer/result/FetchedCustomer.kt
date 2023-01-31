package com.ecwid.apiclient.v3.dto.customer.result

import com.ecwid.apiclient.v3.dto.common.ApiFetchedDTO
import com.ecwid.apiclient.v3.dto.common.ApiFetchedDTO.ModifyKind
import com.ecwid.apiclient.v3.dto.customer.enums.CommercialRelationshipScheme
import com.ecwid.apiclient.v3.dto.customer.request.UpdatedCustomer
import com.ecwid.apiclient.v3.jsontransformer.JsonFieldName
import java.util.*

data class FetchedCustomer(
	val id: Int = 0,
	val email: String = "",
	val registered: Date? = null,
	val updated: Date? = null,
	val customerGroupId: Int? = null,
	val customerGroupName: String? = null,
	val billingPerson: BillingPerson? = null,
	val shippingAddresses: List<ShippingAddress>? = null,
	val taxId: String? = null,
	val taxIdValid: Boolean? = null,
	val taxExempt: Boolean? = null,
	val acceptMarketing: Boolean? = null,
	val lang: String? = null,

	@JsonFieldName("b2b_b2c")
	val commercialRelationshipScheme: CommercialRelationshipScheme = CommercialRelationshipScheme.b2c,
) : ApiFetchedDTO {

	data class BillingPerson(
		val name: String? = null,
		val firstName: String? = null,
		val lastName: String? = null,
		val companyName: String? = null,
		val street: String? = null,
		val city: String? = null,
		val countryCode: String? = null,
		val countryName: String? = null,
		val postalCode: String? = null,
		val stateOrProvinceCode: String? = null,
		val stateOrProvinceName: String? = null,
		val phone: String? = null
	)

	data class ShippingAddress(
		val id: Int? = null,
		val name: String? = null,
		val companyName: String? = null,
		val street: String? = null,
		val city: String? = null,
		val countryCode: String? = null,
		val countryName: String? = null,
		val postalCode: String? = null,
		val stateOrProvinceCode: String? = null,
		val stateOrProvinceName: String? = null,
		val phone: String? = null
	)

	override fun getModifyKind() = ModifyKind.ReadWrite(UpdatedCustomer::class)
}
