package com.ecwid.apiclient.v3.converter

import com.ecwid.apiclient.v3.dto.OrderedStringToStringMap
import com.ecwid.apiclient.v3.dto.order.enums.ProductOptionType
import com.ecwid.apiclient.v3.dto.order.request.UpdatedOrder
import com.ecwid.apiclient.v3.dto.order.result.FetchedOrder
import java.util.ArrayList

fun FetchedOrder.toUpdated(): UpdatedOrder {
	return UpdatedOrder(
			email = email,
			ipAddress = ipAddress,
			hidden = hidden,
			createDate = createDate,

			refererUrl = refererUrl,
			globalReferer = globalReferer,
			affiliateId = affiliateId,
			additionalInfo = OrderedStringToStringMap(additionalInfo),

			orderComments = orderComments,
			privateAdminNotes = privateAdminNotes,

			fulfillmentStatus = fulfillmentStatus,
			trackingNumber = trackingNumber,
			pickupTime = pickupTime,

			paymentStatus = paymentStatus,
			paymentMethod = paymentMethod,
			paymentModule = paymentModule,
			paymentParams = OrderedStringToStringMap(paymentParams),
			paymentMessage = paymentMessage,
			creditCardStatus = creditCardStatus?.toUpdated(),
			externalTransactionId = externalTransactionId,

			customerGroup = customerGroup,

			total = total,
			subtotal = subtotal,

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
			handlingFee = handlingFee?.toUpdated()
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
			description = description
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

			selectedOptions = selectedOptions?.map(FetchedOrder.OrderItemSelectedOption::toUpdated),
			taxes = taxes?.map(FetchedOrder.OrderItemTax::toUpdated),
			dimensions = dimensions?.toUpdated(),
			discounts = discounts?.map(FetchedOrder.OrderItemDiscounts::toUpdated)
	)
}

fun FetchedOrder.OrderItemSelectedOption.toUpdated(): UpdatedOrder.OrderItemSelectedOption {
	return UpdatedOrder.OrderItemSelectedOption(
			name = name,
			type = type,
			value = if (type == ProductOptionType.CHOICES) null else value,
			valuesArray = valuesArray?.let { ArrayList(it) },
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
			includeInPrice = includeInPrice
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
			shippingCarrierName = shippingCarrierName,
			shippingMethodName = shippingMethodName,
			shippingRate = shippingRate,
			estimatedTransitTime = estimatedTransitTime,
			isPickup = isPickup,
			pickupInstruction = pickupInstruction
	)
}

fun FetchedOrder.HandlingFee.toUpdated(): UpdatedOrder.HandlingFee {
	return UpdatedOrder.HandlingFee(
			name = name,
			value = value,
			description = description
	)
}
