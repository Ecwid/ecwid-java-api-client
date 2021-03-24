package com.ecwid.apiclient.v3.entity

import com.ecwid.apiclient.v3.dto.profile.request.StoreProfileRequest
import com.ecwid.apiclient.v3.dto.profile.request.StoreProfileUpdateRequest
import com.ecwid.apiclient.v3.dto.profile.request.UpdatedStoreProfile
import com.ecwid.apiclient.v3.dto.profile.result.FetchedStoreProfile
import com.ecwid.apiclient.v3.util.PropertiesLoader
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class StoreProfileTest : BaseEntityTest() {

	@BeforeEach
	override fun beforeEach() {
		super.beforeEach()
	}

	@Test
	fun testStoreProfile() {
		val testStoreId = PropertiesLoader.load().storeId

		val expectedProfile = UpdatedStoreProfile(
				generalInfo = UpdatedStoreProfile.GeneralInfo(
						storeUrl = "https://www.mysite.com",
						starterSite = UpdatedStoreProfile.InstantSiteInfo(
								ecwidSubdomain = "bobyor",
								customDomain = "example.com"
						),
						websitePlatform = UpdatedStoreProfile.WebsitePlatform.wordpress
				),
				account = UpdatedStoreProfile.Account(
						accountName = "The Bobyør",
						accountNickName = "bobyør",
						accountEmail = "bobyor@example.com"
				),
				settings = UpdatedStoreProfile.Settings(
						closed = true,
						storeName = "The Shop",
						storeDescription = "The Store Description",
						googleRemarketingEnabled = true,
						googleAnalyticsId = "googleAnalyticsId",
						fbPixelId = "fbPixelId",
						orderCommentsEnabled = true,
						orderCommentsCaption = "orderCommentsCaption",
						orderCommentsRequired = true,
						hideOutOfStockProductsInStorefront = true,
						askCompanyName = true,
						favoritesEnabled = true,
						defaultProductSortOrder = UpdatedStoreProfile.ProductSortOrder.NAME_ASC,
						abandonedSales = UpdatedStoreProfile.AbandonedSalesSettings(
								autoAbandonedSalesRecovery = true
						),
						salePrice = UpdatedStoreProfile.SalePriceSettings(
								displayOnProductList = true,
								oldPriceLabel = "oldPriceLabel",
								displayDiscount = UpdatedStoreProfile.SalePriceSettings.DisplayDiscount.PERCENT
						),
						showAcceptMarketingCheckbox = true,
						acceptMarketingCheckboxDefaultValue = true,
						acceptMarketingCheckboxCustomText = "acceptMarketingCheckboxCustomText",
						askConsentToTrackInStorefront = true,
						snapPixelId = "snapPixelId",
						pinterestTagId = "pinterestTagId",
						googleTagId = "googleTagId",
						googleEventId = "googleEventId",
						showPricePerUnit = true
				),
				mailNotifications = UpdatedStoreProfile.MailNotifications(
						adminNotificationEmails = listOf("admin@example.com", "admin2@example.com"),
						customerNotificationFromEmail = "customerfrom@example.com"
				),
				company = UpdatedStoreProfile.Company(
						companyName = "companyName",
						email = "email@example.com",
						street = "street",
						city = "city",
						countryCode = "RU",
						postalCode = "postalCode",
						phone = "phone",
						stateOrProvinceCode = "73"
				),
				formatsAndUnits = UpdatedStoreProfile.FormatsAndUnits(
						currency = "currency",
						currencyPrefix = "currencyPrefix",
						currencySuffix = "currencySuffix",
						currencyGroupSeparator = "G",
						currencyDecimalSeparator = "D",
						currencyTruncateZeroFractional = true,
						currencyRate = 1.23,
						weightUnit = UpdatedStoreProfile.WeightUnit.GRAM,
						weightPrecision = 2,
						weightGroupSeparator = "R",
						weightDecimalSeparator = "E",
						weightTruncateZeroFractional = true,
						dateFormat = "yyyy/MM/dd",
						timeFormat = "HH:mm:ss",
						timezone = "timezone",
						dimensionsUnit = UpdatedStoreProfile.DimensionUnit.MM,
						orderNumberPrefix = "orderNumberPrefix",
						orderNumberSuffix = "orderNumberSuffix"
				),
				languages = UpdatedStoreProfile.Languages(
						enabledLanguages = listOf("en", "ru"),
						defaultLanguage = "ru"
				),
				shipping = UpdatedStoreProfile.Shipping(
						handlingFee = UpdatedStoreProfile.HandlingFee(
								name = "fee",
								value = 123.0,
								description = "fee descr"
						)
				),
				taxSettings = UpdatedStoreProfile.TaxSettings(
						automaticTaxEnabled = true,
						taxes = listOf(UpdatedStoreProfile.TaxSettings.Taxes(
								id = 100,
								name = "tax-name",
								enabled = true,
								includeInPrice = true,
								useShippingAddress = true,
								taxShipping = true,
								appliedByDefault = true,
                                rules = listOf(UpdatedStoreProfile.TaxSettings.TaxRule(
                                        zoneId = "zoneId",
                                        tax = 14.0
                                )),
								defaultTax = 12.0
						)),
						pricesIncludeTax = false,
						taxExemptBusiness = false
				),
                zones = listOf(UpdatedStoreProfile.Zone(
                        id = "zoneId",
                        name = "Zone name",
                        countryCodes = listOf("RU", "UA"),
                        stateOrProvinceCodes = listOf("RU-73", "RU-77"),
                        postCodes = listOf("123456", "654321")
                )),
				businessRegistrationID = UpdatedStoreProfile.BusinessRegistrationID(
						name = "businessRegistrationID",
						value = "busines-value"
				),
				legalPagesSettings = UpdatedStoreProfile.LegalPagesSettingsDetails(
						requireTermsAgreementAtCheckout = true,
						legalPages = listOf(UpdatedStoreProfile.LegalPagesInfo(
								type = UpdatedStoreProfile.LegalPagesInfo.Type.LEGAL_INFO,
								enabled = true,
								title = "page title",
								display = UpdatedStoreProfile.LegalPagesInfo.Display.EXTERNAL_URL,
								text = "page text",
								externalUrl = "page url"
						))
				),
				orderInvoiceSettings = UpdatedStoreProfile.OrderInvoiceSettings(
						displayOrderInvoices = true,
						attachInvoiceToOrderEmailNotifications = UpdatedStoreProfile.OrderInvoiceSettings.AttachValue.ATTACH_TO_ALL_EMAILS,
						invoiceLogoUrl = "invoice.logo.url"
				)
		)

		val updateResult = apiClient.updateStoreProfile(StoreProfileUpdateRequest(expectedProfile))

		assertTrue(updateResult.success)
		assertEquals(1, updateResult.updateCount)

		val actualProfile = apiClient.getStoreProfile(StoreProfileRequest())

		val generalInfo = actualProfile.generalInfo
		require(generalInfo != null)

		val starterSite = generalInfo.starterSite
		require(starterSite != null)

		val account = actualProfile.account
		require(account != null)

		val settings = actualProfile.settings
		require(settings != null)

		val salePrice = settings.salePrice
		require(salePrice != null)

		val availableFeatures = account.availableFeatures
		require(availableFeatures != null)

		val abandonedSales = settings.abandonedSales
		require(abandonedSales != null)

		assertEquals(testStoreId, generalInfo.storeId)
		assertEquals("bobyor", starterSite.ecwidSubdomain)
		assertEquals("example.com", starterSite.customDomain)
		assertEquals("https://bobyor.company.site", starterSite.generatedUrl)
		assertEquals("https://www.mysite.com", generalInfo.storeUrl)
		assertEquals(FetchedStoreProfile.WebsitePlatform.wordpress, generalInfo.websitePlatform)

		assertEquals("The Bobyør", account.accountName)
		assertEquals("bobyør", account.accountNickName)
		assertEquals("bobyor@example.com", account.accountEmail)
		assertTrue(availableFeatures.contains("API"))
		assertEquals(false, account.whiteLabel)

		assertEquals(true, settings.closed)
		assertEquals("The Shop", settings.storeName)
		assertEquals("The Store Description", settings.storeDescription)
		assertEquals(true, settings.googleRemarketingEnabled)
		assertEquals("googleAnalyticsId", settings.googleAnalyticsId)
		assertEquals("fbPixelId", settings.fbPixelId)
		assertEquals(true, settings.orderCommentsEnabled)
		assertEquals("orderCommentsCaption", settings.orderCommentsCaption)
		assertEquals(true, settings.orderCommentsRequired)
		assertEquals(true, settings.hideOutOfStockProductsInStorefront)
		assertEquals(true, settings.askCompanyName)
		assertEquals(true, settings.favoritesEnabled)
		assertEquals(FetchedStoreProfile.ProductSortOrder.NAME_ASC, settings.defaultProductSortOrder)

		assertEquals(true, abandonedSales.autoAbandonedSalesRecovery)

		assertEquals(true, salePrice.displayOnProductList)
		assertEquals("oldPriceLabel", salePrice.oldPriceLabel)
		assertEquals(FetchedStoreProfile.SalePriceSettings.DisplayDiscount.PERCENT, salePrice.displayDiscount)

		assertEquals(true, settings.showAcceptMarketingCheckbox)
		assertEquals(true, settings.acceptMarketingCheckboxDefaultValue)
		assertEquals("acceptMarketingCheckboxCustomText", settings.acceptMarketingCheckboxCustomText)
		assertEquals(true, settings.askConsentToTrackInStorefront)
		assertEquals("snapPixelId", settings.snapPixelId)
		assertEquals("pinterestTagId", settings.pinterestTagId)
		assertEquals("googleTagId", settings.googleTagId)
		assertEquals("googleEventId", settings.googleEventId)
		assertEquals(true, settings.showPricePerUnit)
	}

}
