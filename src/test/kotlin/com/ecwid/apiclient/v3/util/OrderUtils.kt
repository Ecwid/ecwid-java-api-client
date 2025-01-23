package com.ecwid.apiclient.v3.util

import com.ecwid.apiclient.v3.dto.common.OrderedStringToStringMap
import com.ecwid.apiclient.v3.dto.order.enums.*
import com.ecwid.apiclient.v3.dto.order.request.UpdatedOrder

fun generateTestOrder(): UpdatedOrder {
	val externalOrderId = "Order #" + randomAlphanumeric(8)
	val externalFulfillment = randomBoolean()
	val refererId = "Referer " + randomAlphanumeric(8)
	val totalPrice = randomPrice()
	val trackingNumber = randomAlphanumeric(16)
	return UpdatedOrder(
		email = randomEmail(),
		ipAddress = randomIp(),
		hidden = false, // true allowed only for paymentStatus INCOMPLETE
		createDate = randomDate(),

		refererUrl = randomUrl(),
		globalReferer = randomUrl(),
		affiliateId = randomAlphanumeric(16),
		additionalInfo = OrderedStringToStringMap(
			linkedMapOf(
				"additionalParam1" to randomAlphanumeric(16),
				"additionalParam2" to randomAlphanumeric(16)
			)
		),

		orderComments = "Order comment " + randomAlphanumeric(16),
		privateAdminNotes = "Private admin note " + randomAlphanumeric(16),

		fulfillmentStatus = randomEnumValue<OrderFulfillmentStatus>(),
		trackingNumber = trackingNumber,
		trackingUrl = "https://track.aftership.com/$trackingNumber",

		paymentStatus = randomEnumValue(OrderPaymentStatus.INCOMPLETE),
		paymentMethod = "Payment method " + randomAlphanumeric(8),
		paymentModule = "Payment module " + randomAlphanumeric(8),
		paymentParams = OrderedStringToStringMap(
			linkedMapOf(
				"paymentParam1" to randomAlphanumeric(16),
				"paymentParam2" to randomAlphanumeric(16)
			)
		),
		paymentMessage = randomAlphanumeric(16),
		creditCardStatus = UpdatedOrder.CreditCardStatus(
			avsMessage = randomAlphanumeric(8),
			cvvMessage = randomAlphanumeric(8)
		),
		externalTransactionId = randomAlphanumeric(16),

		customerGroup = "Group " + randomAlphanumeric(8),
		acceptMarketing = randomBoolean(),

		total = totalPrice,
		totalBeforeGiftCardRedemption = totalPrice,
		subtotal = randomPrice(),
		giftCardRedemption = 0.0,
		giftCardDoubleSpending = false,

		tax = randomPrice(),
		customerTaxExempt = false, // TODO We cannot set this field to true, we should need update corresponding customer entity field first
		customerTaxId = randomAlphanumeric(16),
		customerTaxIdValid = randomBoolean(),
		reversedTaxApplied = randomBoolean(),

		couponDiscount = randomPrice(),
		volumeDiscount = randomPrice(),
		membershipBasedDiscount = randomPrice(),
		totalAndMembershipBasedDiscount = randomPrice(),
		discount = randomPrice(),
		discountInfo = listOf(
			generateTestOnTotalDiscountInfo(),
			generateTestOnMembershipDiscountInfo(),
			generateTestOnTotalAndMembershipDiscountInfo(),
			generateTestCustomDiscountInfo()
		),

		// TODO Pass real discount coupon code when API client will support this
// 			discountCoupon = UpdatedOrder.DiscountCouponInfo(
// 					name = "Discount coupon " + randomAlphanumeric(8),
// 					code = randomAlphanumeric(16),
// 					discountType = randomEnumValue<DiscountCouponType>(),
// 					status = randomEnumValue<DiscountCouponStatus>(),
// 					discount = randomPrice(),
// 					launchDate = randomDate(),
// 					expirationDate = randomDate(),
// 					totalLimit = randomPrice(),
// 					usesLimit = randomEnumValue<DiscountCouponUsesLimit>(),
// 					repeatCustomerOnly = randomBoolean()
// 			),
		customDiscount = listOf(),

		items = listOf(
			generateTestOrderItem(),
			generateTestOrderItem()
		),

		billingPerson = generatePersonInfo(),
		shippingPerson = generatePersonInfo(),

		shippingOption = UpdatedOrder.ShippingOption(
			shippingMethodId = "MethodId " + randomAlphanumeric(8),
			shippingCarrierName = "Carrier " + randomAlphanumeric(8),
			shippingMethodName = "Method " + randomAlphanumeric(8),
			shippingRate = randomPrice(),
			estimatedTransitTime = "Estimates " + randomAlphanumeric(8),
			isPickup = true,
			pickupInstruction = "Instruction " + randomAlphanumeric(64),
			fulfillmentType = FulfillmentType.PICKUP
		),
		taxesOnShipping = listOf(),
		handlingFee = UpdatedOrder.HandlingFee(
			name = "Name " + randomAlphanumeric(8),
			value = randomPrice(),
			description = "Description " + randomAlphanumeric(64),
			taxes = listOf()
		),
		pricesIncludeTax = false,
		disableAllCustomerNotifications = randomBoolean(),

		customSurcharges = listOf(),

		externalFulfillment = externalFulfillment,
		refererId = refererId,
		externalOrderId = externalOrderId,

		externalOrderData = UpdatedOrder.ExternalOrderData(
			externalFulfillment = externalFulfillment,
			externalOrderId = externalOrderId,
			refererId = refererId,
			platformSpecificFields = HashMap(
				mapOf(
					"field1" to randomAlphanumeric(8),
					"field2" to randomAlphanumeric(8),
				)
			),
			refererChannel = "Referer channel " + randomAlphanumeric(8),
		),
		lang = "en",
		orderExtraFields = listOf(
			UpdatedOrder.OrderExtraFields(
				customerInputType = "TEXT",
				title = "text 1",
				id = "first_id",
				value = "text value one",
				orderDetailsDisplaySection = "customer_info",
				orderBy = "1"
			),
			UpdatedOrder.OrderExtraFields(
				customerInputType = "TEXT",
				title = "text 2",
				id = "second_id",
				value = "text value two",
				orderDetailsDisplaySection = "customer_info",
				orderBy = "2"
			)
		)
	)
}

