package com.ecwid.apiclient.v3

import com.ecwid.apiclient.v3.dto.coupon.request.CouponDetailsRequest
import com.ecwid.apiclient.v3.dto.coupon.request.CouponSearchRequest
import com.ecwid.apiclient.v3.dto.order.enums.DiscountCouponType
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.text.SimpleDateFormat
import java.util.*

class CouponsTest: BaseEntityTest() {

    // Since the methods for creating coupons in the api are not yet implemented,
    // tests will be run on three default coupons: MOXQ3YCWXRXA, J3N4JM3SIPCJ, O3Q4AP5FKXJ1

    @BeforeEach
    override fun beforeEach() {
        super.beforeEach()
    }

    @Test
    fun testGetCouponDetails() {

        val coupon = apiClient.getCouponDetails(CouponDetailsRequest("MOXQ3YCWXRXA"))

        Assertions.assertEquals("Купон # 1", coupon.name)
        Assertions.assertEquals("ABS", coupon.discountType)
        Assertions.assertEquals("ACTIVE", coupon.status)
        Assertions.assertEquals(1.0, coupon.discount)
        Assertions.assertEquals("Tue Apr 09 01:00:00 SAMT 2019", coupon.launchDate.toString())
        Assertions.assertEquals("Tue Apr 09 09:18:54 SAMT 2019", coupon.creationDate.toString())
        Assertions.assertEquals("Fri Oct 04 09:56:19 SAMT 2019", coupon.updateDate.toString())
        Assertions.assertEquals("UNLIMITED", coupon.usesLimit)
        Assertions.assertEquals(false, coupon.repeatCustomerOnly)
        Assertions.assertEquals(0, coupon.orderCount)
        Assertions.assertNull(coupon.catalogLimit)
    }

    @Test
    fun testSearchFields() {

        assertCouponsSearch(
                couponIdentifier = "O3Q4AP5FKXJ1",
                positiveSearchRequest = CouponSearchRequest(offset = 2),
                negativeSearchRequest = CouponSearchRequest(offset = 3)
        )

        assertCouponsSearch(
                couponIdentifier = "MOXQ3YCWXRXA",
                positiveSearchRequest = CouponSearchRequest(
                        offset = 1,
                        limit = 1
                ),
                negativeSearchRequest = CouponSearchRequest(
                        offset = 3,
                        limit = 1
                )
        )

        assertCouponsSearch(
                couponIdentifier = "MOXQ3YCWXRXA",
                positiveSearchRequest = CouponSearchRequest(discount_type = DiscountCouponType.ABS.name),
                negativeSearchRequest = CouponSearchRequest(discount_type = DiscountCouponType.PERCENT_AND_SHIPPING.name)
        )


        val couponsDateCreate = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2019-04-09 09:18:54")
        val couponsDateUpdate = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2019-10-04 09:56:18")

        assertCouponsSearch(
                couponIdentifier = "O3Q4AP5FKXJ1",
                positiveSearchRequest = CouponSearchRequest(
                        createdFrom = couponsDateCreate,
                        updatedTo = couponsDateUpdate
                ),
                negativeSearchRequest = CouponSearchRequest(
                        createdFrom = Date.from(couponsDateCreate.toInstant().plusSeconds(1))
                )
        )

        assertCouponsSearch(
                couponIdentifier = "MOXQ3YCWXRXA",
                positiveSearchRequest = CouponSearchRequest(
                        createdTo = Date.from(couponsDateCreate.toInstant().plusSeconds(1)),
                        updatedFrom = Date.from(couponsDateUpdate.toInstant().plusSeconds(1)),
                        availability = "ACTIVE"
                ),
                negativeSearchRequest = CouponSearchRequest(
                        createdTo = Date.from(couponsDateCreate.toInstant().minusSeconds(1)),
                        availability = "ACTIVE"
                )
        )

        val couponsSearchResult = apiClient.searchCoupons(CouponSearchRequest())
        Assertions.assertEquals(3, couponsSearchResult.count)
    }

    private fun assertCouponsSearch(couponIdentifier: String, positiveSearchRequest: CouponSearchRequest, negativeSearchRequest: CouponSearchRequest) {
        val positiveCouponsSearchResult = apiClient.searchCoupons(positiveSearchRequest)
        Assertions.assertEquals(1, positiveCouponsSearchResult.count)
        Assertions.assertEquals(couponIdentifier, positiveCouponsSearchResult.items[0].code)

        val negativeCouponsSearchResult = apiClient.searchCoupons(negativeSearchRequest)
        Assertions.assertEquals(0, negativeCouponsSearchResult.count)
    }
}
