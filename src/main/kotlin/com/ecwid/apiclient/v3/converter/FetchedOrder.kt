package com.ecwid.apiclient.v3.converter

import com.ecwid.apiclient.v3.dto.common.OrderedStringToListStringMap
import com.ecwid.apiclient.v3.dto.common.OrderedStringToStringMap
import com.ecwid.apiclient.v3.dto.order.enums.ProductOptionType
import com.ecwid.apiclient.v3.dto.order.request.UpdatedOrder
import com.ecwid.apiclient.v3.dto.order.result.FetchedOrder

fun FetchedOrder.toUpdated(): UpdatedOrder {
	return UpdatedOrder(
		email = email,
		ipAddress = ipAddress,
		hidden = hidden,
		createDate = createDate,
		latestShipDate = latestShipDate,

		refererUrl = refererUrl,
		globalReferer = globalReferer,
		affiliateId = affiliateId,
		additionalInfo = OrderedStringToStringMap(additionalInfo),

		orderComments = orderComments,
		privateAdminNotes = privateAdminNotes,

		fulfillmentStatus = fulfillmentStatus,
		trackingNumber = trackingNumber,
		trackingUrl = trackingUrl,
		pickupTime = pickupTime,

		paymentStatus = paymentStatus,
		paymentMethod = paymentMethod,
		paymentModule = paymentModule,
		paymentParams = OrderedStringToStringMap(paymentParams),
		paymentMessage = paymentMessage,
		creditCardStatus = creditCardStatus?.toUpdated(),
		externalTransactionId = externalTransactionId,

		customerId = customerId,
		customerGroup = customerGroup,
		acceptMarketing = acceptMarketing,

		total = total,
		subtotal = subtotal,

		totalBeforeGiftCardRedemption = totalBeforeGiftCardRedemption,
		giftCardRedemption = giftCardRedemption,
		giftCardDoubleSpending = giftCardDoubleSpending,
		giftCardCode = giftCardCode,
		giftCardId = giftCardId,
		giftCardUuid = giftCardUuid,

		tax = tax,
		customerTaxExempt = customerTaxExempt,
		customerTaxId = customerTaxId,
		customerTaxIdValid = customerTaxIdValid,
		reversedTaxApplied = reversedTaxApplied,

		couponDiscount = couponDiscount,
		volumeDiscount = volumeDiscount,
		membershipBasedDiscount = membershipBasedDiscount,
		totalAndMembershipBasedDiscount = totalAndMembershipBasedDiscount,
		discount = discount,
		discountInfo = discountInfo?.map(FetchedOrder.DiscountInfo::toUpdated),
		discountCoupon = discountCoupon?.toUpdated(),

		items = items?.map(FetchedOrder.OrderItem::toUpdated),

		billingPerson = billingPerson?.toUpdated(),
		shippingPerson = shippingPerson?.toUpdated(),

		shippingOption = shippingOption?.toUpdated(),
		taxesOnShipping = taxesOnShipping.map(FetchedOrder.BaseOrderItemTax::toUpdated),
		handlingFee = handlingFee?.toUpdated(),

		customSurcharges = customSurcharges.map(FetchedOrder.Surcharge::toUpdated),

		utmData = utmData?.toUpdated(),

		customDiscount = customDiscount,
		disableAllCustomerNotifications = disableAllCustomerNotifications,
		ebayId = ebayId,
		externalFulfillment = externalFulfillment,
		externalOrderId = externalOrderId,
		externalTransactionUrl = externalTransactionUrl,
		latestDeliveryDate = latestDeliveryDate,
		referenceTransactionId = referenceTransactionId,
		refererId = refererId,

		pricesIncludeTax = pricesIncludeTax,
		externalOrderData = externalOrderData?.toUpdated(),

		orderExtraFields = orderExtraFields?.map(FetchedOrder.ExtraFieldsInfo::toUpdated),
		paymentReference = paymentReference,
		loyalty = loyalty?.toUpdated(),
		customerFiscalCode = customerFiscalCode,
		lang = lang,
	)
}

fun FetchedOrder.CreditCardStatus.toUpdated(): UpdatedOrder.CreditCardStatus {
	return UpdatedOrder.CreditCardStatus(
		avsMessage = avsMessage,
		cvvMessage = cvvMessage
	)
}

