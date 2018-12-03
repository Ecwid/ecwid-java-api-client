package com.ecwid.apiclient.v3

import com.ecwid.apiclient.v3.dto.OrderedStringToStringMap
import com.ecwid.apiclient.v3.dto.order.enums.*
import com.ecwid.apiclient.v3.dto.order.request.*
import com.ecwid.apiclient.v3.dto.order.request.OrderItemOptionFileUploadRequest.UploadFileData
import com.ecwid.apiclient.v3.dto.order.result.FetchedOrder
import com.ecwid.apiclient.v3.dto.order.result.OrdersSearchResult
import com.ecwid.apiclient.v3.exception.EcwidApiException
import com.ecwid.apiclient.v3.util.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import java.io.FileInputStream
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*
import kotlin.random.Random


class OrdersTest: BaseEntityTest() {

	@BeforeEach
	override fun beforeEach() {
		super.beforeEach()

		// We need to start from scratch each time
		removeAllOrders()
	}

	@Test
	fun testOrderLifecycle() {
		// Creating new order
		val orderCreateRequest = OrderCreateRequest(
				newOrder = generateTestOrder()
		)
		val orderCreateResult = apiClient.createOrder(orderCreateRequest)
		assertTrue(orderCreateResult.id > 0)

		// Checking that order was successfully created with necessary parameters
		val orderDetailsRequest = OrderDetailsRequest(orderNumber = orderCreateResult.id)
		val orderDetails1 = apiClient.getOrderDetails(orderDetailsRequest)
		assertEquals(orderCreateRequest.newOrder, orderDetails1.toUpdated())

		// Completely updating newly created order
		val orderUpdateRequest = OrderUpdateRequest(
				orderNumber = orderDetails1.orderNumber,
				updatedOrder = generateTestOrder().copy(
						customerGroup = null, // TODO Discover why after each update this field resets to null
						discountCoupon = orderCreateRequest.newOrder.discountCoupon // TODO Discover why we cannot update this field after order creation 
				)
		)
		val orderUpdateResult1 = apiClient.updateOrder(orderUpdateRequest)
		assertEquals(1, orderUpdateResult1.updateCount)

		// Checking that order was successfully updated with necessary parameters
		val orderDetails2 = apiClient.getOrderDetails(orderDetailsRequest)
		assertEquals(orderUpdateRequest.updatedOrder, orderDetails2.toUpdated())

		// Deleting order
		val orderDeleteRequest = OrderDeleteRequest(orderNumber = orderDetails1.orderNumber)
		val orderDeleteResult = apiClient.deleteOrder(orderDeleteRequest)
		assertEquals(1, orderDeleteResult.deleteCount)

		// Checking that deleted order is not accessible anymore
		try {
			apiClient.getOrderDetails(orderDetailsRequest)
		} catch (e: EcwidApiException) {
			assertEquals(404, e.statusCode)
			assertEquals("Order #${orderCreateResult.id} not found", e.message)
		}
	}

