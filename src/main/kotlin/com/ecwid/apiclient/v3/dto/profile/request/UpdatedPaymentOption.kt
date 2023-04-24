package com.ecwid.apiclient.v3.dto.profile.request

import com.ecwid.apiclient.v3.dto.common.ApiUpdatedDTO
import com.ecwid.apiclient.v3.dto.profile.result.FetchedStoreProfile

data class UpdatedPaymentOption(
	val appClientId: String? = null,
	val checkoutDescription: String? = null,
	val checkoutTitle: String? = null,
	val configured: Boolean? = null,
	val enabled: Boolean? = null,
	val instructionsForCustomer: InstructionsForCustomerInfo? = null,
	val methods: List<PaymentMethod>? = null,
	val orderBy: Int? = null,
	val paymentProcessorId: String? = null,
	val shippingSettings: ShippingSettings? = null,
	val supportsSubtypes: Boolean? = null,
) : ApiUpdatedDTO {

	data class InstructionsForCustomerInfo(
		val instructionsTitle: String? = null,
		val instructions: String? = null
	)

	data class ShippingSettings(
		val enabledShippingMethods: List<String>? = null
	)

	data class PaymentMethod(
		val cards: List<String>? = null,
		val subtype: String? = null,
		val subtypeMethodName: String? = null,
	)

	override fun getModifyKind() = ApiUpdatedDTO.ModifyKind.ReadWrite(FetchedStoreProfile.PaymentOptionInfo::class)
}
