package com.ecwid.apiclient.v3.entity

import com.ecwid.apiclient.v3.converter.toUpdated
import com.ecwid.apiclient.v3.dto.common.ProductCondition
import com.ecwid.apiclient.v3.dto.profile.enums.ProductFilterType
import com.ecwid.apiclient.v3.dto.profile.request.StoreProfileRequest
import com.ecwid.apiclient.v3.dto.profile.request.StoreProfileUpdateRequest
import com.ecwid.apiclient.v3.dto.profile.request.UpdatedStoreProfile
import com.ecwid.apiclient.v3.dto.profile.result.FetchedStoreProfile
import com.ecwid.apiclient.v3.util.PropertiesLoader
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
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
				storeName = "Test store for ecwid-java-api-client",
				storeDescription = "Test Store Description for ecwid-java-api-client",
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
				showPricePerUnit = true,
				googleProductCategory = 632,
				productCondition = ProductCondition.USED,
				tikTokPixel = UpdatedStoreProfile.TikTokPixelSettings(
					advancedMatching = true
				)
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
				countryCode = "GE",
				postalCode = "postalCode",
				phone = "phone",
				stateOrProvinceCode = "TB"
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
				timezoneOffsetInMinutes = 1234567,
				dimensionsUnit = UpdatedStoreProfile.DimensionUnit.MM,
				volumeUnit = UpdatedStoreProfile.VolumeUnit.L,
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
				taxes = listOf(
					UpdatedStoreProfile.TaxSettings.Taxes(
						id = 100,
						name = "tax-name",
						enabled = true,
						includeInPrice = true,
						useShippingAddress = true,
						taxShipping = true,
						appliedByDefault = true,
						rules = listOf(
							UpdatedStoreProfile.TaxSettings.TaxRule(
								zoneId = "zoneId",
								tax = 14.0
							)
						),
						defaultTax = 12.0
					)
				),
				pricesIncludeTax = false,
				taxExemptBusiness = false
			),
			zones = listOf(
				UpdatedStoreProfile.Zone(
					id = "zoneId",
					name = "Zone name",
					countryCodes = listOf("GE", "UA"),
					stateOrProvinceCodes = listOf("GE-AJ", "GE-TB"),
					postCodes = listOf("123456", "654321")
				)
			),
			businessRegistrationID = UpdatedStoreProfile.BusinessRegistrationID(
				name = "businessRegistrationID",
				value = "busines-value"
			),
			legalPagesSettings = UpdatedStoreProfile.LegalPagesSettingsDetails(
				requireTermsAgreementAtCheckout = true,
				legalPages = listOf(
					UpdatedStoreProfile.LegalPagesInfo(
						type = UpdatedStoreProfile.LegalPagesInfo.Type.LEGAL_INFO,
						enabled = true,
						title = "page title",
						display = UpdatedStoreProfile.LegalPagesInfo.Display.EXTERNAL_URL,
						text = "page text",
						externalUrl = "page url"
					)
				)
			),
			productFiltersSettings = UpdatedStoreProfile.ProductFiltersSettings(
				enabledInStorefront = true,
				filterSections = createTestFilterSections(),
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

		val designSettings = actualProfile.designSettings
		require(designSettings != null)

		val formatsAndUnits = actualProfile.formatsAndUnits
		require(formatsAndUnits != null)

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
		assertEquals("Test store for ecwid-java-api-client", settings.storeName)
		assertEquals("Test Store Description for ecwid-java-api-client", settings.storeDescription)
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

		// Checking field with custom name and annotation @JsonFieldName
		assertEquals("MEDIUM", designSettings.productListImageSize)

		assertEquals(632, settings.googleProductCategory)
		assertEquals(ProductCondition.USED, settings.productCondition)

		val tikTokPixel = settings.tikTokPixel
		require(tikTokPixel != null)
		assertTrue(tikTokPixel.advancedMatching)

		assertEquals(FetchedStoreProfile.VolumeUnit.L, formatsAndUnits.volumeUnit)

		assertEquals(
			expectedProfile.productFiltersSettings,
			actualProfile.productFiltersSettings.toUpdated()
		)
	}

	private fun createTestFilterSections() = listOf(
		UpdatedStoreProfile.ProductFilterItem(
			type = ProductFilterType.IN_STOCK,
			enabled = true,
		),
		UpdatedStoreProfile.ProductFilterItem(
			type = ProductFilterType.ON_SALE,
			enabled = true,
		),
		UpdatedStoreProfile.ProductFilterItem(
			type = ProductFilterType.PRICE,
			enabled = false,
		),
		UpdatedStoreProfile.ProductFilterItem(
			type = ProductFilterType.CATEGORIES,
			enabled = false,
		),
		UpdatedStoreProfile.ProductFilterItem(
			type = ProductFilterType.SEARCH,
			enabled = true,
		),
		UpdatedStoreProfile.ProductFilterItem(
			name = "Option 1",
			type = ProductFilterType.OPTION,
			enabled = true,
		),
		UpdatedStoreProfile.ProductFilterItem(
			name = "Option 2",
			type = ProductFilterType.OPTION,
			enabled = true,
		),
		UpdatedStoreProfile.ProductFilterItem(
			name = "Attribute 2",
			type = ProductFilterType.ATTRIBUTE,
			enabled = true,
		),
		UpdatedStoreProfile.ProductFilterItem(
			name = "Attribute 1",
			type = ProductFilterType.ATTRIBUTE,
			enabled = true,
		),
	)

}
