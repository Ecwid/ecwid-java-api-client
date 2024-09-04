package com.ecwid.apiclient.v3.entity

import com.ecwid.apiclient.v3.converter.toUpdated
import com.ecwid.apiclient.v3.dto.category.request.CategoryCreateRequest
import com.ecwid.apiclient.v3.dto.category.request.UpdatedCategory
import com.ecwid.apiclient.v3.dto.category.result.CategoryCreateResult
import com.ecwid.apiclient.v3.dto.coupon.request.*
import com.ecwid.apiclient.v3.dto.order.enums.DiscountCouponApplicationLimit
import com.ecwid.apiclient.v3.dto.order.enums.DiscountCouponStatus
import com.ecwid.apiclient.v3.dto.order.enums.DiscountCouponType
import com.ecwid.apiclient.v3.dto.order.enums.DiscountCouponUsesLimit
import com.ecwid.apiclient.v3.dto.product.request.ProductCreateRequest
import com.ecwid.apiclient.v3.dto.product.request.UpdatedProduct
import com.ecwid.apiclient.v3.dto.product.result.ProductCreateResult
import com.ecwid.apiclient.v3.exception.EcwidApiException
import com.ecwid.apiclient.v3.util.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*

class CouponsTest : BaseEntityTest() {

	@BeforeEach
	override fun beforeEach() {
		super.beforeEach()

		// We need to start from scratch each time
		removeAllCoupons()
		removeAllCategories()
		removeAllProducts()
	}

