package com.ecwid.apiclient.v3.rule.nullablepropertyrules

import com.ecwid.apiclient.v3.dto.profile.result.FetchedStoreProfile
import com.ecwid.apiclient.v3.rule.NullablePropertyRule
import com.ecwid.apiclient.v3.rule.NullablePropertyRule.AllowNullable
import com.ecwid.apiclient.v3.rule.NullablePropertyRule.IgnoreNullable

val fetchedStoreProfileNullablePropertyRules: List<NullablePropertyRule<*, *>> = listOf(
	IgnoreNullable(FetchedStoreProfile::account),
	IgnoreNullable(FetchedStoreProfile::businessRegistrationID),
	IgnoreNullable(FetchedStoreProfile::company),
	IgnoreNullable(FetchedStoreProfile::designSettings),
	IgnoreNullable(FetchedStoreProfile::fbMessengerSettings),
	IgnoreNullable(FetchedStoreProfile::featureToggles),
	IgnoreNullable(FetchedStoreProfile::formatsAndUnits),
	IgnoreNullable(FetchedStoreProfile::generalInfo),
	IgnoreNullable(FetchedStoreProfile::giftCardSettings),
	IgnoreNullable(FetchedStoreProfile::languages),
	IgnoreNullable(FetchedStoreProfile::legalPagesSettings),
	IgnoreNullable(FetchedStoreProfile::mailNotifications),
	IgnoreNullable(FetchedStoreProfile::orderInvoiceSettings),
	IgnoreNullable(FetchedStoreProfile::payment),
	IgnoreNullable(FetchedStoreProfile::productFiltersSettings),
	IgnoreNullable(FetchedStoreProfile::settings),
	IgnoreNullable(FetchedStoreProfile::shipping),
	IgnoreNullable(FetchedStoreProfile::taxSettings),
	IgnoreNullable(FetchedStoreProfile::zones),
	IgnoreNullable(FetchedStoreProfile.AbandonedSalesSettings::autoAbandonedSalesRecovery),
	IgnoreNullable(FetchedStoreProfile.Account::accountEmail),
	IgnoreNullable(FetchedStoreProfile.Account::accountName),
	IgnoreNullable(FetchedStoreProfile.Account::accountNickName),
	IgnoreNullable(FetchedStoreProfile.Account::availableFeatures),
	IgnoreNullable(FetchedStoreProfile.Account::whiteLabel),
	AllowNullable(FetchedStoreProfile.Account::brandName),
	AllowNullable(FetchedStoreProfile.Account::supportEmail),
	IgnoreNullable(FetchedStoreProfile.ApplePay::gateway),
	IgnoreNullable(FetchedStoreProfile.ApplePay::verificationFileUrl),
	IgnoreNullable(FetchedStoreProfile.BusinessRegistrationID::name),
	IgnoreNullable(FetchedStoreProfile.BusinessRegistrationID::value),
	IgnoreNullable(FetchedStoreProfile.CarrierMethod::enabled),
	IgnoreNullable(FetchedStoreProfile.CarrierMethod::id),
	IgnoreNullable(FetchedStoreProfile.CarrierMethod::name),
	IgnoreNullable(FetchedStoreProfile.CarrierMethod::orderBy),
	IgnoreNullable(FetchedStoreProfile.CarrierSettings::defaultCarrierAccountEnabled),
	IgnoreNullable(FetchedStoreProfile.CarrierSettings::defaultPostageDimensions),
	IgnoreNullable(FetchedStoreProfile.Company::city),
	IgnoreNullable(FetchedStoreProfile.Company::companyName),
	IgnoreNullable(FetchedStoreProfile.Company::countryCode),
	IgnoreNullable(FetchedStoreProfile.Company::email),
	IgnoreNullable(FetchedStoreProfile.Company::phone),
	IgnoreNullable(FetchedStoreProfile.Company::postalCode),
	IgnoreNullable(FetchedStoreProfile.Company::stateOrProvinceCode),
	IgnoreNullable(FetchedStoreProfile.Company::street),
	IgnoreNullable(FetchedStoreProfile.DefaultPostageDimensions::height),
	IgnoreNullable(FetchedStoreProfile.DefaultPostageDimensions::length),
	IgnoreNullable(FetchedStoreProfile.DefaultPostageDimensions::width),
	AllowNullable(FetchedStoreProfile.DesignSettings::breadcrumbsHaveHomeItem),
	AllowNullable(FetchedStoreProfile.DesignSettings::breadcrumbsHomeUrl),
	AllowNullable(FetchedStoreProfile.DesignSettings::breadcrumbsSeparator),
	AllowNullable(FetchedStoreProfile.DesignSettings::cartWidgetFixed),
	AllowNullable(FetchedStoreProfile.DesignSettings::cartWidgetFixedPosition),
	AllowNullable(FetchedStoreProfile.DesignSettings::cartWidgetFixedShape),
	AllowNullable(FetchedStoreProfile.DesignSettings::cartWidgetHorizontalIndent),
	AllowNullable(FetchedStoreProfile.DesignSettings::cartWidgetIcon),
	AllowNullable(FetchedStoreProfile.DesignSettings::cartWidgetLayout),
	AllowNullable(FetchedStoreProfile.DesignSettings::cartWidgetShowBuyAnimation),
	AllowNullable(FetchedStoreProfile.DesignSettings::cartWidgetShowEmptyCart),
	AllowNullable(FetchedStoreProfile.DesignSettings::cartWidgetStoreCustomIconUrl),
	AllowNullable(FetchedStoreProfile.DesignSettings::cartWidgetVerticalIndent),
	AllowNullable(FetchedStoreProfile.DesignSettings::checkoutProductsCollapsedOnDesktop),
	AllowNullable(FetchedStoreProfile.DesignSettings::checkoutProductsCollapsedOnMobile),
	AllowNullable(FetchedStoreProfile.DesignSettings::enableCatalogOnOnePage),
	AllowNullable(FetchedStoreProfile.DesignSettings::enablePageTransitions),
	AllowNullable(FetchedStoreProfile.DesignSettings::enableSimpleCategoryList),
	AllowNullable(FetchedStoreProfile.DesignSettings::feedbackMessagePosition),
	AllowNullable(FetchedStoreProfile.DesignSettings::legalPagesShowPageLinks),
	AllowNullable(FetchedStoreProfile.DesignSettings::productDetailsAdditionalImagesHasShadow),
	AllowNullable(FetchedStoreProfile.DesignSettings::productDetailsAdditionalImagesPreviewOnClick),
	AllowNullable(FetchedStoreProfile.DesignSettings::productDetailsCutProductDescriptionInSidebar),
	AllowNullable(FetchedStoreProfile.DesignSettings::productDetailsGalleryLayout),
	AllowNullable(FetchedStoreProfile.DesignSettings::productDetailsHidePriceModifiers),
	AllowNullable(FetchedStoreProfile.DesignSettings::productDetailsImageCarousel),
	AllowNullable(FetchedStoreProfile.DesignSettings::productDetailsLayout),
	AllowNullable(FetchedStoreProfile.DesignSettings::productDetailsPositionBreadcrumbs),
	AllowNullable(FetchedStoreProfile.DesignSettings::productDetailsPositionBuyButton),
	AllowNullable(FetchedStoreProfile.DesignSettings::productDetailsPositionProductDescription),
	AllowNullable(FetchedStoreProfile.DesignSettings::productDetailsPositionDeliveryTime),
	AllowNullable(FetchedStoreProfile.DesignSettings::productDetailsPositionProductName),
	AllowNullable(FetchedStoreProfile.DesignSettings::productDetailsPositionProductOptions),
	AllowNullable(FetchedStoreProfile.DesignSettings::productDetailsPositionProductPrice),
	AllowNullable(FetchedStoreProfile.DesignSettings::productDetailsPositionProductSku),
	AllowNullable(FetchedStoreProfile.DesignSettings::productDetailsPositionSaveForLater),
	AllowNullable(FetchedStoreProfile.DesignSettings::productDetailsPositionShareButtons),
	AllowNullable(FetchedStoreProfile.DesignSettings::productDetailsPositionSubtitle),
	AllowNullable(FetchedStoreProfile.DesignSettings::productDetailsPositionWholesalePrices),
	AllowNullable(FetchedStoreProfile.DesignSettings::productDetailsShowAttributes),
	AllowNullable(FetchedStoreProfile.DesignSettings::productDetailsShowBreadcrumbs),
	AllowNullable(FetchedStoreProfile.DesignSettings::productDetailsShowBreadcrumbsPosition),
	AllowNullable(FetchedStoreProfile.DesignSettings::productDetailsShowBuyButton),
	AllowNullable(FetchedStoreProfile.DesignSettings::productDetailsShowFacebookShareButton),
	AllowNullable(FetchedStoreProfile.DesignSettings::productDetailsShowInStockLabel),
	AllowNullable(FetchedStoreProfile.DesignSettings::productDetailsShowNavigationArrows),
	AllowNullable(FetchedStoreProfile.DesignSettings::productDetailsShowNumberOfItemsInStock),
	AllowNullable(FetchedStoreProfile.DesignSettings::productDetailsShowPinterestShareButton),
	AllowNullable(FetchedStoreProfile.DesignSettings::productDetailsShowPricePerUnit),
	AllowNullable(FetchedStoreProfile.DesignSettings::productDetailsShowProductDescription),
	AllowNullable(FetchedStoreProfile.DesignSettings::productDetailsShowDeliveryTime),
	AllowNullable(FetchedStoreProfile.DesignSettings::productDetailsShowProductName),
	AllowNullable(FetchedStoreProfile.DesignSettings::productDetailsShowProductNameAlwaysFirstOnMobile),
	AllowNullable(FetchedStoreProfile.DesignSettings::productDetailsShowProductOptions),
	AllowNullable(FetchedStoreProfile.DesignSettings::productDetailsShowProductPhotoZoom),
	AllowNullable(FetchedStoreProfile.DesignSettings::productDetailsShowProductPrice),
	AllowNullable(FetchedStoreProfile.DesignSettings::productDetailsShowProductSku),
	AllowNullable(FetchedStoreProfile.DesignSettings::productDetailsShowQty),
	AllowNullable(FetchedStoreProfile.DesignSettings::productDetailsShowSalePrice),
	AllowNullable(FetchedStoreProfile.DesignSettings::productDetailsShowSaveForLater),
	AllowNullable(FetchedStoreProfile.DesignSettings::productDetailsShowShareButtons),
	AllowNullable(FetchedStoreProfile.DesignSettings::productDetailsShowSubtitle),
	AllowNullable(FetchedStoreProfile.DesignSettings::productDetailsShowTax),
	AllowNullable(FetchedStoreProfile.DesignSettings::productDetailsShowTwitterShareButton),
	AllowNullable(FetchedStoreProfile.DesignSettings::productDetailsShowVkShareButton),
	AllowNullable(FetchedStoreProfile.DesignSettings::productDetailsShowWeight),
	AllowNullable(FetchedStoreProfile.DesignSettings::productDetailsShowWholesalePrices),
	AllowNullable(FetchedStoreProfile.DesignSettings::productDetailsThumbnailsAspectRatio),
	AllowNullable(FetchedStoreProfile.DesignSettings::productDetailsTwoColumnsWithLeftSidebarShowProductDescriptionOnSidebar),
	AllowNullable(FetchedStoreProfile.DesignSettings::productDetailsTwoColumnsWithRightSidebarShowProductDescriptionOnSidebar),
	AllowNullable(FetchedStoreProfile.DesignSettings::productFiltersOpenedByDefaultOnCategoryPage),
	AllowNullable(FetchedStoreProfile.DesignSettings::productFiltersPositionCategoryPage),
	AllowNullable(FetchedStoreProfile.DesignSettings::productFiltersPositionSearchPage),
	AllowNullable(FetchedStoreProfile.DesignSettings::productListBuyNowBehaviour),
	AllowNullable(FetchedStoreProfile.DesignSettings::productListCardLayout),
	AllowNullable(FetchedStoreProfile.DesignSettings::productListCategoryImageLayout),
	AllowNullable(FetchedStoreProfile.DesignSettings::productListCategoryImagePosition),
	AllowNullable(FetchedStoreProfile.DesignSettings::productListCategoryImageSize),
	AllowNullable(FetchedStoreProfile.DesignSettings::productListCategoryNameBehaviour),
	AllowNullable(FetchedStoreProfile.DesignSettings::productListImageHasShadow),
	AllowNullable(FetchedStoreProfile.DesignSettings::productListImageLayout),
	AllowNullable(FetchedStoreProfile.DesignSettings::productListImagePosition),
	AllowNullable(FetchedStoreProfile.DesignSettings::productListImageSize),
	AllowNullable(FetchedStoreProfile.DesignSettings::productListNameBehaviour),
	AllowNullable(FetchedStoreProfile.DesignSettings::productListPriceBehaviour),
	AllowNullable(FetchedStoreProfile.DesignSettings::productListSKUBehaviour),
	AllowNullable(FetchedStoreProfile.DesignSettings::productListShowAdditionalImage),
	AllowNullable(FetchedStoreProfile.DesignSettings::productListShowBreadcrumbs),
	AllowNullable(FetchedStoreProfile.DesignSettings::productListShowCardFrame),
	AllowNullable(FetchedStoreProfile.DesignSettings::productListShowFooterMenu),
	AllowNullable(FetchedStoreProfile.DesignSettings::productListShowOnSaleLabel),
	AllowNullable(FetchedStoreProfile.DesignSettings::productListShowPayWhatYouWantLabel),
	AllowNullable(FetchedStoreProfile.DesignSettings::productListShowProductImages),
	AllowNullable(FetchedStoreProfile.DesignSettings::productListShowSignInLink),
	AllowNullable(FetchedStoreProfile.DesignSettings::productListShowSoldOutLabel),
	AllowNullable(FetchedStoreProfile.DesignSettings::productListShowSortViewAsOptions),
	AllowNullable(FetchedStoreProfile.DesignSettings::productListSubtitlesBehavior),
	AllowNullable(FetchedStoreProfile.DesignSettings::shoppingCartProductsCollapsedOnDesktop),
	AllowNullable(FetchedStoreProfile.DesignSettings::shoppingCartProductsCollapsedOnMobile),
	AllowNullable(FetchedStoreProfile.DesignSettings::shoppingCartShowQtyInputs),
	AllowNullable(FetchedStoreProfile.DesignSettings::shoppingCartShowWeight),
	AllowNullable(FetchedStoreProfile.DesignSettings::showCartWidget),
	AllowNullable(FetchedStoreProfile.DesignSettings::showRootCategories),
	IgnoreNullable(FetchedStoreProfile.FBMessengerSettings::enabled),
	IgnoreNullable(FetchedStoreProfile.FBMessengerSettings::fbMessengerMessageUsButtonColor),
	IgnoreNullable(FetchedStoreProfile.FBMessengerSettings::fbMessengerPageId),
	IgnoreNullable(FetchedStoreProfile.FBMessengerSettings::fbMessengerThemeColor),
	IgnoreNullable(FetchedStoreProfile.FeatureTogglesInfo::enabled),
	IgnoreNullable(FetchedStoreProfile.FeatureTogglesInfo::name),
	IgnoreNullable(FetchedStoreProfile.FeatureTogglesInfo::visible),
	IgnoreNullable(FetchedStoreProfile.FlatRate::rate),
	IgnoreNullable(FetchedStoreProfile.FlatRate::rateType),
	IgnoreNullable(FetchedStoreProfile.FormatsAndUnits::currency),
	IgnoreNullable(FetchedStoreProfile.FormatsAndUnits::currencyDecimalSeparator),
	IgnoreNullable(FetchedStoreProfile.FormatsAndUnits::currencyGroupSeparator),
	IgnoreNullable(FetchedStoreProfile.FormatsAndUnits::currencyPrecision),
	IgnoreNullable(FetchedStoreProfile.FormatsAndUnits::currencyPrefix),
	IgnoreNullable(FetchedStoreProfile.FormatsAndUnits::currencyRate),
	IgnoreNullable(FetchedStoreProfile.FormatsAndUnits::currencySuffix),
	IgnoreNullable(FetchedStoreProfile.FormatsAndUnits::currencyTruncateZeroFractional),
	IgnoreNullable(FetchedStoreProfile.FormatsAndUnits::dateFormat),
	IgnoreNullable(FetchedStoreProfile.FormatsAndUnits::dimensionsUnit),
	AllowNullable(FetchedStoreProfile.FormatsAndUnits::orderNumberMinDigitsAmount),
	AllowNullable(FetchedStoreProfile.FormatsAndUnits::orderNumberNextNumber),
	IgnoreNullable(FetchedStoreProfile.FormatsAndUnits::orderNumberPrefix),
	IgnoreNullable(FetchedStoreProfile.FormatsAndUnits::orderNumberSuffix),
	IgnoreNullable(FetchedStoreProfile.FormatsAndUnits::timeFormat),
	IgnoreNullable(FetchedStoreProfile.FormatsAndUnits::timezone),
	IgnoreNullable(FetchedStoreProfile.FormatsAndUnits::weightDecimalSeparator),
	IgnoreNullable(FetchedStoreProfile.FormatsAndUnits::weightGroupSeparator),
	IgnoreNullable(FetchedStoreProfile.FormatsAndUnits::weightPrecision),
	IgnoreNullable(FetchedStoreProfile.FormatsAndUnits::weightTruncateZeroFractional),
	IgnoreNullable(FetchedStoreProfile.FormatsAndUnits::weightUnit),
	IgnoreNullable(FetchedStoreProfile.GeneralInfo::starterSite),
	IgnoreNullable(FetchedStoreProfile.GeneralInfo::storeUrl),
	IgnoreNullable(FetchedStoreProfile.GeneralInfo::websitePlatform),
	IgnoreNullable(FetchedStoreProfile.GiftCardProducts::id),
	IgnoreNullable(FetchedStoreProfile.GiftCardProducts::name),
	IgnoreNullable(FetchedStoreProfile.GiftCardProducts::url),
	IgnoreNullable(FetchedStoreProfile.GiftCardSettings::displayLocation),
	IgnoreNullable(FetchedStoreProfile.GiftCardSettings::products),
	IgnoreNullable(FetchedStoreProfile.HandlingFee::description),
	IgnoreNullable(FetchedStoreProfile.HandlingFee::name),
	IgnoreNullable(FetchedStoreProfile.HandlingFee::value),
	IgnoreNullable(FetchedStoreProfile.InstantSiteInfo::customDomain),
	IgnoreNullable(FetchedStoreProfile.InstantSiteInfo::ecwidSubdomain),
	IgnoreNullable(FetchedStoreProfile.InstantSiteInfo::generatedUrl),
	IgnoreNullable(FetchedStoreProfile.InstantSiteInfo::storeLogoUrl),
	IgnoreNullable(FetchedStoreProfile.InstructionsForCustomerInfo::instructions),
	IgnoreNullable(FetchedStoreProfile.InstructionsForCustomerInfo::instructionsTitle),
	IgnoreNullable(FetchedStoreProfile.Languages::defaultLanguage),
	IgnoreNullable(FetchedStoreProfile.Languages::enabledLanguages),
	IgnoreNullable(FetchedStoreProfile.Languages::facebookPreferredLocale),
	IgnoreNullable(FetchedStoreProfile.LegalPagesInfo::display),
	IgnoreNullable(FetchedStoreProfile.LegalPagesInfo::enabled),
	IgnoreNullable(FetchedStoreProfile.LegalPagesInfo::externalUrl),
	IgnoreNullable(FetchedStoreProfile.LegalPagesInfo::text),
	IgnoreNullable(FetchedStoreProfile.LegalPagesInfo::title),
	IgnoreNullable(FetchedStoreProfile.LegalPagesInfo::type),
	IgnoreNullable(FetchedStoreProfile.LegalPagesSettingsDetails::legalPages),
	IgnoreNullable(FetchedStoreProfile.LegalPagesSettingsDetails::requireTermsAgreementAtCheckout),
	IgnoreNullable(FetchedStoreProfile.MailNotifications::adminNotificationEmails),
	IgnoreNullable(FetchedStoreProfile.MailNotifications::customerNotificationFromEmail),
	IgnoreNullable(FetchedStoreProfile.OrderInvoiceSettings::attachInvoiceToOrderEmailNotifications),
	IgnoreNullable(FetchedStoreProfile.OrderInvoiceSettings::displayOrderInvoices),
	IgnoreNullable(FetchedStoreProfile.OrderInvoiceSettings::invoiceLogoUrl),
	IgnoreNullable(FetchedStoreProfile.PaymentInfo::applePay),
	IgnoreNullable(FetchedStoreProfile.PaymentInfo::paymentOptions),
	IgnoreNullable(FetchedStoreProfile.PaymentOptionInfo::appClientId),
	IgnoreNullable(FetchedStoreProfile.PaymentOptionInfo::checkoutDescription),
	IgnoreNullable(FetchedStoreProfile.PaymentOptionInfo::checkoutTitle),
	IgnoreNullable(FetchedStoreProfile.PaymentOptionInfo::enabled),
	IgnoreNullable(FetchedStoreProfile.PaymentOptionInfo::id),
	IgnoreNullable(FetchedStoreProfile.PaymentOptionInfo::instructionsForCustomer),
	IgnoreNullable(FetchedStoreProfile.PaymentOptionInfo::orderBy),
	IgnoreNullable(FetchedStoreProfile.PaymentOptionInfo::paymentProcessorId),
	IgnoreNullable(FetchedStoreProfile.PaymentOptionInfo::paymentProcessorTitle),
	AllowNullable(FetchedStoreProfile.PaymentOptionInfo::shippingSettings),
	AllowNullable(FetchedStoreProfile.ProductFilterItem::name),
	IgnoreNullable(FetchedStoreProfile::registrationAnswers),
	IgnoreNullable(FetchedStoreProfile.RegistrationAnswers::alreadySelling),
	IgnoreNullable(FetchedStoreProfile.RegistrationAnswers::facebook),
	IgnoreNullable(FetchedStoreProfile.RegistrationAnswers::forSomeone),
	IgnoreNullable(FetchedStoreProfile.RegistrationAnswers::goods),
	IgnoreNullable(FetchedStoreProfile.RegistrationAnswers::otherGoods),
	IgnoreNullable(FetchedStoreProfile.RegistrationAnswers::platform),
	IgnoreNullable(FetchedStoreProfile.RegistrationAnswers::website),
	IgnoreNullable(FetchedStoreProfile.SalePriceSettings::displayDiscount),
	IgnoreNullable(FetchedStoreProfile.SalePriceSettings::displayOnProductList),
	IgnoreNullable(FetchedStoreProfile.SalePriceSettings::oldPriceLabel),
	IgnoreNullable(FetchedStoreProfile.Settings::abandonedSales),
	IgnoreNullable(FetchedStoreProfile.Settings::acceptMarketingCheckboxCustomText),
	IgnoreNullable(FetchedStoreProfile.Settings::acceptMarketingCheckboxDefaultValue),
	IgnoreNullable(FetchedStoreProfile.Settings::askCompanyName),
	IgnoreNullable(FetchedStoreProfile.Settings::askConsentToTrackInStorefront),
	IgnoreNullable(FetchedStoreProfile.Settings::closed),
	IgnoreNullable(FetchedStoreProfile.Settings::defaultProductSortOrder),
	IgnoreNullable(FetchedStoreProfile.Settings::emailLogoUrl),
	IgnoreNullable(FetchedStoreProfile.Settings::favoritesEnabled),
	IgnoreNullable(FetchedStoreProfile.Settings::fbPixelId),
	IgnoreNullable(FetchedStoreProfile.Settings::googleAnalyticsId),
	IgnoreNullable(FetchedStoreProfile.Settings::googleEventId),
	IgnoreNullable(FetchedStoreProfile.Settings::googleRemarketingEnabled),
	IgnoreNullable(FetchedStoreProfile.Settings::googleTagId),
	IgnoreNullable(FetchedStoreProfile.Settings::hideOutOfStockProductsInStorefront),
	IgnoreNullable(FetchedStoreProfile.Settings::invoiceLogoUrl),
	IgnoreNullable(FetchedStoreProfile.Settings::orderCommentsCaption),
	IgnoreNullable(FetchedStoreProfile.Settings::orderCommentsEnabled),
	IgnoreNullable(FetchedStoreProfile.Settings::orderCommentsRequired),
	IgnoreNullable(FetchedStoreProfile.Settings::pinterestTagId),
	IgnoreNullable(FetchedStoreProfile.Settings::salePrice),
	IgnoreNullable(FetchedStoreProfile.Settings::showAcceptMarketingCheckbox),
	IgnoreNullable(FetchedStoreProfile.Settings::snapPixelId),
	IgnoreNullable(FetchedStoreProfile.Settings::storeDescription),
	IgnoreNullable(FetchedStoreProfile.Settings::storeName),
	IgnoreNullable(FetchedStoreProfile.Settings::tikTokPixel),
	IgnoreNullable(FetchedStoreProfile.Shipping::handlingFee),
	IgnoreNullable(FetchedStoreProfile.Shipping::shippingOptions),
	IgnoreNullable(FetchedStoreProfile.Shipping::shippingOrigin),
	IgnoreNullable(FetchedStoreProfile.ShippingOption::appClientId),
	IgnoreNullable(FetchedStoreProfile.ShippingOption::carrier),
	IgnoreNullable(FetchedStoreProfile.ShippingOption::carrierMethods),
	IgnoreNullable(FetchedStoreProfile.ShippingOption::carrierSettings),
	IgnoreNullable(FetchedStoreProfile.ShippingOption::deliveryTimeDays),
	IgnoreNullable(FetchedStoreProfile.ShippingOption::description),
	IgnoreNullable(FetchedStoreProfile.ShippingOption::destinationZone),
	IgnoreNullable(FetchedStoreProfile.ShippingOption::enabled),
	IgnoreNullable(FetchedStoreProfile.ShippingOption::flatRate),
	IgnoreNullable(FetchedStoreProfile.ShippingOption::fulfilmentType),
	IgnoreNullable(FetchedStoreProfile.ShippingOption::id),
	IgnoreNullable(FetchedStoreProfile.ShippingOption::orderBy),
	IgnoreNullable(FetchedStoreProfile.ShippingOption::pickupBusinessHours),
	IgnoreNullable(FetchedStoreProfile.ShippingOption::pickupInstruction),
	IgnoreNullable(FetchedStoreProfile.ShippingOption::pickupPreparationTimeHours),
	IgnoreNullable(FetchedStoreProfile.ShippingOption::ratesCalculationType),
	IgnoreNullable(FetchedStoreProfile.ShippingOption::ratesTable),
	IgnoreNullable(FetchedStoreProfile.ShippingOption::scheduledPickup),
	IgnoreNullable(FetchedStoreProfile.ShippingOption::shippingCostMarkup),
	IgnoreNullable(FetchedStoreProfile.ShippingOption::title),
	IgnoreNullable(FetchedStoreProfile.ShippingOrigin::city),
	IgnoreNullable(FetchedStoreProfile.ShippingOrigin::companyName),
	IgnoreNullable(FetchedStoreProfile.ShippingOrigin::countryCode),
	IgnoreNullable(FetchedStoreProfile.ShippingOrigin::email),
	IgnoreNullable(FetchedStoreProfile.ShippingOrigin::phone),
	IgnoreNullable(FetchedStoreProfile.ShippingOrigin::postalCode),
	IgnoreNullable(FetchedStoreProfile.ShippingOrigin::stateOrProvinceCode),
	IgnoreNullable(FetchedStoreProfile.ShippingOrigin::street),
	AllowNullable(FetchedStoreProfile.ShippingSettings::enabledShippingMethods),
	IgnoreNullable(FetchedStoreProfile.TableRate::rate),
	IgnoreNullable(FetchedStoreProfile.TableRate::tableRateConditions),
	IgnoreNullable(FetchedStoreProfile.TableRateConditions::discountedSubtotalFrom),
	IgnoreNullable(FetchedStoreProfile.TableRateConditions::discountedSubtotalTo),
	IgnoreNullable(FetchedStoreProfile.TableRateConditions::subtotalFrom),
	IgnoreNullable(FetchedStoreProfile.TableRateConditions::subtotalTo),
	IgnoreNullable(FetchedStoreProfile.TableRateConditions::weightFrom),
	IgnoreNullable(FetchedStoreProfile.TableRateConditions::weightTo),
	IgnoreNullable(FetchedStoreProfile.TableRateDetails::perItem),
	IgnoreNullable(FetchedStoreProfile.TableRateDetails::perOrderAbs),
	IgnoreNullable(FetchedStoreProfile.TableRateDetails::perOrderPercent),
	IgnoreNullable(FetchedStoreProfile.TableRateDetails::perWeightUnitRate),
	IgnoreNullable(FetchedStoreProfile.TableRatesDetails::rates),
	IgnoreNullable(FetchedStoreProfile.TableRatesDetails::tableBasedOn),
	IgnoreNullable(FetchedStoreProfile.TaxSettings::automaticTaxEnabled),
	IgnoreNullable(FetchedStoreProfile.TaxSettings::pricesIncludeTax),
	IgnoreNullable(FetchedStoreProfile.TaxSettings::taxes),
	IgnoreNullable(FetchedStoreProfile.TaxSettings.TaxRule::tax),
	IgnoreNullable(FetchedStoreProfile.TaxSettings.TaxRule::zoneId),
	IgnoreNullable(FetchedStoreProfile.TaxSettings.Taxes::appliedByDefault),
	IgnoreNullable(FetchedStoreProfile.TaxSettings.Taxes::defaultTax),
	IgnoreNullable(FetchedStoreProfile.TaxSettings.Taxes::enabled),
	IgnoreNullable(FetchedStoreProfile.TaxSettings.Taxes::id),
	IgnoreNullable(FetchedStoreProfile.TaxSettings.Taxes::includeInPrice),
	IgnoreNullable(FetchedStoreProfile.TaxSettings.Taxes::name),
	IgnoreNullable(FetchedStoreProfile.TaxSettings.Taxes::rules),
	IgnoreNullable(FetchedStoreProfile.TaxSettings.Taxes::taxShipping),
	IgnoreNullable(FetchedStoreProfile.TaxSettings.Taxes::useShippingAddress),
	IgnoreNullable(FetchedStoreProfile.TikTokPixelSettings::code),
	IgnoreNullable(FetchedStoreProfile.Zone::countryCodes),
	IgnoreNullable(FetchedStoreProfile.Zone::id),
	IgnoreNullable(FetchedStoreProfile.Zone::name),
	IgnoreNullable(FetchedStoreProfile.Zone::postCodes),
	IgnoreNullable(FetchedStoreProfile.Zone::stateOrProvinceCodes),
	AllowNullable(FetchedStoreProfile.Settings::googleProductCategory),
	AllowNullable(FetchedStoreProfile.Settings::googleProductCategoryName),
)
