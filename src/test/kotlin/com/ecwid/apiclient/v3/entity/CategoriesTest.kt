package com.ecwid.apiclient.v3.entity

import com.ecwid.apiclient.v3.converter.toUpdated
import com.ecwid.apiclient.v3.dto.category.request.*
import com.ecwid.apiclient.v3.dto.category.request.CategoriesSearchRequest.ParentCategory
import com.ecwid.apiclient.v3.dto.category.result.CategoriesSearchResult
import com.ecwid.apiclient.v3.dto.category.result.FetchedCategory
import com.ecwid.apiclient.v3.dto.common.AsyncPictureData
import com.ecwid.apiclient.v3.dto.common.LocalizedValueMap
import com.ecwid.apiclient.v3.dto.common.UploadFileData
import com.ecwid.apiclient.v3.dto.product.request.ProductCreateRequest
import com.ecwid.apiclient.v3.dto.product.request.UpdatedProduct
import com.ecwid.apiclient.v3.exception.EcwidApiException
import com.ecwid.apiclient.v3.util.randomAlphanumeric
import com.ecwid.apiclient.v3.util.randomBoolean
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import java.io.FileInputStream
import java.nio.file.Files

class CategoriesTest : BaseEntityTest() {

	@BeforeEach
	override fun beforeEach() {
		super.beforeEach()

		// We need to start from scratch each time
		initStoreProfile()
		removeAllCategories()
		removeAllProducts()
	}

	@Test
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
		val searchCategoriesResult1 = waitForIndexedCategories(
			categoriesSearchRequest = CategoriesSearchRequest(
				parentCategoryId = ParentCategory.WithId(categoryCreateResult2.id),
				hiddenCategories = true
			),
			desiredCategoriesCount = 1
		)
		assertEquals(1, searchCategoriesResult1.total)
		assertCategory(
			desiredId = categoryCreateResult3.id,
			desiredProductIds = null,
			categoriesSearchResult = searchCategoriesResult1
		)

		val searchCategoriesResult2 = waitForIndexedCategories(
			categoriesSearchRequest = CategoriesSearchRequest(
				parentCategoryId = ParentCategory.Root,
				hiddenCategories = true
			),
			desiredCategoriesCount = 2
		)
		assertEquals(2, searchCategoriesResult2.total)
		assertCategory(
			desiredId = categoryCreateResult1.id,
			desiredProductIds = null,
			categoriesSearchResult = searchCategoriesResult2
		)
		assertCategory(
			desiredId = categoryCreateResult2.id,
			desiredProductIds = null,
			categoriesSearchResult = searchCategoriesResult2
		)

		val searchCategoriesResult3 = waitForIndexedCategories(
			categoriesSearchRequest = CategoriesSearchRequest(
				parentCategoryId = ParentCategory.Any,
				hiddenCategories = true,
				returnProductIds = true
			),
			desiredCategoriesCount = 3
		)
		assertEquals(3, searchCategoriesResult3.total)
		assertCategory(
			desiredId = categoryCreateResult1.id,
			desiredProductIds = null,
			categoriesSearchResult = searchCategoriesResult3
		)
		assertCategory(
			desiredId = categoryCreateResult2.id,
			desiredProductIds = null,
			categoriesSearchResult = searchCategoriesResult3
		)
		assertCategory(
			desiredId = categoryCreateResult3.id,
			desiredProductIds = listOf(productCreateResult1.id, productCreateResult2.id),
			categoriesSearchResult = searchCategoriesResult3
		)

		val searchCategoriesResult4 = waitForIndexedCategories(
			categoriesSearchRequest = CategoriesSearchRequest(
				parentCategoryId = ParentCategory.Any,
				hiddenCategories = true
			),
			desiredCategoriesCount = 3
		)
		assertEquals(3, searchCategoriesResult3.total)
		assertCategory(
			desiredId = categoryCreateResult1.id,
			desiredProductIds = null,
			categoriesSearchResult = searchCategoriesResult4
		)
		assertCategory(
			desiredId = categoryCreateResult2.id,
			desiredProductIds = null,
			categoriesSearchResult = searchCategoriesResult4
		)
		assertCategory(
			desiredId = categoryCreateResult3.id,
			desiredProductIds = null,
			categoriesSearchResult = searchCategoriesResult4
		)

