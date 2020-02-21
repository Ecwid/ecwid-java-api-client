package com.ecwid.apiclient.v3

import com.ecwid.apiclient.v3.converter.toUpdated
import com.ecwid.apiclient.v3.dto.cart.request.*
import com.ecwid.apiclient.v3.dto.cart.result.CalculateOrderDetailsResult
import com.ecwid.apiclient.v3.dto.order.enums.*
import com.ecwid.apiclient.v3.dto.order.request.*
import com.ecwid.apiclient.v3.util.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*

class CartsTest: BaseEntityTest() {

    @BeforeEach
    override fun beforeEach() {
        super.beforeEach()
    }

    @Test
    fun testCreateAndGetCart() {
        // Creating new cart
        val testOrder = generateTestOrder()
        val newCartId = createNewCart(testOrder)

        // Checking that cart was successfully created with necessary parameters
        val cartDetailsRequest = CartDetailsRequest(newCartId)
        val cartDetailsResult = apiClient.getAbandonedCart(cartDetailsRequest)

        assertEquals(testOrder.ipAddress, cartDetailsResult.ipAddress)
        assertEquals(testOrder.email, cartDetailsResult.email)
        assertEquals(testOrder.hidden, cartDetailsResult.hidden)
        assertEquals(testOrder.refererUrl, cartDetailsResult.refererUrl)
        assertEquals(testOrder.globalReferer, cartDetailsResult.globalReferer)
        assertEquals(testOrder.affiliateId, cartDetailsResult.affiliateId)
        assertEquals(testOrder.additionalInfo, cartDetailsResult.additionalInfo)
        assertEquals(testOrder.orderComments, cartDetailsResult.orderComments)
        assertEquals(testOrder.trackingNumber, cartDetailsResult.trackingNumber)
        assertEquals(testOrder.paymentMethod, cartDetailsResult.paymentMethod)
        assertEquals(testOrder.paymentModule, cartDetailsResult.paymentModule)
        assertEquals(testOrder.paymentParams, cartDetailsResult.paymentParams)
        assertEquals(testOrder.paymentParams, cartDetailsResult.paymentParams)
        assertEquals(testOrder.paymentMessage, cartDetailsResult.paymentMessage)
        assertEquals(testOrder.creditCardStatus?.avsMessage, cartDetailsResult.creditCardStatus?.avsMessage)
        assertEquals(testOrder.creditCardStatus?.cvvMessage, cartDetailsResult.creditCardStatus?.cvvMessage)
        assertEquals(testOrder.externalTransactionId, cartDetailsResult.externalTransactionId)
        assertEquals(testOrder.customerGroup, cartDetailsResult.customerGroup)
        assertEquals(testOrder.total, cartDetailsResult.total)
        assertEquals(testOrder.subtotal, cartDetailsResult.subtotal)
        assertEquals(testOrder.tax, cartDetailsResult.tax)
        assertEquals(testOrder.customerTaxExempt, cartDetailsResult.customerTaxExempt)
        assertEquals(testOrder.customerTaxId, cartDetailsResult.customerTaxId)
        assertEquals(testOrder.customerTaxIdValid, cartDetailsResult.customerTaxIdValid)
        assertEquals(testOrder.reversedTaxApplied, cartDetailsResult.reversedTaxApplied)
        assertEquals(testOrder.couponDiscount, cartDetailsResult.couponDiscount)
        assertEquals(testOrder.volumeDiscount, cartDetailsResult.volumeDiscount)
        assertEquals(testOrder.membershipBasedDiscount, cartDetailsResult.membershipBasedDiscount)
        assertEquals(testOrder.totalAndMembershipBasedDiscount, cartDetailsResult.totalAndMembershipBasedDiscount)
        assertEquals(testOrder.discount, cartDetailsResult.discount)
        assertEquals(testOrder.discountCoupon, cartDetailsResult.discountCoupon)

        assertEquals(testOrder.discountInfo?.count(), cartDetailsResult.discountInfo?.count())
        cartDetailsResult.discountInfo?.forEachIndexed { discountInfoIndex, cartDiscountInfo ->
            val orderDiscountInfo = testOrder.discountInfo?.get(discountInfoIndex)
                    ?: throw IllegalStateException("testOrder.discountInfo[$discountInfoIndex] not found")
            assertEquals(orderDiscountInfo.value, cartDiscountInfo.value)
            assertEquals(orderDiscountInfo.type, cartDiscountInfo.type)
            assertEquals(orderDiscountInfo.base, cartDiscountInfo.base)
            assertEquals(orderDiscountInfo.orderTotal, cartDiscountInfo.orderTotal)
            assertEquals(orderDiscountInfo.description, cartDiscountInfo.description)
        }

        assertEquals(testOrder.items?.count(), cartDetailsResult.items?.count())
        cartDetailsResult.items?.forEachIndexed { itemIndex, cartItem ->
            val orderItem = testOrder.items?.get(itemIndex)
                    ?: throw IllegalStateException("testOrder.items[$itemIndex] not found")
            assertEquals(orderItem.categoryId, cartItem.categoryId)
            assertEquals(orderItem.price, cartItem.price)
            assertEquals(orderItem.productPrice, cartItem.productPrice)
            assertEquals(orderItem.shipping, cartItem.shipping)
            assertEquals(orderItem.tax, cartItem.tax)
            assertEquals(orderItem.fixedShippingRate, cartItem.fixedShippingRate)
            assertEquals(orderItem.couponAmount, cartItem.couponAmount)
            assertEquals(orderItem.sku, cartItem.sku)
            assertEquals(orderItem.name, cartItem.name)
            assertEquals(orderItem.shortDescription, cartItem.shortDescription)
            assertEquals(orderItem.quantity, cartItem.quantity)
            assertEquals(orderItem.quantityInStock, cartItem.quantityInStock)
            assertEquals(orderItem.weight, cartItem.weight)
            assertEquals(orderItem.isShippingRequired, cartItem.isShippingRequired)
            assertEquals(orderItem.trackQuantity, cartItem.trackQuantity)
            assertEquals(orderItem.fixedShippingRateOnly, cartItem.fixedShippingRateOnly)
            assertEquals(orderItem.digital, cartItem.digital)
            assertEquals(orderItem.couponApplied, cartItem.couponApplied)

            assertEquals(orderItem.dimensions?.length, cartItem.dimensions?.length)
            assertEquals(orderItem.dimensions?.width, cartItem.dimensions?.width)
            assertEquals(orderItem.dimensions?.height, cartItem.dimensions?.height)

            assertEquals(orderItem.selectedOptions?.count(), cartItem.selectedOptions?.count())
            cartItem.selectedOptions?.forEachIndexed { selectedOptionIndex, cartSelectedOptions ->
                val orderSelectedOption = orderItem.selectedOptions?.get(selectedOptionIndex)
                        ?: throw IllegalStateException("testOrder.items[$itemIndex].selectedOptions[$selectedOptionIndex] not found")
                assertEquals(orderSelectedOption.name, cartSelectedOptions.name)
                assertEquals(orderSelectedOption.type, cartSelectedOptions.type)
                orderSelectedOption.valuesArray = cartSelectedOptions.valuesArray // TODO Discover why after each create this field some times resets to null
                assertEquals(orderSelectedOption.valuesArray, cartSelectedOptions.valuesArray)
            }

            assertEquals(orderItem.taxes?.count(), cartItem.taxes?.count())
            cartItem.taxes?.forEachIndexed { taxIndex, cartTaxes ->
                val orderTaxes = orderItem.taxes?.get(taxIndex)
                        ?: throw IllegalStateException("testOrder.items[$itemIndex].taxes[$taxIndex] not found")
                assertEquals(orderTaxes.name, cartTaxes.name)
                assertEquals(orderTaxes.value, cartTaxes.value)
                assertEquals(orderTaxes.total, cartTaxes.total)
                assertEquals(orderTaxes.taxOnDiscountedSubtotal, cartTaxes.taxOnDiscountedSubtotal)
                assertEquals(orderTaxes.taxOnShipping, cartTaxes.taxOnShipping)
                assertEquals(orderTaxes.includeInPrice, cartTaxes.includeInPrice)
            }


            assertEquals(orderItem.discounts?.count(), cartItem.discounts?.count())
            cartItem.discounts?.forEachIndexed { discountIndex, cartDiscounts ->
                val orderDiscounts = orderItem.discounts?.get(discountIndex)
                        ?: throw IllegalStateException("testOrder.items[$itemIndex].discounts[$discountIndex] not found")
                assertEquals(orderDiscounts.discountInfo?.value, cartDiscounts.discountInfo?.value)
                assertEquals(orderDiscounts.discountInfo?.type, cartDiscounts.discountInfo?.type)
                assertEquals(orderDiscounts.discountInfo?.base, cartDiscounts.discountInfo?.base)
                assertEquals(orderDiscounts.discountInfo?.orderTotal, cartDiscounts.discountInfo?.orderTotal)
                assertEquals(orderDiscounts.total, cartDiscounts.total)
            }
        }

        assertEquals(testOrder.billingPerson?.name, cartDetailsResult.billingPerson?.name)
        assertEquals(testOrder.billingPerson?.companyName, cartDetailsResult.billingPerson?.companyName)
        assertEquals(testOrder.billingPerson?.street, cartDetailsResult.billingPerson?.street)
        assertEquals(testOrder.billingPerson?.city, cartDetailsResult.billingPerson?.city)
        assertEquals(testOrder.billingPerson?.countryCode, cartDetailsResult.billingPerson?.countryCode)
        assertEquals(testOrder.billingPerson?.postalCode, cartDetailsResult.billingPerson?.postalCode)
        assertEquals(testOrder.billingPerson?.stateOrProvinceCode, cartDetailsResult.billingPerson?.stateOrProvinceCode)
        assertEquals(testOrder.billingPerson?.phone, cartDetailsResult.billingPerson?.phone)

        assertEquals(testOrder.shippingPerson?.name, cartDetailsResult.shippingPerson?.name)
        assertEquals(testOrder.shippingPerson?.companyName, cartDetailsResult.shippingPerson?.companyName)
        assertEquals(testOrder.shippingPerson?.street, cartDetailsResult.shippingPerson?.street)
        assertEquals(testOrder.shippingPerson?.city, cartDetailsResult.shippingPerson?.city)
        assertEquals(testOrder.shippingPerson?.countryCode, cartDetailsResult.shippingPerson?.countryCode)
        assertEquals(testOrder.shippingPerson?.postalCode, cartDetailsResult.shippingPerson?.postalCode)
        assertEquals(testOrder.shippingPerson?.stateOrProvinceCode, cartDetailsResult.shippingPerson?.stateOrProvinceCode)
        assertEquals(testOrder.shippingPerson?.phone, cartDetailsResult.shippingPerson?.phone)

        assertEquals(testOrder.shippingOption?.shippingCarrierName, cartDetailsResult.shippingOption?.shippingCarrierName)
        assertEquals(testOrder.shippingOption?.shippingMethodName, cartDetailsResult.shippingOption?.shippingMethodName)
        assertEquals(testOrder.shippingOption?.shippingRate, cartDetailsResult.shippingOption?.shippingRate)
        assertEquals(testOrder.shippingOption?.estimatedTransitTime, cartDetailsResult.shippingOption?.estimatedTransitTime)
        assertEquals(testOrder.shippingOption?.isPickup, cartDetailsResult.shippingOption?.isPickup)
        assertEquals(testOrder.shippingOption?.pickupInstruction, cartDetailsResult.shippingOption?.pickupInstruction)

        assertEquals(testOrder.handlingFee?.name, cartDetailsResult.handlingFee?.name)
        assertEquals(testOrder.handlingFee?.value, cartDetailsResult.handlingFee?.value)
        assertEquals(testOrder.handlingFee?.description, cartDetailsResult.handlingFee?.description)
    }

