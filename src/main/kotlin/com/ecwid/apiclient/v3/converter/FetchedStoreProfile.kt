package com.ecwid.apiclient.v3.converter

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
		orderInvoiceSettings = orderInvoiceSettings?.toUpdated()
	)
}

private fun FetchedStoreProfile.GeneralInfo.toUpdated(): UpdatedStoreProfile.GeneralInfo {
	return UpdatedStoreProfile.GeneralInfo(
		storeUrl = storeUrl,
		starterSite = starterSite?.toUpdated(),
		websitePlatform = websitePlatform?.toUpdated()
	)
}

private fun FetchedStoreProfile.InstantSiteInfo.toUpdated(): UpdatedStoreProfile.InstantSiteInfo {
	return UpdatedStoreProfile.InstantSiteInfo(
		ecwidSubdomain = ecwidSubdomain,
		customDomain = customDomain
	)
}

private fun FetchedStoreProfile.WebsitePlatform.toUpdated(): UpdatedStoreProfile.WebsitePlatform {
	return when (this) {
		FetchedStoreProfile.WebsitePlatform.wix -> UpdatedStoreProfile.WebsitePlatform.wix
		FetchedStoreProfile.WebsitePlatform.wordpress -> UpdatedStoreProfile.WebsitePlatform.wordpress
		FetchedStoreProfile.WebsitePlatform.iframe -> UpdatedStoreProfile.WebsitePlatform.iframe
		FetchedStoreProfile.WebsitePlatform.joomla -> UpdatedStoreProfile.WebsitePlatform.joomla
		FetchedStoreProfile.WebsitePlatform.yola -> UpdatedStoreProfile.WebsitePlatform.yola
		FetchedStoreProfile.WebsitePlatform.unknown -> UpdatedStoreProfile.WebsitePlatform.unknown
	}
}

private fun FetchedStoreProfile.Account.toUpdated(): UpdatedStoreProfile.Account {
	return UpdatedStoreProfile.Account(
		accountName = accountName,
		accountNickName = accountNickName,
		accountEmail = accountEmail
	)
}

private fun FetchedStoreProfile.Settings.toUpdated(): UpdatedStoreProfile.Settings {
	return UpdatedStoreProfile.Settings(
		closed = closed,
		storeName = storeName,
		storeDescription = storeDescription,
		googleRemarketingEnabled = googleRemarketingEnabled,
		googleAnalyticsId = googleAnalyticsId,
		fbPixelId = fbPixelId,
		orderCommentsEnabled = orderCommentsEnabled,
		orderCommentsCaption = orderCommentsCaption,
		orderCommentsRequired = orderCommentsRequired,
		hideOutOfStockProductsInStorefront = hideOutOfStockProductsInStorefront,
		askCompanyName = askCompanyName,
		favoritesEnabled = favoritesEnabled,
		defaultProductSortOrder = defaultProductSortOrder?.toUpdated(),
		abandonedSales = abandonedSales?.toUpdated(),
		salePrice = salePrice?.toUpdated(),
		showAcceptMarketingCheckbox = showAcceptMarketingCheckbox,
		acceptMarketingCheckboxDefaultValue = acceptMarketingCheckboxDefaultValue,
		acceptMarketingCheckboxCustomText = acceptMarketingCheckboxCustomText,
		askConsentToTrackInStorefront = askConsentToTrackInStorefront,
		snapPixelId = snapPixelId,
		pinterestTagId = pinterestTagId,
		googleTagId = googleTagId,
		googleEventId = googleEventId,
		showPricePerUnit = showPricePerUnit,
		googleProductCategory = googleProductCategory,
		productCondition = productCondition
	)
}

private fun FetchedStoreProfile.ProductSortOrder.toUpdated(): UpdatedStoreProfile.ProductSortOrder {
	return when (this) {
		FetchedStoreProfile.ProductSortOrder.DEFINED_BY_STORE_OWNER -> UpdatedStoreProfile.ProductSortOrder.DEFINED_BY_STORE_OWNER
		FetchedStoreProfile.ProductSortOrder.ADDED_TIME_DESC -> UpdatedStoreProfile.ProductSortOrder.ADDED_TIME_DESC
		FetchedStoreProfile.ProductSortOrder.PRICE_ASC -> UpdatedStoreProfile.ProductSortOrder.PRICE_ASC
		FetchedStoreProfile.ProductSortOrder.PRICE_DESC -> UpdatedStoreProfile.ProductSortOrder.PRICE_DESC
		FetchedStoreProfile.ProductSortOrder.NAME_ASC -> UpdatedStoreProfile.ProductSortOrder.NAME_ASC
		FetchedStoreProfile.ProductSortOrder.NAME_DESC -> UpdatedStoreProfile.ProductSortOrder.NAME_DESC
	}
}

