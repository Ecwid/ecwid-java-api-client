package com.ecwid.apiclient.v3.dto.variation.result

import com.ecwid.apiclient.v3.dto.common.ApiFetchedDTO
import com.ecwid.apiclient.v3.dto.common.ApiFetchedDTO.ModifyKind
import com.ecwid.apiclient.v3.dto.common.LocalizedValueMap
import com.ecwid.apiclient.v3.dto.product.enums.AttributeValueLocation
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
		val compareToPrice: Double? = null,
		val wholesalePrices: List<WholesalePrice>? = null,

		val quantity: Int? = null,
		val unlimited: Boolean? = null,
		val warningLimit: Int? = null,

		val weight: Double? = null,

		val attributes: List<AttributeValue>? = null,

		val isShippingRequired: Boolean? = null

) : ApiFetchedDTO {

	data class AttributeValue(
			val id: Int? = null,
			val name: String? = null,
			val type: AttributeType? = null,
			val value: String? = null,
			val show: AttributeValueLocation? = null
	)

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

	override fun getModifyKind() = ModifyKind.ReadWrite(UpdatedVariation::class)

}
