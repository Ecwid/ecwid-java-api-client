package com.ecwid.apiclient.v3

import com.ecwid.apiclient.v3.dto.product.request.ProductCreateRequest
import com.ecwid.apiclient.v3.dto.product.request.ProductDetailsRequest
import com.ecwid.apiclient.v3.dto.product.request.UpdatedProduct
import com.ecwid.apiclient.v3.dto.variation.request.CreateProductVariationRequest
import com.ecwid.apiclient.v3.dto.variation.request.UpdatedVariation
import com.ecwid.apiclient.v3.util.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class VariationsTest : BaseEntityTest() {
	@BeforeEach
	override fun beforeEach() {
		super.beforeEach()

		// We need to start from scratch each time
		removeAllCategories()
		removeAllProducts()
	}

	@Test
	fun `Create product, add combo and fetch product`() {
		// Create one product
		val productPrice = randomPrice()
		val productCreateRequest = ProductCreateRequest(
				newProduct = UpdatedProduct(
						price = productPrice,
						name = "Product ${randomAlphanumeric(8)}",
						sku = "testVariations",
						options = listOf(
								generateProductSelectOption("Size", listOf("S", "M", "L"))
						)
				)
		)

		val productCreateResult = apiClient.createProduct(productCreateRequest)
		val newProductId = productCreateResult.id
		Assertions.assertTrue(newProductId > 0)

		val testVariationSku = "testVariation"
		val testVariationPrice = randomPrice()
		val testVariationWeight = randomWeight()
		val createProductVariationRequest = CreateProductVariationRequest(
				productId = newProductId,
				newVariaion = UpdatedVariation(
						sku = testVariationSku,
						quantity = 2,
						price = testVariationPrice,
						weight = testVariationWeight,
						options = listOf(
								UpdatedVariation.Option(
										name = "Size",
										value = "L"
								)
						)
				)
		)

		val createProductVariationResult = apiClient.createProductVariation(createProductVariationRequest)
		Assertions.assertTrue(createProductVariationResult.id > 0)

		val fetchedProduct = apiClient.getProductDetails(ProductDetailsRequest(productId = newProductId))
		val variations = fetchedProduct.combinations
		require(variations != null)
		val variation = variations.first()
		Assertions.assertEquals(testVariationPrice, variation.price)
		Assertions.assertEquals(testVariationWeight, variation.weight)
		Assertions.assertEquals(testVariationSku, variation.sku)

	}
}

private fun generateProductSelectOption(name: String, values: List<String>): UpdatedProduct.ProductOption {
	val choices = values.map { generateProductOptionChoice(it) }
	return UpdatedProduct.ProductOption.createSelectOption(
			name = name,
			choices = choices,
			defaultChoice = randomIndex(choices),
			required = randomBoolean()
	)
}

private fun generateProductOptionChoice(value: String) = UpdatedProduct.ProductOptionChoice(
		text = value,
		priceModifier = randomModifier(),
		priceModifierType = randomEnumValue()
)