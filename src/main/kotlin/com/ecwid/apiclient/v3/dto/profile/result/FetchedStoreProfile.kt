package com.ecwid.apiclient.v3.dto.profile.result

data class FetchedStoreProfile(
        var generalInfo: GeneralInfo? = null,
        var account: Account? = null,
        var settings: Settings? = null
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
}