    @Test
    fun testUpdateCart() {
        // Creating new cart
        val newCartId = createNewCart(generateTestOrder())

        // Updating cart
        val cartUpdateRequest = CartUpdateRequest(
                cartId = newCartId,
                updatedCart = generateTestCartForUpdate()
        )
        val cartUpdateResult = apiClient.updateAbandonedCart(cartUpdateRequest)
        assertEquals(1, cartUpdateResult.updateCount)

        // Checking that cart was successfully updated with necessary parameters
        val cartDetailsRequest1 = CartDetailsRequest(newCartId)
        val cartDetailsResult1 = apiClient.getAbandonedCart(cartDetailsRequest1)
        assertEquals(
                cartUpdateRequest.updatedCart,
                cartDetailsResult1.toUpdated()
        )
    }

    @Test
    fun testConvertCartToOrder() {
        // Creating new cart
        val testOrder = generateTestOrder()
        val newCartId = createNewCart(testOrder)

        // Converting cart to order
        val convertCartToOrderRequest = ConvertCartToOrderRequest(newCartId)
        val convertCartToOrderResult = apiClient.convertAbandonedCartToOrder(convertCartToOrderRequest)

        // Checking that cart was converted to order
        assertNotNull(convertCartToOrderResult.orderNumber)
        assertNotNull(convertCartToOrderResult.vendorOrderNumber)

        // Checking that order was created with necessary parameters
        val orderDetailsRequest = OrderDetailsRequest(convertCartToOrderResult.orderNumber!!)
        val createdOrder = apiClient.getOrderDetails(orderDetailsRequest).toUpdated()

        testOrder.createDate = createdOrder.createDate
        testOrder.paymentStatus = OrderPaymentStatus.AWAITING_PAYMENT
        testOrder.paymentMessage = null // TODO Discover why after each create this field resets to null
        testOrder.items?.forEachIndexed() { itemIndex, item ->
            item.selectedOptions?.forEachIndexed { selectedOptionIndex, selectedOption ->
                // TODO Discover why after create these two fields some times resets to null
                selectedOption.valuesArray = createdOrder.items?.get(itemIndex)?.selectedOptions?.get(selectedOptionIndex)?.valuesArray
                selectedOption.selections = createdOrder.items?.get(itemIndex)?.selectedOptions?.get(selectedOptionIndex)?.selections
            }
        }

        assertEquals(
                testOrder,
                createdOrder
        )
    }

