package com.ecwid.apiclient.v3.dto.variation.result

import com.ecwid.apiclient.v3.dto.common.*
import com.ecwid.apiclient.v3.dto.common.ApiFetchedDTO.ModifyKind
import com.ecwid.apiclient.v3.dto.product.enums.AttributeValueLocation
import com.ecwid.apiclient.v3.dto.product.enums.OutOfStockVisibilityBehaviour
import com.ecwid.apiclient.v3.dto.product.enums.RecurringSubscriptionInterval
import com.ecwid.apiclient.v3.dto.producttype.enums.AttributeType
import com.ecwid.apiclient.v3.dto.variation.request.UpdatedVariation

data class FetchedVariation(
	val id: Int = 0,
	val sku: String? = null,

	val combinationNumber: Int = 0,

	val options: List<Option>? = null,

	val smallThumbnailUrl: String? = null,
	val hdThumbnailUrl: String? = null,
	val thumbnailUrl: String? = null,
	val imageUrl: String? = null,
	val originalImageUrl: String? = null,

	val price: Double? = null,
	val defaultDisplayedPrice: Double? = null,
	val defaultDisplayedPriceFormatted: String? = null,
	val costPrice: Double? = null,
	val compareToPrice: Double? = null,
	val lowestPrice: Double? = null,
	val defaultDisplayedLowestPrice: Double? = null,
	val defaultDisplayedLowestPriceFormatted: String? = null,
	val wholesalePrices: List<WholesalePrice>? = null,
	val lowestPriceSettings: LowestPriceSettings = LowestPriceSettings(),

	val quantity: Int? = null,
	val locationInventory: Map<String, Int>? = null,
	val outOfStockVisibilityBehaviour: OutOfStockVisibilityBehaviour? = null,
	val unlimited: Boolean? = null,
	val inStock: Boolean? = null,
	val warningLimit: Int? = null,
	val minPurchaseQuantity: Int? = null,
	val maxPurchaseQuantity: Int? = null,

	val weight: Double? = null,
	val dimensions: ProductDimensions? = null,
	val volume: Double = 0.0,
	val borderInfo: BorderInfo? = null,

	val attributes: List<AttributeValue>? = null,
	val externalReferenceId: String? = null,

	val isShippingRequired: Boolean? = null,

	val customsHsTariffCode: String? = null,
	val subscriptionSettings: SubscriptionSettings? = null,
	val alt: FetchedAlt? = null,
	val imageExternalId: String? = null,
) : ApiFetchedDTO, ApiResultDTO {

	data class AttributeValue(
		override val id: Int? = null,
		override val name: String? = null,
		override val type: AttributeType? = null,
		override val value: String? = null,
		override val valueTranslated: LocalizedValueMap? = null,
		override val show: AttributeValueLocation? = null
	) : FetchedAttributeValue

	data class WholesalePrice(
		val quantity: Int = 0,
		val price: Double = 0.0
	)

	data class Option(
		val name: String? = null,
		val nameTranslated: LocalizedValueMap? = null,
		val value: String? = null,
		val valueTranslated: LocalizedValueMap? = null
	)

	data class BorderInfo(
		val dominatingColor: Color? = null,
		val homogeneity: Boolean? = false
	)

	data class Color(
		val red: Int? = null,
		val green: Int? = null,
		val blue: Int? = null,
		val alpha: Int? = null
	)

	data class ProductDimensions(
		val length: Double = 0.0,
		val width: Double = 0.0,
		val height: Double = 0.0
	)

	data class SubscriptionSettings(
		val subscriptionAllowed: Boolean = false,
		val oneTimePurchaseAllowed: Boolean = false,
		val recurringChargeSettings: List<RecurringChargeSettings> = emptyList(),
		val oneTimePurchasePrice: Double? = null,
		val oneTimePurchasePriceFormatted: String? = null,
		val oneTimePurchaseMarkup: Double? = null,
		val oneTimePurchaseMarkupFormatted: String? = null,
		val oneTimePurchaseMarkupPercent: Double? = null,
		val oneTimePurchaseMarkupPercentFormatted: String? = null,
		val displayedOneTimePurchaseMarkupPercent: Double? = null,
		val displayedOneTimePurchaseMarkupPercentFormatted: String? = null
	)

	data class RecurringChargeSettings(
		val recurringInterval: RecurringSubscriptionInterval = RecurringSubscriptionInterval.MONTH,
		val recurringIntervalCount: Int = 1,
		val subscriptionPriceWithSignUpFee: Double? = null,
		val subscriptionPriceWithSignUpFeeFormatted: String? = null,
		val signUpFee: Double? = null,
		val signUpFeeFormatted: String? = null
	)

	data class LowestPriceSettings(
		val lowestPriceEnabled: Boolean = false,
		val manualLowestPrice: Double? = null,
		val defaultDisplayedLowestPrice: Double? = null,
		val defaultDisplayedLowestPriceFormatted: String? = null,
		val automaticLowestPrice: Double? = null,
		val defaultDisplayedAutomaticLowestPrice: Double? = null,
		val defaultDisplayedAutomaticLowestPriceFormatted: String? = null,
	)

	override fun getModifyKind() = ModifyKind.ReadWrite(UpdatedVariation::class)
}