		val searchCategoriesResult5 = waitForIndexedCategories(
			categoriesSearchRequest = CategoriesSearchRequest(
				parentCategoryId = ParentCategory.Any,
				hiddenCategories = false
			),
			desiredCategoriesCount = 1
		)
		assertEquals(1, searchCategoriesResult5.total)
		assertCategory(
			desiredId = categoryCreateResult1.id,
			desiredProductIds = null,
			categoriesSearchResult = searchCategoriesResult5
		)
	}

	@Test
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
			urlPattern = "https://.*.company.site.*/products/Category-.*c.*"
		)
		assertCategoryUrlMatchesRegex(
			categorySearchRequest = CategoriesSearchRequest(
				cleanUrls = true
			),
			urlPattern = "https://.*.company.site.*/Category-.*-c.*"
		)
		assertCategoryUrlMatchesRegex(
			categorySearchRequest = CategoriesSearchRequest(
				cleanUrls = false
			),
			urlPattern = "https://.*.company.site.*/products#!/Category-.*/c/.*"
		)
		assertCategoryUrlMatchesRegex(
			categorySearchRequest = CategoriesSearchRequest(
				baseUrl = "https://google.com/"
			),
			urlPattern = "https://google.com/#!/Category-.*/c/.*"
		)
		assertCategoryUrlMatchesRegex(
			categorySearchRequest = CategoriesSearchRequest(
				baseUrl = "https://google.com/",
				cleanUrls = true
			),
			urlPattern = "https://google.com/Category-.*-c.*"
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
		repeat(3) {
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
	@Disabled("Will be fixed in ECWID-75364")
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

	@Test
	fun testUploadCategoryImageAsync() {
		// Creating new category
		val categoryCreateRequest = CategoryCreateRequest(
			newCategory = generateTestCategory()
		)
		val categoryCreateResult = apiClient.createCategory(categoryCreateRequest)
		assertTrue(categoryCreateResult.id > 0)

		val categoryDetailsRequest = CategoryDetailsRequest(categoryId = categoryCreateResult.id)
		val categoryDetails = apiClient.getCategoryDetails(categoryDetailsRequest)
		assertNull(categoryDetails.imageUrl)

		// Try to send async image upload request
		val request = CategoryImageAsyncUploadRequest(
			categoryId = categoryCreateResult.id,
			asyncPictureData = AsyncPictureData(
				url = "https://don16obqbay2c.cloudfront.net/favicons/apple-touch-icon-180x180.png",
				width = 180,
				height = 180
			)
		)
		apiClient.uploadCategoryImageAsync(request)

		val categoryDetailsAfterUpload = apiClient.getCategoryDetails(categoryDetailsRequest)
		assertNotNull(categoryDetailsAfterUpload.imageUrl)

		val requestWithBlankUrl = CategoryImageAsyncUploadRequest(
			categoryId = categoryCreateResult.id,
			asyncPictureData = AsyncPictureData(
				url = "  ",
				width = 180,
				height = 180
			)
		)
		try {
			apiClient.uploadCategoryImageAsync(requestWithBlankUrl)
			fail(message = "Request must return error")
		} catch (ignore: EcwidApiException) {
			// ok
		}

		val requestWithWrongUrl = CategoryImageAsyncUploadRequest(
			categoryId = categoryCreateResult.id,
			asyncPictureData = AsyncPictureData(
				url = "htt://sssss",
				width = 180,
				height = 180
			)
		)
		try {
			apiClient.uploadCategoryImageAsync(requestWithWrongUrl)
			fail(message = "Request must return error")
		} catch (ignore: EcwidApiException) {
			// ok
		}
	}

	@Test
	fun testAssignProductsToCategory() {
		// Create new products to put into new categories
		val productCreateRequest1 = ProductCreateRequest(generateTestProduct("testAssignProductsToCategory", enabled = true))
		val productCreateResult1 = apiClient.createProduct(productCreateRequest1)
		assertTrue(productCreateResult1.id > 0)

		val productCreateRequest2 = ProductCreateRequest(generateTestProduct("testAssignProductsToCategory", enabled = false))
		val productCreateResult2 = apiClient.createProduct(productCreateRequest2)
		assertTrue(productCreateResult2.id > 0)

		// Create new categories
		val categoryCreateRequest1 = CategoryCreateRequest(generateTestCategory(enabled = true))
		val categoryCreateResult1 = apiClient.createCategory(categoryCreateRequest1)
		assertTrue(categoryCreateResult1.id > 0)

		val categoryCreateRequest2 = CategoryCreateRequest(generateTestCategory(enabled = false))
		val categoryCreateResult2 = apiClient.createCategory(categoryCreateRequest2)
		assertTrue(categoryCreateResult2.id > 0)

		// Assign products to category
		val productIds = listOf(productCreateResult1.id, productCreateResult2.id)

		val assignRequest1 = AssignProductsToCategoryRequest(categoryCreateResult1.id, productIds)
		val assignResult1 = apiClient.assignProductsToCategory(assignRequest1)
		assertEquals(1, assignResult1.updateCount)

		val assignRequest2 = AssignProductsToCategoryRequest(categoryCreateResult2.id, productIds)
		val assignResult2 = apiClient.assignProductsToCategory(assignRequest2)
		assertEquals(1, assignResult2.updateCount)

		val categoryAfterAssign1 = apiClient.getCategoryDetails(CategoryDetailsRequest(categoryCreateResult1.id))
		assertEquals(productIds.toSet(), categoryAfterAssign1.productIds?.toSet())

		val categoryAfterAssign2 = apiClient.getCategoryDetails(CategoryDetailsRequest(categoryCreateResult2.id))
		assertEquals(productIds.toSet(), categoryAfterAssign2.productIds?.toSet())

		// Unassign products from category
		val unassignRequest1 = UnassignProductsFromCategoryRequest(categoryCreateResult1.id, listOf(productCreateResult1.id))
		val unassignResult1 = apiClient.unassignProductsFromCategory(unassignRequest1)
		assertEquals(1, unassignResult1.deleteCount)

		val unassignRequest2 = UnassignProductsFromCategoryRequest(categoryCreateResult2.id, listOf(productCreateResult2.id))
		val unassignResult2 = apiClient.unassignProductsFromCategory(unassignRequest2)
		assertEquals(1, unassignResult2.deleteCount)

		val categoryAfterUnassign1 = apiClient.getCategoryDetails(CategoryDetailsRequest(categoryCreateResult1.id))
		assertEquals(listOf(productCreateResult2.id), categoryAfterUnassign1.productIds)

		val categoryAfterUnassign2 = apiClient.getCategoryDetails(CategoryDetailsRequest(categoryCreateResult2.id))
		assertEquals(listOf(productCreateResult1.id), categoryAfterUnassign2.productIds)
	}

	private fun assertCategory(
		desiredId: Int,
		desiredProductIds: List<Int>?,
		categoriesSearchResult: CategoriesSearchResult
	) {
		val category = categoriesSearchResult.items.find { category ->
			category.id == desiredId
		} ?: fail("Category with id $desiredId not found")

		assertAll(
			{ assertEquals(desiredId, category.id) },
			{ assertEquals(desiredProductIds, category.productIds) }
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

	private fun assertCategoryImage(expectedThumbnailImageId: Long, categoryDetails: FetchedCategory) {
		assertAll(
			{
				assertTrue(
					categoryDetails.thumbnailUrl?.endsWith("/$expectedThumbnailImageId.png")
						?: false,
					"thumbnailUrl mismatch"
				)
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
		isSampleCategory = false,
		seoTitle = "",
		seoTitleTranslated = LocalizedValueMap(
			"ru" to "",
			"en" to ""
		),
		seoDescription = "",
		seoDescriptionTranslated = LocalizedValueMap(
			"ru" to "",
			"en" to ""
		),
		customSlug = ""
	)
}

private fun UpdatedCategory.cleanupForComparison(): UpdatedCategory {
	return copy(
		parentId = parentId ?: 0,
		// orderBy can be changed by internal ecwid rules so reset it
		orderBy = 10
	)
}