private fun generateTestOnTotalDiscountInfo() = UpdatedOrder.DiscountInfo(
	value = randomPrice(),
	type = randomEnumValue<DiscountType>(),
	base = DiscountBase.ON_TOTAL,
	orderTotal = randomPrice(),
	description = "On total discount " + randomAlphanumeric(8)
)

private fun generateTestOnMembershipDiscountInfo() = UpdatedOrder.DiscountInfo(
	value = randomPrice(),
	type = randomEnumValue<DiscountType>(),
	base = DiscountBase.ON_MEMBERSHIP,
	description = "On membership discount " + randomAlphanumeric(8)
)

private fun generateTestOnTotalAndMembershipDiscountInfo() = UpdatedOrder.DiscountInfo(
	value = randomPrice(),
	type = randomEnumValue<DiscountType>(),
	base = DiscountBase.ON_TOTAL_AND_MEMBERSHIP,
	orderTotal = randomPrice(),
	description = "On total and membership discount " + randomAlphanumeric(8)
)

private fun generateTestCustomDiscountInfo() = UpdatedOrder.DiscountInfo(
	value = randomPrice(),
	type = randomEnumValue<DiscountType>(),
	base = DiscountBase.CUSTOM,
	orderTotal = randomPrice(),
	description = "Custom discount " + randomAlphanumeric(8)
)

