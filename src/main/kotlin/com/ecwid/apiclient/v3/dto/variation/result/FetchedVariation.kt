package com.ecwid.apiclient.v3.dto.variation.result

import com.ecwid.apiclient.v3.dto.common.ApiFetchedDTO
import com.ecwid.apiclient.v3.dto.common.ApiFetchedDTO.ModifyKind
import com.ecwid.apiclient.v3.dto.common.FetchedAttributeValue
import com.ecwid.apiclient.v3.dto.common.LocalizedValueMap
import com.ecwid.apiclient.v3.dto.product.enums.AttributeValueLocation
import com.ecwid.apiclient.v3.dto.product.enums.OutOfStockVisibilityBehaviour
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
	val wholesalePrices: List<WholesalePrice>? = null,

	val quantity: Int? = null,
	val outOfStockVisibilityBehaviour: OutOfStockVisibilityBehaviour? = null,
	val unlimited: Boolean? = null,
	val inStock: Boolean? = null,
	val warningLimit: Int? = null,

	val weight: Double? = null,
	val dimensions: ProductDimensions? = null,
	val volume: Double = 0.0,
	val borderInfo: BorderInfo? = null,

	val attributes: List<AttributeValue>? = null,
	val externalReferenceId: String? = null,

	val isShippingRequired: Boolean? = null,

	val customsHsTariffCode: String? = null,
) : ApiFetchedDTO {

	data class AttributeValue(
		override val id: Int? = null,
		override val name: String? = null,
		override val type: AttributeType? = null,
		override val value: String? = null,
		override val show: AttributeValueLocation? = null
	) : FetchedAttributeValue<AttributeValue> {

		override fun cast() = AttributeValue(
			id = id,
			name = name,
			type = type,
			value = value,
			show = show
		)
	}

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

	override fun getModifyKind() = ModifyKind.ReadWrite(UpdatedVariation::class)
}
