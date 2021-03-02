package com.ecwid.apiclient.v3.entity

import com.ecwid.apiclient.v3.dto.profile.request.StoreProfileRequest
import com.ecwid.apiclient.v3.dto.profile.request.StoreProfileUpdateRequest
import com.ecwid.apiclient.v3.dto.profile.request.UpdatedStoreProfile
import com.ecwid.apiclient.v3.dto.profile.result.FetchedStoreProfile
import com.ecwid.apiclient.v3.util.PropertiesLoader
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

class StoreProfileTest : BaseEntityTest() {

	@BeforeEach
	override fun beforeEach() {
		super.beforeEach()
	}

	@Test
	@Disabled("Will be fixed in ECWID-75364")
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
// TODO: uncomment after bug ECWID-65852 is fixed
//                                rules = listOf(UpdatedStoreProfile.TaxSettings.TaxRule(
//                                        zoneId = "zoneId",
//                                        tax = 14.0
//                                )),
								defaultTax = 12.0
						)),
						pricesIncludeTax = false,
						taxExemptBusiness = false
				),
// TODO: uncomment after bug ECWID-65838 is fixed
//                zones = listOf(UpdatedStoreProfile.Zone(
//                        id = "zoneId",
//                        name = "Zone name",
//                        countryCodes = listOf("RU", "UA"),
//                        stateOrProvinceCodes = listOf("73", "77"),
//                        postCodes = listOf("123456", "654321")
//                )),
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

		assertEquals(testStoreId, actualProfile.generalInfo!!.storeId)
		assertEquals("bobyor", actualProfile.generalInfo!!.starterSite!!.ecwidSubdomain)
		assertEquals("example.com", actualProfile.generalInfo!!.starterSite!!.customDomain)
		assertEquals("https://bobyor.company.site", actualProfile.generalInfo!!.starterSite!!.generatedUrl)
		assertEquals("https://www.mysite.com", actualProfile.generalInfo!!.storeUrl)
		assertEquals(FetchedStoreProfile.WebsitePlatform.wordpress, actualProfile.generalInfo!!.websitePlatform)

		assertEquals("The Bobyør", actualProfile.account!!.accountName)
		assertEquals("bobyør", actualProfile.account!!.accountNickName)
		assertEquals("bobyor@example.com", actualProfile.account!!.accountEmail)
		assertTrue(actualProfile.account!!.availableFeatures!!.contains("API"))
		assertEquals(false, actualProfile.account!!.whiteLabel)

		assertEquals(true, actualProfile.settings!!.closed)
		assertEquals("The Shop", actualProfile.settings!!.storeName)
		assertEquals("The Store Description", actualProfile.settings!!.storeDescription)
		assertEquals(true, actualProfile.settings!!.googleRemarketingEnabled)
		assertEquals("googleAnalyticsId", actualProfile.settings!!.googleAnalyticsId)
		assertEquals("fbPixelId", actualProfile.settings!!.fbPixelId)
		assertEquals(true, actualProfile.settings!!.orderCommentsEnabled)
		assertEquals("orderCommentsCaption", actualProfile.settings!!.orderCommentsCaption)
		assertEquals(true, actualProfile.settings!!.orderCommentsRequired)
		assertEquals(true, actualProfile.settings!!.hideOutOfStockProductsInStorefront)
		assertEquals(true, actualProfile.settings!!.askCompanyName)
		assertEquals(true, actualProfile.settings!!.favoritesEnabled)
		assertEquals(FetchedStoreProfile.ProductSortOrder.NAME_ASC, actualProfile.settings!!.defaultProductSortOrder)

		assertEquals(true, actualProfile.settings!!.abandonedSales!!.autoAbandonedSalesRecovery)

		assertEquals(true, actualProfile.settings!!.salePrice!!.displayOnProductList)
		assertEquals("oldPriceLabel", actualProfile.settings!!.salePrice!!.oldPriceLabel)
		assertEquals(FetchedStoreProfile.SalePriceSettings.DisplayDiscount.PERCENT, actualProfile.settings!!.salePrice!!.displayDiscount)

		assertEquals(true, actualProfile.settings!!.showAcceptMarketingCheckbox)
		assertEquals(true, actualProfile.settings!!.acceptMarketingCheckboxDefaultValue)
		assertEquals("acceptMarketingCheckboxCustomText", actualProfile.settings!!.acceptMarketingCheckboxCustomText)
		assertEquals(true, actualProfile.settings!!.askConsentToTrackInStorefront)
		assertEquals("snapPixelId", actualProfile.settings!!.snapPixelId)
		assertEquals("pinterestTagId", actualProfile.settings!!.pinterestTagId)
		assertEquals("googleTagId", actualProfile.settings!!.googleTagId)
		assertEquals("googleEventId", actualProfile.settings!!.googleEventId)
		assertEquals(true, actualProfile.settings!!.showPricePerUnit)
	}

}