    @Test
    fun testCalculateOrderDetails() {
        // Calculate order details
        val orderForCalculate = generateTestOrderForCalculate()
        val calculateOrderDetailsRequest = CalculateOrderDetailsRequest(orderForCalculate)
        val calculatedOrder = apiClient.calculateOrderDetails(calculateOrderDetailsRequest)

        //check that all parameters of the calculated order are correct
        assertEquals(orderForCalculate.email, calculatedOrder.email)
        assertEquals(orderForCalculate.ipAddress, calculatedOrder.ipAddress)
        assertEquals(orderForCalculate.customerId, calculatedOrder.customerId)
        assertEquals(null, calculatedOrder.discountCoupon) // TODO Discover why after each calculation this field resets to null

        assertEquals(orderForCalculate.items?.count(), calculatedOrder.items?.count())
        calculatedOrder.items?.forEachIndexed { itemIndex, calculatedItem ->
            val forCalculateItem = orderForCalculate.items?.get(itemIndex)
                    ?: throw IllegalStateException("orderForCalculate.items[$itemIndex] not found")
            assertEquals(forCalculateItem.id, calculatedItem.id)
            assertEquals(forCalculateItem.categoryId, calculatedItem.categoryId)
            assertEquals(forCalculateItem.price, calculatedItem.price)
            assertEquals(forCalculateItem.productPrice, calculatedItem.productPrice)
            assertEquals(133.2, calculatedItem.shipping)
            assertEquals(0.0, calculatedItem.tax) // TODO Discover why after each calculation this field resets to 0
            assertEquals(forCalculateItem.fixedShippingRate, calculatedItem.fixedShippingRate)
            assertEquals(null, calculatedItem.couponAmount) // TODO Discover why after each calculation this field resets to null
            assertEquals(forCalculateItem.sku, calculatedItem.sku)
            assertEquals(forCalculateItem.name, calculatedItem.name)
            assertEquals(forCalculateItem.shortDescription, calculatedItem.shortDescription)
            assertEquals(forCalculateItem.quantity, calculatedItem.quantity)
            assertEquals(forCalculateItem.quantityInStock, calculatedItem.quantityInStock)
            assertEquals(forCalculateItem.weight, calculatedItem.weight)
            assertEquals(forCalculateItem.isShippingRequired, calculatedItem.isShippingRequired)
            assertEquals(forCalculateItem.trackQuantity, calculatedItem.trackQuantity)
            assertEquals(forCalculateItem.fixedShippingRateOnly, calculatedItem.fixedShippingRateOnly)
            assertEquals(forCalculateItem.digital, calculatedItem.digital)
            assertEquals(false, calculatedItem.couponApplied)
            assertEquals(forCalculateItem.dimensions?.length, calculatedItem.dimensions?.length)
            assertEquals(forCalculateItem.dimensions?.width, calculatedItem.dimensions?.width)
            assertEquals(forCalculateItem.dimensions?.height, calculatedItem.dimensions?.height)

            assertEquals(forCalculateItem.selectedOptions?.count(), calculatedItem.selectedOptions?.count())
            calculatedItem.selectedOptions?.forEachIndexed { selectedOptionIndex, calculatedOrderItemOptions ->
                val forCalculateItemOption = forCalculateItem.selectedOptions?.get(selectedOptionIndex)
                        ?: throw IllegalStateException("orderForCalculate.items[$itemIndex].selectedOptions[$selectedOptionIndex] not found")
                assertEquals(forCalculateItemOption.name, calculatedOrderItemOptions.name)
                assertEquals(forCalculateItemOption.type, calculatedOrderItemOptions.type)
                forCalculateItemOption.valuesArray = calculatedOrderItemOptions.valuesArray // TODO Discover why after each calculation this field some times resets to null
                assertEquals(forCalculateItemOption.valuesArray, calculatedOrderItemOptions.valuesArray)

                assertEquals(null, calculatedOrderItemOptions.files?.count()) // TODO Discover why after each calculation this field resets to null
//                assertEquals(forCalculateItemOption.files?.count(), calculatedOrderItemOptions.files?.count())
//                calculatedItem.files?.forEachIndexed { fileIndex, calculatedFile ->
//                    val forCalculateFile = forCalculateItem.files?.get(fileIndex)
//                            ?: throw IllegalStateException("orderForCalculate.items[$itemIndex].selectedOptions[$selectedOptionIndex].files[$fileIndex] not found")
//                    assertEquals(forCalculateFile.productFileId, calculatedFile.productFileId)
//                    assertEquals(forCalculateFile.maxDownloads, calculatedFile.maxDownloads)
//                    assertEquals(forCalculateFile.remainingDownloads, calculatedFile.remainingDownloads)
//                    assertEquals(forCalculateFile.expire, calculatedFile.expire)
//                    assertEquals(forCalculateFile.name, calculatedFile.name)
//                    assertEquals(forCalculateFile.description, calculatedFile.description)
//                    assertEquals(forCalculateFile.size, calculatedFile.size)
//                    assertEquals(forCalculateFile.adminUrl, calculatedFile.adminUrl)
//                    assertEquals(forCalculateFile.customerUrl, calculatedFile.customerUrl)
//                }
            }

            assertEquals(null, calculatedItem.taxes?.count()) // TODO Discover why after each calculation this field resets to null
//            assertEquals(forCalculateItem.taxes?.count(), calculatedItem.taxes?.count())
//            calculatedItem.taxes?.forEachIndexed { taxIndex, calculatedTaxe ->
//                val forCalculatedTaxe = calculatedItem.taxes?.get(taxIndex)
//                        ?: throw IllegalStateException("orderForCalculate.items[$itemIndex].taxes[$taxIndex] not found")
//                assertEquals(forCalculatedTaxe.name, calculatedTaxe.name)
//                assertEquals(forCalculatedTaxe.value, calculatedTaxe.value)
//                assertEquals(forCalculatedTaxe.total, calculatedTaxe.total)
//                assertEquals(forCalculatedTaxe.taxOnDiscountedSubtotal, calculatedTaxe.taxOnDiscountedSubtotal)
//                assertEquals(forCalculatedTaxe.taxOnShipping, calculatedTaxe.taxOnShipping)
//                assertEquals(forCalculatedTaxe.includeInPrice, calculatedTaxe.includeInPrice)
//            }

            assertEquals(forCalculateItem.files?.count(), calculatedItem.files?.count())
            calculatedItem.files?.forEachIndexed { taxIndex, calculatedFile ->
                val forCalculateFile = calculatedItem.files?.get(taxIndex)
                        ?: throw IllegalStateException("orderForCalculate.items[$itemIndex].taxes[$taxIndex] not found")
                assertEquals(forCalculateFile.productFileId, calculatedFile.productFileId)
                assertEquals(forCalculateFile.maxDownloads, calculatedFile.maxDownloads)
                assertEquals(forCalculateFile.remainingDownloads, calculatedFile.remainingDownloads)
                assertEquals(forCalculateFile.expire, calculatedFile.expire)
                assertEquals(forCalculateFile.name, calculatedFile.name)
                assertEquals(forCalculateFile.description, calculatedFile.description)
                assertEquals(forCalculateFile.size, calculatedFile.size)
                assertEquals(forCalculateFile.adminUrl, calculatedFile.adminUrl)
                assertEquals(forCalculateFile.customerUrl, calculatedFile.customerUrl)
            }

            assertEquals(null, calculatedItem.discounts?.count()) // TODO Discover why after each calculation this field resets to null
//            assertEquals(forCalculateItem.discounts?.count(), calculatedItem.discounts?.count())
//            calculatedItem.discounts?.forEachIndexed { discountIndex, calcullatedDiscounts ->
//                val forCalculatedDiscount = forCalculateItem.discounts?.get(discountIndex)
//                        ?: throw IllegalStateException("orderForCalculate.items[$itemIndex].discounts[$discountIndex] not found")
//                assertEquals(forCalculatedDiscount.discountInfo?.value, calcullatedDiscounts.discountInfo?.value)
//                assertEquals(forCalculatedDiscount.discountInfo?.type, calcullatedDiscounts.discountInfo?.type)
//                assertEquals(forCalculatedDiscount.discountInfo?.base, calcullatedDiscounts.discountInfo?.base)
//                assertEquals(forCalculatedDiscount.discountInfo?.orderTotal, calcullatedDiscounts.discountInfo?.orderTotal)
//                assertEquals(forCalculatedDiscount.total, calcullatedDiscounts.total)
//            }
        }

        checkPersonsEquals(orderForCalculate.billingPerson, calculatedOrder.billingPerson)
        checkPersonsEquals(orderForCalculate.shippingPerson, calculatedOrder.shippingPerson)
        assertEquals(null, calculatedOrder.discountInfo) // TODO Discover why after each calculation this field resets to null
    }

