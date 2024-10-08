package com.ecwid.apiclient.v3.dto.customer.request

import com.ecwid.apiclient.v3.dto.common.ApiUpdatedDTO
import com.ecwid.apiclient.v3.dto.common.ApiUpdatedDTO.ModifyKind
import com.ecwid.apiclient.v3.dto.customer.enums.CommercialRelationshipScheme
import com.ecwid.apiclient.v3.dto.customer.result.FetchedCustomer
import com.ecwid.apiclient.v3.dto.extrafield.enums.ExtrafieldType
import com.ecwid.apiclient.v3.jsontransformer.JsonFieldName

data class UpdatedCustomer(
	val email: String? = null,
	val password: String? = null,
	val customerGroupId: Int? = null,
	val billingPerson: BillingPerson? = null,
	val shippingAddresses: List<ShippingAddress>? = null,
	val contacts: List<CustomerContact>? = null,
	val taxId: String? = null,
	val taxIdValid: Boolean? = null,
	val taxExempt: Boolean? = null,
	val acceptMarketing: Boolean? = null,
	val lang: String? = null,
	val privateAdminNotes: String? = null,
	val extrafields: List<CustomerExtrafield>? = null,
	val primaryPhone: String? = null,

	@JsonFieldName("b2b_b2c")
	val commercialRelationshipScheme: CommercialRelationshipScheme? = null,
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
		val phone: String? = null,
		val note: String? = null,
		val defaultAddress: Boolean? = null,
		val orderBy: Int? = null,
	)

	data class CustomerContact(
		val id: Long? = null,
		val contact: String? = null,
		val handle: String? = null,
		val note: String? = null,
		val type: String? = null,
		val default: Boolean? = null,
		val orderBy: Int? = null,
	)

	data class CustomerExtrafield(
		val key: String? = null,
		val title: String? = null,
		val value: String? = null,
		val type: ExtrafieldType? = null,
	)

	override fun getModifyKind() = ModifyKind.ReadWrite(FetchedCustomer::class)
}
