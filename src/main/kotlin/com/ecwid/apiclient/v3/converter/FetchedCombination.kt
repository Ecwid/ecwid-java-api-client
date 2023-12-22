package com.ecwid.apiclient.v3.converter

import com.ecwid.apiclient.v3.dto.product.enums.toAttributeValueAlias
import com.ecwid.apiclient.v3.dto.variation.request.UpdatedVariation
import com.ecwid.apiclient.v3.dto.variation.result.FetchedVariation

fun FetchedVariation.toUpdated(): UpdatedVariation {
	return UpdatedVariation(
		sku = sku,

		quantity = quantity,
		locationInventory = locationInventory,
		outOfStockVisibilityBehaviour = outOfStockVisibilityBehaviour,
		unlimited = unlimited,
		warningLimit = warningLimit,
		minPurchaseQuantity = minPurchaseQuantity,
		maxPurchaseQuantity = maxPurchaseQuantity,

		price = price,
		costPrice = costPrice,
		wholesalePrices = wholesalePrices?.map(FetchedVariation.WholesalePrice::toUpdated),
		compareToPrice = compareToPrice,
		lowestPrice = lowestPrice,

		weight = weight,
		dimensions = dimensions?.toUpdated(),
		volume = volume,

		attributes = attributes?.map(FetchedVariation.AttributeValue::toUpdated),

		externalReferenceId = externalReferenceId,

		options = options?.map(FetchedVariation.Option::toUpdated),

		isShippingRequired = isShippingRequired,

		customsHsTariffCode = customsHsTariffCode,
		subscriptionSettings = subscriptionSettings?.toUpdated(),
	)
}

fun FetchedVariation.WholesalePrice.toUpdated() = UpdatedVariation.WholesalePrice(
	quantity = quantity,
	price = price
)

fun FetchedVariation.AttributeValue.toUpdated() = UpdatedVariation.AttributeValue(
	id = id,
	alias = type?.toAttributeValueAlias(),
	value = value,
	valueTranslated = valueTranslated,
	show = show,
)

fun FetchedVariation.Option.toUpdated() = UpdatedVariation.Option(
	name = name,
	value = value
)

fun FetchedVariation.ProductDimensions.toUpdated() = UpdatedVariation.ProductDimensions(
	length = length,
	width = width,
	height = height
)

private fun FetchedVariation.SubscriptionSettings.toUpdated() = UpdatedVariation.SubscriptionSettings(
	subscriptionAllowed = subscriptionAllowed,
	oneTimePurchaseAllowed = oneTimePurchaseAllowed,
	oneTimePurchasePrice = oneTimePurchasePrice,
	recurringChargeSettings = recurringChargeSettings.toUpdated()
)

private fun List<FetchedVariation.RecurringChargeSettings>.toUpdated() = map {
	UpdatedVariation.RecurringChargeSettings(
		recurringInterval = it.recurringInterval,
		recurringIntervalCount = it.recurringIntervalCount,
		subscriptionPriceWithSignUpFee = it.subscriptionPriceWithSignUpFee
	)
}
