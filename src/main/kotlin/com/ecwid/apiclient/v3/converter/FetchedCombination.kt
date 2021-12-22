package com.ecwid.apiclient.v3.converter

import com.ecwid.apiclient.v3.dto.product.enums.toAttributeValueAlias
import com.ecwid.apiclient.v3.dto.variation.request.UpdatedVariation
import com.ecwid.apiclient.v3.dto.variation.result.FetchedVariation

fun FetchedVariation.toUpdated(): UpdatedVariation {
	return UpdatedVariation(
		sku = sku,

		quantity = quantity,
		unlimited = unlimited,
		warningLimit = warningLimit,

		price = price,
		wholesalePrices = wholesalePrices?.map(FetchedVariation.WholesalePrice::toUpdated),
		compareToPrice = compareToPrice,

		weight = weight,
		dimensions = dimensions?.toUpdated(),

		attributes = attributes?.map(FetchedVariation.AttributeValue::toUpdated),

		options = options?.map(FetchedVariation.Option::toUpdated),

		isShippingRequired = isShippingRequired
	)
}

private fun FetchedVariation.WholesalePrice.toUpdated() = UpdatedVariation.WholesalePrice(
	quantity = quantity,
	price = price
)

private fun FetchedVariation.AttributeValue.toUpdated() = UpdatedVariation.AttributeValue(
	id = id,
	alias = type?.toAttributeValueAlias(),
	value = value
)

private fun FetchedVariation.Option.toUpdated() = UpdatedVariation.Option(
	name = name,
	value = value
)

private fun FetchedVariation.ProductDimensions.toUpdated() = UpdatedVariation.ProductDimensions(
	length = length,
	width = width,
	height = height
)