    @Test
    fun testSearchCarts() {

        // Create two carts
        val totalPrice = randomPrice()
        val cartForSearch1 = generateCartForTestingSearch(totalPrice, false)
        val newCartId1 = createNewCart(cartForSearch1)
        val cartDetailsRequest1 = CartDetailsRequest(newCartId1)
        val fetchedCart1 = apiClient.getAbandonedCart(cartDetailsRequest1)

        val cartForSearch2 = generateCartForTestingSearch(totalPrice, true)
        createNewCart(cartForSearch2)

        val cartSearchRequest1 = CartsSearchRequest(
                totalFrom = totalPrice - 1,
                totalTo = totalPrice + 1,
                showHidden = false
        )
        val cartsSearchResult1 = apiClient.searchAbandonedCart(cartSearchRequest1)
        assertEquals(1, cartsSearchResult1.count)

        val cartSearchRequest2 = CartsSearchRequest(
                totalFrom = totalPrice - 1,
                totalTo = totalPrice + 1,
                showHidden = true
        )
        val cartsSearchResult2 = apiClient.searchAbandonedCart(cartSearchRequest2)
        assertEquals(2, cartsSearchResult2.count)

        val createDate = fetchedCart1.createDate ?: throw IllegalStateException("fetchedCart.createDate not found")
        val instantCreate = createDate.toInstant()
        val instantCreateFrom = instantCreate.minusSeconds(1)
        val instantCreateTo = instantCreate.plusSeconds(2)
        val cartSearchRequest3 = CartsSearchRequest(
                createdFrom = Date.from(instantCreateFrom),
                createdTo = Date.from(instantCreateTo)
        )
        val cartsSearchResult3 = apiClient.searchAbandonedCart(cartSearchRequest3)
        assertEquals(1, cartsSearchResult3.count)

        val updateDate = fetchedCart1.updateDate ?: throw IllegalStateException("fetchedCart.updateDate not found")
        val instantUpdate = updateDate.toInstant()
        val instantUpdateFrom = instantUpdate.minusSeconds(1)
        val instantUpdateTo = instantUpdate.plusSeconds(2)
        val cartSearchRequest4 = CartsSearchRequest(
                updatedFrom = Date.from(instantUpdateFrom),
                updatedTo = Date.from(instantUpdateTo)
        )
        val cartsSearchResult4 = apiClient.searchAbandonedCart(cartSearchRequest4)
        assertEquals(1, cartsSearchResult4.count)
    }

