package com.ecwid.apiclient.v3.converter

import com.ecwid.apiclient.v3.dto.product.request.UpdatedProduct
import com.ecwid.apiclient.v3.dto.product.result.FetchedProduct

fun FetchedProduct.toUpdated(): UpdatedProduct {
	return UpdatedProduct(
			name = name,
			description = description,
			sku = sku,

			enabled = enabled,
			quantity = quantity,
			unlimited = unlimited,
			warningLimit = warningLimit,

			categoryIds = categoryIds,
			defaultCategoryId = defaultCategoryId,
			showOnFrontpage = showOnFrontpage,

			price = price,
			wholesalePrices = wholesalePrices?.map(FetchedProduct.WholesalePrice::toUpdated),
			compareToPrice = compareToPrice,

			weight = weight,
			dimensions = dimensions?.toUpdated(),
			shipping = shipping?.toUpdated(),
			isShippingRequired = isShippingRequired,

			productClassId = productClassId,
			attributes = attributes?.map(FetchedProduct.AttributeValue::toUpdated),

			seoTitle = seoTitle,
			seoDescription = seoDescription,

			options = options?.map(FetchedProduct.ProductOption::toUpdated),
//			tax: TaxInfo? = null, TODO restore when tax field will be filled
			relatedProducts = relatedProducts?.toUpdated(),

			media = media?.toUpdated()
	)
}

private fun FetchedProduct.WholesalePrice.toUpdated() = UpdatedProduct.WholesalePrice(
		quantity = quantity,
		price = price
)

private fun FetchedProduct.ProductOption.toUpdated() = UpdatedProduct.ProductOption(
		type = type,
		name = name,
		choices = choices?.map { it.toUpdated() },
		defaultChoice = defaultChoice,
		required = required
)

private fun FetchedProduct.ProductOptionChoice.toUpdated() = UpdatedProduct.ProductOptionChoice(
		text = text,
		priceModifier = priceModifier,
		priceModifierType = priceModifierType
)

private fun FetchedProduct.ShippingSettings.toUpdated() = UpdatedProduct.ShippingSettings(
		type = type,
		methodMarkup = methodMarkup,
		flatRate = flatRate,
		disabledMethods = disabledMethods,
		enabledMethods = enabledMethods
)

private fun FetchedProduct.AttributeValue.toUpdated() = UpdatedProduct.AttributeValue(
		id = id,
		alias = alias,
		value = value,
		show = show
)

private fun FetchedProduct.RelatedProducts.toUpdated() = UpdatedProduct.RelatedProducts(
		productIds = productIds,
		relatedCategory = relatedCategory?.toUpdated()
)

private fun FetchedProduct.RelatedCategory.toUpdated() = UpdatedProduct.RelatedCategory(
		enabled = enabled,
		categoryId = categoryId,
		productCount = productCount
)

private fun FetchedProduct.ProductDimensions.toUpdated() = UpdatedProduct.ProductDimensions(
		length = length,
		width = width,
		height = height
)

fun FetchedProduct.ProductMedia.toUpdated() = UpdatedProduct.ProductMedia(
		images = images.map(FetchedProduct.ProductImage::toUpdated)
)

private fun FetchedProduct.ProductImage.toUpdated() = UpdatedProduct.ProductImage(
		id = id,
		orderBy = orderBy
)
