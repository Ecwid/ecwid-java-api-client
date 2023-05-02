package com.ecwid.apiclient.v3.converter

import com.ecwid.apiclient.v3.dto.profile.request.UpdatedPaymentOption
import com.ecwid.apiclient.v3.dto.profile.request.UpdatedStoreProfile
import com.ecwid.apiclient.v3.dto.profile.result.FetchedStoreProfile

fun FetchedStoreProfile.toUpdated(): UpdatedStoreProfile {
	return UpdatedStoreProfile(
		generalInfo = generalInfo?.toUpdated(),
		account = account?.toUpdated(),
		settings = settings?.toUpdated(),
		mailNotifications = mailNotifications?.toUpdated(),
		company = company?.toUpdated(),
		formatsAndUnits = formatsAndUnits?.toUpdated(),
		languages = languages?.toUpdated(),
		shipping = shipping?.toUpdated(),
		taxSettings = taxSettings?.toUpdated(),
		zones = zones?.map(FetchedStoreProfile.Zone::toUpdated),
		businessRegistrationID = businessRegistrationID?.toUpdated(),
		legalPagesSettings = legalPagesSettings?.toUpdated(),
		productFiltersSettings = productFiltersSettings.toUpdated(),
		orderInvoiceSettings = orderInvoiceSettings?.toUpdated()
	)
}

fun FetchedStoreProfile.GeneralInfo.toUpdated(): UpdatedStoreProfile.GeneralInfo {
	return UpdatedStoreProfile.GeneralInfo(
		storeUrl = storeUrl,
		starterSite = starterSite?.toUpdated(),
		websitePlatform = websitePlatform?.toUpdated()
	)
}

fun FetchedStoreProfile.InstantSiteInfo.toUpdated(): UpdatedStoreProfile.InstantSiteInfo {
	return UpdatedStoreProfile.InstantSiteInfo(
		ecwidSubdomain = ecwidSubdomain,
		customDomain = customDomain
	)
}