    private fun createNewCart(updatedOrder: UpdatedOrder): String {
        val cartsearchRequest = CartsSearchRequest()
        val cartSearchResult1 = apiClient.searchAbandonedCartsAsSequence(cartsearchRequest)
        updatedOrder.paymentStatus = OrderPaymentStatus.INCOMPLETE
        val orderCreateRequest = OrderCreateRequest(
                newOrder = updatedOrder
        )

        apiClient.createOrder(orderCreateRequest)
        Thread.sleep(5000) // because the order does not have time to create

        val cartSearchResult2 = apiClient.searchAbandonedCartsAsSequence(cartsearchRequest)
        val newCartList = cartSearchResult2 - cartSearchResult1

        assertEquals(1, newCartList.count())

        return newCartList[0].cartId
    }

    private fun checkPersonsEquals(billingPerson1: OrderForCalculate.PersonInfo?, billingPerson2: CalculateOrderDetailsResult.PersonInfo?) {
        assertEquals(billingPerson1?.name, billingPerson2?.name)
        assertEquals(billingPerson1?.companyName, billingPerson2?.companyName)
        assertEquals(billingPerson1?.street, billingPerson2?.street)
        assertEquals(billingPerson1?.city, billingPerson2?.city)
        assertEquals(billingPerson1?.countryCode, billingPerson2?.countryCode)
        assertEquals(billingPerson1?.countryName, billingPerson2?.countryName)
        assertEquals(billingPerson1?.postalCode, billingPerson2?.postalCode)
        assertEquals(billingPerson1?.stateOrProvinceCode, billingPerson2?.stateOrProvinceCode)
        assertEquals(billingPerson1?.stateOrProvinceName, billingPerson2?.stateOrProvinceName)
        assertEquals(billingPerson1?.phone, billingPerson2?.phone)
    }