	@Test
	fun testManipulateOrderItemOptionFiles() {
		// Creating order with item with FILES option
		val filesOptionName = "File option Тест !@#$%^&*()"

		val orderCreateRequest = OrderCreateRequest(
				newOrder = UpdatedOrder(
						email = randomEmail(),
						items = listOf(
								UpdatedOrder.OrderItem(
										name = "Order item with files",
										selectedOptions = listOf(
												UpdatedOrder.OrderItemSelectedOption.createForFilesOption(
														name = filesOptionName
												)
										)
								)
						)
				)
		)
		val orderCreateResult = apiClient.createOrder(orderCreateRequest)
		val orderNumber = orderCreateResult.id
		assertTrue(orderNumber > 0)

		// Get order item ID in newly created order
		val orderDetailsRequest = OrderDetailsRequest(orderNumber = orderNumber)
		val orderDetails1 = apiClient.getOrderDetails(orderDetailsRequest)
		val orderItemId = orderDetails1.items?.firstOrNull()?.id
		require(orderItemId != null)

		// Upload some order files from different sources
		val orderItemOptionFileUploadRequest1 = OrderItemOptionFileUploadRequest(
				orderNumber = orderNumber,
				orderItemId = orderItemId,
				optionName = filesOptionName,
				fileName = generateFileName("test", "png"),
				fileData = UploadFileData.ExternalUrlData(externalUrl = "https://don16obqbay2c.cloudfront.net/favicons/apple-touch-icon-180x180.png")
		)
		val orderItemOptionFileUploadRequest2 = OrderItemOptionFileUploadRequest(
				orderNumber = orderNumber,
				orderItemId = orderItemId,
				optionName = filesOptionName,
				fileName = generateFileName("test", "png"),
				fileData = UploadFileData.LocalFileData(file = getTestPngFilePath().toFile())
		)
		val orderItemOptionFileUploadRequest3 = OrderItemOptionFileUploadRequest(
				orderNumber = orderNumber,
				orderItemId = orderItemId,
				optionName = filesOptionName,
				fileName = generateFileName("test", "png"),
				fileData = UploadFileData.InputStreamData(stream = FileInputStream(getTestPngFilePath().toFile()))
		)
		val orderItemOptionFileUploadRequest4 = OrderItemOptionFileUploadRequest(
				orderNumber = orderNumber,
				orderItemId = orderItemId,
				optionName = filesOptionName,
				fileName = generateFileName("test", "png"),
				fileData = UploadFileData.ByteArrayData(bytes = Files.readAllBytes(getTestPngFilePath()))
		)

		val orderItemOptionFileUploadResult1 = apiClient.uploadOrderItemOptionFile(orderItemOptionFileUploadRequest1)
		val orderItemOptionFileUploadResult2 = apiClient.uploadOrderItemOptionFile(orderItemOptionFileUploadRequest2)
		val orderItemOptionFileUploadResult3 = apiClient.uploadOrderItemOptionFile(orderItemOptionFileUploadRequest3)
		val orderItemOptionFileUploadResult4 = apiClient.uploadOrderItemOptionFile(orderItemOptionFileUploadRequest4)

		assertAll(
				{ assertTrue(orderItemOptionFileUploadResult1.id > 0) },
				{ assertTrue(orderItemOptionFileUploadResult2.id > 0) },
				{ assertTrue(orderItemOptionFileUploadResult3.id > 0) },
				{ assertTrue(orderItemOptionFileUploadResult4.id > 0) }
		)

		// Check that files option has accurate 4 attached files
		val orderDetails2 = apiClient.getOrderDetails(orderDetailsRequest)
		val filesOption2 = orderDetails2.items?.first()?.selectedOptions?.first()?.files
		require(filesOption2 != null)
		assertEquals(4, filesOption2.size)
		assertFileOption(filesOption2[0], orderItemOptionFileUploadRequest1.fileName)
		assertFileOption(filesOption2[1], orderItemOptionFileUploadRequest2.fileName)
		assertFileOption(filesOption2[2], orderItemOptionFileUploadRequest3.fileName)
		assertFileOption(filesOption2[3], orderItemOptionFileUploadRequest4.fileName)

		// Now delete first attached to option file
		val deleteOrderItemOptionFileRequest = OrderItemOptionFileDeleteRequest(
				orderNumber = orderNumber,
				orderItemId = orderItemId,
				optionName = filesOptionName,
				fileId = orderItemOptionFileUploadResult1.id
		)
		val orderItemOptionFileDeleteResult = apiClient.deleteOrderItemOptionFile(deleteOrderItemOptionFileRequest)
		assertEquals(1, orderItemOptionFileDeleteResult.deleteCount)

		// Check that only 3 attached files left
		val orderDetails3 = apiClient.getOrderDetails(orderDetailsRequest)
		val filesOption3 = orderDetails3.items?.first()?.selectedOptions?.first()?.files
		assertEquals(3, filesOption3?.size)

		// Now delete the rest attached to option files
		val deleteOrderItemOptionFilesRequest = OrderItemOptionFilesDeleteRequest(
				orderNumber = orderNumber,
				orderItemId = orderItemId,
				optionName = filesOptionName
		)
		val orderItemOptionFilesDeleteResult = apiClient.deleteOrderItemOptionFiles(deleteOrderItemOptionFilesRequest)
		assertEquals(3, orderItemOptionFilesDeleteResult.deleteCount)

		// Check that no attached files remain
		val orderDetails4 = apiClient.getOrderDetails(orderDetailsRequest)
		val filesOption4 = orderDetails4.items?.first()?.selectedOptions?.first()?.files
		assertNull(filesOption4)
	}