private fun generateTestOrderItem() = UpdatedOrder.OrderItem(
	// var id: Int? = null, // TODO readonly
	// productId = com.ecwid.api.v3.randomId(), // TODO need to pass existing product id
	categoryId = randomLongId(),

	price = randomPrice(),
	productPrice = randomPrice(),
	shipping = randomPrice(),
	tax = randomPrice(),
	fixedShippingRate = randomPrice(),
	couponAmount = randomPrice(),

	sku = randomAlphanumeric(16),
	name = "Order item " + randomAlphanumeric(8),
	shortDescription = "Order item description " + randomAlphanumeric(32),
	quantity = randomByte(),
	quantityInStock = randomByte(),
	weight = randomWeight(), // TODO readonly?

	isShippingRequired = true, // true for weight field
	trackQuantity = randomBoolean(),
	fixedShippingRateOnly = randomBoolean(),
	digital = randomBoolean(),
	couponApplied = randomBoolean(),
	giftCard = false,
	isCustomerSetPrice = randomBoolean(),
	taxable = randomBoolean(),

	selectedOptions = listOf(
		generateChoiceSelectedOption(),
		generateChoicesSelectedOption(),
		generateTextSelectedOption(),
		generateDateSelectedOption(),
		generateFilesSelectedOption()
	),
	combinationId = randomId(),
	taxes = listOf(
		generateTestOrderItemTax(),
		generateTestOrderItemTax()
	),
	dimensions = UpdatedOrder.ProductDimensions(
		length = randomDimension(),
		width = randomDimension(),
		height = randomDimension()
	),
	discounts = listOf(
		generateOrderItemDiscounts(),
		generateOrderItemDiscounts()
	)
)

private fun generateOrderItemDiscounts(): UpdatedOrder.OrderItemDiscounts {
	return UpdatedOrder.OrderItemDiscounts(
		discountInfo = generateTestCustomDiscountInfo(),
		total = randomPrice()
	)
}

private fun generateChoiceSelectedOption(): UpdatedOrder.OrderItemSelectedOption {
	return UpdatedOrder.OrderItemSelectedOption.createForChoiceOption(
		name = "Choice Option " + randomAlphanumeric(8),
		selection = "Selection #1, " + randomAlphanumeric(8)
	)
}

private fun generateChoicesSelectedOption(): UpdatedOrder.OrderItemSelectedOption {
	val value1 = "Selection #1, " + randomAlphanumeric(8)
	val value3 = "Selection #3, " + randomAlphanumeric(8)
	return UpdatedOrder.OrderItemSelectedOption.createForChoicesOption(
		name = "Choices Option " + randomAlphanumeric(8),
		selections = listOf(
			UpdatedOrder.OrderItemSelectionInfo(
				selectionTitle = value1,
				selectionModifier = randomModifier(),
				selectionModifierType = randomEnumValue<PriceModifierType>()
			),
			UpdatedOrder.OrderItemSelectionInfo(
				selectionTitle = value3,
				selectionModifier = randomModifier(),
				selectionModifierType = randomEnumValue<PriceModifierType>()
			)
		)
	)
}

private fun generateTextSelectedOption(): UpdatedOrder.OrderItemSelectedOption {
	return UpdatedOrder.OrderItemSelectedOption.createForTextOption(
		name = "Text Option " + randomAlphanumeric(8),
		value = randomAlphanumeric(8)
	)
}

private fun generateDateSelectedOption(): UpdatedOrder.OrderItemSelectedOption {
	return UpdatedOrder.OrderItemSelectedOption.createForDateOption(
		name = "Date Option " + randomAlphanumeric(8),
		date = randomDate()
	)
}

private fun generateFilesSelectedOption(): UpdatedOrder.OrderItemSelectedOption {
	return UpdatedOrder.OrderItemSelectedOption.createForFilesOption(name = "Files Option " + randomAlphanumeric(8))
}

private fun generateTestOrderItemTax(): UpdatedOrder.OrderItemTax {
	return UpdatedOrder.OrderItemTax(
		name = "Tax " + randomAlphanumeric(8),
		value = randomPrice(),
		total = randomPrice(),
		taxOnDiscountedSubtotal = randomPrice(),
		taxOnShipping = randomPrice(),
		includeInPrice = randomBoolean()
	)
}

private fun generatePersonInfo(): UpdatedOrder.PersonInfo {
	return UpdatedOrder.PersonInfo(
		name = "Name " + randomAlphanumeric(8),
		companyName = "Company " + randomAlphanumeric(8),
		street = "Line 1 " + randomAlphanumeric(8) + "\nLine 2 " + randomAlphanumeric(8),
		city = "City " + randomAlphanumeric(8),
		countryCode = "US",
		postalCode = randomAlphanumeric(5),
		stateOrProvinceCode = "CA",
		phone = randomAlphanumeric(10)
	)
}
