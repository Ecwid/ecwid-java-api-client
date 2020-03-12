package com.ecwid.apiclient.v3.dto.profile.result

data class FetchedStoreProfile(
        var generalInfo: GeneralInfo? = null,
        var account: Account? = null,
        var settings: Settings? = null,
        var mailNotifications: MailNotifications? = null,
        var company: Company? = null,
        var formatsAndUnits: FormatsAndUnits? = null,
        var languages: Languages? = null,
        var shipping: Shipping? = null
) {
    data class GeneralInfo(
            var storeId: Int = 0,
            var storeUrl: String? = null,
            var starterSite: InstantSiteInfo? = null,
            var websitePlatform: WebsitePlatform? = null
    )

    data class InstantSiteInfo(
            var ecwidSubdomain: String? = null,
            var customDomain: String? = null,
            var generatedUrl: String? = null,
            var storeLogoUrl: String? = null
    )

    enum class WebsitePlatform {
        wix, wordpress, iframe, joomla, yola, unknown
    }

    data class Account(
            var accountName: String? = null,
            var accountNickName: String? = null,
            var accountEmail: String? = null,
            var availableFeatures: List<String>? = null,
            var whiteLabel: Boolean? = null
    )

    data class Settings(
            var closed: Boolean? = null,
            var storeName: String? = null,
            var storeDescription: String? = null,
            var invoiceLogoUrl: String? = null,
            var emailLogoUrl: String? = null,
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

    class SalePriceSettings {
        var displayOnProductList: Boolean? = null
        var oldPriceLabel: String? = null
        var displayDiscount: DisplayDiscount? = null

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
            var currencyPrecision: String? = null,
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
            var facebookPreferredLocale: String? = null,
            var defaultLanguage: String? = null
    )

    data class Shipping(
            var handlingFee: HandlingFee? = null,
            var shippingOrigin: ShippingOrigin? = null,
            var shippingOptions: List<ShippingOption>? = null
    )

    data class HandlingFee(
            var name: String? = null,
            var value: Double? = null,
            var description: String? = null
    )

    data class ShippingOrigin(
            var companyName: String? = null,
            var email: String? = null,
            var street: String? = null,
            var city: String? = null,
            var countryCode: String? = null,
            var postalCode: String? = null,
            var stateOrProvinceCode: String? = null,
            var phone: String? = null
    )

    data class ShippingOption(
            var id: String? = null,
            var title: String? = null,
            var enabled: Boolean? = null,
            var orderBy: Int? = null,
            var fulfillmentType: FulfillmentType? = null,
            var destinationZone: Zone? = null,
            var deliveryTimeDays: String? = null,
            var description: String? = null,
            var carrier: String? = null,
            var carrierMethods: List<CarrierMethod>? = null,
            var carrierSettings: CarrierSettings? = null,
            var ratesCalculationType: RatesCalculationType? = null,
            var shippingCostMarkup: Double? = null,
            var flatRate: FlatRate? = null,
            var ratesTable: TableRatesDetails? = null,
            var appClientId: String? = null,
            var pickupInstruction: String? = null,
            var scheduledPickup: Boolean? = null,
            var pickupPreparationTimeHours: Int? = null,
            var pickupBusinessHours: String? = null
    )

    enum class FulfillmentType {
        pickup, shipping
    }

    data class Zone(
        var id: String? = null,
        var name: String? = null,
        var countryCodes: List<String>? = null,
        var stateOrProvinceCodes: List<String>? = null,
        var postCodes: List<String>? = null
    )

    data class CarrierMethod(
            var id: String? = null,
            var name: String? = null,
            var enabled: Boolean? = null,
            var orderBy: Int? = null
    )

    data class CarrierSettings(
            var defaultCarrierAccountEnabled: Boolean? = null,
            var defaultPostageDimensions: DefaultPostageDimensions? = null
    )

    data class DefaultPostageDimensions(
            var length: Int? = null,
            var width: Int? = null,
            var height: Int? = null
    )

    enum class RatesCalculationType {
        carrier_calculated, table, flat, app;

        override fun toString(): String {
            return super.toString().replace("_", "-")
        }
    }

    fun RatesCalculationType.valueOf(value: String?): RatesCalculationType? {
        return RatesCalculationType.values().firstOrNull() { it.toString() == value }
    }

    data class FlatRate(
            var rateType: RateType? = null,
            var rate: Double? = null
    ) {
        enum class RateType {
            ABSOLUTE, PERCENT
        }
    }

    data class TableRatesDetails(
            var tableBasedOn: RateBase? = null,
            var rates: List<TableRate>? = null
    ) {
        enum class RateBase {
            subtotal, discountedSubtotal, weight
        }

    }

    data class TableRate(
            var tableRateConditions: TableRateConditions? = null,
            var rate: TableRateDetails? = null
    )

    data class TableRateConditions(
            var weightFrom: Double? = null,
            var weightTo: Double? = null,
            var subtotalFrom: Double? = null,
            var subtotalTo: Double? = null,
            var discountedSubtotalFrom: Double? = null,
            var discountedSubtotalTo: Double? = null
    )

    data class TableRateDetails(
            var perOrderAbs: Double? = null,
            var perOrderPercent: Double? = null,
            var perItem: Double? = null,
            var perWeightUnitRate: Double? = null
    )

}