	@Test
	fun testSearchFields() {
		// Creating new order
		val orderCreateRequest = OrderCreateRequest(
				newOrder = UpdatedOrder(
						email = randomEmail(),
						total = randomPrice(),
						discountCoupon = UpdatedOrder.DiscountCouponInfo(
								name = "Discount coupon " + randomAlphanumeric(16),
								code = randomAlphanumeric(16)
						),
						paymentMethod = "Payment method " + randomAlphanumeric(8),
						shippingOption = UpdatedOrder.ShippingOption(
								shippingMethodName = "Method " + randomAlphanumeric(8)
						),
						paymentStatus = OrderPaymentStatus.AWAITING_PAYMENT,
						fulfillmentStatus = OrderFulfillmentStatus.AWAITING_PROCESSING,
						orderComments = randomAlphanumeric(16),
						billingPerson = UpdatedOrder.PersonInfo(
								name = randomAlphanumeric(16)
						)
				)
		)
		val orderCreateResult = apiClient.createOrder(orderCreateRequest)
		assertTrue(orderCreateResult.id > 0)

		// Waiting till order became available for searching
		var tries = 0
		do {
			val ordersSearchRequest = OrdersSearchRequest(keywords = orderCreateRequest.newOrder.orderComments)
			val ordersSearchResult = apiClient.searchOrders(ordersSearchRequest)
			if (ordersSearchResult.total > 0) break
			tries++
			Thread.sleep(1000)
		} while (tries <= 10)

		// Trying to search by different fields

		assertOrderSearch(
				positiveOrderNumber = orderCreateResult.id,
				positiveSearchRequest = OrdersSearchRequest(keywords = orderCreateRequest.newOrder.orderComments),
				negativeSearchRequest = OrdersSearchRequest(keywords = orderCreateRequest.newOrder.orderComments + "foo")
		)

		assertOrderSearch(
				positiveOrderNumber = orderCreateResult.id,
				positiveSearchRequest = OrdersSearchRequest(
						totalFrom = orderCreateRequest.newOrder.total!! - 10.0,
						totalTo = orderCreateRequest.newOrder.total!! + 10.0
				),
				negativeSearchRequest = OrdersSearchRequest(
						totalFrom = orderCreateRequest.newOrder.total!! - 20.0,
						totalTo = orderCreateRequest.newOrder.total!! - 10.0
				)
		)

		assertOrderSearch(
				positiveOrderNumber = orderCreateResult.id,
				positiveSearchRequest = OrdersSearchRequest(
						createdFrom = Date.from(Date().toInstant().minusSeconds(60)),
						createdTo = Date.from(Date().toInstant().plusSeconds(60))
				),
				negativeSearchRequest = OrdersSearchRequest(
						createdFrom = Date(0),
						createdTo = Date(0)
				)
		)

		assertOrderSearch(
				positiveOrderNumber = orderCreateResult.id,
				positiveSearchRequest = OrdersSearchRequest(
						updatedFrom = Date.from(Date().toInstant().minusSeconds(60)),
						updatedTo = Date.from(Date().toInstant().plusSeconds(60))
				),
				negativeSearchRequest = OrdersSearchRequest(
						updatedFrom = Date(0),
						updatedTo = Date(0)
				)
		)

		// TODO We should create real discount coupon to make this search works correctly
//		assertOrderSearch(
//				positiveOrderNumber = createOrderResult.id,
//				positiveSearchRequest = SearchOrdersRequest(couponCode = createOrderRequest.newOrder.discountCoupon?.code),
//				negativeSearchRequest = SearchOrdersRequest(couponCode = createOrderRequest.newOrder.discountCoupon?.code + "foo")
//		)

		assertOrderSearch(
				positiveOrderNumber = orderCreateResult.id,
				positiveSearchRequest = OrdersSearchRequest(orderNumber = orderCreateResult.id),
				negativeSearchRequest = OrdersSearchRequest(orderNumber = Int.MAX_VALUE)
		)

		assertOrderSearch(
				positiveOrderNumber = orderCreateResult.id,
				positiveSearchRequest = OrdersSearchRequest(vendorOrderNumber = orderCreateResult.id.toString()),
				negativeSearchRequest = OrdersSearchRequest(vendorOrderNumber = "foo")
		)

		assertOrderSearch(
				positiveOrderNumber = orderCreateResult.id,
				positiveSearchRequest = OrdersSearchRequest(customer = orderCreateRequest.newOrder.billingPerson?.name),
				negativeSearchRequest = OrdersSearchRequest(customer = "foo")
		)

		assertOrderSearch(
				positiveOrderNumber = orderCreateResult.id,
				positiveSearchRequest = OrdersSearchRequest(paymentMethod = orderCreateRequest.newOrder.paymentMethod),
				negativeSearchRequest = OrdersSearchRequest(paymentMethod = "foo")
		)

		assertOrderSearch(
				positiveOrderNumber = orderCreateResult.id,
				positiveSearchRequest = OrdersSearchRequest(shippingMethod = orderCreateRequest.newOrder.shippingOption?.shippingMethodName),
				negativeSearchRequest = OrdersSearchRequest(shippingMethod = "foo")
		)

		assertOrderSearch(
				positiveOrderNumber = orderCreateResult.id,
				positiveSearchRequest = OrdersSearchRequest(paymentStatus = OrderPaymentStatus.AWAITING_PAYMENT),
				negativeSearchRequest = OrdersSearchRequest(paymentStatus = OrderPaymentStatus.PAID)
		)

		assertOrderSearch(
				positiveOrderNumber = orderCreateResult.id,
				positiveSearchRequest = OrdersSearchRequest(fulfillmentStatus = OrderFulfillmentStatus.AWAITING_PROCESSING),
				negativeSearchRequest = OrdersSearchRequest(fulfillmentStatus = OrderFulfillmentStatus.SHIPPED)
		)
	}