fun FetchedStoreProfile.WebsitePlatform.toUpdated(): UpdatedStoreProfile.WebsitePlatform {
	return when (this) {
		FetchedStoreProfile.WebsitePlatform.unknown -> UpdatedStoreProfile.WebsitePlatform.unknown

		FetchedStoreProfile.WebsitePlatform.adobeMuse -> UpdatedStoreProfile.WebsitePlatform.adobeMuse
		FetchedStoreProfile.WebsitePlatform.bitrix24 -> UpdatedStoreProfile.WebsitePlatform.bitrix24
		FetchedStoreProfile.WebsitePlatform.blogger -> UpdatedStoreProfile.WebsitePlatform.blogger
		FetchedStoreProfile.WebsitePlatform.drupal -> UpdatedStoreProfile.WebsitePlatform.drupal
		FetchedStoreProfile.WebsitePlatform.duda -> UpdatedStoreProfile.WebsitePlatform.duda
		FetchedStoreProfile.WebsitePlatform.etsy -> UpdatedStoreProfile.WebsitePlatform.etsy
		FetchedStoreProfile.WebsitePlatform.facebook -> UpdatedStoreProfile.WebsitePlatform.facebook
		FetchedStoreProfile.WebsitePlatform.godaddy -> UpdatedStoreProfile.WebsitePlatform.godaddy
		FetchedStoreProfile.WebsitePlatform.google_sites -> UpdatedStoreProfile.WebsitePlatform.google_sites
		FetchedStoreProfile.WebsitePlatform.iframe -> UpdatedStoreProfile.WebsitePlatform.iframe
		FetchedStoreProfile.WebsitePlatform.instagram -> UpdatedStoreProfile.WebsitePlatform.instagram
		FetchedStoreProfile.WebsitePlatform.instantsite -> UpdatedStoreProfile.WebsitePlatform.instantsite
		FetchedStoreProfile.WebsitePlatform.jimdo -> UpdatedStoreProfile.WebsitePlatform.jimdo
		FetchedStoreProfile.WebsitePlatform.joomla -> UpdatedStoreProfile.WebsitePlatform.joomla
		FetchedStoreProfile.WebsitePlatform.prestashop -> UpdatedStoreProfile.WebsitePlatform.prestashop
		FetchedStoreProfile.WebsitePlatform.rapidWeaver -> UpdatedStoreProfile.WebsitePlatform.rapidWeaver
		FetchedStoreProfile.WebsitePlatform.shopify -> UpdatedStoreProfile.WebsitePlatform.shopify
		FetchedStoreProfile.WebsitePlatform.squarespace -> UpdatedStoreProfile.WebsitePlatform.squarespace
		FetchedStoreProfile.WebsitePlatform.strikingly -> UpdatedStoreProfile.WebsitePlatform.strikingly
		FetchedStoreProfile.WebsitePlatform.tilda -> UpdatedStoreProfile.WebsitePlatform.tilda
		FetchedStoreProfile.WebsitePlatform.tumblr -> UpdatedStoreProfile.WebsitePlatform.tumblr
		FetchedStoreProfile.WebsitePlatform.typo3 -> UpdatedStoreProfile.WebsitePlatform.typo3
		FetchedStoreProfile.WebsitePlatform.ucraft -> UpdatedStoreProfile.WebsitePlatform.ucraft
		FetchedStoreProfile.WebsitePlatform.ukit -> UpdatedStoreProfile.WebsitePlatform.ukit
		FetchedStoreProfile.WebsitePlatform.webflow -> UpdatedStoreProfile.WebsitePlatform.webflow
		FetchedStoreProfile.WebsitePlatform.weblium -> UpdatedStoreProfile.WebsitePlatform.weblium
		FetchedStoreProfile.WebsitePlatform.weebly -> UpdatedStoreProfile.WebsitePlatform.weebly
		FetchedStoreProfile.WebsitePlatform.wix -> UpdatedStoreProfile.WebsitePlatform.wix
		FetchedStoreProfile.WebsitePlatform.wordpress -> UpdatedStoreProfile.WebsitePlatform.wordpress
		FetchedStoreProfile.WebsitePlatform.xara -> UpdatedStoreProfile.WebsitePlatform.xara
		FetchedStoreProfile.WebsitePlatform.yola -> UpdatedStoreProfile.WebsitePlatform.yola
	}
}

fun FetchedStoreProfile.Account.toUpdated(): UpdatedStoreProfile.Account {
	return UpdatedStoreProfile.Account(
		accountName = accountName,
		accountNickName = accountNickName,
		accountEmail = accountEmail
	)
}

fun FetchedStoreProfile.Settings.toUpdated(): UpdatedStoreProfile.Settings {
	return UpdatedStoreProfile.Settings(
		abandonedSales = abandonedSales?.toUpdated(),
		acceptMarketingCheckboxCustomText = acceptMarketingCheckboxCustomText,
		acceptMarketingCheckboxDefaultValue = acceptMarketingCheckboxDefaultValue,
		askCompanyName = askCompanyName,
		askConsentToTrackInStorefront = askConsentToTrackInStorefront,
		askTaxId = askTaxId,
		closed = closed,
		defaultProductSortOrder = defaultProductSortOrder?.toUpdated(),
		favoritesEnabled = favoritesEnabled,
		fbPixelId = fbPixelId,
		googleAnalyticsId = googleAnalyticsId,
		googleEventId = googleEventId,
		googleProductCategory = googleProductCategory,
		googleRemarketingEnabled = googleRemarketingEnabled,
		googleTagId = googleTagId,
		hideOutOfStockProductsInStorefront = hideOutOfStockProductsInStorefront,
		openBagOnAddition = openBagOnAddition,
		orderCommentsCaption = orderCommentsCaption,
		orderCommentsEnabled = orderCommentsEnabled,
		orderCommentsRequired = orderCommentsRequired,
		pinterestTagId = pinterestTagId,
		productCondition = productCondition,
		rootCategorySeoDescription = rootCategorySeoDescription,
		rootCategorySeoDescriptionTranslated = rootCategorySeoDescriptionTranslated,
		rootCategorySeoTitle = rootCategorySeoTitle,
		rootCategorySeoTitleTranslated = rootCategorySeoTitleTranslated,
		salePrice = salePrice?.toUpdated(),
		showAcceptMarketingCheckbox = showAcceptMarketingCheckbox,
		showPricePerUnit = showPricePerUnit,
		snapPixelId = snapPixelId,
		storeDescription = storeDescription,
		storeDescriptionTranslated = storeDescriptionTranslated,
		storeName = storeName,
		tikTokPixel = tikTokPixel?.toUpdated(),
	)
}

