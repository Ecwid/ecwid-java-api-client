package com.ecwid.apiclient.v3.rule.nullablepropertyrules

import com.ecwid.apiclient.v3.dto.cart.result.CalculateOrderDetailsResult
import com.ecwid.apiclient.v3.rule.NullablePropertyRule
import com.ecwid.apiclient.v3.rule.NullablePropertyRule.AllowNullable
import com.ecwid.apiclient.v3.rule.NullablePropertyRule.IgnoreNullable

val calculateOrderDetailsResultNullablePropertyRules: List<NullablePropertyRule<*, *>> = listOf(
    IgnoreNullable(CalculateOrderDetailsResult.DiscountCouponCatalogLimit::categories),
	IgnoreNullable(CalculateOrderDetailsResult.DiscountCouponCatalogLimit::products),
	IgnoreNullable(CalculateOrderDetailsResult.DiscountCouponInfo::applicationLimit),
	IgnoreNullable(CalculateOrderDetailsResult.DiscountCouponInfo::catalogLimit),
	IgnoreNullable(CalculateOrderDetailsResult.DiscountCouponInfo::code),
	IgnoreNullable(CalculateOrderDetailsResult.DiscountCouponInfo::creationDate),
	IgnoreNullable(CalculateOrderDetailsResult.DiscountCouponInfo::discount),
	IgnoreNullable(CalculateOrderDetailsResult.DiscountCouponInfo::discountType),
	IgnoreNullable(CalculateOrderDetailsResult.DiscountCouponInfo::expirationDate),
	IgnoreNullable(CalculateOrderDetailsResult.DiscountCouponInfo::launchDate),
	IgnoreNullable(CalculateOrderDetailsResult.DiscountCouponInfo::name),
	IgnoreNullable(CalculateOrderDetailsResult.DiscountCouponInfo::orderCount),
	IgnoreNullable(CalculateOrderDetailsResult.DiscountCouponInfo::status),
	IgnoreNullable(CalculateOrderDetailsResult.DiscountCouponInfo::totalLimit),
	IgnoreNullable(CalculateOrderDetailsResult.DiscountCouponInfo::usesLimit),
	IgnoreNullable(CalculateOrderDetailsResult.DiscountInfo::base),
	IgnoreNullable(CalculateOrderDetailsResult.DiscountInfo::description),
	IgnoreNullable(CalculateOrderDetailsResult.DiscountInfo::orderTotal),
	IgnoreNullable(CalculateOrderDetailsResult.DiscountInfo::type),
	IgnoreNullable(CalculateOrderDetailsResult.DiscountInfo::value),
	IgnoreNullable(CalculateOrderDetailsResult.HandlingFeeInfo::description),
	IgnoreNullable(CalculateOrderDetailsResult.HandlingFeeInfo::name),
	AllowNullable(CalculateOrderDetailsResult.HandlingFeeInfo::taxes),
	IgnoreNullable(CalculateOrderDetailsResult.HandlingFeeInfo::value),
	AllowNullable(CalculateOrderDetailsResult.HandlingFeeTax::includeInPrice),
	AllowNullable(CalculateOrderDetailsResult.HandlingFeeTax::name),
	AllowNullable(CalculateOrderDetailsResult.HandlingFeeTax::total),
	AllowNullable(CalculateOrderDetailsResult.HandlingFeeTax::value),
	IgnoreNullable(CalculateOrderDetailsResult.OrderItem::categoryId),
	IgnoreNullable(CalculateOrderDetailsResult.OrderItem::couponAmount),
	IgnoreNullable(CalculateOrderDetailsResult.OrderItem::couponApplied),
	AllowNullable(CalculateOrderDetailsResult.OrderItem::giftCard),
	IgnoreNullable(CalculateOrderDetailsResult.OrderItem::digital),
	IgnoreNullable(CalculateOrderDetailsResult.OrderItem::dimensions),
	IgnoreNullable(CalculateOrderDetailsResult.OrderItem::discounts),
	IgnoreNullable(CalculateOrderDetailsResult.OrderItem::files),
	IgnoreNullable(CalculateOrderDetailsResult.OrderItem::fixedShippingRate),
	IgnoreNullable(CalculateOrderDetailsResult.OrderItem::fixedShippingRateOnly),
	IgnoreNullable(CalculateOrderDetailsResult.OrderItem::id),
	IgnoreNullable(CalculateOrderDetailsResult.OrderItem::imageUrl),
	IgnoreNullable(CalculateOrderDetailsResult.OrderItem::isShippingRequired),
	IgnoreNullable(CalculateOrderDetailsResult.OrderItem::name),
	IgnoreNullable(CalculateOrderDetailsResult.OrderItem::price),
	IgnoreNullable(CalculateOrderDetailsResult.OrderItem::productId),
	IgnoreNullable(CalculateOrderDetailsResult.OrderItem::productPrice),
	IgnoreNullable(CalculateOrderDetailsResult.OrderItem::quantity),
	IgnoreNullable(CalculateOrderDetailsResult.OrderItem::quantityInStock),
	IgnoreNullable(CalculateOrderDetailsResult.OrderItem::selectedOptions),
	AllowNullable(CalculateOrderDetailsResult.OrderItem::combinationId),
	IgnoreNullable(CalculateOrderDetailsResult.OrderItem::shipping),
	IgnoreNullable(CalculateOrderDetailsResult.OrderItem::shortDescription),
	IgnoreNullable(CalculateOrderDetailsResult.OrderItem::sku),
	IgnoreNullable(CalculateOrderDetailsResult.OrderItem::tax),
	IgnoreNullable(CalculateOrderDetailsResult.OrderItem::taxes),
	IgnoreNullable(CalculateOrderDetailsResult.OrderItem::trackQuantity),
	IgnoreNullable(CalculateOrderDetailsResult.OrderItem::weight),
	IgnoreNullable(CalculateOrderDetailsResult.OrderItemDiscountInfo::base),
	IgnoreNullable(CalculateOrderDetailsResult.OrderItemDiscountInfo::orderTotal),
	IgnoreNullable(CalculateOrderDetailsResult.OrderItemDiscountInfo::type),
	IgnoreNullable(CalculateOrderDetailsResult.OrderItemDiscountInfo::value),
	IgnoreNullable(CalculateOrderDetailsResult.OrderItemDiscounts::discountInfo),
	IgnoreNullable(CalculateOrderDetailsResult.OrderItemDiscounts::total),
	IgnoreNullable(CalculateOrderDetailsResult.OrderItemOptionFile::id),
	IgnoreNullable(CalculateOrderDetailsResult.OrderItemOptionFile::name),
	IgnoreNullable(CalculateOrderDetailsResult.OrderItemOptionFile::size),
	IgnoreNullable(CalculateOrderDetailsResult.OrderItemOptionFile::url),
	IgnoreNullable(CalculateOrderDetailsResult.OrderItemOption::files),
	IgnoreNullable(CalculateOrderDetailsResult.OrderItemOption::name),
	IgnoreNullable(CalculateOrderDetailsResult.OrderItemOption::selections),
	IgnoreNullable(CalculateOrderDetailsResult.OrderItemOption::type),
	IgnoreNullable(CalculateOrderDetailsResult.OrderItemOption::value),
	IgnoreNullable(CalculateOrderDetailsResult.OrderItemOption::valuesArray),
	IgnoreNullable(CalculateOrderDetailsResult.OrderItemProductFile::adminUrl),
	IgnoreNullable(CalculateOrderDetailsResult.OrderItemProductFile::customerUrl),
	IgnoreNullable(CalculateOrderDetailsResult.OrderItemProductFile::description),
	IgnoreNullable(CalculateOrderDetailsResult.OrderItemProductFile::expire),
	IgnoreNullable(CalculateOrderDetailsResult.OrderItemProductFile::maxDownloads),
	IgnoreNullable(CalculateOrderDetailsResult.OrderItemProductFile::name),
	IgnoreNullable(CalculateOrderDetailsResult.OrderItemProductFile::productFileId),
	IgnoreNullable(CalculateOrderDetailsResult.OrderItemProductFile::remainingDownloads),
	IgnoreNullable(CalculateOrderDetailsResult.OrderItemProductFile::size),
	AllowNullable(CalculateOrderDetailsResult.OrderItemTax::taxType),
	IgnoreNullable(CalculateOrderDetailsResult.OrderItemTax::includeInPrice),
	IgnoreNullable(CalculateOrderDetailsResult.OrderItemTax::name),
	IgnoreNullable(CalculateOrderDetailsResult.OrderItemTax::taxOnDiscountedSubtotal),
	IgnoreNullable(CalculateOrderDetailsResult.OrderItemTax::taxOnShipping),
	IgnoreNullable(CalculateOrderDetailsResult.OrderItemTax::total),
	IgnoreNullable(CalculateOrderDetailsResult.OrderItemTax::value),
	IgnoreNullable(CalculateOrderDetailsResult.PersonInfo::city),
	IgnoreNullable(CalculateOrderDetailsResult.PersonInfo::companyName),
	IgnoreNullable(CalculateOrderDetailsResult.PersonInfo::countryCode),
	IgnoreNullable(CalculateOrderDetailsResult.PersonInfo::countryName),
	IgnoreNullable(CalculateOrderDetailsResult.PersonInfo::name),
	IgnoreNullable(CalculateOrderDetailsResult.PersonInfo::phone),
	IgnoreNullable(CalculateOrderDetailsResult.PersonInfo::postalCode),
	IgnoreNullable(CalculateOrderDetailsResult.PersonInfo::stateOrProvinceCode),
	IgnoreNullable(CalculateOrderDetailsResult.PersonInfo::stateOrProvinceName),
	IgnoreNullable(CalculateOrderDetailsResult.PersonInfo::street),
	IgnoreNullable(CalculateOrderDetailsResult.PredictedPackage::declaredValue),
	IgnoreNullable(CalculateOrderDetailsResult.PredictedPackage::height),
	IgnoreNullable(CalculateOrderDetailsResult.PredictedPackage::length),
	IgnoreNullable(CalculateOrderDetailsResult.PredictedPackage::weight),
	IgnoreNullable(CalculateOrderDetailsResult.PredictedPackage::width),
	IgnoreNullable(CalculateOrderDetailsResult.ProductDimensions::height),
	IgnoreNullable(CalculateOrderDetailsResult.ProductDimensions::length),
	IgnoreNullable(CalculateOrderDetailsResult.ProductDimensions::width),
	IgnoreNullable(CalculateOrderDetailsResult.SelectionInfo::selectionModifier),
	IgnoreNullable(CalculateOrderDetailsResult.SelectionInfo::selectionModifierType),
	IgnoreNullable(CalculateOrderDetailsResult.SelectionInfo::selectionTitle),
	IgnoreNullable(CalculateOrderDetailsResult.ShippingOptionInfo::estimatedTransitTime),
	IgnoreNullable(CalculateOrderDetailsResult.ShippingOptionInfo::isPickup),
	IgnoreNullable(CalculateOrderDetailsResult.ShippingOptionInfo::pickupInstruction),
	IgnoreNullable(CalculateOrderDetailsResult.ShippingOptionInfo::shippingCarrierName),
	IgnoreNullable(CalculateOrderDetailsResult.ShippingOptionInfo::shippingMethodName),
	IgnoreNullable(CalculateOrderDetailsResult.ShippingOptionInfo::shippingRate),
	AllowNullable(CalculateOrderDetailsResult.Surcharge::description),
	AllowNullable(CalculateOrderDetailsResult.Surcharge::descriptionTranslated),
	AllowNullable(CalculateOrderDetailsResult.Surcharge::id),
	AllowNullable(CalculateOrderDetailsResult.Surcharge::taxable),
	AllowNullable(CalculateOrderDetailsResult.Surcharge::taxes),
	AllowNullable(CalculateOrderDetailsResult.Surcharge::total),
	AllowNullable(CalculateOrderDetailsResult.Surcharge::totalWithoutTax),
	AllowNullable(CalculateOrderDetailsResult.Surcharge::type),
	AllowNullable(CalculateOrderDetailsResult.Surcharge::value),
	IgnoreNullable(CalculateOrderDetailsResult.TaxInfo::appliedByDefault),
	IgnoreNullable(CalculateOrderDetailsResult.TaxInfo::defaultTax),
	IgnoreNullable(CalculateOrderDetailsResult.TaxInfo::enabled),
	IgnoreNullable(CalculateOrderDetailsResult.TaxInfo::id),
	IgnoreNullable(CalculateOrderDetailsResult.TaxInfo::includeInPrice),
	IgnoreNullable(CalculateOrderDetailsResult.TaxInfo::name),
	IgnoreNullable(CalculateOrderDetailsResult.TaxInfo::rules),
	IgnoreNullable(CalculateOrderDetailsResult.TaxInfo::taxShipping),
	IgnoreNullable(CalculateOrderDetailsResult.TaxInfo::useShippingAddress),
	IgnoreNullable(CalculateOrderDetailsResult.TaxOnShipping::name),
	IgnoreNullable(CalculateOrderDetailsResult.TaxOnShipping::total),
	IgnoreNullable(CalculateOrderDetailsResult.TaxOnShipping::value),
	IgnoreNullable(CalculateOrderDetailsResult.TaxRule::tax),
	IgnoreNullable(CalculateOrderDetailsResult.TaxRule::zoneId),
	IgnoreNullable(CalculateOrderDetailsResult::additionalInfo),
	IgnoreNullable(CalculateOrderDetailsResult::availableShippingOptions),
	IgnoreNullable(CalculateOrderDetailsResult::billingPerson),
	IgnoreNullable(CalculateOrderDetailsResult::couponDiscount),
	IgnoreNullable(CalculateOrderDetailsResult::createDate),
	IgnoreNullable(CalculateOrderDetailsResult::createTimestamp),
	IgnoreNullable(CalculateOrderDetailsResult::customerId),
	AllowNullable(CalculateOrderDetailsResult::customerTaxExempt),
	AllowNullable(CalculateOrderDetailsResult::customerTaxId),
	AllowNullable(CalculateOrderDetailsResult::customerTaxIdValid),
	AllowNullable(CalculateOrderDetailsResult::customSurcharges),
	IgnoreNullable(CalculateOrderDetailsResult::discount),
	IgnoreNullable(CalculateOrderDetailsResult::discountCoupon),
	IgnoreNullable(CalculateOrderDetailsResult::discountInfo),
	IgnoreNullable(CalculateOrderDetailsResult::email),
	IgnoreNullable(CalculateOrderDetailsResult::handlingFee),
	IgnoreNullable(CalculateOrderDetailsResult::hidden),
	IgnoreNullable(CalculateOrderDetailsResult::ipAddress),
	IgnoreNullable(CalculateOrderDetailsResult::items),
	IgnoreNullable(CalculateOrderDetailsResult::membershipBasedDiscount),
	IgnoreNullable(CalculateOrderDetailsResult::paymentParams),
	AllowNullable(CalculateOrderDetailsResult::pricesIncludeTax),
	AllowNullable(CalculateOrderDetailsResult::reversedTaxApplied),
	IgnoreNullable(CalculateOrderDetailsResult::shippingOption),
	IgnoreNullable(CalculateOrderDetailsResult::shippingPerson),
	IgnoreNullable(CalculateOrderDetailsResult::subtotal),
	AllowNullable(CalculateOrderDetailsResult::subtotalWithoutTax),
	IgnoreNullable(CalculateOrderDetailsResult::tax),
	IgnoreNullable(CalculateOrderDetailsResult::taxesOnShipping),
	IgnoreNullable(CalculateOrderDetailsResult::total),
	IgnoreNullable(CalculateOrderDetailsResult::totalAndMembershipBasedDiscount),
	AllowNullable(CalculateOrderDetailsResult::totalBeforeGiftCardRedemption),
	AllowNullable(CalculateOrderDetailsResult::totalWithoutTax),
	IgnoreNullable(CalculateOrderDetailsResult::usdTotal),
	IgnoreNullable(CalculateOrderDetailsResult::volumeDiscount)
)
