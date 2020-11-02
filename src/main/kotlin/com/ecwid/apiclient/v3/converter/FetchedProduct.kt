package com.ecwid.apiclient.v3.converter

import com.ecwid.apiclient.v3.dto.product.enums.toAttributeValueAlias
import com.ecwid.apiclient.v3.dto.product.request.UpdatedProduct
import com.ecwid.apiclient.v3.dto.product.result.FetchedProduct

fun FetchedProduct.toUpdated(): UpdatedProduct {
	return UpdatedProduct(
			name = name,
			nameTranslated = nameTranslated,
			description = description,
			descriptionTranslated = descriptionTranslated,
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

			isSampleProduct = isSampleProduct,

			seoTitle = seoTitle,
			seoDescription = seoDescription,

			options = options?.map(FetchedProduct.ProductOption::toUpdated),
			tax = tax?.toUpdated(),
			relatedProducts = relatedProducts?.toUpdated(),

			media = media?.toUpdated(),

			subtitle = subtitle,
			ribbon = ribbon?.toUpdated(),
			ribbonTranslated = ribbonTranslated,
			subtitleTranslated = subtitleTranslated,
			nameYourPriceEnabled = nameYourPriceEnabled
	)
}

private fun FetchedProduct.Ribbon.toUpdated() = UpdatedProduct.Ribbon(
		text = text,
		color = color
)

private fun FetchedProduct.WholesalePrice.toUpdated() = UpdatedProduct.WholesalePrice(
		quantity = quantity,
		price = price
)

private fun FetchedProduct.ProductOption.toUpdated() = when (this) {
	is FetchedProduct.ProductOption.SelectOption -> toUpdated()
	is FetchedProduct.ProductOption.SizeOption -> toUpdated()
	is FetchedProduct.ProductOption.RadioOption -> toUpdated()
	is FetchedProduct.ProductOption.CheckboxOption -> toUpdated()
	is FetchedProduct.ProductOption.TextFieldOption -> toUpdated()
	is FetchedProduct.ProductOption.TextAreaOption -> toUpdated()
	is FetchedProduct.ProductOption.DateOption -> toUpdated()
	is FetchedProduct.ProductOption.FilesOption -> toUpdated()
}

private fun FetchedProduct.ProductOption.SelectOption.toUpdated() = UpdatedProduct.ProductOption.SelectOption(
		name = name,
		nameTranslated = nameTranslated,
		choices = choices.map { it.toUpdated() },
		defaultChoice = defaultChoice,
		required = required
)

private fun FetchedProduct.ProductOption.SizeOption.toUpdated() = UpdatedProduct.ProductOption.SizeOption(
		name = name,
		nameTranslated = nameTranslated,
		choices = choices.map { it.toUpdated() },
		defaultChoice = defaultChoice,
		required = required
)

private fun FetchedProduct.ProductOption.RadioOption.toUpdated() = UpdatedProduct.ProductOption.RadioOption(
		name = name,
		nameTranslated = nameTranslated,
		choices = choices.map { it.toUpdated() },
		defaultChoice = defaultChoice,
		required = required
)

private fun FetchedProduct.ProductOption.CheckboxOption.toUpdated() = UpdatedProduct.ProductOption.CheckboxOption(
		name = name,
		nameTranslated = nameTranslated,
		choices = choices.map { it.toUpdated() }
)

private fun FetchedProduct.ProductOption.TextFieldOption.toUpdated() = UpdatedProduct.ProductOption.TextFieldOption(
		name = name,
		nameTranslated = nameTranslated,
		required = required
)

private fun FetchedProduct.ProductOption.TextAreaOption.toUpdated() = UpdatedProduct.ProductOption.TextAreaOption(
		name = name,
		nameTranslated = nameTranslated,
		required = required
)

private fun FetchedProduct.ProductOption.DateOption.toUpdated() = UpdatedProduct.ProductOption.DateOption(
		name = name,
		nameTranslated = nameTranslated,
		required = required
)

private fun FetchedProduct.ProductOption.FilesOption.toUpdated() = UpdatedProduct.ProductOption.FilesOption(
		name = name,
		nameTranslated = nameTranslated,
		required = required
)

private fun FetchedProduct.ProductOptionChoice.toUpdated() = UpdatedProduct.ProductOptionChoice(
		text = text,
		textTranslated = textTranslated,
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
		alias = type?.toAttributeValueAlias(),
		value = value
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

private fun FetchedProduct.TaxInfo.toUpdated() = UpdatedProduct.TaxInfo(
		taxable = taxable,
		enabledManualTaxes = enabledManualTaxes
)