fun FetchedStoreProfile.TikTokPixelSettings.toUpdated(): UpdatedStoreProfile.TikTokPixelSettings {
	return UpdatedStoreProfile.TikTokPixelSettings(
		advancedMatching = advancedMatching
	)
}

fun FetchedStoreProfile.ProductSortOrder.toUpdated(): UpdatedStoreProfile.ProductSortOrder {
	return when (this) {
		FetchedStoreProfile.ProductSortOrder.DEFINED_BY_STORE_OWNER -> UpdatedStoreProfile.ProductSortOrder.DEFINED_BY_STORE_OWNER
		FetchedStoreProfile.ProductSortOrder.ADDED_TIME_DESC -> UpdatedStoreProfile.ProductSortOrder.ADDED_TIME_DESC
		FetchedStoreProfile.ProductSortOrder.PRICE_ASC -> UpdatedStoreProfile.ProductSortOrder.PRICE_ASC
		FetchedStoreProfile.ProductSortOrder.PRICE_DESC -> UpdatedStoreProfile.ProductSortOrder.PRICE_DESC
		FetchedStoreProfile.ProductSortOrder.NAME_ASC -> UpdatedStoreProfile.ProductSortOrder.NAME_ASC
		FetchedStoreProfile.ProductSortOrder.NAME_DESC -> UpdatedStoreProfile.ProductSortOrder.NAME_DESC
	}
}

fun FetchedStoreProfile.AbandonedSalesSettings.toUpdated(): UpdatedStoreProfile.AbandonedSalesSettings {
	return UpdatedStoreProfile.AbandonedSalesSettings(
		autoAbandonedSalesRecovery = autoAbandonedSalesRecovery
	)
}

fun FetchedStoreProfile.SalePriceSettings.toUpdated(): UpdatedStoreProfile.SalePriceSettings {
	return UpdatedStoreProfile.SalePriceSettings(
		displayOnProductList = displayOnProductList,
		oldPriceLabel = oldPriceLabel,
		displayDiscount = displayDiscount?.toUpdated()
	)
}

fun FetchedStoreProfile.SalePriceSettings.DisplayDiscount.toUpdated(): UpdatedStoreProfile.SalePriceSettings.DisplayDiscount {
	return when (this) {
		FetchedStoreProfile.SalePriceSettings.DisplayDiscount.NONE -> UpdatedStoreProfile.SalePriceSettings.DisplayDiscount.NONE
		FetchedStoreProfile.SalePriceSettings.DisplayDiscount.ABS -> UpdatedStoreProfile.SalePriceSettings.DisplayDiscount.ABS
		FetchedStoreProfile.SalePriceSettings.DisplayDiscount.PERCENT -> UpdatedStoreProfile.SalePriceSettings.DisplayDiscount.PERCENT
	}
}

fun FetchedStoreProfile.MailNotifications.toUpdated(): UpdatedStoreProfile.MailNotifications {
	return UpdatedStoreProfile.MailNotifications(
		adminNotificationEmails = adminNotificationEmails?.map { email -> email },
		customerNotificationFromEmail = customerNotificationFromEmail
	)
}

