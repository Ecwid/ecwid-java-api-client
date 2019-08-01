package com.ecwid.apiclient.v3.dto.variation.result

import com.ecwid.apiclient.v3.dto.product.enums.AttributeValueAlias
import com.ecwid.apiclient.v3.dto.product.enums.AttributeValueLocation

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
		val compareToPrice: Double? = null,
		val wholesalePrices: List<WholesalePrice>? = null,

		val quantity: Int? = null,
		val unlimited: Boolean? = null,
		val warningLimit: Int? = null,

		val weight: Double? = null,

		val attributes: List<AttributeValue>? = null

) {

	data class AttributeValue(
			val id: Int? = null,
			val name: String? = null,
			val alias: AttributeValueAlias? = null,
			val value: String? = null,
			val show: AttributeValueLocation? = null
	)

	data class WholesalePrice(
			val quantity: Int = 0,
			val price: Double = 0.0
	)

	data class Option(
			val name: String? = null,
			val nameTranslated: Map<String, String>? = null,
			val value: String? = null,
			val valueTranslated: Map<String, String>? = null
	)
}