	@Test
	fun testSearchPaging() {
		// Create some orders
		for (i in 1..3) {
			val orderCreateRequest = OrderCreateRequest(newOrder = UpdatedOrder())
			val orderCreateResult = apiClient.createOrder(orderCreateRequest)
			assertTrue(orderCreateResult.id > 0)
		}

		// Waiting till order became available for searching and trying to request only one page 
		var tries = 0
		val ordersSearchRequest = OrdersSearchRequest(offset = 2, limit = 2)
		var ordersSearchResult: OrdersSearchResult
		do {
			ordersSearchResult = apiClient.searchOrders(ordersSearchRequest)
			if (ordersSearchResult.total == 3) break
			tries++
			Thread.sleep(1000)
		} while (tries <= 10)
		assertEquals(1, ordersSearchResult.count)
		assertEquals(3, ordersSearchResult.total)
	}

	@Test
	fun testGetOrderInvoice() {
		val orderCreateRequest = OrderCreateRequest(
				newOrder = UpdatedOrder(
						email = randomEmail()
				)
		)
		val orderCreateResult = apiClient.createOrder(orderCreateRequest)
		assertTrue(orderCreateResult.id > 0)

		val orderInvoiceRequest = OrderInvoiceRequest(orderNumber = orderCreateResult.id)
		val invoiceHtml = apiClient.getOrderInvoice(orderInvoiceRequest)
		assertTrue(invoiceHtml.contains("<!-- Invoice body markup -->"))
		assertTrue(invoiceHtml.contains("<!-- Order number -->"))
		assertTrue(invoiceHtml.contains("#${orderCreateResult.id}</span>"))
	}

	private fun removeAllOrders() {
		apiClient
				.searchOrdersAsSequence(OrdersSearchRequest())
				.map(FetchedOrder::orderNumber)
				.filterNotNull()
				.forEach { orderNumber ->
					apiClient.deleteOrder(OrderDeleteRequest(orderNumber))
				}
	}