fun FetchedOrder.DiscountInfo.toUpdated(): UpdatedOrder.DiscountInfo {
	return UpdatedOrder.DiscountInfo(
		value = value,
		type = type,
		base = base,
		orderTotal = orderTotal,
		description = description,
		appliesToProducts = appliesToProducts,
		appliesToItems = appliesToItems,
	)
}

fun FetchedOrder.DiscountCouponInfo.toUpdated(): UpdatedOrder.DiscountCouponInfo {
	return UpdatedOrder.DiscountCouponInfo(
		name = name,
		code = code,
		discountType = discountType,
		status = status,
		discount = discount,
		launchDate = launchDate,
		expirationDate = expirationDate,
		totalLimit = totalLimit,
		usesLimit = usesLimit,
		repeatCustomerOnly = repeatCustomerOnly
	)
}

fun FetchedOrder.OrderItem.toUpdated(): UpdatedOrder.OrderItem {
	return UpdatedOrder.OrderItem(
		id = id,
		productId = if (productId == 0) null else productId,
		categoryId = categoryId,

		price = price,
		productPrice = productPrice,
		shipping = shipping,
		tax = tax,
		fixedShippingRate = fixedShippingRate,
		couponAmount = couponAmount,

		sku = sku,
		name = name,
		shortDescription = shortDescription,
		quantity = quantity,
		quantityInStock = quantityInStock,
		weight = weight,

		isShippingRequired = isShippingRequired,
		trackQuantity = trackQuantity,
		fixedShippingRateOnly = fixedShippingRateOnly,
		digital = digital,
		couponApplied = couponApplied,
		giftCard = giftCard,

		discountsAllowed = discountsAllowed,
		isCustomerSetPrice = isCustomerSetPrice,
		nameTranslated = nameTranslated,
		selectedPrice = selectedPrice?.toUpdated(),
		shortDescriptionTranslated = shortDescriptionTranslated,
		taxable = taxable,

		selectedOptions = selectedOptions?.map(FetchedOrder.OrderItemSelectedOption::toUpdated),
		combinationId = combinationId,
		taxes = taxes?.map(FetchedOrder.OrderItemTax::toUpdated),
		dimensions = dimensions?.toUpdated(),
		discounts = discounts?.map(FetchedOrder.OrderItemDiscounts::toUpdated),
		externalReferenceId = externalReferenceId,
		isPreorder = isPreorder,
	)
}

fun FetchedOrder.OrderItemSelectedOption.toUpdated(): UpdatedOrder.OrderItemSelectedOption {
	return UpdatedOrder.OrderItemSelectedOption(
		name = name,
		nameTranslated = nameTranslated?.let { OrderedStringToStringMap(it) },
		type = type,
		value = if (type == ProductOptionType.CHOICES) null else value,
		valueTranslated = valueTranslated?.let { OrderedStringToStringMap(it) },
		valuesArray = valuesArray?.let { ArrayList(it) },
		valuesArrayTranslated = valuesArrayTranslated?.let { OrderedStringToListStringMap(it) },
		selections = selections?.map(FetchedOrder.OrderItemSelectionInfo::toUpdated)
	)
}

fun FetchedOrder.OrderItemSelectionInfo.toUpdated(): UpdatedOrder.OrderItemSelectionInfo {
	return UpdatedOrder.OrderItemSelectionInfo(
		selectionTitle = selectionTitle,
		selectionModifier = selectionModifier,
		selectionModifierType = selectionModifierType
	)
}

fun FetchedOrder.OrderItemTax.toUpdated(): UpdatedOrder.OrderItemTax {
	return UpdatedOrder.OrderItemTax(
		name = name,
		value = value,
		total = total,
		taxOnDiscountedSubtotal = taxOnDiscountedSubtotal,
		taxOnShipping = taxOnShipping,
		includeInPrice = includeInPrice,
		taxType = taxType,
	)
}

fun FetchedOrder.ProductDimensions.toUpdated(): UpdatedOrder.ProductDimensions {
	return UpdatedOrder.ProductDimensions(
		length = length,
		width = width,
		height = height
	)
}

