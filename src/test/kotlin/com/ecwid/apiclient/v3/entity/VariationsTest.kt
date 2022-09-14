package com.ecwid.apiclient.v3.entity

import com.ecwid.apiclient.v3.converter.toUpdated
import com.ecwid.apiclient.v3.dto.common.AsyncPictureData
import com.ecwid.apiclient.v3.dto.common.UploadFileData
import com.ecwid.apiclient.v3.dto.product.request.ProductCreateRequest
import com.ecwid.apiclient.v3.dto.product.request.ProductDetailsRequest
import com.ecwid.apiclient.v3.dto.product.request.UpdatedProduct
import com.ecwid.apiclient.v3.dto.variation.request.*
import com.ecwid.apiclient.v3.util.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class VariationsTest : BaseEntityTest() {

	@BeforeEach
	override fun beforeEach() {
		super.beforeEach()

		// We need to start from scratch each time
		removeAllCategories()
		removeAllVariations()
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
				sku = "testAddVariations",
				options = listOf(
					generateProductSelectOption("Size", listOf("S", "M", "L"))
				)
			)
		)

		val productCreateResult = apiClient.createProduct(productCreateRequest)
		val newProductId = productCreateResult.id
		assertTrue(newProductId > 0)

		val testVariationSku = "testVariation"
		val testVariationPrice = randomPrice()
		val testVariationCostPrice = testVariationPrice * 0.9
		val testVariationWeight = randomWeight()
		val testVariationDimensions = generateDimensions()
		val testVariationVolume = randomVolume()
		val testVariationCustomsHsTariffCode = randomAlphanumeric(10)
		val createProductVariationRequest = CreateProductVariationRequest(
			productId = newProductId,
			newVariation = UpdatedVariation(
				sku = testVariationSku,
				quantity = 2,
				isShippingRequired = true,
				price = testVariationPrice,
				costPrice = testVariationCostPrice,
				weight = testVariationWeight,
				dimensions = testVariationDimensions,
				volume = testVariationVolume,
				options = listOf(
					UpdatedVariation.Option(
						name = "Size",
						value = "L"
					)
				),
				customsHsTariffCode = testVariationCustomsHsTariffCode,
			)
		)

		val createProductVariationResult = apiClient.createProductVariation(createProductVariationRequest)
		assertTrue(createProductVariationResult.id > 0)

		val fetchedProduct = apiClient.getProductDetails(ProductDetailsRequest(productId = newProductId))
		val variations = fetchedProduct.combinations
		require(variations != null)
		val variation = variations.first()
		assertEquals(testVariationPrice, variation.price)
		assertEquals(testVariationCostPrice, variation.costPrice)
		assertEquals(testVariationWeight, variation.weight)
		assertEquals(testVariationDimensions, variation.toUpdated().dimensions)
		assertEquals(testVariationVolume, variation.volume)
		assertEquals(testVariationSku, variation.sku)
		assertEquals(testVariationCustomsHsTariffCode, variation.customsHsTariffCode)
	}

	@Test
	fun `manipulate variations`() {
		// Create one product
		val productPrice = randomPrice()
		val productCreateRequest = ProductCreateRequest(
			newProduct = UpdatedProduct(
				price = productPrice,
				name = "Product ${randomAlphanumeric(8)}",
				sku = "testVariations",
				quantity = 10,
				options = listOf(
					generateProductSelectOption("Size", listOf("S", "M", "L"))
				)
			)
		)

		val productCreateResult = apiClient.createProduct(productCreateRequest)
		val newProductId = productCreateResult.id
		assertTrue(newProductId > 0)

		// add 1st variation
		val createProductVariationRequest = CreateProductVariationRequest(
			productId = newProductId,
			newVariation = UpdatedVariation(
				sku = "first test Variation",
				quantity = 2,
				isShippingRequired = true,
				price = 51.2,
				costPrice = 46.6,
				weight = 16.7,
				dimensions = generateDimensions(),
				volume = 16.0,
				options = listOf(
					UpdatedVariation.Option(
						name = "Size",
						value = "L"
					)
				),
				customsHsTariffCode = "123456",
			)
		)

		val create1stVariationResult = apiClient.createProductVariation(createProductVariationRequest)
		assertTrue(create1stVariationResult.id > 0)

		// add second variation
		val create2ndProductVariationRequest = CreateProductVariationRequest(
			productId = newProductId,
			newVariation = UpdatedVariation(
				sku = "second test Variation",
				quantity = 9,
				isShippingRequired = false,
				price = 100.0,
				weight = 15.0,
				options = listOf(
					UpdatedVariation.Option(
						name = "Size",
						value = "S"
					)
				)
			)
		)
		val create2ndVariationResult = apiClient.createProductVariation(create2ndProductVariationRequest)
		assertTrue(create2ndVariationResult.id > 0)

		// update 1st variation
		val newDimensions = generateDimensions()
		val update1stVariationRequest = UpdateProductVariationRequest(
			productId = newProductId,
			variationId = create1stVariationResult.id,
			variation = UpdatedVariation(
				sku = "modified first test Variation",
				quantity = 15,
				costPrice = 47.2,
				dimensions = newDimensions,
				volume = 15.25,
				customsHsTariffCode = "654321",
			)
		)
		val updateResult = apiClient.updateProductVariation(update1stVariationRequest)
		assertEquals(1, updateResult.updateCount)

		// change inventory of 2nd variation
		val change2ndVariationInventoryRequest = AdjustVariationInventoryRequest(
			productId = newProductId,
			variationId = create2ndVariationResult.id,
			quantityDelta = -4
		)
		val adjustResult = apiClient.adjustVariationInventory(change2ndVariationInventoryRequest)
		assertEquals(1, adjustResult.updateCount)

		// get all variations to check inventory and sku
		val allVariationsResult = apiClient.getAllProductVariations(ProductVariationsRequest(productId = newProductId))
		assertEquals(2, allVariationsResult.size)
		val (firstVar, secondVar) = allVariationsResult
		assertEquals(create1stVariationResult.id, firstVar.id)
		assertEquals("modified first test Variation", firstVar.sku)
		assertEquals(15, firstVar.quantity)
		assertEquals(47.2, firstVar.costPrice)
		assertEquals(newDimensions, firstVar.toUpdated().dimensions)
		assertEquals(15.25, firstVar.volume)
		assertEquals("654321", firstVar.customsHsTariffCode)
		assertEquals(create2ndVariationResult.id, secondVar.id)
		assertEquals("second test Variation", secondVar.sku)
		assertEquals(5 /* = 9 - 4 */, secondVar.quantity)

		// delete 1st variation
		val delete1stVariationRequest =
			DeleteProductVariationRequest(productId = newProductId, variationId = create1stVariationResult.id)
		val delete1stVariationResult = apiClient.deleteProductVariation(delete1stVariationRequest)
		assertEquals(1, delete1stVariationResult.deleteCount)

		// ensure 1st variation is deleted, but 2nd is still there
		val anotherAllVariationsResult =
			apiClient.getAllProductVariations(ProductVariationsRequest(productId = newProductId))
		assertEquals(1, anotherAllVariationsResult.size)
		assertEquals(create2ndVariationResult.id, anotherAllVariationsResult.first().id)
	}

	@Test
	fun manipulateVariationImage() {
		// Create one product, basically, redo same test as above, but with another option type
		val productPrice = randomPrice()
		val productCreateRequest = ProductCreateRequest(
			newProduct = UpdatedProduct(
				price = productPrice,
				name = "Product ${randomAlphanumeric(8)}",
				sku = "testProduct1",
				options = listOf(
					generateProductRadioOption("Test", listOf("1", "2", "3", "4", "5"))
				)
			)
		)

		val productCreateResult = apiClient.createProduct(productCreateRequest)
		val newProductId = productCreateResult.id
		assertTrue(newProductId > 0)

		val testVariationPrice = randomPrice()
		val testVariationWeight = randomWeight()
		val createProductVariationRequest = CreateProductVariationRequest(
			productId = newProductId,
			newVariation = UpdatedVariation(
				sku = "testVariation1",
				quantity = 2,
				price = testVariationPrice,
				weight = testVariationWeight,
				options = listOf(
					UpdatedVariation.Option(
						name = "Test",
						value = "5"
					)
				)
			)
		)

		val createProductVariationResult = apiClient.createProductVariation(createProductVariationRequest)
		val newVariationId = createProductVariationResult.id
		assertTrue(newVariationId > 0)

		val fetchedProduct = apiClient.getProductDetails(ProductDetailsRequest(productId = newProductId))
		val variations = fetchedProduct.combinations
		require(variations != null)
		val variation = variations.first()
		assertEquals(testVariationPrice, variation.price)
		assertEquals(testVariationWeight, variation.weight)
		assertEquals("testVariation1", variation.sku)

		// Now create image for variation and then delete it
		val createProductVariationImageRequest = ProductVariationImageUploadRequest(
			productId = newProductId,
			variationId = newVariationId,
			fileData = UploadFileData.ExternalUrlData(externalUrl = "https://don16obqbay2c.cloudfront.net/favicons/apple-touch-icon-180x180.png")
		)
		val createProductVariationImageResult = apiClient.uploadVariationImage(createProductVariationImageRequest)
		assertTrue(createProductVariationImageResult.id > 0)

		val productVariationDetailsRequest = ProductVariationDetailsRequest(
			productId = newProductId,
			variationId = newVariationId
		)
		val fetchedVariation1 = apiClient.getProductVariation(productVariationDetailsRequest)
		Assertions.assertNotNull(fetchedVariation1.imageUrl)

		// Now delete category image
		val deleteProductVariationImageRequest = ProductVariationImageDeleteRequest(
			productId = newProductId,
			variationId = newVariationId
		)
		val deleteProductVariationImageResult = apiClient.deleteVariationImage(deleteProductVariationImageRequest)
		assertTrue(deleteProductVariationImageResult.deleteCount > 0)

		// Check that category has now no main image now
		val fetchedVariation2 = apiClient.getProductVariation(productVariationDetailsRequest)
		Assertions.assertNull(fetchedVariation2.imageUrl)
	}

	@Test
	fun `test uploading product variation image asynchronously`() {
		val productPrice = randomPrice()
		val productCreateRequest = ProductCreateRequest(
			newProduct = UpdatedProduct(
				price = productPrice,
				name = "Product ${randomAlphanumeric(8)}",
				sku = "testVariationImageUploadAsync",
				options = listOf(generateProductRadioOption("Test", listOf("1", "2", "3", "4", "5")))
			)
		)
		val productCreateResult = apiClient.createProduct(productCreateRequest)
		val newProductId = productCreateResult.id
		val testVariationPrice = randomPrice()
		val testVariationWeight = randomWeight()
		val createProductVariationRequest = CreateProductVariationRequest(
			productId = newProductId,
			newVariation = UpdatedVariation(
				sku = "testVariation1",
				quantity = 2,
				price = testVariationPrice,
				weight = testVariationWeight,
				options = listOf(UpdatedVariation.Option(name = "Test", value = "5"))
			)
		)
		val createProductVariationResult = apiClient.createProductVariation(createProductVariationRequest)
		val newVariationId = createProductVariationResult.id
		val uploadImageAsyncReq = ProductVariationImageAsyncUploadRequest(
			productId = newProductId,
			variationId = newVariationId,
			asyncPictureData = AsyncPictureData("https://example.com/dummy.jpg", 100, 200)
		)
		apiClient.uploadProductVariationImageAsync(uploadImageAsyncReq)
		val fetchedProduct = apiClient.getProductDetails(ProductDetailsRequest(productId = newProductId))
		val variations = fetchedProduct.combinations
		assertEquals(variations?.size, 1)
		assertEquals(variations?.get(0)?.originalImageUrl, uploadImageAsyncReq.asyncPictureData.url)
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

private fun generateProductRadioOption(name: String, values: List<String>): UpdatedProduct.ProductOption.RadioOption {
	val choices = values.map { generateProductOptionChoice(it) }
	return UpdatedProduct.ProductOption.createRadioOption(
		name = name,
		choices = choices
	)
}

private fun generateDimensions() = UpdatedVariation.ProductDimensions(
	length = randomDimension(),
	width = randomDimension(),
	height = randomDimension()
)
