package com.ecwid.apiclient.v3

import com.ecwid.apiclient.v3.dto.coupon.request.*
import com.ecwid.apiclient.v3.dto.order.enums.DiscountCouponApplicationLimit
import com.ecwid.apiclient.v3.dto.order.enums.DiscountCouponStatus
import com.ecwid.apiclient.v3.dto.order.enums.DiscountCouponType
import com.ecwid.apiclient.v3.dto.order.enums.DiscountCouponUsesLimit
import com.ecwid.apiclient.v3.exception.EcwidApiException
import com.ecwid.apiclient.v3.util.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*

class CouponsTest: BaseEntityTest() {

    // Since the methods for creating coupons in the api are not yet implemented,
    // tests will be run on three default coupons: MOXQ3YCWXRXA, J3N4JM3SIPCJ, O3Q4AP5FKXJ1

    @BeforeEach
    override fun beforeEach() {
        super.beforeEach()

        // We need to start from scratch each time
        removeAllCoupons()
    }

    @Test
    fun testCouponLifecycle() {

        // Creating new coupon
        val couponCreateRequest = CouponCreateRequest(
                newCoupon = generateTestCouponForCreate()
        )
        val couponCreateResult = apiClient.createCoupon(couponCreateRequest)
        assertTrue(couponCreateResult.id > 0)

        // Checking that coupon was successfully created with necessary parameters

        val couponDetailsRequest = CouponDetailsRequest(couponIdentifier = couponCreateRequest.newCoupon.code)
        val couponDetails = apiClient.getCouponDetails(couponDetailsRequest)
        assertEquals(couponCreateRequest.newCoupon.code, couponDetails.code)
        assertEquals(couponCreateRequest.newCoupon.name, couponDetails.name)
        assertEquals(couponCreateRequest.newCoupon.discountType?.name, couponDetails.discountType?.name)
        assertEquals(DiscountCouponStatus.ACTIVE, couponDetails.status)
        assertEquals(couponCreateRequest.newCoupon.launchDate.toString(), couponDetails.launchDate.toString())
        assertEquals(couponCreateRequest.newCoupon.expirationDate.toString(), couponDetails.expirationDate.toString())
        assertEquals(couponCreateRequest.newCoupon.usesLimit, couponDetails.usesLimit)
        assertEquals(0, couponDetails.orderCount)
        assertEquals(couponCreateRequest.newCoupon.catalogLimit?.products, couponDetails.catalogLimit?.products)
        assertEquals(couponCreateRequest.newCoupon.catalogLimit?.categories, couponDetails.catalogLimit?.categories)

        // Completely updating newly created customer, leaving shipping addresses ids to update not recreate them
        val couponUpdateRequest = CouponUpdateRequest(
                couponIdentifier = couponDetails.code,
                updatedCoupon = generateTestCouponForUpdate()
        )
        val couponUpdateResult = apiClient.updateCoupon(couponUpdateRequest)
        assertEquals(1, couponUpdateResult.updateCount)

        // Checking that customer was successfully updated with necessary parameters
        val couponDetailsRequest1 = CouponDetailsRequest(couponIdentifier = couponUpdateRequest.updatedCoupon.code)
        val couponDetails1 = apiClient.getCouponDetails(couponDetailsRequest1)
        assertEquals(couponUpdateRequest.updatedCoupon.code, couponDetails1.code)
        assertEquals(couponUpdateRequest.updatedCoupon.name, couponDetails1.name)
        assertEquals(couponUpdateRequest.updatedCoupon.discountType, couponDetails1.discountType)
        assertEquals(DiscountCouponStatus.ACTIVE.name, couponDetails1.status?.name)
        assertEquals(couponUpdateRequest.updatedCoupon.launchDate.toString(), couponDetails1.launchDate.toString())
        assertEquals(couponUpdateRequest.updatedCoupon.expirationDate.toString(), couponDetails1.expirationDate.toString())
        assertEquals(couponUpdateRequest.updatedCoupon.usesLimit, couponDetails1.usesLimit)
        assertEquals(0, couponDetails1.orderCount)
        assertEquals(couponUpdateRequest.updatedCoupon.catalogLimit?.products, couponDetails1.catalogLimit?.products)
        assertEquals(couponUpdateRequest.updatedCoupon.catalogLimit?.categories, couponDetails1.catalogLimit?.categories)

        // Deleting coupon
        val couponDeleteRequest = CouponDeleteRequest(couponIdentifier = couponDetails1.code)
        val couponDeleteResult = apiClient.deleteCoupon(couponDeleteRequest)
        assertEquals(1, couponDeleteResult.deleteCount)

        // Checking that deleted customer is not accessible anymore
        try {
            apiClient.getCouponDetails(couponDetailsRequest1)
        } catch (e: EcwidApiException) {
            assertEquals(404, e.statusCode)
            assertEquals("Coupon is not found", e.message)
        }
    }

