package com.ecwid.apiclient.v3

import com.ecwid.apiclient.v3.converter.toUpdated
import com.ecwid.apiclient.v3.dto.UploadFileData
import com.ecwid.apiclient.v3.dto.category.request.*
import com.ecwid.apiclient.v3.dto.category.request.CategoriesSearchRequest.ParentCategory
import com.ecwid.apiclient.v3.dto.category.result.FetchedCategory
import com.ecwid.apiclient.v3.dto.common.LocalizedValueMap
import com.ecwid.apiclient.v3.dto.product.request.ProductCreateRequest
import com.ecwid.apiclient.v3.dto.product.request.UpdatedProduct
import com.ecwid.apiclient.v3.exception.EcwidApiException
import com.ecwid.apiclient.v3.util.randomAlphanumeric
import com.ecwid.apiclient.v3.util.randomBoolean
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import java.io.FileInputStream
import java.nio.file.Files


class CategoriesTest : BaseEntityTest() {

	@BeforeEach
	override fun beforeEach() {
		super.beforeEach()

		// We need to start from scratch each time
		removeAllCategories()
		removeAllProducts()
	}

	@Test
	@Disabled("Fix in ECWID-66808")
	fun testSearchByFilters() {
		// Creating some products to put into new categories

		val productCreateRequest1 = ProductCreateRequest(
				newProduct = generateTestProduct(nameSuffix = "testSearchByFilters", enabled = true)
		)
		val productCreateResult1 = apiClient.createProduct(productCreateRequest1)
		assertTrue(productCreateResult1.id > 0)

		val productCreateRequest2 = ProductCreateRequest(
				newProduct = generateTestProduct(nameSuffix = "testSearchByFilters", enabled = false)
		)
		val productCreateResult2 = apiClient.createProduct(productCreateRequest2)
		assertTrue(productCreateResult2.id > 0)

		// Creating some categories

		val categoryCreateRequest1 = CategoryCreateRequest(
				newCategory = generateTestCategory(enabled = true)
		)
		val categoryCreateResult1 = apiClient.createCategory(categoryCreateRequest1)
		assertTrue(categoryCreateResult1.id > 0)

		val categoryCreateRequest2 = CategoryCreateRequest(
				newCategory = generateTestCategory(enabled = false)
		)
		val categoryCreateResult2 = apiClient.createCategory(categoryCreateRequest2)
		assertTrue(categoryCreateResult2.id > 0)

		val categoryCreateRequest3 = CategoryCreateRequest(
				newCategory = generateTestCategory(
						parentCategoryId = categoryCreateResult2.id,
						productIds = listOf(productCreateResult1.id, productCreateResult2.id),
						enabled = false
				)
		)
		val categoryCreateResult3 = apiClient.createCategory(categoryCreateRequest3)
		assertTrue(categoryCreateResult3.id > 0)

		// Trying to search by different filters

		val categoriesSearchRequest1 = CategoriesSearchRequest(
				parentCategoryId = ParentCategory.WithId(categoryCreateResult2.id),
				hiddenCategories = true
		)
		val searchCategoriesResult1 = apiClient.searchCategories(categoriesSearchRequest1)
		assertEquals(1, searchCategoriesResult1.total)
		assertCategory(
				desiredId = categoryCreateResult3.id,
				desiredProductIds = null,
				desiredProductCount = 2,
				desiredEnabledProductCount = null,
				category = searchCategoriesResult1.items[0]
		)

		val categoriesSearchRequest2 = CategoriesSearchRequest(
				parentCategoryId = ParentCategory.Root,
				hiddenCategories = true
		)
		val searchCategoriesResult2 = apiClient.searchCategories(categoriesSearchRequest2)
		assertEquals(2, searchCategoriesResult2.total)
		assertCategory(
				desiredId = categoryCreateResult1.id,
				desiredProductIds = null,
				desiredProductCount = 0,
				desiredEnabledProductCount = null,
				category = searchCategoriesResult2.items[0]
		)
		assertCategory(
				desiredId = categoryCreateResult2.id,
				desiredProductIds = null,
				desiredProductCount = 2,
				desiredEnabledProductCount = null,
				category = searchCategoriesResult2.items[1]
		)

		val categoriesSearchRequest3 = CategoriesSearchRequest(
				parentCategoryId = ParentCategory.Any,
				hiddenCategories = true,
				returnProductIds = true
		)
		val searchCategoriesResult3 = apiClient.searchCategories(categoriesSearchRequest3)
		assertEquals(3, searchCategoriesResult3.total)
		assertCategory(
				desiredId = categoryCreateResult1.id,
				desiredProductIds = null,
				desiredProductCount = 0,
				desiredEnabledProductCount = null,
				category = searchCategoriesResult3.items[0]
		)
		assertCategory(
				desiredId = categoryCreateResult2.id,
				desiredProductIds = null,
				desiredProductCount = 2,
				desiredEnabledProductCount = null,
				category = searchCategoriesResult3.items[1]
		)
		assertCategory(
				desiredId = categoryCreateResult3.id,
				desiredProductIds = listOf(productCreateResult1.id, productCreateResult2.id),
				desiredProductCount = 2,
				desiredEnabledProductCount = 1,
				category = searchCategoriesResult3.items[2]
		)

		val categoriesSearchRequest4 = CategoriesSearchRequest(
				parentCategoryId = ParentCategory.Any,
				hiddenCategories = true
		)
		val searchCategoriesResult4 = apiClient.searchCategories(categoriesSearchRequest4)
		assertEquals(3, searchCategoriesResult3.total)
		assertCategory(
				desiredId = categoryCreateResult1.id,
				desiredProductIds = null,
				desiredProductCount = 0,
				desiredEnabledProductCount = null,
				category = searchCategoriesResult4.items[0]
		)
		assertCategory(
				desiredId = categoryCreateResult2.id,
				desiredProductIds = null,
				desiredProductCount = 2,
				desiredEnabledProductCount = null,
				category = searchCategoriesResult4.items[1]
		)
		assertCategory(
				desiredId = categoryCreateResult3.id,
				desiredProductIds = null,
				desiredProductCount = 2,
				desiredEnabledProductCount = null,
				category = searchCategoriesResult4.items[2]
		)

		val categoriesSearchRequest5 = CategoriesSearchRequest(
				parentCategoryId = ParentCategory.Any,
				hiddenCategories = false
		)
		val searchCategoriesResult5 = apiClient.searchCategories(categoriesSearchRequest5)
		assertEquals(1, searchCategoriesResult5.total)
		assertCategory(
				desiredId = categoryCreateResult1.id,
				desiredProductIds = null,
				desiredProductCount = 0,
				desiredEnabledProductCount = null,
				category = searchCategoriesResult5.items[0]
		)
	}

