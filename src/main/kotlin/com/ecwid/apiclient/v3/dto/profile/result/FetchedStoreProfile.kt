package com.ecwid.apiclient.v3.dto.profile.result

data class FetchedStoreProfile(
        var generalInfo: GeneralInfo? = null,
        var account: Account? = null
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
}
