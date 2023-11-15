package com.ecwid.apiclient.v3.dto.variation.request

import com.ecwid.apiclient.v3.dto.common.ApiUpdatedDTO
import com.ecwid.apiclient.v3.dto.common.ApiUpdatedDTO.ModifyKind
import com.ecwid.apiclient.v3.dto.common.LocalizedValueMap
import com.ecwid.apiclient.v3.dto.common.UpdatedAttributeValue
import com.ecwid.apiclient.v3.dto.product.enums.AttributeValueAlias
import com.ecwid.apiclient.v3.dto.product.enums.AttributeValueLocation
import com.ecwid.apiclient.v3.dto.product.enums.OutOfStockVisibilityBehaviour
import com.ecwid.apiclient.v3.dto.product.enums.RecurringSubscriptionInterval
import com.ecwid.apiclient.v3.dto.variation.result.FetchedVariation

data class UpdatedVariation(
	val sku: String? = null,

	val options: List<Option>? = null,

	val price: Double? = null,
	val costPrice: Double? = null,
	val compareToPrice: Double? = null,
	val lowestPrice: Double? = null,
	val wholesalePrices: List<WholesalePrice>? = null,

	val quantity: Int? = null,
	val outOfStockVisibilityBehaviour: OutOfStockVisibilityBehaviour? = null,
	val unlimited: Boolean? = null,
	val warningLimit: Int? = null,
	val minPurchaseQuantity: Int? = null,
	val maxPurchaseQuantity: Int? = null,

	val weight: Double? = null,
	val dimensions: ProductDimensions? = null,
	val volume: Double? = null,

	val attributes: List<AttributeValue>? = null,
	val externalReferenceId: String? = null,

	val isShippingRequired: Boolean? = null,

	val customsHsTariffCode: String? = null,
	val subscriptionSettings: SubscriptionSettings? = null,
) : ApiUpdatedDTO {

	data class AttributeValue(
		override val id: Int? = null,
		override val alias: AttributeValueAlias? = null,
		override val name: String? = null,
		override val value: String? = null,
		override val valueTranslated: LocalizedValueMap? = null,
		override val show: AttributeValueLocation? = null,
	) : UpdatedAttributeValue

	data class WholesalePrice(
		val quantity: Int = 0,
		val price: Double = 0.0
	)

	data class Option(
		val name: String? = null,
		val value: String? = null
	)

	data class ProductDimensions(
		val length: Double? = null,
		val width: Double? = null,
		val height: Double? = null
	)

	data class SubscriptionSettings(
		val subscriptionAllowed: Boolean? = null,
		val oneTimePurchaseAllowed: Boolean? = null,
		val recurringChargeSettings: List<RecurringChargeSettings>? = null,
		val oneTimePurchasePrice: Double? = null,
	)

	data class RecurringChargeSettings(
		val recurringInterval: RecurringSubscriptionInterval = RecurringSubscriptionInterval.MONTH,
		val recurringIntervalCount: Int = 1,
		val subscriptionPriceWithSignUpFee: Double? = null,
	)

	override fun getModifyKind() = ModifyKind.ReadWrite(FetchedVariation::class)
}