	@Test
	@Disabled("Fix in ECWID-66808")
	fun testSearchUrls() {
		// Create one category
		val categoryCreateRequest = CategoryCreateRequest(
				newCategory = generateTestCategory(enabled = true)
		)
		val categoryCreateResult = apiClient.createCategory(categoryCreateRequest)
		assertTrue(categoryCreateResult.id > 0)

		// Searching categories with different combinations of baseUrl and cleanUrls parameters
		assertCategoryUrlMatchesRegex(
				categorySearchRequest = CategoriesSearchRequest(),
				urlPattern = "https://store.*.ecwid.com/Category-.*-c.*"
		)
		assertCategoryUrlMatchesRegex(
				categorySearchRequest = CategoriesSearchRequest(
						baseUrl = "https://google.com/"
				),
				urlPattern = "https://google.com/Category-.*-c.*"
		)
		assertCategoryUrlMatchesRegex(
				categorySearchRequest = CategoriesSearchRequest(
						cleanUrls = false
				),
				urlPattern = "https://store.*.ecwid.com/#!/Category-.*/c/.*"
		)
		assertCategoryUrlMatchesRegex(
				categorySearchRequest = CategoriesSearchRequest(
						baseUrl = "https://google.com/",
						cleanUrls = false
				),
				urlPattern = "https://google.com/#!/Category-.*/c/.*"
		)
	}