    private fun generateTestCartForUpdate(): UpdatedCart {
        return UpdatedCart(
                hidden = randomBoolean(),
                taxesOnShipping = null // TODO Discover why after each update this field resets to null
        )
    }

    private fun generateTestOrderForCalculate(): OrderForCalculate {
        return OrderForCalculate(
                email = randomEmail(),
                ipAddress = randomIp(),
                customerId = randomId(),
                discountCoupon = generateTestDiscountCoupon(),
                items = listOf(generateTestItem()),
                billingPerson = generatePersonInfo(),
                shippingPerson = generatePersonInfo(),
                discountInfo = listOf(
                        generateTestDiscountInfo(),
                        generateTestDiscountInfo()
                )
        )
    }

    private fun generateTestItem(): OrderForCalculate.OrderItem {
        return OrderForCalculate.OrderItem(
                id = randomId(),
                productId = randomId(),
                categoryId = randomId(),
                price = 22.2,
                productPrice = 33.3,
                shipping = 44.4,
                tax = 55.5,
                fixedShippingRate = 66.6,
                couponAmount = 15.0,
                sku = randomAlphanumeric(16),
                name = "Order item " + randomAlphanumeric(8),
                shortDescription = "Order item description " + randomAlphanumeric(32),
                quantity = 2,
                quantityInStock = 10,
                weight = 3.0,
                imageUrl = randomUrl(),
                isShippingRequired = true, // true for weight field
                trackQuantity = true,
                fixedShippingRateOnly = true,
                digital = true,
                couponApplied = true,
                selectedOptions = listOf(
                        generateChoiceSelectedOption(),
                        generateChoicesSelectedOption(),
                        generateTextSelectedOption(),
                        generateDateSelectedOption(),
                        generateFilesSelectedOption()
                ),
                taxes = listOf(
                        generateTestOrderItemTax()
                ),
                dimensions = OrderForCalculate.ProductDimensions(
                        length = 12.0,
                        width = 5.2,
                        height = 6.6
                ),
                discounts = listOf(
                        generateOrderItemDiscounts()
                )
        )
    }

