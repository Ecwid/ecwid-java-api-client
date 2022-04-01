package com.ecwid.apiclient.v3.dto.profile.result

import com.ecwid.apiclient.v3.dto.common.ApiFetchedDTO
import com.ecwid.apiclient.v3.dto.common.ApiFetchedDTO.ModifyKind
import com.ecwid.apiclient.v3.dto.profile.enums.ExtrafieldType
import com.ecwid.apiclient.v3.dto.profile.enums.SurchargeType
import com.ecwid.apiclient.v3.dto.profile.enums.OrderDetailsDisplaySection
import com.ecwid.apiclient.v3.dto.profile.enums.CheckoutDisplaySection
import com.ecwid.apiclient.v3.dto.profile.request.UpdatedExtrafieldConfig

data class FetchedExtrafieldConfig(
	val key: String = "",
	val title: String = "",
	val type: ExtrafieldType? = null,
	val textPlaceholder: String? = null,
	val tip: String? = null,
	val options: List<FetchedExtrafieldOptionConfig>? = null,
	val value: String? = null,
	val available: Boolean? = null,
	val required: Boolean? = null,
	val checkoutDisplaySection: CheckoutDisplaySection = CheckoutDisplaySection.email,
	val orderDetailsDisplaySection: OrderDetailsDisplaySection? = null,
	val showForCountry: List<String>? = null,
	val showForPaymentMethodIds: List<String>? = null,
	val showForShippingMethodIds: List<String>? = null,
	val showInInvoice: Boolean? = null,
	val showInNotifications: Boolean? = null,
	val orderBy: Int? = null,
	val surchargeType: SurchargeType? = null,
	val surchargeTaxable: Boolean? = null,
	val showZeroSurchargeInTotal: Boolean? = null,
	val surchargeShortName: FetchedExtrafieldSurchargeConfig? = null,
	val titleTranslated: Map<String, String>? = null,
	val textPlaceholderTranslated: Map<String, String>? = null,
	val tipTranslated: Map<String, String>? = null,
	val valueTranslated: Map<String, String>? = null
) : ApiFetchedDTO {

	data class FetchedExtrafieldOptionConfig(
		val title: String? = null,
		val subtitle: String? = null,
		val surcharge: Int? = null,
		val titleTranslated: Map<String, String>? = null,
		val subtitleTranslated: Map<String, String>? = null
	)

	data class FetchedExtrafieldSurchargeConfig(
		val name: String? = null,
		val showSurchargePercentValue: Boolean? = null,
		val nameTranslated: Map<String, String>? = null
	)

	override fun getModifyKind() = ModifyKind.ReadWrite(UpdatedExtrafieldConfig::class)
}