	@Test
	fun testSearchPaging() {
		// Create some categories
		for (i in 1..3) {
			val categoryCreateRequest = CategoryCreateRequest(
					newCategory = generateTestCategory(enabled = true)
			)
			val categoryCreateResult = apiClient.createCategory(categoryCreateRequest)
			assertTrue(categoryCreateResult.id > 0)
		}

		val categorySearchRequest = CategoriesSearchRequest(offset = 2, limit = 2)
		val categoriesSearchResult = apiClient.searchCategories(categorySearchRequest)
		assertEquals(1, categoriesSearchResult.count)
		assertEquals(3, categoriesSearchResult.total)
	}

	@Test
	@Disabled("Fix in ECWID-66808")
	fun testCategoryLifecycle() {
		val nameSuffix = "testCategoryLifecycle"

		// Creating some products to put into new categories

		val productCreateRequest1 = ProductCreateRequest(
				newProduct = generateTestProduct(nameSuffix = nameSuffix)
		)
		val productCreateResult1 = apiClient.createProduct(productCreateRequest1)
		assertTrue(productCreateResult1.id > 0)

		val productCreateRequest2 = ProductCreateRequest(
				newProduct = generateTestProduct(nameSuffix = nameSuffix)
		)
		val productCreateResult2 = apiClient.createProduct(productCreateRequest2)
		assertTrue(productCreateResult2.id > 0)

		// Creating two categories in hierarchy

		val categoryCreateRequest1 = CategoryCreateRequest(
				newCategory = generateTestCategory()
		)
		val categoryCreateResult1 = apiClient.createCategory(categoryCreateRequest1)
		assertTrue(categoryCreateResult1.id > 0)

		val categoryCreateRequest2 = CategoryCreateRequest(
				newCategory = generateTestCategory(
						parentCategoryId = categoryCreateResult1.id,
						productIds = listOf(productCreateResult1.id, productCreateResult2.id)
				)
		)
		val categoryCreateResult2 = apiClient.createCategory(categoryCreateRequest2)
		assertTrue(categoryCreateResult2.id > 0)

		// Checking that inner category was successfully created with necessary parameters
		val categoryDetailsRequest = CategoryDetailsRequest(categoryId = categoryCreateResult2.id)
		val categoryDetails1 = apiClient.getCategoryDetails(categoryDetailsRequest)
		assertEquals(
				categoryCreateRequest2.newCategory,
				categoryDetails1.toUpdated()
		)

		// Completely updating newly created category
		val categoryUpdateRequest = CategoryUpdateRequest(
				categoryId = categoryDetails1.id,
				updatedCategory = generateTestCategory(
						parentCategoryId = 0,
						productIds = listOf(productCreateResult2.id)
				)
		)
		val categoryUpdateResult1 = apiClient.updateCategory(categoryUpdateRequest)
		assertEquals(1, categoryUpdateResult1.updateCount)

		// Checking that category was successfully updated with necessary parameters
		val categoryDetails2 = apiClient.getCategoryDetails(categoryDetailsRequest)
		assertEquals(
				categoryUpdateRequest.updatedCategory,
				categoryDetails2.toUpdated().cleanupForComparison()
		)

		// Deleting category
		val categoryDeleteRequest = CategoryDeleteRequest(categoryId = categoryDetails1.id)
		val categoryDeleteResult = apiClient.deleteCategory(categoryDeleteRequest)
		assertEquals(1, categoryDeleteResult.deleteCount)

		// Checking that deleted category is not accessible anymore
		try {
			apiClient.getCategoryDetails(categoryDetailsRequest)
		} catch (e: EcwidApiException) {
			assertEquals(404, e.statusCode)
			assertEquals("Category #${categoryCreateResult2.id} not found", e.message)
		}
	}