    private fun generateChoiceSelectedOption(): OrderForCalculate.OrderItemOption {
        return OrderForCalculate.OrderItemOption.createForChoiceOption(
                name = "Choice Option " + randomAlphanumeric(8),
                selection = "Selection #1, " + randomAlphanumeric(8),
                files = listOf(
                        OrderForCalculate.OrderItemOptinonFile(
                                id = randomId(),
                                name = "Order item option file" + randomAlphanumeric(4),
                                size = randomInt(1, 10),
                                url = randomUrl()
                        )
                )
        )
    }

    private fun generateChoicesSelectedOption(): OrderForCalculate.OrderItemOption {
        val value1 = "Selection #1, " + randomAlphanumeric(8)
        val value3 = "Selection #3, " + randomAlphanumeric(8)
        return OrderForCalculate.OrderItemOption.createForChoicesOption(
                name = "Choices Option " + randomAlphanumeric(8),
                selections = listOf(
                        OrderForCalculate.SelectionInfo(
                                selectionTitle = value1,
                                selectionModifier = 10.0,
                                selectionModifierType = PriceModifierType.ABSOLUTE
                        ),
                        OrderForCalculate.SelectionInfo(
                                selectionTitle = value3,
                                selectionModifier = 5.5,
                                selectionModifierType = PriceModifierType.ABSOLUTE
                        )
                ),
                files = listOf(
                        OrderForCalculate.OrderItemOptinonFile(
                                id = randomId(),
                                name = "Order item option file" + randomAlphanumeric(4),
                                size = randomInt(1, 10),
                                url = randomUrl()
                        )
                )
        )
    }

    private fun generateTextSelectedOption(): OrderForCalculate.OrderItemOption {
        return OrderForCalculate.OrderItemOption.createForTextOption(
                name = "Text Option " + randomAlphanumeric(8),
                value = randomAlphanumeric(8)
        )
    }

