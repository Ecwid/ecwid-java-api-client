package com.ecwid.apiclient.v3.entity

import com.ecwid.apiclient.v3.dto.brand.request.BrandsSearchRequest
import com.ecwid.apiclient.v3.dto.product.request.ProductCreateRequest
import com.ecwid.apiclient.v3.dto.product.request.ProductsSearchRequest.ByFilters
import com.ecwid.apiclient.v3.dto.product.request.UpdatedProduct
import com.ecwid.apiclient.v3.dto.product.request.UpdatedProduct.*
import com.ecwid.apiclient.v3.util.randomAlphanumeric
import com.ecwid.apiclient.v3.util.randomPrice
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test
import kotlin.test.assertEquals

class BrandsTest : BaseEntityTest() {

	@BeforeEach
	override fun beforeEach() {
		super.beforeEach()

		initStoreProfile()
		removeAllProducts()
	}

	@Test
	fun getBrands() {
		val brandedProductsCreateResult = createProductsWithBrands()

		// Waiting till product became available for searching
		brandedProductsCreateResult.skus.forEach { sku ->
			waitForIndexedProducts(
				productsSearchRequest = ByFilters(sku = sku),
				desiredProductCount = 1
			)
		}

		val result = apiClient.getBrands(BrandsSearchRequest.ByFilters())
		assertEquals(
			brandedProductsCreateResult.brandNames,
			result.items.map { it.name }
		)
	}

	private fun createProductsWithBrands(productsCount: Int = 5): BrandedProductsCreateResult {
		val brands = mutableListOf<String>()
		val skus = mutableListOf<String>()

		for (i in 1..productsCount) {
			val randomBrand = randomAlphanumeric(8)
			val randomSku = randomAlphanumeric(10)
			val price = randomPrice()

			val productCreateRequest = ProductCreateRequest(
				newProduct = UpdatedProduct(
					name = "Product ${randomAlphanumeric(8)}",
					sku = randomSku,
					price = price,
					compareToPrice = 2 * price,
					enabled = true,
					quantity = 10,
					attributes = listOf(
						AttributeValue.createBrandAttributeValue(randomBrand),
						AttributeValue.createUpcAttributeValue("UPC ${randomAlphanumeric(8)}")
					),
					options = listOf(
						ProductOption.createSelectOption(
							name = "Color",
							choices = listOf(
								ProductOptionChoice("Black"),
								ProductOptionChoice("White"),
								ProductOptionChoice("Yellow"),
								ProductOptionChoice("Red")
							),
							defaultChoice = 0,
							required = true
						)
					)
				)
			)
			val productCreateResult = apiClient.createProduct(productCreateRequest)
			assertTrue(productCreateResult.id > 0)

			skus.add(randomSku)
			brands.add(randomBrand)
		}

		return BrandedProductsCreateResult(
			brandNames = brands,
			skus = skus,
		)
	}

	data class BrandedProductsCreateResult(
		val brandNames: List<String>,
		val skus: List<String>,
	)
}