private fun FetchedStoreProfile.AbandonedSalesSettings.toUpdated(): UpdatedStoreProfile.AbandonedSalesSettings {
	return UpdatedStoreProfile.AbandonedSalesSettings(
		autoAbandonedSalesRecovery = autoAbandonedSalesRecovery
	)
}

private fun FetchedStoreProfile.SalePriceSettings.toUpdated(): UpdatedStoreProfile.SalePriceSettings {
	return UpdatedStoreProfile.SalePriceSettings(
		displayOnProductList = displayOnProductList,
		oldPriceLabel = oldPriceLabel,
		displayDiscount = displayDiscount?.toUpdated()
	)
}

private fun FetchedStoreProfile.SalePriceSettings.DisplayDiscount.toUpdated(): UpdatedStoreProfile.SalePriceSettings.DisplayDiscount {
	return when (this) {
		FetchedStoreProfile.SalePriceSettings.DisplayDiscount.NONE -> UpdatedStoreProfile.SalePriceSettings.DisplayDiscount.NONE
		FetchedStoreProfile.SalePriceSettings.DisplayDiscount.ABS -> UpdatedStoreProfile.SalePriceSettings.DisplayDiscount.ABS
		FetchedStoreProfile.SalePriceSettings.DisplayDiscount.PERCENT -> UpdatedStoreProfile.SalePriceSettings.DisplayDiscount.PERCENT
	}
}

private fun FetchedStoreProfile.MailNotifications.toUpdated(): UpdatedStoreProfile.MailNotifications {
	return UpdatedStoreProfile.MailNotifications(
		adminNotificationEmails = adminNotificationEmails?.map { email -> email },
		customerNotificationFromEmail = customerNotificationFromEmail
	)
}

private fun FetchedStoreProfile.Company.toUpdated(): UpdatedStoreProfile.Company {
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

private fun FetchedStoreProfile.FormatsAndUnits.toUpdated(): UpdatedStoreProfile.FormatsAndUnits {
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
		orderNumberPrefix = orderNumberPrefix,
		orderNumberSuffix = orderNumberSuffix
	)
}

private fun FetchedStoreProfile.WeightUnit.toUpdated(): UpdatedStoreProfile.WeightUnit {
	return when (this) {
		FetchedStoreProfile.WeightUnit.CARAT -> UpdatedStoreProfile.WeightUnit.CARAT
		FetchedStoreProfile.WeightUnit.GRAM -> UpdatedStoreProfile.WeightUnit.GRAM
		FetchedStoreProfile.WeightUnit.OUNCE -> UpdatedStoreProfile.WeightUnit.OUNCE
		FetchedStoreProfile.WeightUnit.POUND -> UpdatedStoreProfile.WeightUnit.POUND
		FetchedStoreProfile.WeightUnit.KILOGRAM -> UpdatedStoreProfile.WeightUnit.KILOGRAM
	}
}

private fun FetchedStoreProfile.DimensionUnit.toUpdated(): UpdatedStoreProfile.DimensionUnit {
	return when (this) {
		FetchedStoreProfile.DimensionUnit.MM -> UpdatedStoreProfile.DimensionUnit.MM
		FetchedStoreProfile.DimensionUnit.CM -> UpdatedStoreProfile.DimensionUnit.CM
		FetchedStoreProfile.DimensionUnit.IN -> UpdatedStoreProfile.DimensionUnit.IN
		FetchedStoreProfile.DimensionUnit.YD -> UpdatedStoreProfile.DimensionUnit.YD
	}
}

private fun FetchedStoreProfile.Languages.toUpdated(): UpdatedStoreProfile.Languages {
	return UpdatedStoreProfile.Languages(
		enabledLanguages = enabledLanguages?.map { enabledLanguage -> enabledLanguage },
		defaultLanguage = defaultLanguage
	)
}

private fun FetchedStoreProfile.Shipping.toUpdated(): UpdatedStoreProfile.Shipping {
	return UpdatedStoreProfile.Shipping(
		handlingFee = handlingFee?.toUpdated()
	)
}

private fun FetchedStoreProfile.HandlingFee.toUpdated(): UpdatedStoreProfile.HandlingFee {
	return UpdatedStoreProfile.HandlingFee(
		name = name,
		value = value,
		description = description
	)
}

private fun FetchedStoreProfile.TaxSettings.toUpdated(): UpdatedStoreProfile.TaxSettings {
	return UpdatedStoreProfile.TaxSettings(
		automaticTaxEnabled = automaticTaxEnabled,
		taxes = taxes?.map(FetchedStoreProfile.TaxSettings.Taxes::toUpdated),
		pricesIncludeTax = pricesIncludeTax,
		taxExemptBusiness = taxExemptBusiness
	)
}