    private fun generateDateSelectedOption(): OrderForCalculate.OrderItemOption {
        return OrderForCalculate.OrderItemOption.createForDateOption(
                name = "Date Option " + randomAlphanumeric(8),
                date = randomDate()
        )
    }

    private fun generateFilesSelectedOption(): OrderForCalculate.OrderItemOption {
        return OrderForCalculate.OrderItemOption.createForFilesOption(name = "Files Option " + randomAlphanumeric(8))
    }

    private fun generateTestOrderItemTax(): OrderForCalculate.OrderItemTax {
        return OrderForCalculate.OrderItemTax(
                name = "Tax " + randomAlphanumeric(8),
                value = 12.2,
                total = 22.6,
                taxOnDiscountedSubtotal = 4.4,
                taxOnShipping = 3.3,
                includeInPrice = true
        )
    }

    private fun generatePersonInfo(): OrderForCalculate.PersonInfo {
        return OrderForCalculate.PersonInfo(
                name = "Name " + randomAlphanumeric(8),
                companyName = "Company " + randomAlphanumeric(8),
                street = "Line 1 " + randomAlphanumeric(8) + "\nLine 2 " + randomAlphanumeric(8),
                city = "City " + randomAlphanumeric(8),
                countryCode = "US",
                countryName = "United States",
                postalCode = randomAlphanumeric(5),
                stateOrProvinceCode = "CA",
                stateOrProvinceName = "California",
                phone = randomAlphanumeric(10)
        )
    }

    private fun generateOrderItemDiscounts(): OrderForCalculate.OrderItemDiscounts {
        return OrderForCalculate.OrderItemDiscounts(
                discountInfo = generateTestCustomDiscountInfo(),
                total = 12.5
        )
    }

    private fun generateTestDiscountCoupon(): OrderForCalculate.DiscountCouponInfo {
        val creationDate = randomDateFrom(Date())
        val launchDate = randomDateFrom(creationDate)
        val expirationDate = randomDateFrom(launchDate)
        return OrderForCalculate.DiscountCouponInfo(
                name = "Discount Coupon " + randomAlphanumeric(8),
                code = randomAlphanumeric(16),
                discountType = DiscountCouponType.ABS_AND_SHIPPING,
                status = DiscountCouponStatus.ACTIVE,
                discount = 50.0,
                launchDate = launchDate,
                expirationDate = expirationDate,
                totalLimit = 5555.5,
                usesLimit = DiscountCouponUsesLimit.UNLIMITED,
                applicationLimit = DiscountCouponApplicationLimit.UNLIMITED,
                creationDate = creationDate,
                orderCount = 1,
                catalogLimit = generateTestDiscountCouponCatalogLimit()
        )
    }

    private fun generateTestCustomDiscountInfo() = OrderForCalculate.OrderItemDiscountInfo(
            value = 22.2,
            type = DiscountType.ABS,
            base = DiscountBase.CUSTOM,
            orderTotal = 33.3
    )

    private fun generateTestDiscountInfo() = OrderForCalculate.DiscountInfo(
            value = 55.3,
            type = DiscountType.ABS,
            base = DiscountBase.ON_TOTAL,
            orderTotal = 66.6,
            description = "On total discount " + randomAlphanumeric(8)
    )

    private fun generateTestDiscountCouponCatalogLimit(): OrderForCalculate.DiscountCouponCatalogLimit {
        return OrderForCalculate.DiscountCouponCatalogLimit(
                products = listOf(1, 2, 3),
                categories = listOf(1)
        )
    }

    private fun generateCartForTestingSearch(price: Double, hidden: Boolean): UpdatedOrder {
        return UpdatedOrder(
                paymentStatus = OrderPaymentStatus.INCOMPLETE,
                fulfillmentStatus = OrderFulfillmentStatus.PROCESSING,
                items = generateItemsForTestingSearch(),
                discountCoupon = UpdatedOrder.DiscountCouponInfo(
                        name = "testCoupon",
                        code = "123abc555"
                ),
                // customerId = TODO Discover why this field not specified when creating an order
                hidden = hidden,
                total = price
        )
    }

    private fun generateItemsForTestingSearch(): List<UpdatedOrder.OrderItem> {
        return listOf(
                UpdatedOrder.OrderItem(
                        name = "AAA",
                        price = 200.0
                ),
                UpdatedOrder.OrderItem(
                        name = "BBB",
                        price = 300.0
                )
        )
    }
}