fun FetchedStoreProfile.Company.toUpdated(): UpdatedStoreProfile.Company {
	return UpdatedStoreProfile.Company(
		companyName = companyName,
		email = email,
		street = street,
		city = city,
		countryCode = countryCode,
		postalCode = postalCode,
		stateOrProvinceCode = stateOrProvinceCode,
		phone = phone
	)
}

fun FetchedStoreProfile.FormatsAndUnits.toUpdated(): UpdatedStoreProfile.FormatsAndUnits {
	return UpdatedStoreProfile.FormatsAndUnits(
		currency = currency,
		currencyPrefix = currencyPrefix,
		currencySuffix = currencySuffix,
		currencyGroupSeparator = currencyGroupSeparator,
		currencyDecimalSeparator = currencyDecimalSeparator,
		currencyTruncateZeroFractional = currencyTruncateZeroFractional,
		currencyRate = currencyRate,
		weightUnit = weightUnit?.toUpdated(),
		weightPrecision = weightPrecision,
		weightGroupSeparator = weightGroupSeparator,
		weightDecimalSeparator = weightDecimalSeparator,
		weightTruncateZeroFractional = weightTruncateZeroFractional,
		dateFormat = dateFormat,
		timeFormat = timeFormat,
		timezone = timezone,
		dimensionsUnit = dimensionsUnit?.toUpdated(),
		volumeUnit = volumeUnit.toUpdated(),
		orderNumberPrefix = orderNumberPrefix,
		orderNumberSuffix = orderNumberSuffix,
		orderNumberMinDigitsAmount = orderNumberMinDigitsAmount,
		orderNumberNextNumber = orderNumberNextNumber,
	)
}

fun FetchedStoreProfile.WeightUnit.toUpdated(): UpdatedStoreProfile.WeightUnit {
	return when (this) {
		FetchedStoreProfile.WeightUnit.CARAT -> UpdatedStoreProfile.WeightUnit.CARAT
		FetchedStoreProfile.WeightUnit.GRAM -> UpdatedStoreProfile.WeightUnit.GRAM
		FetchedStoreProfile.WeightUnit.OUNCE -> UpdatedStoreProfile.WeightUnit.OUNCE
		FetchedStoreProfile.WeightUnit.POUND -> UpdatedStoreProfile.WeightUnit.POUND
		FetchedStoreProfile.WeightUnit.KILOGRAM -> UpdatedStoreProfile.WeightUnit.KILOGRAM
	}
}

fun FetchedStoreProfile.DimensionUnit.toUpdated(): UpdatedStoreProfile.DimensionUnit {
	return when (this) {
		FetchedStoreProfile.DimensionUnit.MM -> UpdatedStoreProfile.DimensionUnit.MM
		FetchedStoreProfile.DimensionUnit.CM -> UpdatedStoreProfile.DimensionUnit.CM
		FetchedStoreProfile.DimensionUnit.IN -> UpdatedStoreProfile.DimensionUnit.IN
		FetchedStoreProfile.DimensionUnit.YD -> UpdatedStoreProfile.DimensionUnit.YD
	}
}

fun FetchedStoreProfile.VolumeUnit.toUpdated(): UpdatedStoreProfile.VolumeUnit {
	return when (this) {
		FetchedStoreProfile.VolumeUnit.L -> UpdatedStoreProfile.VolumeUnit.L
		FetchedStoreProfile.VolumeUnit.ML -> UpdatedStoreProfile.VolumeUnit.ML
		FetchedStoreProfile.VolumeUnit.OZ -> UpdatedStoreProfile.VolumeUnit.OZ
	}
}

fun FetchedStoreProfile.Languages.toUpdated(): UpdatedStoreProfile.Languages {
	return UpdatedStoreProfile.Languages(
		enabledLanguages = enabledLanguages?.map { enabledLanguage -> enabledLanguage },
		defaultLanguage = defaultLanguage
	)
}

fun FetchedStoreProfile.Shipping.toUpdated(): UpdatedStoreProfile.Shipping {
	return UpdatedStoreProfile.Shipping(
		handlingFee = handlingFee?.toUpdated()
	)
}

