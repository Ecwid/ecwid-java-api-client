package com.ecwid.apiclient.v3.dto.startersite.result

import com.ecwid.apiclient.v3.dto.common.ApiResultDTO

data class StarterSiteSettingsResult(
	var cleanUrlsEnabled: Boolean? = null,
	var storeName: String? = null,
	var hiddenSocialLinks: List<String>? = null,
	var allowSearchEnginesIndexing: Boolean? = null,
	var customHeaderHtmlCode: String? = null,
	var langCode: String? = null,

	var cover: StarterSiteCover? = null,
	var info: List<StarterSiteTextBlock>? = null,
	var reviews: List<StarterSiteReviewBlock>? = null,
	var about: List<StarterSiteAboutBlock>? = null,
	var featuredProducts: List<StarterSiteProductBlock>? = null,
	var gallery: List<StarterSiteGalleryBlock>? = null,
	var location: StarterSiteLocation? = null,
	var contactInfo: StarterSiteContactInfo? = null,
	var socialLinks: StarterSiteSocialLinks? = null,
	var favicon: StarterSiteImage? = null,
	var store: StarterSiteStoreBlock? = null,
	var customCopyrightText: String? = null,
	var hiddenSections: List<String>? = null,
	var forceApplyDemoData: Boolean? = null,
	var storeCountry: String? = null
) : ApiResultDTO

data class StarterSiteTextBlock(
	var title: String? = null,
	var content: String? = null,
	var show: Boolean? = null,
	var id: Int? = null,
	var orderBy: Int? = null
)

data class StarterSiteReviewBlock(
	var title: String? = null,
	var show: Boolean? = null,
	var content: String? = null,
	var personName: String? = null,
	var personTitle: String? = null,
	var personImage: StarterSiteImage? = null,
	var id: Int? = null,
	var orderBy: Int? = null
)

data class StarterSiteCover(
	var layout: StarterSiteCoverLayout? = null,
	var position: StarterSiteCoverPosition? = null,
	var align: StarterSiteCoverAlign? = null,
	var background: StarterSiteCoverBackground? = null,
	var overlay: StarterSiteCoverOverlay? = null,
	var logo: StarterSiteLogo? = null,
	var title: StarterSiteCoverTitle? = null,
	var subtitle: StarterSiteCoverSubtitle? = null,
	var button: StarterSiteCoverButton? = null,
	var phoneLayout: StarterSitePhoneLayout? = null,
	var socialLinksLayout: StarterSiteSocialLinksLayout? = null,
	var showStoreHoursLink: Boolean? = null,
	var showGetDirectionLink: Boolean? = null,
	var showCartIcon: Boolean? = null,
	var showMenu: Boolean? = null,
	var arrowDown: StarterSiteCoverArrowDown? = null,
	var orderBy: Int? = null
)

data class StarterSiteAboutBlock(
	var title: String? = null,
	var content: String? = null,
	var ownerName: String? = null,
	var ownerTitle: String? = null,
	var show: Boolean? = null,
	var ownerImage: StarterSiteImage? = null,
	var id: Int? = null,
	var orderBy: Int? = null
)

data class StarterSiteProductBlock(
	var id: Int? = null,
	var orderBy: Int? = null,
	var productId: Int? = null,
	var settings: String? = null,
	var show: Boolean? = null
)

data class StarterSiteGalleryBlock(
	var title: String? = null,
	var show: Boolean? = null,
	var images: List<StarterSiteImage>? = null,
	var orderBy: Int? = null,
	var id: Int? = null
)

data class StarterSiteLocation(
	var title: String? = null,
	var subtitle: String? = null,
	var content: String? = null,
	var address: String? = null,
	var longitude: String? = null,
	var lattitude: String? = null,
	var hours: StarterSiteLocationHoursInfo? = null,
	var show: Boolean? = null,
	var orderBy: Int? = null
)

data class StarterSiteLocationHoursInfo(
	var title: String? = null,
	var detailedHoursInfo: List<StarterSiteLocationHoursDetailedInfo>? = null
)

data class StarterSiteLocationHoursDetailedInfo(
	var day: String? = null,
	var hour: String? = null
)

data class StarterSiteContactInfo(
	var title: String? = null,
	var show: Boolean? = null,
	var channels: List<StarterSiteContactChannelInfo>? = null,
	var orderBy: Int? = null
)