private fun FetchedStoreProfile.TaxSettings.Taxes.toUpdated(): UpdatedStoreProfile.TaxSettings.Taxes {
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

private fun FetchedStoreProfile.TaxSettings.TaxRule.toUpdated(): UpdatedStoreProfile.TaxSettings.TaxRule {
	return UpdatedStoreProfile.TaxSettings.TaxRule(
		zoneId = zoneId,
		tax = tax
	)
}

private fun FetchedStoreProfile.Zone.toUpdated(): UpdatedStoreProfile.Zone {
	return UpdatedStoreProfile.Zone(
		id = id,
		name = name,
		countryCodes = countryCodes?.map { countryCode -> countryCode },
		stateOrProvinceCodes = stateOrProvinceCodes?.map { stateOrProvinceCode -> stateOrProvinceCode },
		postCodes = postCodes?.map { postCode -> postCode }
	)
}

private fun FetchedStoreProfile.BusinessRegistrationID.toUpdated(): UpdatedStoreProfile.BusinessRegistrationID {
	return UpdatedStoreProfile.BusinessRegistrationID(
		name = name,
		value = value
	)
}

private fun FetchedStoreProfile.LegalPagesSettingsDetails.toUpdated(): UpdatedStoreProfile.LegalPagesSettingsDetails {
	return UpdatedStoreProfile.LegalPagesSettingsDetails(
		requireTermsAgreementAtCheckout = requireTermsAgreementAtCheckout,
		legalPages = legalPages?.map(FetchedStoreProfile.LegalPagesInfo::toUpdated)
	)
}

private fun FetchedStoreProfile.LegalPagesInfo.toUpdated(): UpdatedStoreProfile.LegalPagesInfo {
	return UpdatedStoreProfile.LegalPagesInfo(
		type = type?.toUpdated(),
		enabled = enabled,
		title = title,
		display = display?.toUpdated(),
		text = text,
		externalUrl = externalUrl
	)
}

private fun FetchedStoreProfile.LegalPagesInfo.Type.toUpdated(): UpdatedStoreProfile.LegalPagesInfo.Type {
	return when (this) {
		FetchedStoreProfile.LegalPagesInfo.Type.LEGAL_INFO -> UpdatedStoreProfile.LegalPagesInfo.Type.LEGAL_INFO
		FetchedStoreProfile.LegalPagesInfo.Type.SHIPPING_COST_PAYMENT_INFO -> UpdatedStoreProfile.LegalPagesInfo.Type.SHIPPING_COST_PAYMENT_INFO
		FetchedStoreProfile.LegalPagesInfo.Type.REVOCATION_TERMS -> UpdatedStoreProfile.LegalPagesInfo.Type.REVOCATION_TERMS
		FetchedStoreProfile.LegalPagesInfo.Type.TERMS -> UpdatedStoreProfile.LegalPagesInfo.Type.TERMS
		FetchedStoreProfile.LegalPagesInfo.Type.PRIVACY_STATEMENT -> UpdatedStoreProfile.LegalPagesInfo.Type.PRIVACY_STATEMENT
	}
}

private fun FetchedStoreProfile.LegalPagesInfo.Display.toUpdated(): UpdatedStoreProfile.LegalPagesInfo.Display {
	return when (this) {
		FetchedStoreProfile.LegalPagesInfo.Display.INLINE -> UpdatedStoreProfile.LegalPagesInfo.Display.INLINE
		FetchedStoreProfile.LegalPagesInfo.Display.EXTERNAL_URL -> UpdatedStoreProfile.LegalPagesInfo.Display.EXTERNAL_URL
	}
}

private fun FetchedStoreProfile.OrderInvoiceSettings.toUpdated(): UpdatedStoreProfile.OrderInvoiceSettings {
	return UpdatedStoreProfile.OrderInvoiceSettings(
		displayOrderInvoices = displayOrderInvoices,
		attachInvoiceToOrderEmailNotifications = attachInvoiceToOrderEmailNotifications?.toUpdated(),
		invoiceLogoUrl = invoiceLogoUrl
	)
}

private fun FetchedStoreProfile.OrderInvoiceSettings.AttachValue.toUpdated(): UpdatedStoreProfile.OrderInvoiceSettings.AttachValue {
	return when (this) {
		FetchedStoreProfile.OrderInvoiceSettings.AttachValue.ATTACH_TO_ALL_EMAILS -> UpdatedStoreProfile.OrderInvoiceSettings.AttachValue.ATTACH_TO_ALL_EMAILS
		FetchedStoreProfile.OrderInvoiceSettings.AttachValue.DO_NOT_ATTACH -> UpdatedStoreProfile.OrderInvoiceSettings.AttachValue.DO_NOT_ATTACH
	}
}
