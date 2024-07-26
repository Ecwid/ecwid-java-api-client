package com.ecwid.apiclient.v3.dto.customer.result

import com.ecwid.apiclient.v3.dto.common.ApiFetchedDTO
import com.ecwid.apiclient.v3.dto.common.ApiFetchedDTO.ModifyKind
import com.ecwid.apiclient.v3.dto.customer.enums.CommercialRelationshipScheme
import com.ecwid.apiclient.v3.dto.customer.request.UpdatedCustomer
import com.ecwid.apiclient.v3.dto.extrafield.enums.ExtrafieldEntityType
import com.ecwid.apiclient.v3.dto.extrafield.enums.ExtrafieldType
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
	val contacts: List<CustomerContact>? = null,
	val taxId: String? = null,
	val taxIdValid: Boolean? = null,
	val taxExempt: Boolean? = null,
	val acceptMarketing: Boolean? = null,
	val lang: String? = null,
	val stats: CustomerStats? = null,
	val privateAdminNotes: String? = null,
	val favorites: List<CustomerFavorite> = ArrayList(),
	val extrafields: List<CustomerExtrafield>? = null,

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
		val phone: String? = null,
		val note: String? = null,
		val createdDate: Date? = null,
		val defaultAddress: Boolean? = null,
		val orderBy: Int? = null,
		val addressFormatted: String? = null,
	)

	data class CustomerStats(
		val numberOfOrders: Int = 0,
		val salesValue: Double = 0.0,
		val averageOrderValue: Double = 0.0,
		val firstOrderDate: Date? = null,
		val lastOrderDate: Date? = null,
	)

	data class CustomerContact(
		val id: Long = 0,
		val contact: String = "",
		val handle: String? = null,
		val note: String? = null,
		val type: String = "",
		val default: Boolean = false,
		val orderBy: Int = 0,
		val timestamp: Date? = null,
	)

	data class CustomerFavorite(
		val productId: Long = 0,
		val addedTimestamp: Date? = null,
	)

	data class CustomerExtrafield(
        val key: String? = null,
        val title: String? = null,
        val value: String? = null,
        val orderBy: Int = 0,
        val type: ExtrafieldType? = null,
        val entityTypes: List<ExtrafieldEntityType> = emptyList(),
	)

	override fun getModifyKind() = ModifyKind.ReadWrite(UpdatedCustomer::class)
}