fun FetchedOrder.OrderItemDiscounts.toUpdated(): UpdatedOrder.OrderItemDiscounts {
	return UpdatedOrder.OrderItemDiscounts(
		discountInfo = discountInfo?.toUpdated(),
		total = total
	)
}

fun FetchedOrder.PersonInfo.toUpdated(): UpdatedOrder.PersonInfo {
	return UpdatedOrder.PersonInfo(
		name = name,
		companyName = companyName,
		street = street,
		city = city,
		countryCode = countryCode,
		postalCode = postalCode,
		stateOrProvinceCode = stateOrProvinceCode,
		phone = phone
	)
}

fun FetchedOrder.ShippingOption.toUpdated(): UpdatedOrder.ShippingOption {
	return UpdatedOrder.ShippingOption(
		shippingMethodId = shippingMethodId,
		shippingCarrierName = shippingCarrierName,
		shippingMethodName = shippingMethodName,
		shippingRate = shippingRate,
		estimatedTransitTime = estimatedTransitTime,
		isPickup = isPickup,
		pickupInstruction = pickupInstruction,
		fulfillmentType = fulfillmentType,
		locationId = locationId,
	)
}

fun FetchedOrder.HandlingFee.toUpdated(): UpdatedOrder.HandlingFee {
	return UpdatedOrder.HandlingFee(
		name = name,
		value = value,
		description = description,
		taxes = taxes.map(FetchedOrder.HandlingFeeTax::toUpdated)
	)
}

fun FetchedOrder.Surcharge.toUpdated(): UpdatedOrder.Surcharge {
	return UpdatedOrder.Surcharge(
		id = id,
		value = value,
		type = type,
		total = total,
		description = description,
		descriptionTranslated = descriptionTranslated,
		taxable = taxable,
		taxes = taxes.map(FetchedOrder.BaseOrderItemTax::toUpdated)
	)
}

fun FetchedOrder.BaseOrderItemTax.toUpdated(): UpdatedOrder.BaseOrderItemTax {
	return UpdatedOrder.BaseOrderItemTax(
		name = name,
		value = value,
		total = total,
		taxType = taxType,
	)
}

fun FetchedOrder.HandlingFeeTax.toUpdated(): UpdatedOrder.HandlingFeeTax {
	return UpdatedOrder.HandlingFeeTax(
		name = name,
		value = value,
		total = total
	)
}

fun FetchedOrder.UtmData.toUpdated(): UpdatedOrder.UtmData {
	return UpdatedOrder.UtmData(
		source = source,
		campaign = campaign,
		medium = medium,
		mcEid = mcEid,
		mcCid = mcCid
	)
}

fun FetchedOrder.ExternalOrderData.toUpdated(): UpdatedOrder.ExternalOrderData {
	return UpdatedOrder.ExternalOrderData(
		externalFulfillment = externalFulfillment,
		externalOrderId = externalOrderId,
		refererId = refererId,
		platformSpecificFields = platformSpecificFields?.let { HashMap(it) },
		refererChannel = refererChannel
	)
}

fun FetchedOrder.SelectedPrice.toUpdated(): UpdatedOrder.SelectedPrice {
	return UpdatedOrder.SelectedPrice(
		value = this.value
	)
}

fun FetchedOrder.ExtraFieldsInfo.toUpdated(): UpdatedOrder.OrderExtraFields {
	return UpdatedOrder.OrderExtraFields(
		customerInputType = this.customerInputType,
		title = this.title,
		id = this.id,
		value = this.value,
		orderDetailsDisplaySection = this.orderDetailsDisplaySection,
		orderBy = this.orderBy
	)
}

fun FetchedOrder.Loyalty.toUpdated(): UpdatedOrder.Loyalty {
	return UpdatedOrder.Loyalty(
		earned = this.earned,
		redemption = this.redemption?.toUpdated(),
		balance = this.balance,
	)
}

fun FetchedOrder.LoyaltyRedemption.toUpdated(): UpdatedOrder.LoyaltyRedemption {
	return UpdatedOrder.LoyaltyRedemption(
		id = this.id,
		amount = this.amount,
		cancelled = this.cancelled,
	)
}
