package com.ecwid.apiclient.v3.dto.profile.result

import com.ecwid.apiclient.v3.dto.common.ApiFetchedDTO
import com.ecwid.apiclient.v3.dto.common.ApiFetchedDTO.ModifyKind
import com.ecwid.apiclient.v3.dto.common.ApiResultDTO
import com.ecwid.apiclient.v3.dto.common.LocalizedValueMap
import com.ecwid.apiclient.v3.dto.common.ProductCondition
import com.ecwid.apiclient.v3.dto.profile.enums.ProductFilterType
import com.ecwid.apiclient.v3.dto.profile.request.UpdatedPaymentOption
import com.ecwid.apiclient.v3.dto.profile.request.UpdatedStoreProfile
import com.ecwid.apiclient.v3.jsontransformer.JsonFieldName
import java.util.*

data class FetchedStoreProfile(
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
	val payment: PaymentInfo? = null,
	val featureToggles: List<FeatureTogglesInfo>? = null,
	val designSettings: DesignSettings? = null,
	val productFiltersSettings: ProductFiltersSettings = ProductFiltersSettings(),
	val fbMessengerSettings: FBMessengerSettings? = null,
	val orderInvoiceSettings: OrderInvoiceSettings? = null,
	val giftCardSettings: GiftCardSettings? = null,
	val registrationAnswers: RegistrationAnswers? = null,
) : ApiFetchedDTO, ApiResultDTO {

	data class GeneralInfo(
		val storeId: Int = 0,
		val storeUrl: String? = null,
		val starterSite: InstantSiteInfo? = null,
		val websitePlatform: WebsitePlatform? = null
	)

	data class InstantSiteInfo(
		val ecwidSubdomain: String? = null,
		val customDomain: String? = null,
		val generatedUrl: String? = null,
		val storeLogoUrl: String? = null,
		val ecwidSubdomainSuffix: String? = null,
		val slugsWithoutIdsEnabled: Boolean? = null,
	)

	@Suppress("unused")
	enum class WebsitePlatform {
		unknown,

		adobeMuse,
		bitrix24,
		blogger,
		drupal,
		duda,
		etsy,
		facebook,
		godaddy,
		google_sites,
		iframe,
		instagram,
		instantsite,
		jimdo,
		joomla,
		prestashop,
		rapidWeaver,
		shopify,
		squarespace,
		strikingly,
		tilda,
		tumblr,
		typo3,
		ucraft,
		ukit,
		webflow,
		weblium,
		weebly,
		wix,
		wordpress,
		xara,
		yola,
	}

	data class Account(
		val accountName: String? = null,
		val accountNickName: String? = null,
		val accountEmail: String? = null,
		val availableFeatures: List<String>? = null,
		val whiteLabel: Boolean? = null,
		val brandName: String? = null,
		val supportEmail: String? = null,
		val registrationDate: Date = Date(),
	)

	data class RegistrationAnswers(
		val goods: String? = null,
		val alreadySelling: String? = null,
		val forSomeone: String? = null,
		val website: String? = null,
		val platform: String? = null,
		val facebook: String? = null,
		val otherGoods: String? = null
	)

	data class Settings(
		val abandonedSales: AbandonedSalesSettings? = null,
		val acceptMarketingCheckboxCustomText: String? = null,
		val acceptMarketingCheckboxDefaultValue: Boolean? = null,
		val askCompanyName: Boolean? = null,
		val askConsentToTrackInStorefront: Boolean? = null,
		val askTaxId: Boolean = false,
		val closed: Boolean? = null,
		val defaultProductSortOrder: ProductSortOrder? = null,
		val emailLogoUrl: String? = null,
		val favoritesEnabled: Boolean? = null,
		val fbPixelId: String? = null,
		val googleAnalyticsId: String? = null,
		val googleEventId: String? = null,
		val googleProductCategory: Int? = null,
		val googleProductCategoryName: String? = null,
		val googleRemarketingEnabled: Boolean? = null,
		val googleTagId: String? = null,
		val hideOutOfStockProductsInStorefront: Boolean? = null,
		val invoiceLogoUrl: String? = null,
		val openBagOnAddition: Boolean = false,
		val orderCommentsCaption: String? = null,
		val orderCommentsEnabled: Boolean? = null,
		val orderCommentsRequired: Boolean? = null,
		val pinterestTagId: String? = null,
		val productCondition: ProductCondition = ProductCondition.NEW,
		val productReviewsFeatureEnabled: Boolean? = null,
		val rootCategorySeoDescription: String? = null,
		val rootCategorySeoDescriptionTranslated: LocalizedValueMap? = null,
		val rootCategorySeoTitle: String? = null,
		val rootCategorySeoTitleTranslated: LocalizedValueMap? = null,
		val salePrice: SalePriceSettings? = null,
		val showAcceptMarketingCheckbox: Boolean? = null,
		val showPricePerUnit: Boolean = false,
		val showRepeatOrderButton: Boolean = false,
		val snapPixelId: String? = null,
		val storeDescription: String? = null,
		val storeDescriptionTranslated: LocalizedValueMap? = null,
		val storeName: String? = null,
		val tikTokPixel: TikTokPixelSettings? = null,
	)

	data class TikTokPixelSettings(
		val code: String? = null,
		val advancedMatching: Boolean = false
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
		val displayDiscount: DisplayDiscount? = null,
		val displayLowestPrice: Boolean? = null
	) {

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
		val currencyPrecision: Int? = null,
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
		val volumeUnit: VolumeUnit = VolumeUnit.ML,
		val orderNumberPrefix: String? = null,
		val orderNumberSuffix: String? = null,
		val orderNumberMinDigitsAmount: Int? = null,
		val orderNumberNextNumber: Int? = null,
	)

	enum class WeightUnit {
		CARAT, GRAM, OUNCE, POUND, KILOGRAM
	}

	enum class DimensionUnit {
		MM, CM, IN, YD
	}

	enum class VolumeUnit {
		L, ML, OZ
	}

	data class Languages(
		val enabledLanguages: List<String>? = null,
		val facebookPreferredLocale: String? = null,
		val defaultLanguage: String? = null
	)

	data class Shipping(
		val handlingFee: HandlingFee? = null,
		val shippingOrigin: ShippingOrigin? = null,
		val shippingOptions: List<ShippingOption>? = null,
		val domesticShippingOnly: Boolean = false,
		val euShippingOnly: Boolean = false,
	)

	data class HandlingFee(
		val name: String? = null,
		val value: Double? = null,
		val description: String? = null
	)

	data class ShippingOrigin(
		val companyName: String? = null,
		val email: String? = null,
		val street: String? = null,
		val city: String? = null,
		val countryCode: String? = null,
		val postalCode: String? = null,
		val stateOrProvinceCode: String? = null,
		val phone: String? = null
	)

	data class ShippingOption(
		val id: String? = null,
		val title: String? = null,
		val enabled: Boolean? = null,
		val orderBy: Int? = null,
		val fulfilmentType: FulfilmentType? = null,
		val destinationZone: Zone? = null,
		val deliveryTimeDays: String? = null,
		val description: String? = null,
		val carrier: String? = null,
		val carrierMethods: List<CarrierMethod>? = null,
		val carrierSettings: CarrierSettings? = null,
		val ratesCalculationType: RatesCalculationType? = null,
		val shippingCostMarkup: Double? = null,
		val flatRate: FlatRate? = null,
		val ratesTable: TableRatesDetails? = null,
		val appClientId: String? = null,
		val locationId: String? = null,
		val pickupInstruction: String? = null,
		val scheduledPickup: Boolean? = null,
		val pickupPreparationTimeHours: Int? = null,
		val pickupBusinessHours: String? = null,
		val scheduled: Boolean? = null,
		val scheduledTimePrecisionType: ScheduledTimePrecisionType? = null,
	)

	@Suppress("unused")
	enum class ScheduledTimePrecisionType {
		DATE,
		DATE_AND_TIME_SLOT
	}

	@Suppress("unused")
	enum class FulfilmentType {
		pickup, shipping, delivery
	}

	data class Zone(
		val id: String? = null,
		val name: String? = null,
		val countryCodes: List<String>? = null,
		val stateOrProvinceCodes: List<String>? = null,
		val postCodes: List<String>? = null
	)

	data class CarrierMethod(
		val id: String? = null,
		val name: String? = null,
		val enabled: Boolean? = null,
		val orderBy: Int? = null
	)

	data class CarrierSettings(
		val defaultCarrierAccountEnabled: Boolean? = null,
		val defaultPostageDimensions: DefaultPostageDimensions? = null
	)

	data class DefaultPostageDimensions(
		val length: Double? = null,
		val width: Double? = null,
		val height: Double? = null
	)

	@Suppress("unused")
	enum class RatesCalculationType {

		carrier_calculated,
		table,
		flat,
		app;

		override fun toString(): String {
			return asApiString()
		}

		fun asApiString(): String {
			return super.toString().replace("_", "-")
		}
	}

	data class FlatRate(
		val rateType: RateType? = null,
		val rate: Double? = null
	) {

		@Suppress("unused")
		enum class RateType {
			ABSOLUTE,
			PERCENT
		}
	}

	data class TableRatesDetails(
		val tableBasedOn: RateBase? = null,
		val rates: List<TableRate>? = null
	) {

		@Suppress("unused")
		enum class RateBase {
			subtotal,
			discountedSubtotal,
			weight
		}
	}

	data class TableRate(
		val tableRateConditions: TableRateConditions? = null,
		val rate: TableRateDetails? = null
	)

	data class TableRateConditions(
		val weightFrom: Double? = null,
		val weightTo: Double? = null,
		val subtotalFrom: Double? = null,
		val subtotalTo: Double? = null,
		val discountedSubtotalFrom: Double? = null,
		val discountedSubtotalTo: Double? = null
	)

	data class TableRateDetails(
		val perOrderAbs: Double? = null,
		val perOrderPercent: Double? = null,
		val perItem: Double? = null,
		val perWeightUnitRate: Double? = null
	)

	data class TaxSettings(
		val automaticTaxEnabled: Boolean? = null,
		val taxes: List<Taxes>? = null,
		val pricesIncludeTax: Boolean? = null,
		val taxExemptBusiness: Boolean = false
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

	data class PaymentInfo(
		val paymentOptions: List<PaymentOptionInfo>? = null,
		val applePay: ApplePay? = null,
		val applePayOptions: List<ApplePay> = emptyList(),
		val countryCode: String? = null,
	)

	data class PaymentOptionInfo(
		val appClientId: String? = null,
		val appNamespace: String? = null,
		val checkoutDescription: String? = null,
		val checkoutTitle: String? = null,
		val configured: Boolean? = null,
		val enabled: Boolean? = null,
		val id: String? = null,
		val instructionsForCustomer: InstructionsForCustomerInfo? = null,
		val methods: List<PaymentMethod>? = null,
		val orderBy: Int? = null,
		val paymentProcessorId: String? = null,
		val paymentProcessorTitle: String? = null,
		val shippingSettings: ShippingSettings? = null,
		val supportsSubtypes: Boolean? = null,
	) : ApiFetchedDTO {
		override fun getModifyKind() = ModifyKind.ReadWrite(UpdatedPaymentOption::class)
	}

	data class ApplePay(
		val enabled: Boolean = false,
		val available: Boolean = false,
		val gateway: String? = null,
		val verificationFileUrl: String? = null
	)

	data class InstructionsForCustomerInfo(
		val instructionsTitle: String? = null,
		val instructions: String? = null
	)

	data class ShippingSettings(
		val enabledShippingMethods: List<String>? = null
	)

	data class PaymentMethod(
		val cards: List<String>? = null,
		val subtype: String? = null,
		val subtypeMethodName: String? = null,
	)

	data class FeatureTogglesInfo(
		val name: String? = null,
		val visible: Boolean? = null,
		val enabled: Boolean? = null
	)

	data class DesignSettings(
		@JsonFieldName("breadcrumbs_have_home_item")
		val breadcrumbsHaveHomeItem: Boolean? = null,

		@JsonFieldName("breadcrumbs_home_url")
		val breadcrumbsHomeUrl: String? = null,

		@JsonFieldName("breadcrumbs_separator")
		val breadcrumbsSeparator: String? = null,

		@JsonFieldName("cart_widget_fixed")
		val cartWidgetFixed: Boolean? = null,

		@JsonFieldName("cart_widget_fixed_position")
		val cartWidgetFixedPosition: String? = null,

		@JsonFieldName("cart_widget_fixed_shape")
		val cartWidgetFixedShape: String? = null,

		@JsonFieldName("cart_widget_horizontal_indent")
		val cartWidgetHorizontalIndent: Int? = null,

		@JsonFieldName("cart_widget_icon")
		val cartWidgetIcon: String? = null,

		@JsonFieldName("cart_widget_is_responsive")
		val cartWidgetIsResponsive: Boolean? = null,

		@JsonFieldName("cart_widget_layout")
		val cartWidgetLayout: String? = null,

		@JsonFieldName("cart_widget_show_buy_animation")
		val cartWidgetShowBuyAnimation: Boolean? = null,

		@JsonFieldName("cart_widget_show_empty_cart")
		val cartWidgetShowEmptyCart: Boolean? = null,

		@JsonFieldName("cart_widget_store_custom_icon_url")
		val cartWidgetStoreCustomIconUrl: String? = null,

		@JsonFieldName("cart_widget_vertical_indent")
		val cartWidgetVerticalIndent: Int? = null,

		@JsonFieldName("checkout_products_collapsed_on_desktop")
		val checkoutProductsCollapsedOnDesktop: Boolean? = null,

		@JsonFieldName("checkout_products_collapsed_on_mobile")
		val checkoutProductsCollapsedOnMobile: Boolean? = null,

		@JsonFieldName("checkout_show_address_line_2")
		val checkoutShowAddressLine2: Boolean? = null,

		@JsonFieldName("checkout_show_state_input")
		val checkoutShowStateInput: Boolean? = null,

		@JsonFieldName("enable_catalog_on_one_page")
		val enableCatalogOnOnePage: Boolean? = null,

		@JsonFieldName("enable_catalog_seamless_product_list_view")
		val enableCatalogSeamlessProductListView: Boolean? = null,

		@JsonFieldName("enable_page_transitions")
		val enablePageTransitions: Boolean? = null,

		@JsonFieldName("enable_simple_category_list")
		val enableSimpleCategoryList: Boolean? = null,

		@JsonFieldName("feedback_message_position")
		val feedbackMessagePosition: String? = null,

		@JsonFieldName("legal_pages_show_page_links")
		val legalPagesShowPageLinks: Boolean? = null,

		@JsonFieldName("product_details_additional_images_has_shadow")
		val productDetailsAdditionalImagesHasShadow: Boolean? = null,

		@JsonFieldName("product_details_additional_images_preview_on_click")
		val productDetailsAdditionalImagesPreviewOnClick: Boolean? = null,

		@JsonFieldName("product_details_cut_product_description_in_sidebar")
		val productDetailsCutProductDescriptionInSidebar: Boolean? = null,

		@JsonFieldName("product_details_gallery_layout")
		val productDetailsGalleryLayout: String? = null,

		@JsonFieldName("product_details_hide_price_modifiers")
		val productDetailsHidePriceModifiers: Boolean? = null,

		@JsonFieldName("product_details_image_carousel")
		val productDetailsImageCarousel: Boolean? = null,

		@JsonFieldName("product_details_layout")
		val productDetailsLayout: String? = null,

		@JsonFieldName("product_details_position_breadcrumbs")
		val productDetailsPositionBreadcrumbs: Int? = null,

		@JsonFieldName("product_details_position_buy_button")
		val productDetailsPositionBuyButton: Int? = null,

		@JsonFieldName("product_details_position_delivery_time")
		val productDetailsPositionDeliveryTime: Int? = null,

		@JsonFieldName("product_details_position_product_description")
		val productDetailsPositionProductDescription: Int? = null,

		@JsonFieldName("product_details_position_product_name")
		val productDetailsPositionProductName: Int? = null,

		@JsonFieldName("product_details_position_product_options")
		val productDetailsPositionProductOptions: Int? = null,

		@JsonFieldName("product_details_position_product_price")
		val productDetailsPositionProductPrice: Int? = null,

		@JsonFieldName("product_details_position_product_loyalty")
		val productDetailsPositionProductLoyalty: Int? = null,

		@JsonFieldName("product_details_position_product_sku")
		val productDetailsPositionProductSku: Int? = null,

		@JsonFieldName("product_details_position_review_section")
		val productDetailsPositionReviewSection: Int? = null,

		@JsonFieldName("product_details_position_save_for_later")
		val productDetailsPositionSaveForLater: Int? = null,

		@JsonFieldName("product_details_position_share_buttons")
		val productDetailsPositionShareButtons: Int? = null,

		@JsonFieldName("product_details_position_subtitle")
		val productDetailsPositionSubtitle: Int? = null,

		@JsonFieldName("product_details_position_wholesale_prices")
		val productDetailsPositionWholesalePrices: Int? = null,

		@JsonFieldName("product_details_show_attributes")
		val productDetailsShowAttributes: Boolean? = null,

		@JsonFieldName("product_details_show_breadcrumbs")
		val productDetailsShowBreadcrumbs: Boolean? = null,

		@JsonFieldName("product_details_show_breadcrumbs_position")
		val productDetailsShowBreadcrumbsPosition: String? = null,

		@JsonFieldName("product_details_show_buy_button")
		val productDetailsShowBuyButton: Boolean? = null,

		@JsonFieldName("product_details_show_delivery_time")
		val productDetailsShowDeliveryTime: Boolean? = null,

		@JsonFieldName("product_details_show_facebook_share_button")
		val productDetailsShowFacebookShareButton: Boolean? = null,

		@JsonFieldName("product_details_show_in_stock_label")
		val productDetailsShowInStockLabel: Boolean? = null,

		@JsonFieldName("product_details_show_navigation_arrows")
		val productDetailsShowNavigationArrows: Boolean? = null,

		@JsonFieldName("product_details_show_number_of_items_in_stock")
		val productDetailsShowNumberOfItemsInStock: Boolean? = null,

		@JsonFieldName("product_details_show_pinterest_share_button")
		val productDetailsShowPinterestShareButton: Boolean? = null,

		@JsonFieldName("product_details_show_price_per_unit")
		val productDetailsShowPricePerUnit: Boolean? = null,

		@JsonFieldName("product_details_show_product_description")
		val productDetailsShowProductDescription: Boolean? = null,

		@JsonFieldName("product_details_show_product_name")
		val productDetailsShowProductName: Boolean? = null,

		@JsonFieldName("product_details_show_product_name_always_first_on_mobile")
		val productDetailsShowProductNameAlwaysFirstOnMobile: Boolean? = null,

		@JsonFieldName("product_details_show_product_options")
		val productDetailsShowProductOptions: Boolean? = null,

		@JsonFieldName("product_details_show_product_photo_zoom")
		val productDetailsShowProductPhotoZoom: Boolean? = null,

		@JsonFieldName("product_details_show_product_price")
		val productDetailsShowProductPrice: Boolean? = null,

		@JsonFieldName("product_details_show_product_loyalty")
		val productDetailsShowProductLoyalty: Boolean? = null,

		@JsonFieldName("product_details_show_product_sku")
		val productDetailsShowProductSku: Boolean? = null,

		@JsonFieldName("product_details_show_qty")
		val productDetailsShowQty: Boolean? = null,

		@JsonFieldName("product_details_show_rating_section")
		val productDetailsShowRatingSection: Boolean? = null,

		@JsonFieldName("product_details_show_reviews_section")
		val productDetailsShowReviewsSection: Boolean? = null,

		@JsonFieldName("product_details_show_reviews_section_in_one_card_view")
		val productDetailsShowReviewsSectionInOneCardView: Boolean? = null,

		@JsonFieldName("product_details_show_sale_price")
		val productDetailsShowSalePrice: Boolean? = null,

		@JsonFieldName("product_details_show_save_for_later")
		val productDetailsShowSaveForLater: Boolean? = null,

		@JsonFieldName("product_details_show_share_buttons")
		val productDetailsShowShareButtons: Boolean? = null,

		@JsonFieldName("product_details_show_subtitle")
		val productDetailsShowSubtitle: Boolean? = null,

		@JsonFieldName("product_details_show_tax")
		val productDetailsShowTax: Boolean? = null,

		@JsonFieldName("product_details_show_twitter_share_button")
		val productDetailsShowTwitterShareButton: Boolean? = null,

		@JsonFieldName("product_details_show_vk_share_button")
		val productDetailsShowVkShareButton: Boolean? = null,

		@JsonFieldName("product_details_show_weight")
		val productDetailsShowWeight: Boolean? = null,

		@JsonFieldName("product_details_show_wholesale_prices")
		val productDetailsShowWholesalePrices: Boolean? = null,

		@JsonFieldName("product_details_show_zoomed_image_in_gallery")
		val productDetailsShowZoomedImageInGallery: Boolean? = null,

		@JsonFieldName("product_details_thumbnails_aspect_ratio")
		val productDetailsThumbnailsAspectRatio: String? = null,

		@JsonFieldName("product_details_two_columns_with_left_sidebar_show_product_description_on_sidebar")
		val productDetailsTwoColumnsWithLeftSidebarShowProductDescriptionOnSidebar: Boolean? = null,

		@JsonFieldName("product_details_two_columns_with_right_sidebar_show_product_description_on_sidebar")
		val productDetailsTwoColumnsWithRightSidebarShowProductDescriptionOnSidebar: Boolean? = null,

		@JsonFieldName("product_filters_opened_by_default_on_catalog_pages")
		val productFiltersOpenedByDefaultOnCatalogPages: Boolean? = null,

		@JsonFieldName("product_filters_opened_by_default_on_category_page")
		val productFiltersOpenedByDefaultOnCategoryPage: Boolean? = null,

		@JsonFieldName("product_filters_position_category_page")
		val productFiltersPositionCategoryPage: String? = null,

		@JsonFieldName("product_filters_position_on_catalog_pages")
		val productFiltersPositionOnCatalogPages: String? = null,

		@JsonFieldName("product_filters_position_search_page")
		val productFiltersPositionSearchPage: String? = null,

		@JsonFieldName("product_filters_visible_on_catalog_pages")
		val productFiltersVisibleOnCatalogPages: Boolean? = null,

		@JsonFieldName("product_list_buybutton_behavior")
		val productListBuyNowBehaviour: String? = null,

		@JsonFieldName("product_list_product_info_layout")
		val productListCardLayout: String? = null,

		@JsonFieldName("product_list_category_cell_spacing")
		val productListCategoryCellSpacing: Int? = null,

		@JsonFieldName("product_list_category_image_aspect_ratio")
		val productListCategoryImageLayout: String? = null,

		@JsonFieldName("product_list_category_image_position")
		val productListCategoryImagePosition: String? = null,

		@JsonFieldName("product_list_category_image_size")
		val productListCategoryImageSize: String? = null,

		@JsonFieldName("product_list_category_title_behavior")
		val productListCategoryNameBehaviour: String? = null,

		@JsonFieldName("product_list_cell_spacing")
		val productListCellSpacing: Int? = null,

		@JsonFieldName("product_list_image_has_shadow")
		val productListImageHasShadow: Boolean? = null,

		@JsonFieldName("product_list_image_aspect_ratio")
		val productListImageLayout: String? = null,

		@JsonFieldName("product_list_image_position")
		val productListImagePosition: String? = null,

		@JsonFieldName("product_list_image_size")
		val productListImageSize: String? = null,

		@JsonFieldName("product_list_title_behavior")
		val productListNameBehaviour: String? = null,

		@JsonFieldName("product_list_price_behavior")
		val productListPriceBehaviour: String? = null,

		@JsonFieldName("product_list_rating_section_behavior")
		val productListRatingSectionBehavior: String? = null,

		@JsonFieldName("product_list_sku_behavior")
		val productListSKUBehaviour: String? = null,

		@JsonFieldName("product_list_show_additional_image_on_hover")
		val productListShowAdditionalImage: Boolean? = null,

		@JsonFieldName("show_breadcrumbs")
		val productListShowBreadcrumbs: Boolean? = null,

		@JsonFieldName("product_list_show_frame")
		val productListShowCardFrame: Boolean? = null,

		@JsonFieldName("show_footer_menu")
		val productListShowFooterMenu: Boolean? = null,

		@JsonFieldName("product_list_show_on_sale_label")
		val productListShowOnSaleLabel: Boolean? = null,

		@JsonFieldName("product_list_show_name_your_price_label")
		val productListShowPayWhatYouWantLabel: Boolean? = null,

		@JsonFieldName("product_list_show_product_images")
		val productListShowProductImages: Boolean? = null,

		@JsonFieldName("product_list_show_rating_in_one_star")
		val productListShowRatingInOneStar: Boolean? = null,

		@JsonFieldName("product_list_show_rating_number_in_five_stars_view")
		val productListShowRatingNumberInFiveStarsView: Boolean? = null,

		@JsonFieldName("product_list_show_reviews_count_in_five_stars_view")
		val productListShowReviewsCountInFiveStarsView: Boolean? = null,

		@JsonFieldName("show_signin_link")
		val productListShowSignInLink: Boolean? = null,

		@JsonFieldName("product_list_show_sold_out_label")
		val productListShowSoldOutLabel: Boolean? = null,

		@JsonFieldName("product_list_show_sort_viewas_options")
		val productListShowSortViewAsOptions: Boolean? = null,

		@JsonFieldName("product_list_subtitles_behavior")
		val productListSubtitlesBehavior: String? = null,

		@JsonFieldName("shopping_cart_products_collapsed_on_desktop")
		val shoppingCartProductsCollapsedOnDesktop: Boolean? = null,

		@JsonFieldName("shopping_cart_products_collapsed_on_mobile")
		val shoppingCartProductsCollapsedOnMobile: Boolean? = null,

		@JsonFieldName("shopping_cart_show_sku")
		val shoppingCartShowSku: Boolean? = null,

		@JsonFieldName("shopping_cart_show_qty_inputs")
		val shoppingCartShowQtyInputs: Boolean? = null,

		@JsonFieldName("shopping_cart_show_weight")
		val shoppingCartShowWeight: Boolean? = null,

		@JsonFieldName("show_cart_widget")
		val showCartWidget: Boolean? = null,

		@JsonFieldName("show_root_categories")
		val showRootCategories: Boolean? = null,

		@JsonFieldName("show_signin_link_with_unified_account_page")
		val showSigninLinkWithUnifiedAccountPage: Boolean? = null,
	)

	data class ProductFilterItem(
		val name: String? = null,
		val type: ProductFilterType = ProductFilterType.IN_STOCK,
		val enabled: Boolean = false,
	)

	data class ProductFiltersSettings(
		val enabledInStorefront: Boolean = false,
		val filterSections: List<ProductFilterItem> = listOf(),
	)

	data class FBMessengerSettings(
		val enabled: Boolean? = null,
		val fbMessengerPageId: String? = null,
		val fbMessengerThemeColor: String? = null,
		val fbMessengerMessageUsButtonColor: String? = null
	)

	data class OrderInvoiceSettings(
		val displayOrderInvoices: Boolean? = null,
		val attachInvoiceToOrderEmailNotifications: AttachValue? = null,
		val invoiceLogoUrl: String? = null
	) {
		enum class AttachValue {
			ATTACH_TO_ALL_EMAILS, DO_NOT_ATTACH
		}
	}

	data class GiftCardSettings(
		val products: List<GiftCardProducts>? = null,
		val displayLocation: String? = null
	)

	data class GiftCardProducts(
		val id: Int? = null,
		val name: String? = null,
		val url: String? = null
	)

	override fun getModifyKind() = ModifyKind.ReadWrite(UpdatedStoreProfile::class)
}