fun FetchedStoreProfile.HandlingFee.toUpdated(): UpdatedStoreProfile.HandlingFee {
	return UpdatedStoreProfile.HandlingFee(
		name = name,
		value = value,
		description = description
	)
}

fun FetchedStoreProfile.TaxSettings.toUpdated(): UpdatedStoreProfile.TaxSettings {
	return UpdatedStoreProfile.TaxSettings(
		automaticTaxEnabled = automaticTaxEnabled,
		taxes = taxes?.map(FetchedStoreProfile.TaxSettings.Taxes::toUpdated),
		pricesIncludeTax = pricesIncludeTax,
		taxExemptBusiness = taxExemptBusiness
	)
}

fun FetchedStoreProfile.TaxSettings.Taxes.toUpdated(): UpdatedStoreProfile.TaxSettings.Taxes {
	return UpdatedStoreProfile.TaxSettings.Taxes(
		id = id,
		name = name,
		enabled = enabled,
		includeInPrice = includeInPrice,
		useShippingAddress = useShippingAddress,
		taxShipping = taxShipping,
		appliedByDefault = appliedByDefault,
		defaultTax = defaultTax,
		rules = rules?.map(FetchedStoreProfile.TaxSettings.TaxRule::toUpdated)
	)
}

fun FetchedStoreProfile.TaxSettings.TaxRule.toUpdated(): UpdatedStoreProfile.TaxSettings.TaxRule {
	return UpdatedStoreProfile.TaxSettings.TaxRule(
		zoneId = zoneId,
		tax = tax
	)
}

fun FetchedStoreProfile.Zone.toUpdated(): UpdatedStoreProfile.Zone {
	return UpdatedStoreProfile.Zone(
		id = id,
		name = name,
		countryCodes = countryCodes?.map { countryCode -> countryCode },
		stateOrProvinceCodes = stateOrProvinceCodes?.map { stateOrProvinceCode -> stateOrProvinceCode },
		postCodes = postCodes?.map { postCode -> postCode }
	)
}

fun FetchedStoreProfile.BusinessRegistrationID.toUpdated(): UpdatedStoreProfile.BusinessRegistrationID {
	return UpdatedStoreProfile.BusinessRegistrationID(
		name = name,
		value = value
	)
}

fun FetchedStoreProfile.LegalPagesSettingsDetails.toUpdated(): UpdatedStoreProfile.LegalPagesSettingsDetails {
	return UpdatedStoreProfile.LegalPagesSettingsDetails(
		requireTermsAgreementAtCheckout = requireTermsAgreementAtCheckout,
		legalPages = legalPages?.map(FetchedStoreProfile.LegalPagesInfo::toUpdated)
	)
}

fun FetchedStoreProfile.LegalPagesInfo.toUpdated(): UpdatedStoreProfile.LegalPagesInfo {
	return UpdatedStoreProfile.LegalPagesInfo(
		type = type?.toUpdated(),
		enabled = enabled,
		title = title,
		display = display?.toUpdated(),
		text = text,
		externalUrl = externalUrl
	)
}

fun FetchedStoreProfile.LegalPagesInfo.Type.toUpdated(): UpdatedStoreProfile.LegalPagesInfo.Type {
	return when (this) {
		FetchedStoreProfile.LegalPagesInfo.Type.LEGAL_INFO -> UpdatedStoreProfile.LegalPagesInfo.Type.LEGAL_INFO
		FetchedStoreProfile.LegalPagesInfo.Type.SHIPPING_COST_PAYMENT_INFO -> UpdatedStoreProfile.LegalPagesInfo.Type.SHIPPING_COST_PAYMENT_INFO
		FetchedStoreProfile.LegalPagesInfo.Type.REVOCATION_TERMS -> UpdatedStoreProfile.LegalPagesInfo.Type.REVOCATION_TERMS
		FetchedStoreProfile.LegalPagesInfo.Type.TERMS -> UpdatedStoreProfile.LegalPagesInfo.Type.TERMS
		FetchedStoreProfile.LegalPagesInfo.Type.PRIVACY_STATEMENT -> UpdatedStoreProfile.LegalPagesInfo.Type.PRIVACY_STATEMENT
	}
}