	@Test
	@Disabled("Fix in ECWID-66808")
	fun testTranslations() {
		// Creating new category
		val categoryCreateRequest = CategoryCreateRequest(
				newCategory = generateTestCategory()
		)
		val categoryCreateResult = apiClient.createCategory(categoryCreateRequest)
		assertTrue(categoryCreateResult.id > 0)

		val categoryDetails = apiClient.getCategoryDetails(
				CategoryDetailsRequest(
						categoryId = categoryCreateResult.id
				)
		)
		val descriptionTranslated = categoryDetails.descriptionTranslated
		require(descriptionTranslated != null)
		assertTrue(descriptionTranslated.size > 0)
		assertTrue(descriptionTranslated.containsKey("ru"))
		assertTrue(descriptionTranslated.getValue("ru").startsWith("Описание"))


		val nameTranslated = categoryDetails.nameTranslated
		require(nameTranslated != null)
		assertTrue(nameTranslated.size > 0)
		assertTrue(nameTranslated.containsKey("ru"))
		assertTrue(nameTranslated.getValue("ru").startsWith("Категория"))
	}

	@Test
	fun testManipulateCategoryImage() {
		// Creating new category
		val categoryCreateRequest = CategoryCreateRequest(
				newCategory = generateTestCategory()
		)
		val categoryCreateResult = apiClient.createCategory(categoryCreateRequest)
		assertTrue(categoryCreateResult.id > 0)

		// Upload some images from different sources

		val categoryImageUploadRequest1 = CategoryImageUploadRequest(
				categoryId = categoryCreateResult.id,
				fileData = UploadFileData.ExternalUrlData(externalUrl = "https://don16obqbay2c.cloudfront.net/favicons/apple-touch-icon-180x180.png")
		)
		val categoryImageUploadResult1 = apiClient.uploadCategoryImage(categoryImageUploadRequest1)
		assertTrue(categoryImageUploadResult1.id > 0)

		val categoryImageUploadRequest2 = CategoryImageUploadRequest(
				categoryId = categoryCreateResult.id,
				fileData = UploadFileData.LocalFileData(file = getTestPngFilePath().toFile())
		)
		val categoryImageUploadResult2 = apiClient.uploadCategoryImage(categoryImageUploadRequest2)
		assertTrue(categoryImageUploadResult2.id > 0)

		val categoryImageUploadRequest3 = CategoryImageUploadRequest(
				categoryId = categoryCreateResult.id,
				fileData = UploadFileData.InputStreamData(stream = FileInputStream(getTestPngFilePath().toFile()))
		)
		val categoryImageUploadResult3 = apiClient.uploadCategoryImage(categoryImageUploadRequest3)
		assertTrue(categoryImageUploadResult3.id > 0)

		val categoryImageUploadRequest4 = CategoryImageUploadRequest(
				categoryId = categoryCreateResult.id,
				fileData = UploadFileData.ByteArrayData(bytes = Files.readAllBytes(getTestPngFilePath()))
		)
		val categoryImageUploadResult4 = apiClient.uploadCategoryImage(categoryImageUploadRequest4)
		assertTrue(categoryImageUploadResult4.id > 0)

		// Checking that category has last uploaded image
		val categoryDetailsRequest = CategoryDetailsRequest(categoryId = categoryCreateResult.id)
		val categoryDetails1 = apiClient.getCategoryDetails(categoryDetailsRequest)
		assertCategoryImage(
				expectedThumbnailImageId = categoryImageUploadResult4.id,
				categoryDetails = categoryDetails1
		)

		// Now delete category image
		val categoryImageDeleteRequest = CategoryImageDeleteRequest(categoryId = categoryCreateResult.id)
		val categoryImageDeleteResult = apiClient.deleteCategoryImage(categoryImageDeleteRequest)
		assertTrue(categoryImageDeleteResult.deleteCount > 0)

		// Check that category has now no main image now
		val categoryDetails2 = apiClient.getCategoryDetails(categoryDetailsRequest)
		assertNoCategoryImage(categoryDetails = categoryDetails2)
	}

