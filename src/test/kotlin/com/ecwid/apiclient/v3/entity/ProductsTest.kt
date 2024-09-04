package com.ecwid.apiclient.v3.entity

import com.ecwid.apiclient.v3.converter.toUpdated
import com.ecwid.apiclient.v3.dto.category.request.CategoriesSearchRequest
import com.ecwid.apiclient.v3.dto.category.request.CategoriesSearchRequest.ParentCategory
import com.ecwid.apiclient.v3.dto.category.request.CategoryCreateRequest
import com.ecwid.apiclient.v3.dto.category.request.UpdatedCategory
import com.ecwid.apiclient.v3.dto.common.*
import com.ecwid.apiclient.v3.dto.product.enums.PriceModifierType
import com.ecwid.apiclient.v3.dto.product.enums.ShippingSettingsType
import com.ecwid.apiclient.v3.dto.product.request.*
import com.ecwid.apiclient.v3.dto.product.request.GetProductFiltersRequest.*
import com.ecwid.apiclient.v3.dto.product.request.ProductInventoryUpdateRequest.InventoryAdjustment
import com.ecwid.apiclient.v3.dto.product.request.ProductsSearchRequest.*
import com.ecwid.apiclient.v3.dto.product.request.UpdatedProduct.*
import com.ecwid.apiclient.v3.dto.product.result.FetchedProduct
import com.ecwid.apiclient.v3.dto.product.result.GetProductFiltersResult
import com.ecwid.apiclient.v3.dto.product.result.GetProductFiltersResult.AttributeFilterValues
import com.ecwid.apiclient.v3.dto.product.result.GetProductFiltersResult.InventoryFilterValues.InventoryFilterValue
import com.ecwid.apiclient.v3.dto.product.result.GetProductFiltersResult.OnSaleFilterValues.OnSaleFilterValue
import com.ecwid.apiclient.v3.dto.product.result.GetProductFiltersResult.OptionFilterValues
import com.ecwid.apiclient.v3.exception.EcwidApiException
import com.ecwid.apiclient.v3.util.*
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import java.io.FileInputStream
import java.nio.file.Files
import java.util.*

class ProductsTest : BaseEntityTest() {

	@BeforeEach
	override fun beforeEach() {
		super.beforeEach()

		// We need to start from scratch each time
		initStoreProfile()
		removeAllCategories()
		removeAllProducts()
	}

