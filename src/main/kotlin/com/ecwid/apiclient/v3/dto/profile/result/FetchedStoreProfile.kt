package com.ecwid.apiclient.v3.dto.profile.result

import com.google.gson.annotations.SerializedName

data class FetchedStoreProfile(
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
		var payment: PaymentInfo? = null,
		var featureToggles: List<FeatureTogglesInfo>? = null,
		var designSettings: DesignSettings? = null,
		var productFiltersSettings: ProductFiltersSettings? = null,
		var fbMessengerSettings: FBMessengerSettings? = null,
		var orderInvoiceSettings: OrderInvoiceSettings? = null,
		var giftCardSettings: GiftCardSettings? = null
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
			var dimensionsUnit: DimensionUnit? = null,
			var orderNumberPrefix: String? = null,
			var orderNumberSuffix: String? = null
	)

	enum class WeightUnit {
		CARAT, GRAM, OUNCE, POUND, KILOGRAM
	}

	enum class DimensionUnit {
		MM, CM, IN, YD
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
			var length: Double? = null,
			var width: Double? = null,
			var height: Double? = null
	)

	enum class RatesCalculationType {
		carrier_calculated, table, flat, app;

		override fun toString(): String {
			return super.toString().replace("_", "-")
		}
	}

	fun RatesCalculationType.valueOf(value: String?): RatesCalculationType? {
		return RatesCalculationType.values().firstOrNull { it.toString() == value }
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

	data class TaxSettings(
			var automaticTaxEnabled: Boolean? = null,
			var taxes: List<Taxes>? = null,
			var pricesIncludeTax: Boolean? = null
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

	data class PaymentInfo(
			var paymentOptions: List<PaymentOptionInfo>? = null,
			var applePay: ApplePay? = null
	)

	data class PaymentOptionInfo(
			var id: String? = null,
			var enabled: Boolean? = null,
			var checkoutTitle: String? = null,
			var checkoutDescription: String? = null,
			var paymentProcessorId: String? = null,
			var paymentProcessorTitle: String? = null,
			var orderBy: Int? = null,
			var appClientId: String? = null,
			var instructionsForCustomer: InstructionsForCustomerInfo? = null
	)

	data class ApplePay(
			var enabled: Boolean,
			var available: Boolean,
			var gateway: String?,
			var verificationFileUrl: String?
	)

	data class InstructionsForCustomerInfo(
			var instructionsTitle: String? = null,
			var instructions: String? = null
	)

	data class FeatureTogglesInfo(
			var name: String? = null,
			var visible: Boolean? = null,
			var enabled: Boolean? = null
	)

	data class DesignSettings(
			@SerializedName("enable_catalog_on_one_page")
			var enableCatalogOnOnePage: Boolean? = null,

			@SerializedName("product_list_image_size")
			var productListImageSize: String? = null,

			@SerializedName("product_list_image_aspect_ratio")
			var productListImageLayout: String? = null,

			@SerializedName("product_list_image_position")
			var productListImagePosition: String? = null,

			@SerializedName("product_list_category_image_size")
			var productListCategoryImageSize: String? = null,

			@SerializedName("product_list_category_image_aspect_ratio")
			var productListCategoryImageLayout: String? = null,

			@SerializedName("product_list_category_image_position")
			var productListCategoryImagePosition: String? = null,

			@SerializedName("product_list_show_product_images")
			var productListShowProductImages: Boolean? = null,

			@SerializedName("product_list_product_info_layout")
			var productListCardLayout: String? = null,

			@SerializedName("product_list_show_frame")
			var productListShowCardFrame: Boolean? = null,

			@SerializedName("product_list_show_additional_image_on_hover")
			var productListShowAdditionalImage: Boolean? = null,

			@SerializedName("product_list_title_behavior")
			var productListNameBehaviour: String? = null,

			@SerializedName("product_list_price_behavior")
			var productListPriceBehaviour: String? = null,

			@SerializedName("product_list_sku_behavior")
			var productListSKUBehaviour: String? = null,

			@SerializedName("product_list_buybutton_behavior")
			var productListBuyNowBehaviour: String? = null,

			@SerializedName("product_list_category_title_behavior")
			var productListCategoryNameBehaviour: String? = null,

			@SerializedName("product_list_image_has_shadow")
			var productListImageHasShadow: Boolean? = null,

			@SerializedName("show_signin_link")
			var productListShowSignInLink: Boolean? = null,

			@SerializedName("show_footer_menu")
			var productListShowFooterMenu: Boolean? = null,

			@SerializedName("show_breadcrumbs")
			var productListShowBreadcrumbs: Boolean? = null,

			@SerializedName("product_list_show_sort_viewas_options")
			var productListShowSortViewAsOptions: Boolean? = null,

			@SerializedName("product_list_show_on_sale_label")
			var productListShowOnSaleLabel: Boolean? = null,

			@SerializedName("product_list_show_sold_out_label")
			var productListShowSoldOutLabel: Boolean? = null,

			@SerializedName("product_details_show_product_sku")
			var productDetailsShowProductSku: Boolean? = null,

			@SerializedName("product_details_additional_images_has_shadow")
			var productDetailsAdditionalImagesHasShadow: Boolean? = null,

			@SerializedName("product_details_image_carousel")
			var productDetailsImageCarousel: Boolean? = null,

			@SerializedName("product_details_additional_images_preview_on_click")
			var productDetailsAdditionalImagesPreviewOnClick: Boolean? = null,

			@SerializedName("product_details_layout")
			var productDetailsLayout: String? = null,

			@SerializedName("product_details_two_columns_with_right_sidebar_show_product_description_on_sidebar")
			var productDetailsTwoColumnsWithRightSidebarShowProductDescriptionOnSidebar: Boolean? = null,

			@SerializedName("product_details_two_columns_with_left_sidebar_show_product_description_on_sidebar")
			var productDetailsTwoColumnsWithLeftSidebarShowProductDescriptionOnSidebar: Boolean? = null,

			@SerializedName("product_details_show_product_name")
			var productDetailsShowProductName: Boolean? = null,

			@SerializedName("product_details_show_breadcrumbs")
			var productDetailsShowBreadcrumbs: Boolean? = null,

			@SerializedName("product_details_show_product_price")
			var productDetailsShowProductPrice: Boolean? = null,

			@SerializedName("product_details_show_sale_price")
			var productDetailsShowSalePrice: Boolean? = null,

			@SerializedName("product_details_show_tax")
			var productDetailsShowTax: Boolean? = null,

			@SerializedName("product_details_show_attributes")
			var productDetailsShowAttributes: Boolean? = null,

			@SerializedName("product_details_show_weight")
			var productDetailsShowWeight: Boolean? = null,

			@SerializedName("product_details_show_product_description")
			var productDetailsShowProductDescription: Boolean? = null,

			@SerializedName("product_details_show_product_options")
			var productDetailsShowProductOptions: Boolean? = null,

			@SerializedName("product_details_show_buy_button")
			var productDetailsShowBuyButton: Boolean? = null,

			@SerializedName("product_details_show_wholesale_prices")
			var productDetailsShowWholesalePrices: Boolean? = null,

			@SerializedName("product_details_show_save_for_later")
			var productDetailsShowSaveForLater: Boolean? = null,

			@SerializedName("product_details_show_share_buttons")
			var productDetailsShowShareButtons: Boolean? = null,

			@SerializedName("product_details_show_product_name_always_first_on_mobile")
			var productDetailsShowProductNameAlwaysFirstOnMobile: Boolean? = null,

			@SerializedName("product_details_position_product_name")
			var productDetailsPositionProductName: Int? = null,

			@SerializedName("product_details_position_breadcrumbs")
			var productDetailsPositionBreadcrumbs: Int? = null,

			@SerializedName("product_details_position_product_sku")
			var productDetailsPositionProductSku: Int? = null,

			@SerializedName("product_details_position_product_price")
			var productDetailsPositionProductPrice: Int? = null,

			@SerializedName("product_details_position_product_options")
			var productDetailsPositionProductOptions: Int? = null,

			@SerializedName("product_details_position_buy_button")
			var productDetailsPositionBuyButton: Int? = null,

			@SerializedName("product_details_position_wholesale_prices")
			var productDetailsPositionWholesalePrices: Int? = null,

			@SerializedName("product_details_position_product_description")
			var productDetailsPositionProductDescription: Int? = null,

			@SerializedName("product_details_position_save_for_later")
			var productDetailsPositionSaveForLater: Int? = null,

			@SerializedName("product_details_position_share_buttons")
			var productDetailsPositionShareButtons: Int? = null,

			@SerializedName("product_details_hide_price_modifiers")
			var productDetailsHidePriceModifiers: Boolean? = null,

			@SerializedName("product_details_show_price_per_unit")
			var productDetailsShowPricePerUnit: Boolean? = null,

			@SerializedName("product_details_cut_product_description_in_sidebar")
			var productDetailsCutProductDescriptionInSidebar: Boolean? = null,

			@SerializedName("product_details_thumbnails_aspect_ratio")
			var productDetailsThumbnailsAspectRatio: String? = null,

			@SerializedName("product_details_show_qty")
			var productDetailsShowQty: Boolean? = null,

			@SerializedName("product_details_show_in_stock_label")
			var productDetailsShowInStockLabel: Boolean? = null,

			@SerializedName("product_details_show_number_of_items_in_stock")
			var productDetailsShowNumberOfItemsInStock: Boolean? = null,

			@SerializedName("product_details_gallery_layout")
			var productDetailsGalleryLayout: String? = null,

			@SerializedName("product_details_show_facebook_share_button")
			var productDetailsShowFacebookShareButton: Boolean? = null,

			@SerializedName("product_details_show_twitter_share_button")
			var productDetailsShowTwitterShareButton: Boolean? = null,

			@SerializedName("product_details_show_pinterest_share_button")
			var productDetailsShowPinterestShareButton: Boolean? = null,

			@SerializedName("product_details_show_vk_share_button")
			var productDetailsShowVkShareButton: Boolean? = null,

			@SerializedName("enable_simple_category_list")
			var enableSimpleCategoryList: Boolean? = null,

			@SerializedName("enable_page_transitions")
			var enablePageTransitions: Boolean? = null,

			@SerializedName("product_filters_opened_by_default_on_category_page")
			var productFiltersOpenedByDefaultOnCategoryPage: Boolean? = null,

			@SerializedName("product_filters_position_category_page")
			var productFiltersPositionCategoryPage: String? = null,

			@SerializedName("show_root_categories")
			var showRootCategories: Boolean? = null,

			@SerializedName("product_details_show_subtitle")
			var productDetailsShowSubtitle: Boolean? = null,

			@SerializedName("product_list_subtitles_behavior")
			var productListSubtitlesBehavior: String? = null
	)

	data class ProductFiltersSettings(
			var enabledInStorefront: Boolean? = null
	)

	data class FBMessengerSettings(
			var enabled: Boolean? = null,
			var fbMessengerPageId: String? = null,
			var fbMessengerThemeColor: String? = null,
			var fbMessengerMessageUsButtonColor: String? = null
	)

	data class OrderInvoiceSettings(
			var displayOrderInvoices: Boolean? = null,
			var attachInvoiceToOrderEmailNotifications: AttachValue? = null,
			var invoiceLogoUrl: String? = null
	) {
		enum class AttachValue {
			ATTACH_TO_ALL_EMAILS, DO_NOT_ATTACH
		}
	}

	data class GiftCardSettings(
			var products: List<GiftCardProducts>? = null,
			var displayLocation: String? = null
	)

	data class GiftCardProducts(
			var id: Int? = null,
			var name: String? = null,
			var url: String? = null
	)

}