data class StarterSiteContactChannelInfo(
	var type: StarterSiteContactChannelInfoType? = null,
	var title: String? = null,
	var value: String? = null
)

enum class StarterSiteContactChannelInfoType {
	PHONE, URL, EMAIL
}

data class StarterSiteSocialLinks(
	var title: String? = null,
	var facebook: String? = null,
	var twitter: String? = null,
	var pinterest: String? = null,
	var instagram: String? = null,
	var instagramId: String? = null,
	var foursquare: String? = null,
	var yelp: String? = null,
	var vk: String? = null,
	var tumblr: String? = null,
	var etsy: String? = null,
	var google: String? = null,
	var youtube: String? = null,
	var vimeo: String? = null,
	var wechat: String? = null,
	var whatsapp: String? = null,
	var telegram: String? = null,
	var messenger: String? = null,
	var line: String? = null,
	var viber: String? = null,
	var showInstagram: Boolean? = null,
	var show: Boolean? = null,
	var orderBy: Int? = null
)

data class StarterSiteImage(
	var id: Int? = null,
	var url: String? = null,
	var mobileUrl: String? = null,
	var thumbnail: String? = null,
	var mobileThumbnail: String? = null,
	var description: String? = null
)

data class StarterSiteStoreBlock(
	var show: Boolean? = null,
	var orderBy: Int? = null
)

enum class StarterSiteCoverLayout {
	FULL_COVER, RIGHT_COVER, LEFT_COVER, HALF_COVER, TOP_COVER, MENU
}


data class StarterSiteCoverPosition(
	var horizontal: StarterSiteCoverHorizontalAlign? = null,
	var vertical: StarterSiteCoverVerticalAlign? = null
)

enum class StarterSiteCoverHorizontalAlign {
	CENTER, LEFT, RIGHT
}

enum class StarterSiteCoverVerticalAlign {
	CENTER, TOP, BOTTOM
}

enum class StarterSiteCoverAlign {
	CENTER, LEFT
}

data class StarterSiteCoverBackground(
	var color: String? = null,
	var gradient: StarterSiteGradient? = null,
	var images: List<StarterSiteImage>? = null,
	var videoUrl: String? = null,
	var type: StarterSiteCoverBackgroundType? = null
)

enum class StarterSiteCoverBackgroundType {
	COLOR, GRADIENT, IMAGE, VIDEO
}

data class StarterSiteCoverOverlay(
	var color: String? = null,
	var gradient: StarterSiteGradient? = null
)

data class StarterSiteGradient(
	var direction: String? = null,
	var colors: List<String>? = null
)

data class StarterSiteLogo(
	var logoUrl: String? = null,
	var layout: StarterSiteLogoLayout? = null,
	var show: Boolean? = null,
	var homogeneity: Boolean? = null,
	var borderColor: String? = null,
	var position: StarterSiteLogoPosition? = null
)

enum class StarterSiteLogoLayout {
	ORIGINAL, CIRCLE
}

enum class StarterSiteLogoPosition {
	SHOW_ON_COVER, SHOW_BELOW_COVER, SHOW_ON_AND_BELOW_COVER, HIDE
}

data class StarterSiteCoverTitle(
	var text: String? = null,
	var color: String? = null,
	var fontFamily: String? = null,
	var showUnderSubtitle: Boolean? = null,
	var fontSize: StarterSiteCoverFontSize? = null,
	var show: Boolean? = null
)

data class StarterSiteCoverSubtitle(
	var content: String? = null,
	var color: String? = null,
	var fontFamily: String? = null,
	var fontSize: StarterSiteCoverFontSize? = null,
	var show: Boolean? = null
)

data class StarterSiteCoverButton(
	var text: String? = null,
	var color: String? = null,
	var backgroundColor: String? = null,
	var fontFamily: String? = null,
	var fontSize: StarterSiteCoverFontSize? = null,
	var show: Boolean? = null
)

enum class StarterSiteCoverFontSize {
	XXXS, XXS, XS, S, M, L, XL, XXL, XXXL, XXXXL
}

enum class StarterSitePhoneLayout {
	SHOW_IN_HEADER, SHOW_ON_COVER, HIDE
}

enum class StarterSiteSocialLinksLayout {
	SHOW_IN_HEADER, SHOW_ON_COVER, HIDE
}

data class StarterSiteCoverArrowDown(
	var text: String? = null,
	var show: Boolean? = null
)
