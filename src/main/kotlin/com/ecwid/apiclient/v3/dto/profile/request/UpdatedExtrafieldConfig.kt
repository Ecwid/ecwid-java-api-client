package com.ecwid.apiclient.v3.dto.profile.request

import com.ecwid.apiclient.v3.dto.common.ApiUpdatedDTO
import com.ecwid.apiclient.v3.dto.common.ApiUpdatedDTO.ModifyKind
import com.ecwid.apiclient.v3.dto.common.LocalizedValueMap
import com.ecwid.apiclient.v3.dto.extrafield.enums.ExtrafieldType
import com.ecwid.apiclient.v3.dto.profile.enums.CheckoutDisplaySection
import com.ecwid.apiclient.v3.dto.profile.enums.OrderDetailsDisplaySection
import com.ecwid.apiclient.v3.dto.profile.enums.SurchargeType
import com.ecwid.apiclient.v3.dto.profile.result.FetchedExtrafieldConfig

data class UpdatedExtrafieldConfig(
    val key: String? = null,
    val title: String? = null,
    val type: ExtrafieldType? = null,
    val textPlaceholder: String? = null,
    val tip: String? = null,
    val options: List<UpdatedExtrafieldOptionConfig>? = null,
    val value: String? = null,
    val available: Boolean? = null,
    val required: Boolean? = null,
    val checkoutDisplaySection: CheckoutDisplaySection? = null,
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
    val surchargeShortName: UpdatedExtrafieldSurchargeConfig? = null,
    val titleTranslated: LocalizedValueMap? = null,
    val textPlaceholderTranslated: LocalizedValueMap? = null,
    val tipTranslated: LocalizedValueMap? = null,
    val valueTranslated: LocalizedValueMap? = null,
	val saveToCustomerProfile: Boolean? = null,
) : ApiUpdatedDTO {

	data class UpdatedExtrafieldOptionConfig(
		val title: String? = null,
		val subtitle: String? = null,
		val surcharge: Double? = null,
		val titleTranslated: LocalizedValueMap? = null,
		val subtitleTranslated: LocalizedValueMap? = null
	) {
		constructor(
			title: String?,
			subtitle: String?,
			surcharge: Int?,
			titleTranslated: LocalizedValueMap?,
			subtitleTranslated: LocalizedValueMap?
		) : this(title, subtitle, surcharge?.toDouble(), titleTranslated, subtitleTranslated)
	}

	data class UpdatedExtrafieldSurchargeConfig(
		val name: String? = null,
		val showSurchargePercentValue: Boolean? = null,
		val nameTranslated: LocalizedValueMap? = null
	)

	override fun getModifyKind() = ModifyKind.ReadWrite(FetchedExtrafieldConfig::class)
}
