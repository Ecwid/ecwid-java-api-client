package com.ecwid.apiclient.v3

import com.ecwid.apiclient.v3.converter.toUpdated
import com.ecwid.apiclient.v3.dto.cart.request.*
import com.ecwid.apiclient.v3.dto.order.enums.*
import com.ecwid.apiclient.v3.dto.order.request.*
import com.ecwid.apiclient.v3.util.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

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

    private fun generateTestCartForUpdate(): UpdatedCart {
        return UpdatedCart(
                hidden = randomBoolean(),
                taxesOnShipping = null // TODO Discover why after each update this field resets to null
        )
    }
}
