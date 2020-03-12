package com.ecwid.apiclient.v3.dto.profile.request

data class UpdatedStoreProfile(
        var generalInfo: GeneralInfo? = null,
        var account: Account? = null,
        var settings: Settings? = null,
        var mailNotifications: MailNotifications? = null,
        var company: Company? = null,
        var formatsAndUnits: FormatsAndUnits? = null,
        var languages: Languages? = null,
        var shipping: Shipping? = null,
        var taxSettings: TaxSettings? = null,
        var zones: List<Zone>? = null,
        var businessRegistrationID: BusinessRegistrationID? = null,
        var legalPagesSettings: LegalPagesSettingsDetails? = null,
        var orderInvoiceSettings: OrderInvoiceSettings? = null
) {
    data class GeneralInfo(
            var storeUrl: String? = null,
            var starterSite: InstantSiteInfo? = null,
            var websitePlatform: WebsitePlatform? = null
    )

    data class InstantSiteInfo(
            var ecwidSubdomain: String? = null,
            var customDomain: String? = null
    )

    enum class WebsitePlatform {
        wix, wordpress, iframe, joomla, yola, unknown
    }

    data class Account(
            var accountName: String? = null,
            var accountNickName: String? = null,
            var accountEmail: String? = null
    )

    data class Settings(
            var closed: Boolean? = null,
            var storeName: String? = null,
            var storeDescription: String? = null,
            var googleRemarketingEnabled: Boolean? = null,
            var googleAnalyticsId: String? = null,
            var fbPixelId: String? = null,
            var orderCommentsEnabled: Boolean? = null,
            var orderCommentsCaption: String? = null,
            var orderCommentsRequired: Boolean? = null,
            var hideOutOfStockProductsInStorefront: Boolean? = null,
            var askCompanyName: Boolean? = null,
            var favoritesEnabled: Boolean? = null,
            var defaultProductSortOrder: ProductSortOrder? = null,
            var abandonedSales: AbandonedSalesSettings? = null,
            var salePrice: SalePriceSettings? = null,
            var showAcceptMarketingCheckbox: Boolean? = null,
            var acceptMarketingCheckboxDefaultValue: Boolean? = null,
            var acceptMarketingCheckboxCustomText: String? = null,
            var askConsentToTrackInStorefront: Boolean? = null,
            var snapPixelId: String? = null,
            var pinterestTagId: String? = null,
            var googleTagId: String? = null,
            var googleEventId: String? = null
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
            var autoAbandonedSalesRecovery: Boolean? = null
    )

    data class SalePriceSettings(
            var displayOnProductList: Boolean? = null,
            var oldPriceLabel: String? = null,
            var displayDiscount: DisplayDiscount? = null) {

        enum class DisplayDiscount {
            NONE, ABS, PERCENT
        }
    }

    data class MailNotifications(
            var adminNotificationEmails: List<String>? = null,
            var customerNotificationFromEmail: String? = null
    )

    data class Company(
            var companyName: String? = null,
            var email: String? = null,
            var street: String? = null,
            var city: String? = null,
            var countryCode: String? = null,
            var postalCode: String? = null,
            var stateOrProvinceCode: String? = null,
            var phone: String? = null
    )

    data class FormatsAndUnits(
            var currency: String? = null,
            var currencyPrefix: String? = null,
            var currencySuffix: String? = null,
            var currencyGroupSeparator: String? = null,
            var currencyDecimalSeparator: String? = null,
            var currencyTruncateZeroFractional: Boolean? = null,
            var currencyRate: Double? = null,
            var weightUnit: WeightUnit? = null,
            var weightPrecision: Int? = null,
            var weightGroupSeparator: String? = null,
            var weightDecimalSeparator: String? = null,
            var weightTruncateZeroFractional: Boolean? = null,
            var dateFormat: String? = null,
            var timeFormat: String? = null,
            var timezone: String? = null,
            var dimensionsUnit: String? = null,
            var orderNumberPrefix: String? = null,
            var orderNumberSuffix: String? = null
    )

    enum class WeightUnit {
        CARAT, GRAM, OUNCE, POUND, KILOGRAM
    }

    data class Languages(
            var enabledLanguages: List<String>? = null,
            var defaultLanguage: String? = null
    )

    data class Shipping(
            var handlingFee: HandlingFee? = null
    )

    data class HandlingFee(
            var name: String? = null,
            var value: Double? = null,
            var description: String? = null
    )

    data class Zone(
        var id: String? = null,
        var name: String? = null,
        var countryCodes: List<String>? = null,
        var stateOrProvinceCodes: List<String>? = null,
        var postCodes: List<String>? = null
    )

    data class TaxSettings(
            var automaticTaxEnabled: Boolean? = null,
            var taxes: List<Taxes>? = null
    ) {
        data class Taxes(
                var id: Int? = null,
                var name: String? = null,
                var enabled: Boolean? = null,
                var includeInPrice: Boolean? = null,
                var useShippingAddress: Boolean? = null,
                var taxShipping: Boolean? = null,
                var appliedByDefault: Boolean? = null,
                var defaultTax: Double? = null,
                var rules: List<TaxRule>? = null
        )

        data class TaxRule(
                var zoneId: String? = null,
                var tax: Double? = null
        )
    }

    data class BusinessRegistrationID(
            var name: String? = null,
            var value: String? = null
    )

    data class LegalPagesSettingsDetails(
            var requireTermsAgreementAtCheckout: Boolean? = null,
            var legalPages: List<LegalPagesInfo>? = null
    )

    data class LegalPagesInfo(
            var type: Type? = null,
            var enabled: Boolean? = null,
            var title: String? = null,
            var display: Display? = null,
            var text: String? = null,
            var externalUrl: String? = null
    ) {
        enum class Type {
            LEGAL_INFO, SHIPPING_COST_PAYMENT_INFO, REVOCATION_TERMS, TERMS, PRIVACY_STATEMENT
        }
        enum class Display {
            INLINE, EXTERNAL_URL
        }
    }

    data class OrderInvoiceSettings(
            var displayOrderInvoices: Boolean? = null,
            var attachInvoiceToOrderEmailNotifications: AttachValue? = null,
            var invoiceLogoUrl: String? = null
    ) {
        enum class AttachValue {
            ATTACH_TO_ALL_EMAILS, DO_NOT_ATTACH
        }
    }

}