fun FetchedStoreProfile.LegalPagesInfo.Display.toUpdated(): UpdatedStoreProfile.LegalPagesInfo.Display {
	return when (this) {
		FetchedStoreProfile.LegalPagesInfo.Display.INLINE -> UpdatedStoreProfile.LegalPagesInfo.Display.INLINE
		FetchedStoreProfile.LegalPagesInfo.Display.EXTERNAL_URL -> UpdatedStoreProfile.LegalPagesInfo.Display.EXTERNAL_URL
	}
}

internal fun FetchedStoreProfile.ProductFiltersSettings.toUpdated(): UpdatedStoreProfile.ProductFiltersSettings {
	return UpdatedStoreProfile.ProductFiltersSettings(
		enabledInStorefront = enabledInStorefront,
		filterSections = filterSections.map { it.toUpdated() }
	)
}

fun FetchedStoreProfile.ProductFilterItem.toUpdated(): UpdatedStoreProfile.ProductFilterItem {
	return UpdatedStoreProfile.ProductFilterItem(
		name = name,
		type = type,
		enabled = enabled,
	)
}

fun FetchedStoreProfile.OrderInvoiceSettings.toUpdated(): UpdatedStoreProfile.OrderInvoiceSettings {
	return UpdatedStoreProfile.OrderInvoiceSettings(
		displayOrderInvoices = displayOrderInvoices,
		attachInvoiceToOrderEmailNotifications = attachInvoiceToOrderEmailNotifications?.toUpdated(),
		invoiceLogoUrl = invoiceLogoUrl
	)
}

fun FetchedStoreProfile.OrderInvoiceSettings.AttachValue.toUpdated(): UpdatedStoreProfile.OrderInvoiceSettings.AttachValue {
	return when (this) {
		FetchedStoreProfile.OrderInvoiceSettings.AttachValue.ATTACH_TO_ALL_EMAILS -> UpdatedStoreProfile.OrderInvoiceSettings.AttachValue.ATTACH_TO_ALL_EMAILS
		FetchedStoreProfile.OrderInvoiceSettings.AttachValue.DO_NOT_ATTACH -> UpdatedStoreProfile.OrderInvoiceSettings.AttachValue.DO_NOT_ATTACH
	}
}

fun FetchedStoreProfile.PaymentOptionInfo.toUpdated(): UpdatedPaymentOption {
	return UpdatedPaymentOption(
		appClientId = appClientId,
		appNamespace = appNamespace,
		checkoutDescription = checkoutDescription,
		checkoutTitle = checkoutTitle,
		configured = configured,
		enabled = enabled,
		instructionsForCustomer = instructionsForCustomer?.toUpdated(),
		methods = methods?.map(FetchedStoreProfile.PaymentMethod::toUpdated),
		orderBy = orderBy,
		paymentProcessorId = paymentProcessorId,
		shippingSettings = shippingSettings?.toUpdated(),
		supportsSubtypes = supportsSubtypes,
	)
}

private fun FetchedStoreProfile.InstructionsForCustomerInfo.toUpdated(): UpdatedPaymentOption.InstructionsForCustomerInfo {
	return UpdatedPaymentOption.InstructionsForCustomerInfo(
		instructionsTitle = instructionsTitle,
		instructions = instructions,
	)
}

private fun FetchedStoreProfile.PaymentMethod.toUpdated(): UpdatedPaymentOption.PaymentMethod {
	return UpdatedPaymentOption.PaymentMethod(
		cards = cards,
		subtype = subtype,
		subtypeMethodName = subtypeMethodName,
	)
}

private fun FetchedStoreProfile.ShippingSettings.toUpdated(): UpdatedPaymentOption.ShippingSettings {
	return UpdatedPaymentOption.ShippingSettings(
		enabledShippingMethods = enabledShippingMethods,
	)
}
