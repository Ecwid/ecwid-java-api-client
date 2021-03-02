package com.ecwid.apiclient.v3.dto.profile.request

data class UpdatedStoreProfile(
		val generalInfo: GeneralInfo? = null,
		val account: Account? = null,
		val settings: Settings? = null,
		val mailNotifications: MailNotifications? = null,
		val company: Company? = null,
		val formatsAndUnits: FormatsAndUnits? = null,
		val languages: Languages? = null,
		val shipping: Shipping? = null,
		val taxSettings: TaxSettings? = null,
		val zones: List<Zone>? = null,
		val businessRegistrationID: BusinessRegistrationID? = null,
		val legalPagesSettings: LegalPagesSettingsDetails? = null,
		val orderInvoiceSettings: OrderInvoiceSettings? = null
) {
	data class GeneralInfo(
			val storeUrl: String? = null,
			val starterSite: InstantSiteInfo? = null,
			val websitePlatform: WebsitePlatform? = null
	)

	data class InstantSiteInfo(
			val ecwidSubdomain: String? = null,
			val customDomain: String? = null
	)

	enum class WebsitePlatform {
		wix, wordpress, iframe, joomla, yola, unknown
	}

	data class Account(
			val accountName: String? = null,
			val accountNickName: String? = null,
			val accountEmail: String? = null
	)

	data class Settings(
			val closed: Boolean? = null,
			val storeName: String? = null,
			val storeDescription: String? = null,
			val googleRemarketingEnabled: Boolean? = null,
			val googleAnalyticsId: String? = null,
			val fbPixelId: String? = null,
			val orderCommentsEnabled: Boolean? = null,
			val orderCommentsCaption: String? = null,
			val orderCommentsRequired: Boolean? = null,
			val hideOutOfStockProductsInStorefront: Boolean? = null,
			val askCompanyName: Boolean? = null,
			val favoritesEnabled: Boolean? = null,
			val defaultProductSortOrder: ProductSortOrder? = null,
			val abandonedSales: AbandonedSalesSettings? = null,
			val salePrice: SalePriceSettings? = null,
			val showAcceptMarketingCheckbox: Boolean? = null,
			val acceptMarketingCheckboxDefaultValue: Boolean? = null,
			val acceptMarketingCheckboxCustomText: String? = null,
			val askConsentToTrackInStorefront: Boolean? = null,
			val snapPixelId: String? = null,
			val pinterestTagId: String? = null,
			val googleTagId: String? = null,
			val googleEventId: String? = null,
			val showPricePerUnit: Boolean? = null
	)

	enum class ProductSortOrder {
		DEFINED_BY_STORE_OWNER,
		ADDED_TIME_DESC,
		PRICE_ASC,
		PRICE_DESC,
		NAME_ASC,
		NAME_DESC
	}

	data class AbandonedSalesSettings(
			val autoAbandonedSalesRecovery: Boolean? = null
	)

	data class SalePriceSettings(
			val displayOnProductList: Boolean? = null,
			val oldPriceLabel: String? = null,
			val displayDiscount: DisplayDiscount? = null) {

		enum class DisplayDiscount {
			NONE, ABS, PERCENT
		}
	}

	data class MailNotifications(
			val adminNotificationEmails: List<String>? = null,
			val customerNotificationFromEmail: String? = null
	)

	data class Company(
			val companyName: String? = null,
			val email: String? = null,
			val street: String? = null,
			val city: String? = null,
			val countryCode: String? = null,
			val postalCode: String? = null,
			val stateOrProvinceCode: String? = null,
			val phone: String? = null
	)

	data class FormatsAndUnits(
			val currency: String? = null,
			val currencyPrefix: String? = null,
			val currencySuffix: String? = null,
			val currencyGroupSeparator: String? = null,
			val currencyDecimalSeparator: String? = null,
			val currencyTruncateZeroFractional: Boolean? = null,
			val currencyRate: Double? = null,
			val weightUnit: WeightUnit? = null,
			val weightPrecision: Int? = null,
			val weightGroupSeparator: String? = null,
			val weightDecimalSeparator: String? = null,
			val weightTruncateZeroFractional: Boolean? = null,
			val dateFormat: String? = null,
			val timeFormat: String? = null,
			val timezone: String? = null,
			val dimensionsUnit: DimensionUnit? = null,
			val orderNumberPrefix: String? = null,
			val orderNumberSuffix: String? = null
	)

	enum class WeightUnit {
		CARAT, GRAM, OUNCE, POUND, KILOGRAM
	}

	enum class DimensionUnit {
		MM, CM, IN, YD
	}

	data class Languages(
			val enabledLanguages: List<String>? = null,
			val defaultLanguage: String? = null
	)

	data class Shipping(
			val handlingFee: HandlingFee? = null
	)

	data class HandlingFee(
			val name: String? = null,
			val value: Double? = null,
			val description: String? = null
	)

	data class Zone(
			val id: String? = null,
			val name: String? = null,
			val countryCodes: List<String>? = null,
			val stateOrProvinceCodes: List<String>? = null,
			val postCodes: List<String>? = null
	)

	data class TaxSettings(
			val automaticTaxEnabled: Boolean? = null,
			val taxes: List<Taxes>? = null,
			val pricesIncludeTax: Boolean? = null,
			val taxExemptBusiness: Boolean? = null
	) {
		data class Taxes(
				val id: Int? = null,
				val name: String? = null,
				val enabled: Boolean? = null,
				val includeInPrice: Boolean? = null,
				val useShippingAddress: Boolean? = null,
				val taxShipping: Boolean? = null,
				val appliedByDefault: Boolean? = null,
				val defaultTax: Double? = null,
				val rules: List<TaxRule>? = null
		)

		data class TaxRule(
				val zoneId: String? = null,
				val tax: Double? = null
		)
	}

	data class BusinessRegistrationID(
			val name: String? = null,
			val value: String? = null
	)

	data class LegalPagesSettingsDetails(
			val requireTermsAgreementAtCheckout: Boolean? = null,
			val legalPages: List<LegalPagesInfo>? = null
	)

	data class LegalPagesInfo(
			val type: Type? = null,
			val enabled: Boolean? = null,
			val title: String? = null,
			val display: Display? = null,
			val text: String? = null,
			val externalUrl: String? = null
	) {
		enum class Type {
			LEGAL_INFO, SHIPPING_COST_PAYMENT_INFO, REVOCATION_TERMS, TERMS, PRIVACY_STATEMENT
		}

		enum class Display {
			INLINE, EXTERNAL_URL
		}
	}

	data class OrderInvoiceSettings(
			val displayOrderInvoices: Boolean? = null,
			val attachInvoiceToOrderEmailNotifications: AttachValue? = null,
			val invoiceLogoUrl: String? = null
	) {
		enum class AttachValue {
			ATTACH_TO_ALL_EMAILS, DO_NOT_ATTACH
		}
	}

}