	private fun assertCategory(desiredId: Int, desiredProductIds: List<Int>?, desiredProductCount: Int, desiredEnabledProductCount: Int?, category: FetchedCategory) {
		assertAll(
				{ assertEquals(desiredId, category.id) },
				{ assertEquals(desiredProductIds, category.productIds) },
				{ assertEquals(desiredProductCount, category.productCount) },
				{ assertEquals(desiredEnabledProductCount, category.enabledProductCount) }
		)
	}

	private fun assertCategoryUrlMatchesRegex(categorySearchRequest: CategoriesSearchRequest, urlPattern: String) {
		val searchCategories = apiClient.searchCategories(categorySearchRequest)
		assertEquals(1, searchCategories.total)

		val url = searchCategories.items[0].url ?: ""
		assertTrue(
				url.matches(Regex(urlPattern)),
				"Url '$url' is not matching regex pattern '$urlPattern'"
		)
	}

	private fun assertCategoryImage(expectedThumbnailImageId: Int, categoryDetails: FetchedCategory) {
		assertAll(
				{
					assertTrue(categoryDetails.thumbnailUrl?.endsWith("/$expectedThumbnailImageId.jpg")
							?: false, "thumbnailUrl mismatch")
				},
				{ assertFalse(categoryDetails.hdThumbnailUrl.isNullOrEmpty(), "hdThumbnailUrl is empty") },
				// { assertFalse(categoryDetails.imageUrl.isNullOrEmpty(), "imageUrl is empty") }, // TODO Cannot test due to bug https://track.ecwid.com/youtrack/issue/ECWID-53222
				{ assertFalse(categoryDetails.originalImageUrl.isNullOrEmpty(), "originalImageUrl is empty") },
				{ assertFalse(categoryDetails.originalImage?.url.isNullOrEmpty(), "originalImage.url is empty") },
				{ assertTrue((categoryDetails.originalImage?.width ?: 0) > 0, "originalImage.width is zero") },
				{ assertTrue((categoryDetails.originalImage?.height ?: 0) > 0, "originalImage.height is zero") }
		)
	}

	private fun assertNoCategoryImage(categoryDetails: FetchedCategory) {
		assertAll(
				{ assertNull(categoryDetails.thumbnailUrl, "thumbnailUrl not empty") },
				{ assertNull(categoryDetails.hdThumbnailUrl, "hdThumbnailUrl is not empty") },
				// { assertNull(categoryDetails.imageUrl, "imageUrl is not empty") }, // TODO Cannot test due to bug https://track.ecwid.com/youtrack/issue/ECWID-53222
				{ assertNull(categoryDetails.originalImageUrl, "originalImageUrl is not empty") },
				{ assertNull(categoryDetails.originalImage, "originalImage is not null") }
		)
	}

}

private fun generateTestProduct(
		nameSuffix: String,
		enabled: Boolean = randomBoolean()
): UpdatedProduct {
	return UpdatedProduct(
			name = "Product $nameSuffix: ${randomAlphanumeric(8)}",
			enabled = enabled
	)
}

private fun generateTestCategory(
		parentCategoryId: Int? = null,
		productIds: List<Int> = listOf(),
		enabled: Boolean = randomBoolean()
): UpdatedCategory {
	val name = "Category " + randomAlphanumeric(8)
	val description = "Description " + randomAlphanumeric(16)

	return UpdatedCategory(
			parentId = parentCategoryId,
			orderBy = 10,
			name = name,
			nameTranslated = LocalizedValueMap(
					"ru" to "Категория " + randomAlphanumeric(8),
					"en" to name
			),
			description = description,
			descriptionTranslated = LocalizedValueMap(
					"ru" to "Описание " + randomAlphanumeric(16),
					"en" to description
			),
			enabled = enabled,
			productIds = productIds,
			isSampleCategory = false
	)
}

private fun UpdatedCategory.cleanupForComparison(): UpdatedCategory {
	return copy(
			parentId = parentId ?: 0
	)
}