	@Test
	fun testCouponLifecycle() {
		// Create two products
		val productCreateResult1 = createTestProduct()
		val productCreateResult2 = createTestProduct()
		assertTrue(productCreateResult1.id > 0)
		assertTrue(productCreateResult2.id > 0)

		// Create two categories
		val categoryCreateResult1 = createTestCategory()
		val categoryCreateResult2 = createTestCategory()
		assertTrue(categoryCreateResult1.id > 0)
		assertTrue(categoryCreateResult2.id > 0)

		// Creating new coupon
		val couponCreateRequest = CouponCreateRequest(
			newCoupon = generateTestCouponForCreate(productCreateResult1.id, categoryCreateResult1.id)
		)
		val couponCreateResult = apiClient.createCoupon(couponCreateRequest)
		assertTrue(couponCreateResult.id > 0)

		// Checking that coupon was successfully created with necessary parameters
		val couponDetailsRequest = CouponDetailsRequest(couponIdentifier = couponCreateRequest.newCoupon.code)
		val couponDetails = apiClient.getCouponDetails(couponDetailsRequest)
		assertEquals(
			couponCreateRequest.newCoupon,
			couponDetails.toUpdated()
		)

		// Completely updating newly created coupon
		val couponUpdateRequest = CouponUpdateRequest(
			couponIdentifier = couponDetails.code,
			updatedCoupon = generateTestCouponForUpdate(productCreateResult2.id, categoryCreateResult2.id)
		)
		val couponUpdateResult = apiClient.updateCoupon(couponUpdateRequest)
		assertEquals(1, couponUpdateResult.updateCount)

		// Checking that coupon was successfully updated with necessary parameters
		val couponDetailsRequest1 = CouponDetailsRequest(couponIdentifier = couponUpdateRequest.updatedCoupon.code)
		val couponDetails1 = apiClient.getCouponDetails(couponDetailsRequest1)
		assertEquals(
			couponUpdateRequest.updatedCoupon,
			couponDetails1.toUpdated()
		)

		// Deleting coupon
		val couponDeleteRequest = CouponDeleteRequest(couponIdentifier = couponDetails1.code)
		val couponDeleteResult = apiClient.deleteCoupon(couponDeleteRequest)
		assertEquals(1, couponDeleteResult.deleteCount)

		// Checking that deleted coupon is not accessible anymore
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
				discountType = setOf(couponCreateRequest1.newCoupon.discountType!!)

			),
			negativeSearchRequest = CouponSearchRequest(
				code = couponCreateRequest1.newCoupon.code,
				discountType = setOf(couponCreateRequest2.newCoupon.discountType!!)
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
				name = randomAlphanumeric(10).lowercase(),
				code = randomAlphanumeric(10).lowercase()
			)
		)
		val couponCreateResult = apiClient.createCoupon(couponCreateRequest)
		assertTrue(couponCreateResult.id > 0)

		// Deleting customer
		val customerDeleteRequest = CouponDeleteRequest(couponIdentifier = couponCreateResult.code)
		val customerDeleteResult = apiClient.deleteCoupon(customerDeleteRequest)
		assertEquals(1, customerDeleteResult.deleteCount)
	}

	private fun createTestProduct(): ProductCreateResult {
		val productCreateRequest = ProductCreateRequest(
			newProduct = UpdatedProduct(
				name = "Product ${randomAlphanumeric(8)}"
			)
		)

		return apiClient.createProduct(productCreateRequest)
	}

	private fun createTestCategory(): CategoryCreateResult {
		val categoryCreateRequest = CategoryCreateRequest(
			newCategory = UpdatedCategory(
				name = "Category " + randomAlphanumeric(8)
			)
		)

		return apiClient.createCategory(categoryCreateRequest)
	}

	private fun generateTestCouponForCreate(existingProductId: Int, existingCategoryId: Long): UpdatedCoupon {
		val launchDate = randomDateFrom(Date())
		val expirationDate = randomDateFrom(launchDate)

		val applicationLimit = randomEnumValue<DiscountCouponApplicationLimit>()

		return UpdatedCoupon(
			name = randomAlphanumeric(10),
			code = randomAlphanumeric(10).uppercase(),
			discountType = randomEnumValue(DiscountCouponType.SHIPPING), // DiscountCouponType.SHIPPING is not compatible with catalogLimit
			status = DiscountCouponStatus.ACTIVE,
			discount = randomDouble(0.0, 100000.0),
			expirationDate = expirationDate,
			launchDate = launchDate,
			totalLimit = randomDouble(0.0, 100000.0),
			usesLimit = randomEnumValue<DiscountCouponUsesLimit>(),
			repeatCustomerOnly = applicationLimit.toRepeatCustomerOnly(),
			applicationLimit = applicationLimit,
			orderCount = 0,
			catalogLimit = UpdatedCoupon.DiscountCouponCatalogLimit(
				products = listOf(existingProductId),
				categories = listOf(existingCategoryId)
			)
		)
	}

	private fun generateTestCouponForUpdate(existingProductId: Int, existingCategoryId: Long): UpdatedCoupon {
		val launchDate = randomDateFrom(Date())
		val expirationDate = randomDateFrom(launchDate)

		val applicationLimit = randomEnumValue<DiscountCouponApplicationLimit>()

		return UpdatedCoupon(
			name = randomAlphanumeric(10),
			code = randomAlphanumeric(10).uppercase(),
			discountType = randomEnumValue(DiscountCouponType.SHIPPING), // DiscountCouponType.SHIPPING is not compatible with catalogLimit
			status = DiscountCouponStatus.ACTIVE,
			discount = randomDouble(0.0, 100000.0),
			expirationDate = expirationDate,
			launchDate = launchDate,
			totalLimit = randomDouble(0.0, 100000.0),
			usesLimit = randomEnumValue<DiscountCouponUsesLimit>(),
			repeatCustomerOnly = applicationLimit.toRepeatCustomerOnly(),
			applicationLimit = applicationLimit,
			orderCount = 0,
			catalogLimit = UpdatedCoupon.DiscountCouponCatalogLimit(
				products = listOf(existingProductId),
				categories = listOf(existingCategoryId)
			)
		)
	}

	private fun generateTestCouponForSearch1(): UpdatedCoupon {
		val launchDate = randomDateFrom(Date())
		val expirationDate = randomDateFrom(launchDate)
		return UpdatedCoupon(
			name = randomAlphanumeric(10),
			code = randomAlphanumeric(10).uppercase(),
			discountType = DiscountCouponType.ABS,
			status = DiscountCouponStatus.ACTIVE,
			discount = randomDouble(0.0, 100000.0),
			expirationDate = expirationDate,
			launchDate = launchDate,
			totalLimit = randomDouble(0.0, 100000.0),
			usesLimit = randomEnumValue<DiscountCouponUsesLimit>(),
			repeatCustomerOnly = randomBoolean(),
			applicationLimit = randomEnumValue<DiscountCouponApplicationLimit>(),
			orderCount = 0
		)
	}

	private fun generateTestCouponForSearch2(): UpdatedCoupon {
		val launchDate = randomDateFrom(Date())
		val expirationDate = randomDateFrom(launchDate)
		return UpdatedCoupon(
			name = randomAlphanumeric(10),
			code = randomAlphanumeric(10).uppercase(),
			discountType = DiscountCouponType.PERCENT,
			status = DiscountCouponStatus.ACTIVE,
			discount = randomDouble(0.0, 100000.0),
			expirationDate = expirationDate,
			launchDate = launchDate,
			totalLimit = randomDouble(0.0, 100000.0),
			usesLimit = randomEnumValue<DiscountCouponUsesLimit>(),
			repeatCustomerOnly = randomBoolean(),
			applicationLimit = randomEnumValue<DiscountCouponApplicationLimit>(),
			orderCount = 0
		)
	}

	private fun assertCouponsSearch(
		positiveSearchRequest: CouponSearchRequest,
		negativeSearchRequest: CouponSearchRequest
	) {
		val positiveCouponsSearchResult = apiClient.searchCoupons(positiveSearchRequest)
		assertEquals(1, positiveCouponsSearchResult.count)

		val negativeCouponsSearchResult = apiClient.searchCoupons(negativeSearchRequest)
		assertEquals(0, negativeCouponsSearchResult.count)
	}
}

private fun DiscountCouponApplicationLimit.toRepeatCustomerOnly() =
	this == DiscountCouponApplicationLimit.REPEAT_CUSTOMER_ONLY