	@Test
	@Disabled
	fun testSearchByFilters() {
		// Create some categories

		val categoryCreateRequest1 = CategoryCreateRequest(newCategory = generateTestCategory())
		val categoryCreateResult1 = apiClient.createCategory(categoryCreateRequest1)
		assertTrue(categoryCreateResult1.id > 0)

		val categoryCreateRequest2 = CategoryCreateRequest(
			newCategory = generateTestCategory(parentId = categoryCreateResult1.id)
		)
		val categoryCreateResult2 = apiClient.createCategory(categoryCreateRequest2)
		assertTrue(categoryCreateResult2.id > 0)

		// Creating new product

		val productNameSuffix = randomAlphanumeric(8)
		val brandName = "Brand " + randomAlphanumeric(8)
		val upc = "UPC " + randomAlphanumeric(8)
		val price = randomPrice()
		val productCreateRequest = ProductCreateRequest(
			newProduct = UpdatedProduct(
				name = "Product $productNameSuffix",
				sku = "testSearchByFilters",
				price = price,
				compareToPrice = 2 * price,
				enabled = true,
				quantity = 10,
				categoryIds = listOf(categoryCreateResult2.id),
				attributes = listOf(
					AttributeValue.createBrandAttributeValue(brandName),
					AttributeValue.createUpcAttributeValue(upc)
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

		// Waiting till product became available for searching
		waitForIndexedProducts(
			productsSearchRequest = ByFilters(keyword = productCreateRequest.newProduct.sku),
			desiredProductCount = 1
		)

		val productDetails = apiClient.getProductDetails(ProductDetailsRequest(productId = productCreateResult.id))

		// Trying to search by different fields

		assertProductsSearch(
			positiveProductId = productCreateResult.id,
			positiveSearchRequest = ByFilters(keyword = productNameSuffix),
			negativeSearchRequest = ByFilters(keyword = productNameSuffix + "foo")
		)

		assertProductsSearch(
			positiveProductId = productCreateResult.id,
			positiveSearchRequest = ByFilters(sku = productCreateRequest.newProduct.sku),
			negativeSearchRequest = ByFilters(sku = productCreateRequest.newProduct.sku + "foo")
		)

		assertProductsSearch(
			positiveProductId = productCreateResult.id,
			positiveSearchRequest = ByFilters(
				priceFrom = productCreateRequest.newProduct.price!! - 10.0,
				priceTo = productCreateRequest.newProduct.price!! + 10.0
			),
			negativeSearchRequest = ByFilters(
				priceFrom = productCreateRequest.newProduct.price!! - 20.0,
				priceTo = productCreateRequest.newProduct.price!! - 10.0
			)
		)

		assertProductsSearch(
			positiveProductId = productCreateResult.id,
			positiveSearchRequest = ByFilters(
				createdFrom = Date.from(productDetails.created.toInstant().minusSeconds(1)),
				createdTo = Date.from(productDetails.created.toInstant().plusSeconds(1))
			),
			negativeSearchRequest = ByFilters(
				createdFrom = Date(0),
				createdTo = Date(0)
			)
		)

		assertProductsSearch(
			positiveProductId = productCreateResult.id,
			positiveSearchRequest = ByFilters(
				updatedFrom = Date.from(productDetails.updated.toInstant().minusSeconds(1)),
				updatedTo = Date.from(productDetails.updated.toInstant().plusSeconds(1))
			),
			negativeSearchRequest = ByFilters(
				updatedFrom = Date(0),
				updatedTo = Date(0)
			)
		)

		assertProductsSearch(
			positiveProductId = productCreateResult.id,
			positiveSearchRequest = ByFilters(enabled = true),
			negativeSearchRequest = ByFilters(enabled = false)
		)

		assertProductsSearch(
			positiveProductId = productCreateResult.id,
			positiveSearchRequest = ByFilters(inventory = true),
			negativeSearchRequest = ByFilters(inventory = false)
		)

		assertProductsSearch(
			positiveProductId = productCreateResult.id,
			positiveSearchRequest = ByFilters(onSale = true),
			negativeSearchRequest = ByFilters(onSale = false)
		)

		assertProductsSearch(
			positiveProductId = productCreateResult.id,
			positiveSearchRequest = ByFilters(
				attributes = ProductSearchAttributes(
					listOf(
						ProductSearchAttributes.AttributeValue("Brand", brandName),
						ProductSearchAttributes.AttributeValue("UPC", upc)
					)
				)
			),
			negativeSearchRequest = ByFilters(
				attributes = ProductSearchAttributes(
					listOf(
						ProductSearchAttributes.AttributeValue("Brand", brandName + "foo"),
						ProductSearchAttributes.AttributeValue("UPC", upc + "foo")
					)
				)
			)
		)

		assertProductsSearch(
			positiveProductId = productCreateResult.id,
			positiveSearchRequest = ByFilters(
				options = ProductSearchOptions(
					listOf(
						ProductSearchOptions.OptionValue("Color", "Yellow")
					)
				)
			),
			negativeSearchRequest = ByFilters(
				options = ProductSearchOptions(
					listOf(
						ProductSearchOptions.OptionValue("Color", "Blue")
					)
				)
			)
		)

		assertProductsSearch(
			productSearchRequest = ByFilters(
				categories = listOf(categoryCreateResult1.id)
			),
			desiredSkus = listOf()
		)
		assertProductsSearch(
			productSearchRequest = ByFilters(
				categories = listOf(categoryCreateResult1.id),
				includeProductsFromSubcategories = true
			),
			desiredSkus = listOf(productDetails.sku)
		)
	}

	@Test
	fun testSearchUrls() {
		// Create one product
		val productCreateRequest = ProductCreateRequest(
			newProduct = UpdatedProduct(
				name = "Product ${randomAlphanumeric(8)}",
				sku = "testSearchUrls"
			)
		)
		val productCreateResult = apiClient.createProduct(productCreateRequest)
		assertTrue(productCreateResult.id > 0)

		// Waiting till product became available for searching
		waitForIndexedProducts(
			productsSearchRequest = ByFilters(keyword = productCreateRequest.newProduct.sku),
			desiredProductCount = 1
		)

		// Searching products with different combinations of baseUrl and cleanUrls parameters
		assertProductUrlMatchesRegex(
			productSearchRequest = ByFilters(keyword = productCreateRequest.newProduct.sku),
			urlPattern = "https://.*.company.site.*/products#!/Product-.*p.*"
		)
		assertProductUrlMatchesRegex(
			productSearchRequest = ByFilters(
				cleanUrls = true,
				keyword = productCreateRequest.newProduct.sku
			),
			urlPattern = "https://.*.company.site.*/Product-.*-p.*"
		)
		assertProductUrlMatchesRegex(
			productSearchRequest = ByFilters(
				cleanUrls = false,
				keyword = productCreateRequest.newProduct.sku
			),
			urlPattern = "https://.*.company.site.*/products#!/Product-.*/p/.*"
		)
		assertProductUrlMatchesRegex(
			productSearchRequest = ByFilters(
				baseUrl = "https://google.com/",
				keyword = productCreateRequest.newProduct.sku
			),
			urlPattern = "https://google.com/#!/Product-.*/p/.*"
		)
		assertProductUrlMatchesRegex(
			productSearchRequest = ByFilters(
				baseUrl = "https://google.com/",
				cleanUrls = true,
				keyword = productCreateRequest.newProduct.sku
			),
			urlPattern = "https://google.com/Product-.*-p.*"
		)
		assertProductUrlMatchesRegex(
			productSearchRequest = ByFilters(
				baseUrl = "https://google.com/",
				cleanUrls = false,
				keyword = productCreateRequest.newProduct.sku
			),
			urlPattern = "https://google.com/#!/Product-.*/p/.*"
		)
	}

	@Test
	fun testSearchOrder() {
		val testName = "testSearchOrder"

		val productSku1 = "${testName}1"
		val productSku2 = "${testName}2"
		val productSku3 = "${testName}3"

		// Create three product
		val productCreateRequest1 = ProductCreateRequest(
			newProduct = UpdatedProduct(
				name = "Product $testName A: ${randomAlphanumeric(8)}",
				sku = productSku1,
				price = 10.0,
				description = "Description B B"
			)
		)
		val productCreateRequest2 = ProductCreateRequest(
			newProduct = UpdatedProduct(
				name = "Product $testName B: ${randomAlphanumeric(8)}",
				sku = productSku2,
				price = 20.0
			)
		)

		val productCreateRequest3 = ProductCreateRequest(
			newProduct = UpdatedProduct(
				name = "Product $testName C: ${randomAlphanumeric(8)}",
				sku = productSku3,
				price = 30.0
			)
		)

		val productCreateResult1 = apiClient.createProduct(productCreateRequest1)
		assertTrue(productCreateResult1.id > 0)

		val productCreateResult2 = apiClient.createProduct(productCreateRequest2)
		assertTrue(productCreateResult2.id > 0)

		val productCreateResult3 = apiClient.createProduct(productCreateRequest3)
		assertTrue(productCreateResult3.id > 0)

		// Waiting till product became available for searching
		waitForIndexedProducts(
			productsSearchRequest = ByFilters(keyword = testName),
			desiredProductCount = 3
		)

		// Trying to search with different sort order

		assertProductsSearch(
			productSearchRequest = ByFilters(keyword = testName, sortBy = SortOrder.ADDED_TIME_ASC),
			desiredSkus = listOf(productSku1, productSku2, productSku3)
		)
		assertProductsSearch(
			productSearchRequest = ByFilters(keyword = testName, sortBy = SortOrder.ADDED_TIME_DESC),
			desiredSkus = listOf(productSku3, productSku2, productSku1)
		)

		assertProductsSearch(
			productSearchRequest = ByFilters(keyword = testName, sortBy = SortOrder.UPDATED_TIME_ASC),
			desiredSkus = listOf(productSku1, productSku2, productSku3)
		)
		assertProductsSearch(
			productSearchRequest = ByFilters(keyword = testName, sortBy = SortOrder.UPDATED_TIME_DESC),
			desiredSkus = listOf(productSku3, productSku2, productSku1)
		)

		assertProductsSearch(
			productSearchRequest = ByFilters(keyword = testName, sortBy = SortOrder.NAME_ASC),
			desiredSkus = listOf(productSku1, productSku2, productSku3)
		)
		assertProductsSearch(
			productSearchRequest = ByFilters(keyword = testName, sortBy = SortOrder.NAME_DESC),
			desiredSkus = listOf(productSku3, productSku2, productSku1)
		)

		assertProductsSearch(
			productSearchRequest = ByFilters(keyword = testName, sortBy = SortOrder.PRICE_ASC),
			desiredSkus = listOf(productSku1, productSku2, productSku3)
		)
		assertProductsSearch(
			productSearchRequest = ByFilters(keyword = testName, sortBy = SortOrder.PRICE_DESC),
			desiredSkus = listOf(productSku3, productSku2, productSku1)
		)

		assertProductsSearch(
			productSearchRequest = ByFilters(keyword = "B", sortBy = SortOrder.RELEVANCE),
			desiredSkus = listOf(productSku2, productSku1)
		)
	}

	@Test
	fun testSearchPaging() {
		val testName = "testSearchPaging"

		// Create some products
		for (i in 1..3) {
			val productCreateRequest = ProductCreateRequest(
				newProduct =
				UpdatedProduct(
					name = "Product $testName " + randomAlphanumeric(8),
					sku = "$testName$i"
				)
			)
			val productCreateResult = apiClient.createProduct(productCreateRequest)
			assertTrue(productCreateResult.id > 0)
		}

		// Waiting till product became available for searching and trying to request only one page
		waitForIndexedProducts(
			productsSearchRequest = ByFilters(keyword = testName),
			desiredProductCount = 3
		)

		val productSearchRequest = ByFilters(keyword = testName, offset = 2, limit = 2)
		val productsSearchResult = apiClient.searchProducts(productSearchRequest)
		assertEquals(1, productsSearchResult.count)
		assertEquals(3, productsSearchResult.total)
	}

	@Test
	@Disabled("Will be fixed in ECWID-75364")
	fun testProductLifecycle() {
		// Create some categories

		val categoryCreateRequest1 = CategoryCreateRequest(newCategory = generateTestCategory())
		val categoryCreateResult1 = apiClient.createCategory(categoryCreateRequest1)
		assertTrue(categoryCreateResult1.id > 0)

		val categoryCreateRequest2 = CategoryCreateRequest(newCategory = generateTestCategory())
		val categoryCreateResult2 = apiClient.createCategory(categoryCreateRequest2)
		assertTrue(categoryCreateResult2.id > 0)

		val categoryCreateRequest3 = CategoryCreateRequest(newCategory = generateTestCategory())
		val categoryCreateResult3 = apiClient.createCategory(categoryCreateRequest3)
		assertTrue(categoryCreateResult3.id > 0)

		val categoryIds = listOf(
			categoryCreateResult1.id,
			categoryCreateResult2.id,
			categoryCreateResult3.id
		)

		// Creating new product
		val productCreateRequest = ProductCreateRequest(
			newProduct = generateTestProduct(categoryIds = categoryIds, discountsAllowed = true)
		)
		val productCreateResult = apiClient.createProduct(productCreateRequest)
		assertTrue(productCreateResult.id > 0)

		// Checking that product was successfully created with necessary parameters
		val productDetailsRequest = ProductDetailsRequest(productId = productCreateResult.id)
		val productDetails1 = apiClient.getProductDetails(productDetailsRequest)
		assertEquals(
			productCreateRequest.newProduct,
			productDetails1.toUpdated().cleanupForComparison(productCreateRequest)
		)

		// Completely updating newly created product
		val productUpdateRequest = ProductUpdateRequest(
			productId = productDetails1.id,
			updatedProduct = generateTestProduct(categoryIds = categoryIds, discountsAllowed = false)
				.withUnchangedShowOnFrontend(productCreateRequest)
		)
		val productUpdateResult1 = apiClient.updateProduct(productUpdateRequest)
		assertEquals(1, productUpdateResult1.updateCount)

		// Checking that product was successfully updated with necessary parameters
		val productDetails2 = apiClient.getProductDetails(productDetailsRequest)
		assertEquals(
			productUpdateRequest.updatedProduct,
			productDetails2.toUpdated().cleanupForComparison(productCreateRequest)
		)
		assertEquals(3, productDetails2.categories?.size)
		assertTrue(
			productDetails2.categories?.contains(
				FetchedProduct.CategoryInfo(
					id = categoryCreateResult1.id,
					enabled = true
				)
			)
				?: false
		)
		assertTrue(
			productDetails2.categories?.contains(
				FetchedProduct.CategoryInfo(
					id = categoryCreateResult2.id,
					enabled = true
				)
			)
				?: false
		)
		assertTrue(
			productDetails2.categories?.contains(
				FetchedProduct.CategoryInfo(
					id = categoryCreateResult3.id,
					enabled = true
				)
			)
				?: false
		)

		// Deleting product
		val productDeleteRequest = ProductDeleteRequest(productId = productDetails1.id)
		val productDeleteResult = apiClient.deleteProduct(productDeleteRequest)
		assertEquals(1, productDeleteResult.deleteCount)

		// Checking that deleted product is not accessible anymore
		try {
			apiClient.getProductDetails(productDetailsRequest)
		} catch (e: EcwidApiException) {
			assertEquals(404, e.statusCode)
			assertEquals("Product #${productCreateResult.id} not found", e.message)
		}
	}

	@Test
	fun testSearchByIds() {
		// Creating three new products
		val productCreateRequest1 = ProductCreateRequest(
			newProduct = UpdatedProduct(
				name = "Product ${randomAlphanumeric(8)}",
				sku = "testSearchByIds1"
			)
		)
		val productCreateRequest2 = ProductCreateRequest(
			newProduct = UpdatedProduct(
				name = "Product ${randomAlphanumeric(8)}",
				sku = "testSearchByIds2"
			)
		)
		val productCreateRequest3 = ProductCreateRequest(
			newProduct = UpdatedProduct(
				name = "Product ${randomAlphanumeric(8)}",
				sku = "testSearchByIds3"
			)
		)

		val productCreateResult1 = apiClient.createProduct(productCreateRequest1)
		assertTrue(productCreateResult1.id > 0)

		val productCreateResult2 = apiClient.createProduct(productCreateRequest2)
		assertTrue(productCreateResult2.id > 0)

		val productCreateResult3 = apiClient.createProduct(productCreateRequest3)
		assertTrue(productCreateResult3.id > 0)

		// Verifying that we will get two products if search request contains two product IDs
		val productsSearchRequest = ByIds(
			listOf(productCreateResult1.id, productCreateResult2.id)
		)
		val productsSearchResult = apiClient.searchProducts(productsSearchRequest)
		assertEquals(2, productsSearchResult.items.size)
	}

	@Test
	fun testUpdateProductInventory() {
		// Creating new product
		val productCreateRequest = ProductCreateRequest(
			newProduct = UpdatedProduct(
				name = "Product " + randomAlphanumeric(8),
				sku = "testUpdateProductInventory",
				quantity = 10
			)
		)
		val productCreateResult = apiClient.createProduct(productCreateRequest)
		assertTrue(productCreateResult.id > 0)

		// Checking that product was successfully created with necessary quantity
		val productDetailsRequest = ProductDetailsRequest(productId = productCreateResult.id)
		val productDetails1 = apiClient.getProductDetails(productDetailsRequest)
		assertEquals(10, productDetails1.quantity)

		// Increasing product quantity
		val productInventoryUpdateRequest1 = ProductInventoryUpdateRequest(
			productId = productCreateResult.id,
			inventoryAdjustment = InventoryAdjustment(5)
		)
		val productInventoryUpdateResult1 = apiClient.updateProductInventory(productInventoryUpdateRequest1)
		assertEquals(1, productInventoryUpdateResult1.updateCount)

		// Checking that product quantity was successfully updated
		val productDetails2 = apiClient.getProductDetails(productDetailsRequest)
		assertEquals(15, productDetails2.quantity)

		// Decreasing product quantity
		val productInventoryUpdateRequest2 = ProductInventoryUpdateRequest(
			productId = productCreateResult.id,
			inventoryAdjustment = InventoryAdjustment(-20)
		)
		val productInventoryUpdateResult2 = apiClient.updateProductInventory(productInventoryUpdateRequest2)
		assertEquals(1, productInventoryUpdateResult2.updateCount)
		assertEquals("Product has negative stock.", productInventoryUpdateResult2.warning)

		// Checking that product quantity was successfully updated
		val productDetails3 = apiClient.getProductDetails(productDetailsRequest)
		assertEquals(-5, productDetails3.quantity)
	}

	@Test
	@Disabled
	fun testGetProductFilters() {
		// Create one category

		val categoryCreateRequest = CategoryCreateRequest(newCategory = generateTestCategory())
		val categoryCreateResult = apiClient.createCategory(categoryCreateRequest)
		assertTrue(categoryCreateResult.id > 0)

		// Creating some products

		val baseProductSku = "testGetProductFilters"

		val productCreateRequest1 = ProductCreateRequest(
			newProduct = UpdatedProduct(
				name = "Product " + randomAlphanumeric(8),
				sku = "$baseProductSku 1",
				price = 10.0,
				quantity = 5,
				compareToPrice = 12.0,
				categoryIds = listOf(
					categoryCreateResult.id
				),
				options = listOf(
					ProductOption.createSelectOption(
						name = "Color",
						choices = listOf(
							ProductOptionChoice("Red"),
							ProductOptionChoice("Green"),
						)
					)
				),
				attributes = listOf(
					AttributeValue.createBrandAttributeValue("MyBrandName"),
					AttributeValue.createUpcAttributeValue("UpcValue1")
				)
			)
		)
		val productCreateResult1 = apiClient.createProduct(productCreateRequest1)
		assertTrue(productCreateResult1.id > 0)

		val productCreateRequest2 = ProductCreateRequest(
			newProduct = UpdatedProduct(
				name = "Product " + randomAlphanumeric(8),
				sku = "$baseProductSku 2",
				price = 5.0,
				quantity = 0,
				options = listOf(
					ProductOption.createSelectOption(
						name = "Color",
						choices = listOf(
							ProductOptionChoice("Yellow"),
							ProductOptionChoice("Green"),
						)
					)
				),
				attributes = listOf(
					AttributeValue.createBrandAttributeValue("MyBrandName"),
					AttributeValue.createUpcAttributeValue("UpcValue2")
				)
			)
		)
		val productCreateResult2 = apiClient.createProduct(productCreateRequest2)
		assertTrue(productCreateResult2.id > 0)

		// Waiting till product and categories became available for searching

		waitForIndexedCategories(
			categoriesSearchRequest = CategoriesSearchRequest(
				parentCategoryId = ParentCategory.Root,
			),
			desiredCategoriesCount = 1
		)
		waitForIndexedProducts(
			productsSearchRequest = ByFilters(keyword = baseProductSku),
			desiredProductCount = 2
		)

		// Perform faceted search

		val getProductFiltersRequest = GetProductFiltersRequest(
			filterFields = listOf(
				FilterFieldType.Price,
				FilterFieldType.Inventory,
				FilterFieldType.OnSale,
				FilterFieldType.Categories,
				FilterFieldType.Option("Color"),
				FilterFieldType.Attribute("Brand"),
				FilterFieldType.Attribute("UPC"),
			),
			filterFacetLimits = mapOf(
				FilterFieldType.Option("Color") to FilterFacetLimit.Limit(2)
			),
			filterParentCategoryId = FilterCategoryId.Home,
			keyword = baseProductSku
		)
		val getProductFiltersResult = apiClient.getProductFilters(getProductFiltersRequest)

		// Validate faceted search result

		assertEquals(2, getProductFiltersResult.productCount)

		val filters = getProductFiltersResult.filters

		val priceFilter = filters.price
		requireNotNull(priceFilter)
		assertEquals(5.0, priceFilter.minValue, 1e-5)
		assertEquals(10.0, priceFilter.maxValue, 1e-5)

		val inventoryFilterValues = filters.inventory
		requireNotNull(inventoryFilterValues)
		assertEquals(2, inventoryFilterValues.values.size)
		assertEquals(
			InventoryFilterValue(id = "instock", title = "In stock", productCount = 1),
			inventoryFilterValues.values[0]
		)
		assertEquals(
			InventoryFilterValue(id = "outofstock", title = "Out of stock", productCount = 1),
			inventoryFilterValues.values[1]
		)

		val onSaleFilterValues = filters.onsale
		requireNotNull(onSaleFilterValues)
		assertEquals(2, onSaleFilterValues.values.size)
		assertEquals(
			OnSaleFilterValue(id = "onsale", title = "On sale", productCount = 1),
			onSaleFilterValues.values[0]
		)
		assertEquals(
			OnSaleFilterValue(id = "notonsale", title = "Regular price", productCount = 1),
			onSaleFilterValues.values[1]
		)

		// Will be fixed in ECWID-75364
		// val categoriesFilterValues = filters.categories
		// requireNotNull(categoriesFilterValues)
		// assertEquals(1, categoriesFilterValues.values.size)
		// assertEquals(
		// 	CategoriesFilterValue(
		// 		id = categoryCreateResult.id.toLong(),
		// 		title = categoryCreateRequest.newCategory.name ?: "",
		// 		productCount = 1
		// 	),
		// 	categoriesFilterValues.values[0]
		// )

		val options = filters.options
		requireNotNull(options)
		assertEquals(1, options.size)
		assertEquals(
			OptionFilterValues(
				title = "Color",
				values = listOf(
					OptionFilterValues.OptionFilterValue(title = "Green", productCount = 2),
					OptionFilterValues.OptionFilterValue(title = "Red", productCount = 1),
				)
			),
			options[GetProductFiltersResult.Option("Color")]
		)

		val attributes = filters.attributes
		requireNotNull(attributes)
		assertEquals(2, attributes.size)
		assertEquals(
			AttributeFilterValues(
				values = listOf(
					AttributeFilterValues.AttributeFilterValue(title = "MyBrandName", productCount = 2),
				)
			),
			attributes[GetProductFiltersResult.Attribute("Brand")]
		)
		assertEquals(
			AttributeFilterValues(
				values = listOf(
					AttributeFilterValues.AttributeFilterValue(title = "UpcValue1", productCount = 1),
					AttributeFilterValues.AttributeFilterValue(title = "UpcValue2", productCount = 1),
				)
			),
			attributes[GetProductFiltersResult.Attribute("UPC")]
		)
	}

	@Test
	@Disabled
	fun testManipulateProductImage() {
		// Creating new product
		val productCreateRequest = ProductCreateRequest(
			newProduct = UpdatedProduct(
				name = "Product ${randomAlphanumeric(8)}",
				sku = "testManipulateProductImage"
			)
		)
		val productCreateResult = apiClient.createProduct(productCreateRequest)
		assertTrue(productCreateResult.id > 0)

		// Upload some images from different sources

		val productImageUploadRequest1 = ProductImageUploadRequest(
			productId = productCreateResult.id,
			fileData = UploadFileData.ExternalUrlData(externalUrl = "https://don16obqbay2c.cloudfront.net/favicons/apple-touch-icon-180x180.png")
		)
		val productImageUploadResult1 = apiClient.uploadProductImage(productImageUploadRequest1)
		assertTrue(productImageUploadResult1.id > 0)

		val productImageUploadRequest2 = ProductImageUploadRequest(
			productId = productCreateResult.id,
			fileData = UploadFileData.LocalFileData(file = getTestPngFilePath().toFile())
		)
		val productImageUploadResult2 = apiClient.uploadProductImage(productImageUploadRequest2)
		assertTrue(productImageUploadResult2.id > 0)

		val productImageUploadRequest3 = ProductImageUploadRequest(
			productId = productCreateResult.id,
			fileData = UploadFileData.InputStreamData(stream = FileInputStream(getTestPngFilePath().toFile()))
		)
		val productImageUploadResult3 = apiClient.uploadProductImage(productImageUploadRequest3)
		assertTrue(productImageUploadResult3.id > 0)

		val productImageUploadRequest4 = ProductImageUploadRequest(
			productId = productCreateResult.id,
			fileData = UploadFileData.ByteArrayData(bytes = Files.readAllBytes(getTestPngFilePath()))
		)
		val productImageUploadResult4 = apiClient.uploadProductImage(productImageUploadRequest4)
		assertTrue(productImageUploadResult4.id > 0)

		// Checking that product has main image
		val productDetailsRequest = ProductDetailsRequest(productId = productCreateResult.id)
		val productDetails1 = apiClient.getProductDetails(productDetailsRequest)
		assertEquals(1, productDetails1.media?.images?.size)
		assertMediaProductImage(
			expectedId = "0",
			expectedOrderBy = 0,
			expectedIsMain = true,
			expectedPathEnd = "/${productImageUploadResult4.id}.png",
			productImage = productDetails1.media?.images?.get(0)
		)

		// Now delete product image

		val productImageDeleteRequest = ProductImageDeleteRequest(productId = productCreateResult.id)
		val productImageDeleteResult = apiClient.deleteProductImage(productImageDeleteRequest)
		assertTrue(productImageDeleteResult.deleteCount > 0)

		// Check that product has now no main image now
		val productDetails2 = apiClient.getProductDetails(productDetailsRequest)
		assertEquals(0, productDetails2.media?.images?.size)
	}

	@Test
	fun testManipulateProductGalleryImages() {
		// Creating new product
		val productCreateRequest = ProductCreateRequest(
			newProduct = UpdatedProduct(
				name = "Product ${randomAlphanumeric(8)}",
				sku = "testManipulateProductGalleryImages"
			)
		)
		val productCreateResult = apiClient.createProduct(productCreateRequest)
		assertTrue(productCreateResult.id > 0)

		// Upload gallery images from different sources

		val productGalleryImageUploadRequest1 = ProductGalleryImageUploadRequest(
			productId = productCreateResult.id,
			fileName = randomFileName("test", "png"),
			fileData = UploadFileData.ExternalUrlData(externalUrl = "https://don16obqbay2c.cloudfront.net/favicons/apple-touch-icon-180x180.png")
		)
		val productGalleryImageUploadResult1 = apiClient.uploadProductGalleryImage(productGalleryImageUploadRequest1)
		assertTrue(productGalleryImageUploadResult1.id > 0)

		val productGalleryImageUploadRequest2 = ProductGalleryImageUploadRequest(
			productId = productCreateResult.id,
			fileData = UploadFileData.LocalFileData(file = getTestPngFilePath().toFile())
		)
		val productGalleryImageUploadResult2 = apiClient.uploadProductGalleryImage(productGalleryImageUploadRequest2)
		assertTrue(productGalleryImageUploadResult2.id > 0)

		val productGalleryImageUploadRequest3 = ProductGalleryImageUploadRequest(
			productId = productCreateResult.id,
			fileData = UploadFileData.InputStreamData(stream = FileInputStream(getTestPngFilePath().toFile()))
		)
		val productGalleryImageUploadResult3 = apiClient.uploadProductGalleryImage(productGalleryImageUploadRequest3)
		assertTrue(productGalleryImageUploadResult3.id > 0)

		val productGalleryImageUploadRequest4 = ProductGalleryImageUploadRequest(
			productId = productCreateResult.id,
			fileData = UploadFileData.ByteArrayData(bytes = Files.readAllBytes(getTestPngFilePath()))
		)
		val productGalleryImageUploadResult4 = apiClient.uploadProductGalleryImage(productGalleryImageUploadRequest4)
		assertTrue(productGalleryImageUploadResult4.id > 0)

		// Checking that product has gallery images image

		val productDetailsRequest = ProductDetailsRequest(productId = productCreateResult.id)
		val productDetails1 = apiClient.getProductDetails(productDetailsRequest)
		assertEquals(4, productDetails1.media?.images?.size)
		assertMediaProductImage(
			expectedId = "0",
			expectedOrderBy = 0,
			expectedIsMain = true,
			expectedPathEnd = null,
			productImage = productDetails1.media?.images?.get(0)
		)
		assertMediaProductImage(
			expectedId = productGalleryImageUploadResult2.id.toString(),
			expectedOrderBy = 1,
			expectedIsMain = false,
			expectedPathEnd = null,
			productImage = productDetails1.media?.images?.get(1)
		)
		assertMediaProductImage(
			expectedId = productGalleryImageUploadResult3.id.toString(),
			expectedOrderBy = 2,
			expectedIsMain = false,
			expectedPathEnd = null,
			productImage = productDetails1.media?.images?.get(2)
		)
		assertMediaProductImage(
			expectedId = productGalleryImageUploadResult4.id.toString(),
			expectedOrderBy = 3,
			expectedIsMain = false,
			expectedPathEnd = null,
			productImage = productDetails1.media?.images?.get(3)
		)

		// Changing gallery images order

		val newMedia = ProductMedia(
			images = productDetails1.media?.toUpdated()?.images?.map { productImage ->
				productImage.copy(
					orderBy = if (productImage.id == "0") {
						10
					} else {
						productImage.orderBy
					}
				)
			}
		)
		val productUpdateRequest1 = ProductUpdateRequest(
			productId = productCreateResult.id,
			updatedProduct = UpdatedProduct(
				media = newMedia
			)
		)
		val productUpdateResult1 = apiClient.updateProduct(productUpdateRequest1)
		assertTrue(productUpdateResult1.updateCount > 0)

		val productDetails2 = apiClient.getProductDetails(productDetailsRequest)
		assertEquals(4, productDetails2.media?.images?.size)
		assertMediaProductImage(
			expectedId = "0", // First image became main
			expectedOrderBy = 0,
			expectedIsMain = true, // First image became main
			expectedPathEnd = productDetails1.media?.images?.get(1)?.image1500pxUrl,
			productImage = productDetails2.media?.images?.get(0)
		)
		assertMediaProductImage(
			expectedId = productGalleryImageUploadResult3.id.toString(),
			expectedOrderBy = 1,
			expectedIsMain = false,
			expectedPathEnd = productDetails1.media?.images?.get(2)?.image1500pxUrl,
			productImage = productDetails2.media?.images?.get(1)
		)
		assertMediaProductImage(
			expectedId = productGalleryImageUploadResult4.id.toString(),
			expectedOrderBy = 2,
			expectedIsMain = false,
			expectedPathEnd = productDetails1.media?.images?.get(3)?.image1500pxUrl,
			productImage = productDetails2.media?.images?.get(2)
		)
		assertMediaProductImage(
			expectedId = "4", // This is the moved gallery item, it gets the next vacant id
			expectedOrderBy = 3,
			expectedIsMain = false,
			expectedPathEnd = productDetails1.media?.images?.get(0)?.image1500pxUrl,
			productImage = productDetails2.media?.images?.get(3)
		)

		// Now delete one gallery image
		val productGalleryImageDeleteRequest = ProductGalleryImageDeleteRequest(
			productId = productCreateResult.id,
			fileId = productGalleryImageUploadResult3.id
		)
		val productGalleryImageDeleteResult = apiClient.deleteProductGalleryImage(productGalleryImageDeleteRequest)
		assertTrue(productGalleryImageDeleteResult.deleteCount > 0)

		// Check that product has only three gallery images left
		val productDetails3 = apiClient.getProductDetails(productDetailsRequest)
		assertEquals(3, productDetails3.media?.images?.size)

		// Now delete all other gallery images (not including main image)
		val productGalleryImagesDeleteRequest = ProductGalleryImagesDeleteRequest(productId = productCreateResult.id)
		val productGalleryImagesDeleteResult = apiClient.deleteProductGalleryImages(productGalleryImagesDeleteRequest)
		assertEquals(2, productGalleryImagesDeleteResult.deleteCount)

		// Check that product has no gallery images left (only main image left)
		val productDetails4 = apiClient.getProductDetails(productDetailsRequest)
		assertEquals(1, productDetails4.media?.images?.size)
	}

	@Test
	fun testManipulateProductFiles() {
		// Creating new product
		val productCreateRequest = ProductCreateRequest(
			newProduct = UpdatedProduct(
				name = "Product ${randomAlphanumeric(8)}",
				sku = "testManipulateProductFiles"
			)
		)
		val productCreateResult = apiClient.createProduct(productCreateRequest)
		assertTrue(productCreateResult.id > 0)

		// Upload files from different sources

		val productFileUploadRequest1 = ProductFileUploadRequest(
			productId = productCreateResult.id,
			fileName = randomFileName("test", "png"),
			description = "Description ${randomAlphanumeric(32)}",
			fileData = UploadFileData.ExternalUrlData(externalUrl = "https://don16obqbay2c.cloudfront.net/favicons/apple-touch-icon-180x180.png")
		)
		val productFileUploadResult1 = apiClient.uploadProductFile(productFileUploadRequest1)
		assertTrue(productFileUploadResult1.id > 0)

		val productFileUploadRequest2 = ProductFileUploadRequest(
			productId = productCreateResult.id,
			fileName = randomFileName("test", "png"),
			description = "Description ${randomAlphanumeric(32)}",
			fileData = UploadFileData.LocalFileData(file = getTestPngFilePath().toFile())
		)
		val productFileUploadResult2 = apiClient.uploadProductFile(productFileUploadRequest2)
		assertTrue(productFileUploadResult2.id > 0)

		val productFileUploadRequest3 = ProductFileUploadRequest(
			productId = productCreateResult.id,
			fileName = randomFileName("test", "png"),
			description = "Description ${randomAlphanumeric(32)}",
			fileData = UploadFileData.InputStreamData(stream = FileInputStream(getTestPngFilePath().toFile()))
		)
		val productFileUploadResult3 = apiClient.uploadProductFile(productFileUploadRequest3)
		assertTrue(productFileUploadResult3.id > 0)

		val productFileUploadRequest4 = ProductFileUploadRequest(
			productId = productCreateResult.id,
			fileName = randomFileName("test", "png"),
			description = "Description ${randomAlphanumeric(32)}",
			fileData = UploadFileData.ByteArrayData(bytes = Files.readAllBytes(getTestPngFilePath()))
		)
		val productFileUploadResult4 = apiClient.uploadProductFile(productFileUploadRequest4)
		assertTrue(productFileUploadResult4.id > 0)

		// Checking that product has necessary files

		val productDetailsRequest = ProductDetailsRequest(productId = productCreateResult.id)
		val productDetails1 = apiClient.getProductDetails(productDetailsRequest)
		assertEquals(4, productDetails1.files?.size)
		assertFile(
			expectedProductId = productFileUploadResult1.id,
			expectedFileName = productFileUploadRequest1.fileName,
			expectedDescription = productFileUploadRequest1.description,
			productFile = productDetails1.files?.get(0)
		)
		assertFile(
			expectedProductId = productFileUploadResult2.id,
			expectedFileName = productFileUploadRequest2.fileName,
			expectedDescription = productFileUploadRequest2.description,
			productFile = productDetails1.files?.get(1)
		)
		assertFile(
			expectedProductId = productFileUploadResult3.id,
			expectedFileName = productFileUploadRequest3.fileName,
			expectedDescription = productFileUploadRequest3.description,
			productFile = productDetails1.files?.get(2)
		)
		assertFile(
			expectedProductId = productFileUploadResult4.id,
			expectedFileName = productFileUploadRequest4.fileName,
			expectedDescription = productFileUploadRequest4.description,
			productFile = productDetails1.files?.get(3)
		)

		// Update one product file description

		val productFileUpdateRequest = ProductFileUpdateRequest(
			productId = productCreateResult.id,
			fileId = productFileUploadResult2.id,
			updatedProductFile = ProductFileUpdateRequest.UpdatedProductFile(
				description = "Description ${randomAlphanumeric(32)}"
			)
		)
		val productFileUpdateResult = apiClient.updateProductFile(productFileUpdateRequest)
		assertTrue(productFileUpdateResult.updateCount > 0)

		// Check that product was updated
		val productDetails2 = apiClient.getProductDetails(productDetailsRequest)
		assertFile(
			expectedProductId = productFileUploadResult2.id,
			expectedFileName = productFileUploadRequest2.fileName,
			expectedDescription = productFileUpdateRequest.updatedProductFile.description,
			productFile = productDetails2.files?.get(1)
		)

		// Try to download product file and check its size
		val productFileDownloadRequest = ProductFileDownloadRequest(
			productId = productCreateResult.id,
			fileId = productFileUploadResult2.id
		)
		val downloadedProductFileBytes = apiClient.downloadProductFile(productFileDownloadRequest)
		assertEquals(getTestPngFilePath().toFile().length(), downloadedProductFileBytes.size.toLong())

		// Now delete one product file
		val productFileDeleteRequest = ProductFileDeleteRequest(
			productId = productCreateResult.id,
			fileId = productFileUploadResult2.id
		)
		val productFileDeleteResult = apiClient.deleteProductFile(productFileDeleteRequest)
		assertTrue(productFileDeleteResult.deleteCount > 0)

		// Check that product has only three product files left
		val productDetails3 = apiClient.getProductDetails(productDetailsRequest)
		assertEquals(3, productDetails3.files?.size)

		// Now delete all other product files
		val productFilesDeleteRequest = ProductFilesDeleteRequest(productId = productCreateResult.id)
		val productFilesDeleteResult = apiClient.deleteProductFiles(productFilesDeleteRequest)
		assertEquals(3, productFilesDeleteResult.deleteCount)

		// Check that product has no product files left
		val productDetails4 = apiClient.getProductDetails(productDetailsRequest)
		assertEquals(0, productDetails4.files?.size)
	}

	@Test
	@Disabled("Will be fixed in ECWID-75364")
	fun testDeletedProducts() {
		// Creating new product
		val productCreateRequest = ProductCreateRequest(
			newProduct = generateTestProduct()
		)
		val productCreateResult = apiClient.createProduct(productCreateRequest)
		assertTrue(productCreateResult.id > 0)

		// Deleting product
		val productDeleteRequest = ProductDeleteRequest(productId = productCreateResult.id)
		val productDeleteResult = apiClient.deleteProduct(productDeleteRequest)
		assertEquals(1, productDeleteResult.deleteCount)

		val instant = Date().toInstant()
		val instantFrom = instant.minusSeconds(10)
		val instantTo = instant.plusSeconds(10)

		// Checking that just deleted product returned from api
		val deletedProductsSearchRequest = DeletedProductsSearchRequest(
			deletedFrom = Date.from(instantFrom),
			deletedTo = Date.from(instantTo)
		)
		val deletedProducts = apiClient.searchDeletedProductsAsSequence(deletedProductsSearchRequest)
		val deletedProduct =
			deletedProducts.firstOrNull { deletedProduct -> deletedProduct.id == productCreateResult.id }
		require(deletedProduct != null)
		assertTrue(instantFrom.isBefore(deletedProduct.date.toInstant()))
		assertTrue(instantTo.isAfter(deletedProduct.date.toInstant()))
	}

	@Test
	fun testUploadProductImageAsync() {
		// Creating new product
		val productCreateRequest = ProductCreateRequest(
			newProduct = UpdatedProduct(
				name = "Product ${randomAlphanumeric(8)}",
				sku = "testUploadProductImageAsync"
			)
		)
		val productCreateResult = apiClient.createProduct(productCreateRequest)
		assertTrue(productCreateResult.id > 0)

		val productDetailsRequest = ProductDetailsRequest(productId = productCreateResult.id)
		val productDetails = apiClient.getProductDetails(productDetailsRequest)
		assertEquals(0, productDetails.media?.images?.size)

		val request = ProductImageAsyncUploadRequest(
			productId = productCreateResult.id,
			asyncPictureData = AsyncPictureData(
				url = "https://don16obqbay2c.cloudfront.net/favicons/apple-touch-icon-180x180.png",
				width = 180,
				height = 180
			)
		)
		apiClient.uploadProductImageAsync(request)

		val productDetailsAfterUpload = apiClient.getProductDetails(productDetailsRequest)
		assertEquals(1, productDetailsAfterUpload.media?.images?.size)

		val requestWithBlankUrl = ProductImageAsyncUploadRequest(
			productId = productCreateResult.id,
			asyncPictureData = AsyncPictureData(
				url = "  ",
				width = 180,
				height = 180
			)
		)
		try {
			apiClient.uploadProductImageAsync(requestWithBlankUrl)
			fail("Request must return error")
		} catch (ignore: EcwidApiException) {
			// ok
		}

		val requestWithWrongUrl = ProductImageAsyncUploadRequest(
			productId = productCreateResult.id,
			asyncPictureData = AsyncPictureData(
				url = "htt://sssss",
				width = 180,
				height = 180
			)
		)
		try {
			apiClient.uploadProductImageAsync(requestWithWrongUrl)
			fail("Request must return error")
		} catch (ignore: EcwidApiException) {
			// ok
		}
	}

	@Test
	fun testUploadProductGalleryImageAsync() {
		// Creating new product
		val productCreateRequest = ProductCreateRequest(
			newProduct = UpdatedProduct(
				name = "Product ${randomAlphanumeric(8)}",
				sku = "testUploadProductGalleryImageAsync"
			)
		)
		val productCreateResult = apiClient.createProduct(productCreateRequest)
		assertTrue(productCreateResult.id > 0)

		val productDetailsRequest = ProductDetailsRequest(productId = productCreateResult.id)
		val productDetails = apiClient.getProductDetails(productDetailsRequest)
		assertEquals(0, productDetails.media?.images?.size)

		val request = ProductGalleryImageAsyncUploadRequest(
			productId = productCreateResult.id,
			asyncPictureData = AsyncPictureData(
				url = "https://don16obqbay2c.cloudfront.net/favicons/apple-touch-icon-180x180.png",
				width = 180,
				height = 180
			)
		)
		apiClient.uploadProductGalleryImageAsync(request)

		val productDetailsAfterUpload = apiClient.getProductDetails(productDetailsRequest)
		assertEquals(1, productDetailsAfterUpload.media?.images?.size)

		val requestWithBlankUrl = ProductGalleryImageAsyncUploadRequest(
			productId = productCreateResult.id,
			asyncPictureData = AsyncPictureData(
				url = "  ",
				width = 180,
				height = 180
			)
		)
		try {
			apiClient.uploadProductGalleryImageAsync(requestWithBlankUrl)
			fail("Request must return error")
		} catch (ignore: EcwidApiException) {
			// ok
		}

		val requestWithWrongUrl = ProductGalleryImageAsyncUploadRequest(
			productId = productCreateResult.id,
			asyncPictureData = AsyncPictureData(
				url = "htt://sssss",
				width = 180,
				height = 180
			)
		)
		try {
			apiClient.uploadProductGalleryImageAsync(requestWithWrongUrl)
			fail("Request must return error")
		} catch (ignore: EcwidApiException) {
			// ok
		}
	}

	private fun assertProductUrlMatchesRegex(productSearchRequest: ByFilters, urlPattern: String) {
		val searchProducts = apiClient.searchProducts(productSearchRequest)
		assertEquals(1, searchProducts.total)

		val url = searchProducts.items[0].url ?: ""
		assertTrue(
			url.matches(Regex(urlPattern)),
			"Url '$url' is not matching regex pattern '$urlPattern'"
		)
	}

	private fun assertProductsSearch(
		positiveProductId: Int,
		positiveSearchRequest: ByFilters,
		negativeSearchRequest: ByFilters
	) {
		val positiveProductsSearchResult = apiClient.searchProducts(positiveSearchRequest)
		assertEquals(1, positiveProductsSearchResult.total)
		assertEquals(positiveProductId, positiveProductsSearchResult.items[0].id)

		val negativeProductsSearchResult = apiClient.searchProducts(negativeSearchRequest)
		assertEquals(0, negativeProductsSearchResult.total)
	}

	private fun assertProductsSearch(productSearchRequest: ByFilters, desiredSkus: List<String>) {
		val productsSearchResult = apiClient.searchProducts(productSearchRequest)
		assertEquals(desiredSkus.size, productsSearchResult.items.size)
		assertEquals(desiredSkus.toSet(), productsSearchResult.items.map(FetchedProduct::sku).toSet())
	}

	private fun assertMediaProductImage(
		expectedId: String,
		expectedOrderBy: Int,
		expectedIsMain: Boolean,
		expectedPathEnd: String?,
		productImage: FetchedProduct.ProductImage?
	) {
		assertAll(
			{ assertEquals(expectedId, productImage?.id, "id mismatch") },
			{ assertEquals(expectedOrderBy, productImage?.orderBy, "orderBy mismatch") },
			{ assertEquals(expectedIsMain, productImage?.isMain, "isMain mismatch") },
			{
				if (expectedPathEnd != null) {
					assertEquals(
						true,
						productImage?.image1500pxUrl?.endsWith(expectedPathEnd),
						"image1500pxUrl mismatch"
					)
				}
			}
		)
	}

	private fun assertFile(
		expectedProductId: Int,
		expectedFileName: String,
		expectedDescription: String,
		productFile: FetchedProduct.ProductFile?
	) {
		assertAll(
			{ assertEquals(expectedProductId, productFile?.id, "id mismatch") },
			{ assertEquals(expectedFileName, productFile?.name, "name mismatch") },
			{ assertEquals(expectedDescription, productFile?.description, "description mismatch") },
			{ assertTrue((productFile?.size ?: 0) > 0, "wrong size") }
		)
	}
}

private fun generateTestCategory(parentId: Long? = null): UpdatedCategory {
	return UpdatedCategory(
		name = "Category " + randomAlphanumeric(8),
		description = "Description " + randomAlphanumeric(16),
		parentId = parentId,
		enabled = true
	)
}

private fun generateTestProduct(categoryIds: List<Long> = listOf(), discountsAllowed: Boolean = true): UpdatedProduct {
	val basePrice = randomPrice()
	val enName = "Product " + randomAlphanumeric(8)
	val enDescription = "Description " + randomAlphanumeric(16)
	return UpdatedProduct(
		name = enName,
		nameTranslated = LocalizedValueMap(
			"ru" to "Продукт " + randomAlphanumeric(8),
			"en" to enName
		),
		description = enDescription,
		descriptionTranslated = LocalizedValueMap(
			"ru" to "Описание " + randomAlphanumeric(16),
			"en" to enDescription
		),
		sku = "SKU " + randomAlphanumeric(8),

		enabled = randomBoolean(),
		quantity = randomByte(),
		unlimited = false, // To allow set quantity
		warningLimit = randomByte(),

		categoryIds = categoryIds.sorted(),
		defaultCategoryId = categoryIds.firstOrNull(),
		showOnFrontpage = randomByte(),

		price = basePrice,
		costPrice = basePrice * 0.9,
		wholesalePrices = listOf(
			generateWholesalePrice(basePrice, 2),
			generateWholesalePrice(basePrice, 3)
		),
		compareToPrice = 2 * basePrice,

		productClassId = 0, // TODO Fill with real productClassId when api client will support it
		attributes = listOf(
			generateBrandAttributeValue(),
			generateUpcAttributeValue()
// 					generateGeneralAttributeValue(), // TODO Send real product attribute id when api client will support product attribute creation
// 					generateGeneralAttributeValue()
		),

		weight = randomWeight(),
		dimensions = generateDimensions(),
		volume = randomVolume(),
		shipping = generateShippingSettings(),
		isShippingRequired = true, // To allow set weight field

		seoTitle = "SEO Title " + randomAlphanumeric(16),
		seoTitleTranslated = LocalizedValueMap(
			"ru" to "RU SEO Title " + randomAlphanumeric(16),
			"en" to "EN SEO Title " + randomAlphanumeric(16),
		),
		seoDescription = "SEO Description " + randomAlphanumeric(16),
		seoDescriptionTranslated = LocalizedValueMap(
			"ru" to "RU SEO Description " + randomAlphanumeric(16),
			"en" to "EN SEO Description " + randomAlphanumeric(16),
		),

		options = listOf(
			generateProductSelectOption(),
			generateProductSizeOption(),
			generateProductRadioOption(),
			generateProductCheckboxOption(),
			generateProductTextFieldOption(),
			generateProductTextAreaOption(),
			generateProductDateOption(),
			generateProductFilesOption()
		),
// 			tax = generateTaxInfo(), // TODO Fill with real tax ids when api client will support it
		relatedProducts = generateRelatedProducts(),

		isSampleProduct = false,

		tax = TaxInfo(),

		discountsAllowed = discountsAllowed,
		subtitle = "Subtitle sample",
		ribbon = Ribbon(
			"Ribbon",
			"#FFFFF"
		),
		ribbonTranslated = LocalizedValueMap(
			"ru" to "Лейбл",
			"en" to "Ribbon"
		),
		subtitleTranslated = LocalizedValueMap(
			"ru" to "Сабтайтл",
			"en" to "Subtitle"
		),
		subscriptionSettings = SubscriptionSettings(),
		googleProductCategory = NullableUpdatedValue(632),
		productCondition = ProductCondition.USED,
		externalReferenceId = "EXT_ID_123",
		customsHsTariffCode = randomAlphanumeric(10),
	)
}

private fun generateProductSelectOption(): ProductOption.SelectOption {
	val choices = listOf(
		generateProductOptionChoice(),
		generateProductOptionChoice(),
		generateProductOptionChoice()
	)
	val enName = "Option " + randomAlphanumeric(8)
	return ProductOption.createSelectOption(
		name = enName,
		nameTranslated = LocalizedValueMap(
			"ru" to "Опция " + randomAlphanumeric(8),
			"en" to enName
		),
		choices = choices,
		defaultChoice = randomIndex(choices),
		required = randomBoolean()
	)
}

private fun generateProductSizeOption(): ProductOption.SizeOption {
	val choices = listOf(
		generateProductOptionChoice(),
		generateProductOptionChoice(),
		generateProductOptionChoice()
	)
	val enName = "Option " + randomAlphanumeric(8)
	return ProductOption.createSizeOption(
		name = enName,
		nameTranslated = LocalizedValueMap(
			"ru" to "Опция " + randomAlphanumeric(8),
			"en" to enName
		),
		choices = choices,
		defaultChoice = randomIndex(choices),
		required = randomBoolean()
	)
}

private fun generateProductRadioOption(): ProductOption.RadioOption {
	val choices = listOf(
		generateProductOptionChoice(),
		generateProductOptionChoice(),
		generateProductOptionChoice()
	)
	val enName = "Option " + randomAlphanumeric(8)
	return ProductOption.createRadioOption(
		name = enName,
		nameTranslated = LocalizedValueMap(
			"ru" to "Опция " + randomAlphanumeric(8),
			"en" to enName
		),
		choices = choices,
		defaultChoice = randomIndex(choices),
		required = randomBoolean()
	)
}

private fun generateProductCheckboxOption(): ProductOption.CheckboxOption {
	val enName = "Option " + randomAlphanumeric(8)
	return ProductOption.createCheckboxOption(
		name = enName,
		nameTranslated = LocalizedValueMap(
			"ru" to "Опция " + randomAlphanumeric(8),
			"en" to enName
		),
		choices = listOf(
			generateProductOptionChoice(),
			generateProductOptionChoice(),
			generateProductOptionChoice()
		)
	)
}

private fun generateProductTextFieldOption(): ProductOption.TextFieldOption {
	val enName = "Option " + randomAlphanumeric(8)
	return ProductOption.createTextFieldOption(
		name = enName,
		nameTranslated = LocalizedValueMap(
			"ru" to "Опция " + randomAlphanumeric(8),
			"en" to enName
		),
		required = randomBoolean()
	)
}

private fun generateProductTextAreaOption(): ProductOption.TextAreaOption {
	val enName = "Option " + randomAlphanumeric(8)
	return ProductOption.createTextAreaOption(
		name = enName,
		nameTranslated = LocalizedValueMap(
			"ru" to "Опция " + randomAlphanumeric(8),
			"en" to enName
		),
		required = randomBoolean()
	)
}

private fun generateProductDateOption(): ProductOption.DateOption {
	val enName = "Option " + randomAlphanumeric(8)

	return ProductOption.createDateOption(
		name = enName,
		nameTranslated = LocalizedValueMap(
			"ru" to "Опция " + randomAlphanumeric(8),
			"en" to enName
		),
		required = randomBoolean()
	)
}

private fun generateProductFilesOption(): ProductOption.FilesOption {
	val enName = "Option " + randomAlphanumeric(8)
	return ProductOption.createFilesOption(
		name = enName,
		nameTranslated = LocalizedValueMap(
			"ru" to "Опция " + randomAlphanumeric(8),
			"en" to enName
		),
		required = randomBoolean()
	)
}

private fun generateProductOptionChoice(): ProductOptionChoice {
	val enText = "Option choice " + randomAlphanumeric(8)
	return ProductOptionChoice(
		text = enText,
		textTranslated = LocalizedValueMap(
			"ru" to "Выбор опции " + randomAlphanumeric(8),
			"en" to enText
		),
		priceModifier = randomModifier(),
		priceModifierType = randomEnumValue<PriceModifierType>()
	)
}

fun generateRelatedProducts() = RelatedProducts(
	productIds = listOf(
// 				randomId(), TODO Send real related product ids
// 				randomId(),
// 				randomId()
	),
	relatedCategory = generateRelatedCategory()
)

fun generateRelatedCategory(): RelatedCategory = RelatedCategory(
	enabled = randomBoolean(),
	categoryId = 0, // TODO Send any existing category id when api client will support its creation
	productCount = randomByte()
)

private fun generateShippingSettings() = ShippingSettings(
	type = randomEnumValue<ShippingSettingsType>(),
	methodMarkup = randomPrice(),
	flatRate = randomPrice(),
	disabledMethods = listOf(
		randomAlphanumeric(8),
		randomAlphanumeric(8),
		randomAlphanumeric(8)
	),
	enabledMethods = listOf(
		randomAlphanumeric(8),
		randomAlphanumeric(8),
		randomAlphanumeric(8)
	)
)

private fun generateWholesalePrice(basePrice: Double, divider: Int) = WholesalePrice(
	quantity = divider,
	price = basePrice / divider
)

private fun generateBrandAttributeValue(): AttributeValue {
	return AttributeValue.createBrandAttributeValue("Attribute value " + randomAlphanumeric(8))
}

private fun generateUpcAttributeValue(): AttributeValue {
	return AttributeValue.createUpcAttributeValue("Attribute value " + randomAlphanumeric(8))
}

private fun generateDimensions() = ProductDimensions(
	length = randomDimension(),
	width = randomDimension(),
	height = randomDimension()
)

private fun UpdatedProduct.cleanupForComparison(productCreateRequest: ProductCreateRequest): UpdatedProduct {
	return copy(
		// Password is write only field
		attributes = attributes?.mapIndexed { index, attribute ->
			val attributeValue = productCreateRequest.newProduct.attributes?.get(index)
			attribute.cleanupForComparison(attributeValue)
		},
		media = if (media?.images?.isEmpty() != false) null else media,
		categoryIds = categoryIds?.sorted(),
		subtitle = "Subtitle sample",
		ribbon = Ribbon(
			"Ribbon",
			"#FFFFF"
		),
		ribbonTranslated = LocalizedValueMap(
			"ru" to "Лейбл",
			"en" to "Ribbon"
		),
		subtitleTranslated = LocalizedValueMap(
			"ru" to "Сабтайтл",
			"en" to "Subtitle"
		)
	)
}

private fun AttributeValue.cleanupForComparison(attributeValue: AttributeValue?): AttributeValue {
	return copy(
		// Id is not used for BRAND/UPC attributes and can be used for custom attributes
		id = if (attributeValue?.alias != null) attributeValue.id else null,
		// Alias is write only field
		alias = attributeValue?.alias
	)
}

private fun UpdatedProduct.withUnchangedShowOnFrontend(productCreateRequest: ProductCreateRequest): UpdatedProduct {
	return copy(
		// TODO Probably API bug (see https://track.ecwid.com/youtrack/issue/ECWID-53048)
		showOnFrontpage = productCreateRequest.newProduct.showOnFrontpage
	)
}