    @Test
    fun testSearchFields() {
        // We need to start from scratch
        removeAllCoupons()

        // Creating 2 new coupons
        val couponCreateRequest1 = CouponCreateRequest(
                newCoupon = generateTestCouponForSearch1()
        )
        val couponCreateResult1 = apiClient.createCoupon(couponCreateRequest1)
        assertTrue(couponCreateResult1.id > 0)

        val couponCreateRequest2 = CouponCreateRequest(
                newCoupon = generateTestCouponForSearch2()
        )
        val couponCreateResult2 = apiClient.createCoupon(couponCreateRequest2)
        assertTrue(couponCreateResult2.id > 0)

        // Trying to search by different fields

        assertCouponsSearch(
                positiveSearchRequest = CouponSearchRequest(
                        code = couponCreateRequest1.newCoupon.code,
                        offset = 0
                ),
                negativeSearchRequest = CouponSearchRequest(
                        code = couponCreateRequest1.newCoupon.code,
                        offset = 1
                )
        )

        assertCouponsSearch(
                positiveSearchRequest = CouponSearchRequest(
                        offset = 1,
                        limit = 1
                ),
                negativeSearchRequest = CouponSearchRequest(
                        offset = 2,
                        limit = 1
                )
        )

        assertCouponsSearch(
                positiveSearchRequest = CouponSearchRequest(
                        code = couponCreateRequest1.newCoupon.code,
                        discountType = couponCreateRequest1.newCoupon.discountType?.name
                ),
                negativeSearchRequest = CouponSearchRequest(
                        code = couponCreateRequest1.newCoupon.code,
                        discountType = couponCreateRequest2.newCoupon.discountType?.name
                )
        )

        val instant = Date().toInstant()
        val instantFrom = instant.minusSeconds(10)
        val instantTo = instant.plusSeconds(10)

        assertCouponsSearch(
                positiveSearchRequest = CouponSearchRequest(
                        code = couponCreateRequest1.newCoupon.code,
                        createdTo = Date.from(instantTo),
                        createdFrom = Date.from(instantFrom)
                ),
                negativeSearchRequest = CouponSearchRequest(
                        code = couponCreateRequest1.newCoupon.code,
                        createdTo = Date.from(instantFrom),
                        createdFrom = Date.from(instantFrom)
                )
        )

        assertCouponsSearch(
                positiveSearchRequest = CouponSearchRequest(
                        code = couponCreateRequest1.newCoupon.code,
                        updatedTo = Date.from(instantTo),
                        updatedFrom = Date.from(instantFrom)
                ),
                negativeSearchRequest = CouponSearchRequest(
                        code = couponCreateRequest1.newCoupon.code,
                        updatedTo = Date.from(instantFrom),
                        updatedFrom = Date.from(instantFrom)
                )
        )
    }

    @Test
    fun testDeletedCoupon() {
        // Creating new coupon
        val couponCreateRequest = CouponCreateRequest(
                newCoupon = UpdatedCoupon(
                        name = randomAlphanumeric(10).toLowerCase(),
                        code = randomAlphanumeric(10).toLowerCase()
                )
        )
        val couponCreateResult = apiClient.createCoupon(couponCreateRequest)
        assertTrue(couponCreateResult.id > 0)

        // Deleting customer
        val customerDeleteRequest = CouponDeleteRequest(couponIdentifier = couponCreateResult.code)
        val customerDeleteResult = apiClient.deleteCoupon(customerDeleteRequest)
        assertEquals(1, customerDeleteResult.deleteCount)
    }

