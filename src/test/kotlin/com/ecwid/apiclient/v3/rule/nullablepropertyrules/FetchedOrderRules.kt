package com.ecwid.apiclient.v3.rule.nullablepropertyrules

import com.ecwid.apiclient.v3.dto.order.result.FetchedOrder
import com.ecwid.apiclient.v3.rule.NullablePropertyRule
import com.ecwid.apiclient.v3.rule.NullablePropertyRule.IgnoreNullable
import com.ecwid.apiclient.v3.rule.NullablePropertyRule.AllowNullable

val fetchedOrderNullablePropertyRules: List<NullablePropertyRule<*, *>> = listOf(
	AllowNullable(FetchedOrder.BaseOrderItemTax::name),
	AllowNullable(FetchedOrder.BaseOrderItemTax::total),
	AllowNullable(FetchedOrder.BaseOrderItemTax::value),
	AllowNullable(FetchedOrder.HandlingFeeTax::name),
	AllowNullable(FetchedOrder.HandlingFeeTax::total),
	AllowNullable(FetchedOrder.HandlingFeeTax::value),
	AllowNullable(FetchedOrder::latestShipDate),
	IgnoreNullable(FetchedOrder::publicUid),
	IgnoreNullable(FetchedOrder.CreditCardStatus::avsMessage),
	IgnoreNullable(FetchedOrder.CreditCardStatus::cvvMessage),
	IgnoreNullable(FetchedOrder.DiscountCouponCatalogLimit::categories),
	IgnoreNullable(FetchedOrder.DiscountCouponCatalogLimit::products),
	IgnoreNullable(FetchedOrder.DiscountCouponInfo::applicationLimit),
	IgnoreNullable(FetchedOrder.DiscountCouponInfo::catalogLimit),
	IgnoreNullable(FetchedOrder.DiscountCouponInfo::code),
	IgnoreNullable(FetchedOrder.DiscountCouponInfo::creationDate),
	IgnoreNullable(FetchedOrder.DiscountCouponInfo::discount),
	IgnoreNullable(FetchedOrder.DiscountCouponInfo::discountType),
	IgnoreNullable(FetchedOrder.DiscountCouponInfo::expirationDate),
	IgnoreNullable(FetchedOrder.DiscountCouponInfo::id),
	IgnoreNullable(FetchedOrder.DiscountCouponInfo::launchDate),
	IgnoreNullable(FetchedOrder.DiscountCouponInfo::name),
	IgnoreNullable(FetchedOrder.DiscountCouponInfo::orderCount),
	IgnoreNullable(FetchedOrder.DiscountCouponInfo::repeatCustomerOnly),
	IgnoreNullable(FetchedOrder.DiscountCouponInfo::status),
	IgnoreNullable(FetchedOrder.DiscountCouponInfo::totalLimit),
	IgnoreNullable(FetchedOrder.DiscountCouponInfo::updateDate),
	IgnoreNullable(FetchedOrder.DiscountCouponInfo::usesLimit),
	IgnoreNullable(FetchedOrder.DiscountInfo::base),
	IgnoreNullable(FetchedOrder.DiscountInfo::description),
	IgnoreNullable(FetchedOrder.DiscountInfo::orderTotal),
	IgnoreNullable(FetchedOrder.DiscountInfo::type),
	IgnoreNullable(FetchedOrder.DiscountInfo::value),
	AllowNullable(FetchedOrder.ExtraFieldsInfo::customerInputType),
	AllowNullable(FetchedOrder.ExtraFieldsInfo::id),
	AllowNullable(FetchedOrder.ExtraFieldsInfo::orderBy),
	AllowNullable(FetchedOrder.ExtraFieldsInfo::orderDetailsDisplaySection),
	AllowNullable(FetchedOrder.ExtraFieldsInfo::title),
	AllowNullable(FetchedOrder.ExtraFieldsInfo::value),
	IgnoreNullable(FetchedOrder.HandlingFee::description),
	IgnoreNullable(FetchedOrder.HandlingFee::name),
	IgnoreNullable(FetchedOrder.HandlingFee::value),
	IgnoreNullable(FetchedOrder.OrderItem::categoryId),
	IgnoreNullable(FetchedOrder.OrderItem::couponAmount),
	IgnoreNullable(FetchedOrder.OrderItem::couponApplied),
	IgnoreNullable(FetchedOrder.OrderItem::digital),
	IgnoreNullable(FetchedOrder.OrderItem::dimensions),
	IgnoreNullable(FetchedOrder.OrderItem::discounts),
	AllowNullable(FetchedOrder.OrderItem::discountsAllowed),
	AllowNullable(FetchedOrder.OrderItem::files),
	IgnoreNullable(FetchedOrder.OrderItem::fixedShippingRate),
	IgnoreNullable(FetchedOrder.OrderItem::fixedShippingRateOnly),
	IgnoreNullable(FetchedOrder.OrderItem::hdThumbnailUrl),
	IgnoreNullable(FetchedOrder.OrderItem::id),
	IgnoreNullable(FetchedOrder.OrderItem::imageUrl),
	AllowNullable(FetchedOrder.OrderItem::isCustomerSetPrice),
	AllowNullable(FetchedOrder.OrderItem::isGiftCard),
	IgnoreNullable(FetchedOrder.OrderItem::isShippingRequired),
	IgnoreNullable(FetchedOrder.OrderItem::name),
	AllowNullable(FetchedOrder.OrderItem::nameTranslated),
	IgnoreNullable(FetchedOrder.OrderItem::price),
	AllowNullable(FetchedOrder.OrderItem::priceWithoutTax),
	IgnoreNullable(FetchedOrder.OrderItem::productAvailable),
	IgnoreNullable(FetchedOrder.OrderItem::productId),
	IgnoreNullable(FetchedOrder.OrderItem::productPrice),
	IgnoreNullable(FetchedOrder.OrderItem::quantity),
	IgnoreNullable(FetchedOrder.OrderItem::quantityInStock),
	IgnoreNullable(FetchedOrder.OrderItem::recurringChargeSettings),
	AllowNullable(FetchedOrder.RecurringChargeSettings::subscriptionPriceWithSignUpFee),
	AllowNullable(FetchedOrder.RecurringChargeSettings::signUpFee),
	IgnoreNullable(FetchedOrder.OrderItem::selectedOptions),
	AllowNullable(FetchedOrder.OrderItem::selectedPrice),
	IgnoreNullable(FetchedOrder.OrderItem::shipping),
	IgnoreNullable(FetchedOrder.OrderItem::shortDescription),
	AllowNullable(FetchedOrder.OrderItem::shortDescriptionTranslated),
	IgnoreNullable(FetchedOrder.OrderItem::sku),
	IgnoreNullable(FetchedOrder.OrderItem::smallThumbnailUrl),
	IgnoreNullable(FetchedOrder.OrderItem::subscriptionId),
	IgnoreNullable(FetchedOrder.OrderItem::tax),
	AllowNullable(FetchedOrder.OrderItem::taxable),
	IgnoreNullable(FetchedOrder.OrderItem::taxes),
	IgnoreNullable(FetchedOrder.OrderItem::trackQuantity),
	IgnoreNullable(FetchedOrder.OrderItem::weight),
	IgnoreNullable(FetchedOrder.OrderItem::externalReferenceId),
	IgnoreNullable(FetchedOrder.OrderItemDiscounts::discountInfo),
	IgnoreNullable(FetchedOrder.OrderItemDiscounts::total),
	AllowNullable(FetchedOrder.OrderItemOptionFile::id),
	AllowNullable(FetchedOrder.OrderItemOptionFile::name),
	AllowNullable(FetchedOrder.OrderItemOptionFile::size),
	AllowNullable(FetchedOrder.OrderItemOptionFile::url),
	AllowNullable(FetchedOrder.OrderItemProductFile::adminUrl),
	AllowNullable(FetchedOrder.OrderItemProductFile::customerUrl),
	AllowNullable(FetchedOrder.OrderItemProductFile::description),
	AllowNullable(FetchedOrder.OrderItemProductFile::expire),
	AllowNullable(FetchedOrder.OrderItemProductFile::maxDownloads),
	AllowNullable(FetchedOrder.OrderItemProductFile::name),
	AllowNullable(FetchedOrder.OrderItemProductFile::productFileId),
	AllowNullable(FetchedOrder.OrderItemProductFile::remainingDownloads),
	AllowNullable(FetchedOrder.OrderItemProductFile::size),
	IgnoreNullable(FetchedOrder.OrderItemSelectedOption::files),
	IgnoreNullable(FetchedOrder.OrderItemSelectedOption::name),
	AllowNullable(FetchedOrder.OrderItemSelectedOption::nameTranslated),
	IgnoreNullable(FetchedOrder.OrderItemSelectedOption::selections),
	IgnoreNullable(FetchedOrder.OrderItemSelectedOption::type),
	IgnoreNullable(FetchedOrder.OrderItemSelectedOption::value),
	AllowNullable(FetchedOrder.OrderItemSelectedOption::valueTranslated),
	IgnoreNullable(FetchedOrder.OrderItemSelectedOption::valuesArray),
	AllowNullable(FetchedOrder.OrderItemSelectedOption::valuesArrayTranslated),
	IgnoreNullable(FetchedOrder.OrderItemSelectionInfo::selectionModifier),
	IgnoreNullable(FetchedOrder.OrderItemSelectionInfo::selectionModifierType),
	IgnoreNullable(FetchedOrder.OrderItemSelectionInfo::selectionTitle),
	IgnoreNullable(FetchedOrder.OrderItemTax::includeInPrice),
	IgnoreNullable(FetchedOrder.OrderItemTax::name),
	IgnoreNullable(FetchedOrder.OrderItemTax::taxOnDiscountedSubtotal),
	IgnoreNullable(FetchedOrder.OrderItemTax::taxOnShipping),
	IgnoreNullable(FetchedOrder.OrderItemTax::total),
	IgnoreNullable(FetchedOrder.OrderItemTax::value),
	IgnoreNullable(FetchedOrder.OrderItemTax::sourceTaxRateId),
	IgnoreNullable(FetchedOrder.OrderItemTax::sourceTaxRateType),
	AllowNullable(FetchedOrder.Parcel::dimensionUnit),
	AllowNullable(FetchedOrder.Parcel::height),
	AllowNullable(FetchedOrder.Parcel::length),
	AllowNullable(FetchedOrder.Parcel::template),
	AllowNullable(FetchedOrder.Parcel::weight),
	AllowNullable(FetchedOrder.Parcel::weightUnit),
	AllowNullable(FetchedOrder.Parcel::width),
	IgnoreNullable(FetchedOrder.PersonInfo::city),
	IgnoreNullable(FetchedOrder.PersonInfo::companyName),
	IgnoreNullable(FetchedOrder.PersonInfo::countryCode),
	IgnoreNullable(FetchedOrder.PersonInfo::countryName),
	IgnoreNullable(FetchedOrder.PersonInfo::firstName),
	IgnoreNullable(FetchedOrder.PersonInfo::lastName),
	IgnoreNullable(FetchedOrder.PersonInfo::name),
	IgnoreNullable(FetchedOrder.PersonInfo::phone),
	IgnoreNullable(FetchedOrder.PersonInfo::postalCode),
	IgnoreNullable(FetchedOrder.PersonInfo::stateOrProvinceCode),
	IgnoreNullable(FetchedOrder.PersonInfo::stateOrProvinceName),
	IgnoreNullable(FetchedOrder.PersonInfo::street),
	IgnoreNullable(FetchedOrder.ProductDimensions::height),
	IgnoreNullable(FetchedOrder.ProductDimensions::length),
	IgnoreNullable(FetchedOrder.ProductDimensions::width),
	IgnoreNullable(FetchedOrder.RefundInfo::amount),
	IgnoreNullable(FetchedOrder.RefundInfo::date),
	IgnoreNullable(FetchedOrder.RefundInfo::reason),
	IgnoreNullable(FetchedOrder.RefundInfo::source),
	AllowNullable(FetchedOrder.SelectedPrice::value),
	AllowNullable(FetchedOrder.Shipment::created),
	AllowNullable(FetchedOrder.Shipment::id),
	AllowNullable(FetchedOrder.Shipment::label),
	AllowNullable(FetchedOrder.Shipment::parcel),
	AllowNullable(FetchedOrder.Shipment::shipFrom),
	AllowNullable(FetchedOrder.Shipment::shipTo),
	AllowNullable(FetchedOrder.Shipment::shippingService),
	AllowNullable(FetchedOrder.Shipment::tracking),
	AllowNullable(FetchedOrder.ShippingLabelInfo::billingTransactionId),
	AllowNullable(FetchedOrder.ShippingLabelInfo::commercialInvoiceUrl),
	AllowNullable(FetchedOrder.ShippingLabelInfo::labelFileType),
	AllowNullable(FetchedOrder.ShippingLabelInfo::labelUrl),
	IgnoreNullable(FetchedOrder.ShippingOption::estimatedTransitTime),
	IgnoreNullable(FetchedOrder.ShippingOption::fulfillmentType),
	IgnoreNullable(FetchedOrder.ShippingOption::isPickup),
	AllowNullable(FetchedOrder.ShippingOption::locationId),
	IgnoreNullable(FetchedOrder.ShippingOption::pickupInstruction),
	IgnoreNullable(FetchedOrder.ShippingOption::shippingCarrierName),
	IgnoreNullable(FetchedOrder.ShippingOption::shippingMethodName),
	IgnoreNullable(FetchedOrder.ShippingOption::shippingRate),
	AllowNullable(FetchedOrder.ShippingServiceInfo::carrier),
	AllowNullable(FetchedOrder.ShippingServiceInfo::carrierName),
	AllowNullable(FetchedOrder.ShippingServiceInfo::carrierServiceCode),
	AllowNullable(FetchedOrder.ShippingServiceInfo::carrierServiceName),
	AllowNullable(FetchedOrder.Surcharge::descriptionTranslated),
	AllowNullable(FetchedOrder.TaxRule::tax),
	AllowNullable(FetchedOrder.TaxRule::zoneId),
	AllowNullable(FetchedOrder.Taxes::appliedByDefault),
	AllowNullable(FetchedOrder.Taxes::defaultTax),
	AllowNullable(FetchedOrder.Taxes::enabled),
	AllowNullable(FetchedOrder.Taxes::id),
	AllowNullable(FetchedOrder.Taxes::includeInPrice),
	AllowNullable(FetchedOrder.Taxes::name),
	AllowNullable(FetchedOrder.Taxes::rules),
	AllowNullable(FetchedOrder.Taxes::taxShipping),
	AllowNullable(FetchedOrder.Taxes::useShippingAddress),
	AllowNullable(FetchedOrder.TrackingInfo::estimatedDays),
	AllowNullable(FetchedOrder.TrackingInfo::trackingNumber),
	AllowNullable(FetchedOrder.TrackingInfo::trackingUrl),
	IgnoreNullable(FetchedOrder.UtmData::campaign),
	IgnoreNullable(FetchedOrder.UtmData::mcCid),
	IgnoreNullable(FetchedOrder.UtmData::mcEid),
	IgnoreNullable(FetchedOrder.UtmData::medium),
	IgnoreNullable(FetchedOrder.UtmData::source),
	AllowNullable(FetchedOrder::acceptMarketing),
	AllowNullable(FetchedOrder::additionalInfo),
	AllowNullable(FetchedOrder::affiliateId),
	AllowNullable(FetchedOrder::availableShippingOptions),
	AllowNullable(FetchedOrder::availableTaxes),
	AllowNullable(FetchedOrder::billingPerson),
	AllowNullable(FetchedOrder::couponDiscount),
	AllowNullable(FetchedOrder::createDate),
	AllowNullable(FetchedOrder::createTimestamp),
	AllowNullable(FetchedOrder::creditCardStatus),
	AllowNullable(FetchedOrder::customDiscount),
	AllowNullable(FetchedOrder::customerGroup),
	AllowNullable(FetchedOrder::customerGroupId),
	AllowNullable(FetchedOrder::customerId),
	AllowNullable(FetchedOrder::customerTaxExempt),
	AllowNullable(FetchedOrder::customerTaxId),
	AllowNullable(FetchedOrder::customerTaxIdValid),
	AllowNullable(FetchedOrder::disableAllCustomerNotifications),
	AllowNullable(FetchedOrder::discount),
	AllowNullable(FetchedOrder::discountCoupon),
	AllowNullable(FetchedOrder::discountInfo),
	AllowNullable(FetchedOrder::ebayId),
	AllowNullable(FetchedOrder::email),
	AllowNullable(FetchedOrder::externalFulfillment),
	AllowNullable(FetchedOrder::externalOrderId),
	AllowNullable(FetchedOrder::externalTransactionId),
	AllowNullable(FetchedOrder::externalTransactionUrl),
	AllowNullable(FetchedOrder::extraFields),
	AllowNullable(FetchedOrder::fulfillmentStatus),
	AllowNullable(FetchedOrder::giftCardCode),
	AllowNullable(FetchedOrder::giftCardDoubleSpending),
	AllowNullable(FetchedOrder::giftCardRedemption),
	AllowNullable(FetchedOrder::globalReferer),
	AllowNullable(FetchedOrder::handlingFee),
	AllowNullable(FetchedOrder::hidden),
	AllowNullable(FetchedOrder::id),
	AllowNullable(FetchedOrder::ipAddress),
	AllowNullable(FetchedOrder::items),
	AllowNullable(FetchedOrder::latestDeliveryDate),
	AllowNullable(FetchedOrder::membershipBasedDiscount),
	AllowNullable(FetchedOrder::orderComments),
	AllowNullable(FetchedOrder::orderExtraFields),
	AllowNullable(FetchedOrder::paymentMessage),
	AllowNullable(FetchedOrder::paymentMethod),
	AllowNullable(FetchedOrder::paymentModule),
	AllowNullable(FetchedOrder::paymentParams),
	AllowNullable(FetchedOrder::paymentStatus),
	AllowNullable(FetchedOrder::pickupTime),
	AllowNullable(FetchedOrder::pricesIncludeTax),
	AllowNullable(FetchedOrder::privateAdminNotes),
	AllowNullable(FetchedOrder::referenceTransactionId),
	AllowNullable(FetchedOrder::refererId),
	AllowNullable(FetchedOrder::refererUrl),
	AllowNullable(FetchedOrder::refundedAmount),
	AllowNullable(FetchedOrder::refunds),
	AllowNullable(FetchedOrder::reversedTaxApplied),
	AllowNullable(FetchedOrder::shipments),
	AllowNullable(FetchedOrder::shippingOption),
	AllowNullable(FetchedOrder::shippingPerson),
	AllowNullable(FetchedOrder::subtotal),
	AllowNullable(FetchedOrder::subtotalWithoutTax),
	AllowNullable(FetchedOrder::tax),
	AllowNullable(FetchedOrder::total),
	AllowNullable(FetchedOrder::totalAndMembershipBasedDiscount),
	AllowNullable(FetchedOrder::totalBeforeGiftCardRedemption),
	AllowNullable(FetchedOrder::totalWithoutTax),
	AllowNullable(FetchedOrder::trackingNumber),
	AllowNullable(FetchedOrder::updateDate),
	AllowNullable(FetchedOrder::updateTimestamp),
	AllowNullable(FetchedOrder::usdTotal),
	AllowNullable(FetchedOrder::utmData),
	AllowNullable(FetchedOrder::vendorOrderNumber),
	AllowNullable(FetchedOrder::volumeDiscount),
	AllowNullable(FetchedOrder.PredictedPackage::declaredValue),
	AllowNullable(FetchedOrder.PredictedPackage::height),
	AllowNullable(FetchedOrder.PredictedPackage::length),
	AllowNullable(FetchedOrder.PredictedPackage::weight),
	AllowNullable(FetchedOrder.PredictedPackage::width),
	AllowNullable(FetchedOrder.TaxInvoice::created),
	AllowNullable(FetchedOrder.TaxInvoice::id),
	AllowNullable(FetchedOrder.TaxInvoice::internalId),
	AllowNullable(FetchedOrder.TaxInvoice::link),
	AllowNullable(FetchedOrder.TaxInvoice::type),
	AllowNullable(FetchedOrder::invoices),
	AllowNullable(FetchedOrder::predictedPackage),
    AllowNullable(FetchedOrder::externalOrderData),
	AllowNullable(FetchedOrder.ExternalOrderData::externalOrderId),
	AllowNullable(FetchedOrder.ExternalOrderData::externalFulfillment),
	AllowNullable(FetchedOrder.ExternalOrderData::refererId),
	AllowNullable(FetchedOrder.ExternalOrderData::platformSpecificFields),
	AllowNullable(FetchedOrder.ExternalOrderData::refererChannel)
)