	private fun assertOrderSearch(positiveOrderNumber: Int, positiveSearchRequest: OrdersSearchRequest, negativeSearchRequest: OrdersSearchRequest) {
		val positiveOrdersSearchResult = apiClient.searchOrders(positiveSearchRequest)
		assertEquals(1, positiveOrdersSearchResult.total)
		assertEquals(positiveOrderNumber, positiveOrdersSearchResult.items[0].orderNumber)

		val negativeOrdersSearchResult = apiClient.searchOrders(negativeSearchRequest)
		assertEquals(0, negativeOrdersSearchResult.total)
	}

	private fun getTestPngFilePath(): Path = Paths.get(javaClass.getResource("/logo-ecwid-small.png").toURI())

}

private fun assertFileOption(orderItemProductFile: FetchedOrder.OrderItemProductFile, fileName: String) {
	assertAll(
			{ assertTrue(orderItemProductFile.id ?: 0 > 0) },
			{ assertEquals(fileName, orderItemProductFile.name) },
			{ assertTrue(orderItemProductFile.size ?: 0 > 0) },
			{ assertTrue(orderItemProductFile.url?.contains(fileName) ?: false) }
	)
}

private fun generateTestOrder(): UpdatedOrder {
	return UpdatedOrder(
			email = randomEmail(),
			ipAddress = randomIp(),
			hidden = false, // true allowed only for paymentStatus INCOMPLETE
			createDate = randomDate(),

			refererUrl = randomUrl(),
			globalReferer = randomUrl(),
			affiliateId = randomAlphanumeric(16),
			additionalInfo = OrderedStringToStringMap(linkedMapOf(
					"additionalParam1" to randomAlphanumeric(16),
					"additionalParam2" to randomAlphanumeric(16)
			)),

			orderComments = "Order comment " + randomAlphanumeric(16),
			privateAdminNotes = "Private admin note " + randomAlphanumeric(16),

			fulfillmentStatus = randomEnumValue<OrderFulfillmentStatus>(),
			trackingNumber = randomAlphanumeric(16),
			pickupTime = randomDate(),

			paymentStatus = randomEnumValue(OrderPaymentStatus.INCOMPLETE),
			paymentMethod = "Payment method " + randomAlphanumeric(8),
			paymentModule = "Payment module " + randomAlphanumeric(8),
			paymentParams = OrderedStringToStringMap(linkedMapOf(
					"paymentParam1" to randomAlphanumeric(16),
					"paymentParam2" to randomAlphanumeric(16)
			)),
			paymentMessage = randomAlphanumeric(16),
			creditCardStatus = UpdatedOrder.CreditCardStatus(
					avsMessage = randomAlphanumeric(8),
					cvvMessage = randomAlphanumeric(8)
			),
			externalTransactionId = randomAlphanumeric(16),

			customerGroup = "Group " + randomAlphanumeric(8),

			total = randomPrice(),
			subtotal = randomPrice(),

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
			discountCoupon = UpdatedOrder.DiscountCouponInfo(
					name = "Discount coupon " + randomAlphanumeric(8),
					code = randomAlphanumeric(16),
					discountType = randomEnumValue<DiscountCouponType>(),
					status = randomEnumValue<DiscountCouponStatus>(),
					discount = randomPrice(),
					launchDate = randomDate(),
					expirationDate = randomDate(),
					totalLimit = randomPrice(),
					usesLimit = randomEnumValue<DiscountCouponUsesLimit>(),
					repeatCustomerOnly = randomBoolean()
			),

			items = listOf(
					generateTestOrderItem(),
					generateTestOrderItem()
			),

			billingPerson = generatePersonInfo(),
			shippingPerson = generatePersonInfo(),

			shippingOption = UpdatedOrder.ShippingOption(
					shippingCarrierName = "Carrier " + randomAlphanumeric(8),
					shippingMethodName = "Method " + randomAlphanumeric(8),
					shippingRate = randomPrice(),
					estimatedTransitTime = "Estimates " + randomAlphanumeric(8),
					isPickup = randomBoolean(),
					pickupInstruction = "Instruction " + randomAlphanumeric(64)
			),
			handlingFee = UpdatedOrder.HandlingFee(
					name = "Name " + randomAlphanumeric(8),
					value = randomPrice(),
					description = "Description " + randomAlphanumeric(64)
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
		categoryId = randomId(),

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
		quantityInStock= randomByte(),
		weight = randomWeight(), // TODO readonly?

		isShippingRequired = true, // true for weight field
		trackQuantity = randomBoolean(),
		fixedShippingRateOnly = randomBoolean(),
		digital = randomBoolean(),
		couponApplied = randomBoolean(),

		selectedOptions = listOf(
				generateChoiceSelectedOption(),
				generateChoicesSelectedOption(),
				generateTextSelectedOption(),
				generateDateSelectedOption(),
				generateFilesSelectedOption()
		),
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

private fun generateFileName(prefix: String, extension: String) = "$prefix-${Random.nextInt()}.$extension"

private fun FetchedOrder.toUpdated(): UpdatedOrder {
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

private fun FetchedOrder.CreditCardStatus.toUpdated(): UpdatedOrder.CreditCardStatus {
	return UpdatedOrder.CreditCardStatus(
			avsMessage = avsMessage,
			cvvMessage = cvvMessage
	)
}

private fun FetchedOrder.DiscountInfo.toUpdated(): UpdatedOrder.DiscountInfo {
	return UpdatedOrder.DiscountInfo(
			value = value,
			type = type,
			base = base,
			orderTotal = orderTotal,
			description = description
	)
}

private fun FetchedOrder.DiscountCouponInfo.toUpdated(): UpdatedOrder.DiscountCouponInfo {
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

private fun FetchedOrder.OrderItem.toUpdated(): UpdatedOrder.OrderItem {
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

private fun FetchedOrder.OrderItemSelectedOption.toUpdated(): UpdatedOrder.OrderItemSelectedOption {
	return UpdatedOrder.OrderItemSelectedOption(
			name = name,
			type = type,
			value = if (type == ProductOptionType.CHOICES) null else value,
			valuesArray = valuesArray?.let { ArrayList(it) },
			selections = selections?.map(FetchedOrder.OrderItemSelectionInfo::toUpdated)
	)
}

private fun FetchedOrder.OrderItemSelectionInfo.toUpdated(): UpdatedOrder.OrderItemSelectionInfo {
	return UpdatedOrder.OrderItemSelectionInfo(
			selectionTitle = selectionTitle,
			selectionModifier = selectionModifier,
			selectionModifierType = selectionModifierType
	)
}

private fun FetchedOrder.OrderItemTax.toUpdated(): UpdatedOrder.OrderItemTax {
	return UpdatedOrder.OrderItemTax(
			name = name,
			value = value,
			total = total,
			taxOnDiscountedSubtotal = taxOnDiscountedSubtotal,
			taxOnShipping = taxOnShipping,
			includeInPrice = includeInPrice
	)
}

private fun FetchedOrder.ProductDimensions.toUpdated(): UpdatedOrder.ProductDimensions {
	return UpdatedOrder.ProductDimensions(
			length = length,
			width = width,
			height = height
	)
}

private fun FetchedOrder.OrderItemDiscounts.toUpdated(): UpdatedOrder.OrderItemDiscounts {
	return UpdatedOrder.OrderItemDiscounts(
			discountInfo = discountInfo?.toUpdated(),
			total = total
	)
}

private fun FetchedOrder.PersonInfo.toUpdated(): UpdatedOrder.PersonInfo {
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

private fun FetchedOrder.ShippingOption.toUpdated(): UpdatedOrder.ShippingOption {
	return UpdatedOrder.ShippingOption(
			shippingCarrierName = shippingCarrierName,
			shippingMethodName = shippingMethodName,
			shippingRate = shippingRate,
			estimatedTransitTime = estimatedTransitTime,
			isPickup = isPickup,
			pickupInstruction = pickupInstruction
	)
}

private fun FetchedOrder.HandlingFee.toUpdated(): UpdatedOrder.HandlingFee {
	return UpdatedOrder.HandlingFee(
			name = name,
			value = value,
			description = description
	)
}