    private fun generateTestCouponForCreate(): UpdatedCoupon {
        val launchDate = randomDateFrom(Date())
        val expirationDate = randomDateFrom(launchDate)
        return UpdatedCoupon(
                name = randomAlphanumeric(10),
                code = randomAlphanumeric(10).toUpperCase(),
                discountType = randomEnumValue<DiscountCouponType>(),
                status = DiscountCouponStatus.ACTIVE,
                expirationDate = expirationDate,
                launchDate = launchDate,
                totalLimit = randomDouble(0.0,100000.0),
                usesLimit = randomEnumValue<DiscountCouponUsesLimit>(),
                repeatCustomerOnly = randomBoolean(),
                applicationLimit = randomEnumValue<DiscountCouponApplicationLimit>(),
                orderCount = randomInt(0, 100000),
                catalogLimit = UpdatedCoupon.DiscountCouponCatalogLimit(
                        listOf(randomInt(0,1000)),
                        listOf(randomInt(0,5000))
                )
        )
    }

    private fun generateTestCouponForUpdate(): UpdatedCoupon {
        val launchDate = randomDateFrom(Date())
        val expirationDate = randomDateFrom(launchDate)
        return UpdatedCoupon(
                name = randomAlphanumeric(10),
                code = randomAlphanumeric(10).toUpperCase(),
                discountType = randomEnumValue<DiscountCouponType>(),
                status = DiscountCouponStatus.ACTIVE,
                expirationDate = expirationDate,
                launchDate = launchDate,
                totalLimit = randomDouble(0.0,100000.0),
                usesLimit = randomEnumValue<DiscountCouponUsesLimit>(),
                repeatCustomerOnly = randomBoolean(),
                applicationLimit = randomEnumValue<DiscountCouponApplicationLimit>(),
                orderCount = randomInt(0, 100000),
                catalogLimit = UpdatedCoupon.DiscountCouponCatalogLimit(
                        listOf(randomInt(0,1000)),
                        listOf(randomInt(0,5000))
                )
        )
    }

    private fun generateTestCouponForSearch1(): UpdatedCoupon {
        val launchDate = randomDateFrom(Date())
        val expirationDate = randomDateFrom(launchDate)
        return UpdatedCoupon(
                name = randomAlphanumeric(10),
                code = randomAlphanumeric(10).toUpperCase(),
                discountType = DiscountCouponType.ABS,
                status = DiscountCouponStatus.ACTIVE,
                expirationDate = expirationDate,
                launchDate = launchDate,
                totalLimit = randomDouble(0.0,100000.0),
                usesLimit = randomEnumValue<DiscountCouponUsesLimit>(),
                repeatCustomerOnly = randomBoolean(),
                applicationLimit = randomEnumValue<DiscountCouponApplicationLimit>(),
                orderCount = randomInt(0, 100000),
                catalogLimit = UpdatedCoupon.DiscountCouponCatalogLimit(
                        listOf(randomInt(0,1000)),
                        listOf(randomInt(0,5000))
                )
        )
    }

    private fun generateTestCouponForSearch2(): UpdatedCoupon {
        val launchDate = randomDateFrom(Date())
        val expirationDate = randomDateFrom(launchDate)
        return UpdatedCoupon(
                name = randomAlphanumeric(10),
                code = randomAlphanumeric(10).toUpperCase(),
                discountType = DiscountCouponType.PERCENT,
                status = DiscountCouponStatus.ACTIVE,
                expirationDate = expirationDate,
                launchDate = launchDate,
                totalLimit = randomDouble(0.0,100000.0),
                usesLimit = randomEnumValue<DiscountCouponUsesLimit>(),
                repeatCustomerOnly = randomBoolean(),
                applicationLimit = randomEnumValue<DiscountCouponApplicationLimit>(),
                orderCount = randomInt(0, 100000),
                catalogLimit = UpdatedCoupon.DiscountCouponCatalogLimit(
                        listOf(randomInt(0,1000)),
                        listOf(randomInt(0,5000))
                )
        )
    }

    private fun assertCouponsSearch(positiveSearchRequest: CouponSearchRequest, negativeSearchRequest: CouponSearchRequest) {
        val positiveCouponsSearchResult = apiClient.searchCoupons(positiveSearchRequest)
        assertEquals(1, positiveCouponsSearchResult.count)

        val negativeCouponsSearchResult = apiClient.searchCoupons(negativeSearchRequest)
        assertEquals(0, negativeCouponsSearchResult.count)
    }
}
