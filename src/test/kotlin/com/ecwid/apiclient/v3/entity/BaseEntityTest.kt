package com.ecwid.apiclient.v3.entity

import com.ecwid.apiclient.v3.ApiClient
import com.ecwid.apiclient.v3.ApiClientHelper
import com.ecwid.apiclient.v3.config.ApiServerDomain
import com.ecwid.apiclient.v3.config.ApiStoreCredentials
import com.ecwid.apiclient.v3.config.LoggingSettings
import com.ecwid.apiclient.v3.dto.category.request.CategoriesSearchRequest
import com.ecwid.apiclient.v3.dto.category.request.CategoryDeleteRequest
import com.ecwid.apiclient.v3.dto.category.result.CategoriesSearchResult
import com.ecwid.apiclient.v3.dto.category.result.FetchedCategory
import com.ecwid.apiclient.v3.dto.coupon.request.CouponDeleteRequest
import com.ecwid.apiclient.v3.dto.coupon.request.CouponSearchRequest
import com.ecwid.apiclient.v3.dto.coupon.result.FetchedCoupon
import com.ecwid.apiclient.v3.dto.customer.request.CustomerDeleteRequest
import com.ecwid.apiclient.v3.dto.customer.request.CustomersSearchRequest
import com.ecwid.apiclient.v3.dto.customer.result.FetchedCustomer
import com.ecwid.apiclient.v3.dto.customergroup.request.CustomerGroupDeleteRequest
import com.ecwid.apiclient.v3.dto.customergroup.request.CustomerGroupsSearchRequest
import com.ecwid.apiclient.v3.dto.customergroup.result.FetchedCustomerGroup
import com.ecwid.apiclient.v3.dto.order.request.OrderDeleteRequest
import com.ecwid.apiclient.v3.dto.order.request.OrdersSearchRequest
import com.ecwid.apiclient.v3.dto.order.result.FetchedOrder
import com.ecwid.apiclient.v3.dto.product.request.ProductDeleteRequest
import com.ecwid.apiclient.v3.dto.product.request.ProductsSearchRequest
import com.ecwid.apiclient.v3.dto.product.result.FetchedProduct
import com.ecwid.apiclient.v3.dto.product.result.ProductsSearchResult
import com.ecwid.apiclient.v3.dto.producttype.request.ProductTypeDeleteRequest
import com.ecwid.apiclient.v3.dto.producttype.request.ProductTypesGetAllRequest
import com.ecwid.apiclient.v3.dto.producttype.result.FetchedProductType
import com.ecwid.apiclient.v3.dto.profile.enums.ProductFilterType
import com.ecwid.apiclient.v3.dto.profile.request.StoreProfileUpdateRequest
import com.ecwid.apiclient.v3.dto.profile.request.UpdatedStoreProfile
import com.ecwid.apiclient.v3.dto.variation.request.DeleteAllProductVariationsRequest
import com.ecwid.apiclient.v3.exception.EcwidApiException
import com.ecwid.apiclient.v3.httptransport.impl.ApacheCommonsHttpClientTransport
import com.ecwid.apiclient.v3.jsontransformer.gson.GsonTransformerProvider
import com.ecwid.apiclient.v3.util.PropertiesLoader
import org.junit.jupiter.api.Assertions
import java.nio.file.Path
import java.nio.file.Paths

abstract class BaseEntityTest {

	protected lateinit var apiClient: ApiClient
	protected lateinit var apiClientHelper: ApiClientHelper

	protected open fun beforeEach() {
		val properties = PropertiesLoader.load()
		apiClientHelper = ApiClientHelper(
			apiServerDomain = ApiServerDomain(
				host = properties.apiHost,
				securePort = properties.apiPort
			),
			credentials = ApiStoreCredentials(
				storeId = properties.storeId,
				apiToken = properties.apiToken
			),
			loggingSettings = LoggingSettings().copy(
				logRequest = true,
				logRequestBody = true,
				logSuccessfulResponseBody = true,
				maxLogSectionLength = 100 * 1024,
			),
			httpTransport = ApacheCommonsHttpClientTransport(defaultHeaders = emptyList()),
			jsonTransformerProvider = GsonTransformerProvider()
		)
		apiClient = ApiClient(apiClientHelper)
	}

	protected fun initStoreProfile() {
		val expectedProfile = UpdatedStoreProfile(
			generalInfo = UpdatedStoreProfile.GeneralInfo(
				storeUrl = ""
			),
			formatsAndUnits = UpdatedStoreProfile.FormatsAndUnits(
				orderNumberPrefix = "",
				orderNumberSuffix = ""
			),
			languages = UpdatedStoreProfile.Languages(
				enabledLanguages = listOf("en", "ru"),
				defaultLanguage = "en"
			),
			taxSettings = UpdatedStoreProfile.TaxSettings(
				automaticTaxEnabled = false,
				taxes = listOf(),
				pricesIncludeTax = false
			),
			productFiltersSettings = UpdatedStoreProfile.ProductFiltersSettings(
				enabledInStorefront = true,
				filterSections = listOf(
					UpdatedStoreProfile.ProductFilterItem(
						type = ProductFilterType.PRICE,
						enabled = false,
					),
					UpdatedStoreProfile.ProductFilterItem(
						type = ProductFilterType.IN_STOCK,
						enabled = true,
					),
					UpdatedStoreProfile.ProductFilterItem(
						type = ProductFilterType.ON_SALE,
						enabled = true,
					),
					UpdatedStoreProfile.ProductFilterItem(
						type = ProductFilterType.CATEGORIES,
						enabled = false,
					),
					UpdatedStoreProfile.ProductFilterItem(
						type = ProductFilterType.SEARCH,
						enabled = true,
					),
					UpdatedStoreProfile.ProductFilterItem(
						name = "Color",
						type = ProductFilterType.OPTION,
						enabled = true,
					),
					UpdatedStoreProfile.ProductFilterItem(
						name = "Brand",
						type = ProductFilterType.ATTRIBUTE,
						enabled = true,
					),
					UpdatedStoreProfile.ProductFilterItem(
						name = "UPC",
						type = ProductFilterType.ATTRIBUTE,
						enabled = true,
					),
				),
			)
		)

		apiClient.updateStoreProfile(StoreProfileUpdateRequest(expectedProfile))
	}

	protected fun removeAllVariations() {
		apiClient
			.searchProductsAsSequence(ProductsSearchRequest.ByFilters())
			.map(FetchedProduct::id)
			.filterNotNull()
			.forEach { productId ->
				apiClient.deleteAllProductVariations(DeleteAllProductVariationsRequest(productId))
			}
	}

	protected fun removeAllProducts() {
		apiClient
			.searchProductsAsSequence(ProductsSearchRequest.ByFilters())
			.map(FetchedProduct::id)
			.forEach { productId ->
				apiClient.deleteProduct(ProductDeleteRequest(productId))
			}
	}

	protected fun removeAllCategories() {
		apiClient
			.searchCategoriesAsSequence(CategoriesSearchRequest(hiddenCategories = true))
			.map(FetchedCategory::id)
			.forEach { categoryId ->
				try {
					apiClient.deleteCategory(CategoryDeleteRequest(categoryId))
				} catch (ignore: EcwidApiException) {
					// ok
				}
			}
	}

	protected fun removeAllOrders() {
		// This test is very much tied to the number of orders. Therefore, we wait for some time, until the order to index
		// and remove them again.
		Thread.sleep(1000)

		apiClient
			.searchOrdersAsSequence(OrdersSearchRequest())
			.map(FetchedOrder::orderNumber)
			.filterNotNull()
			.forEach { orderNumber ->
				apiClient.deleteOrder(OrderDeleteRequest(orderNumber))
			}
	}

	protected fun removeAllProductTypes() {
		apiClient
			.getAllProductTypes(ProductTypesGetAllRequest())
			.map(FetchedProductType::id)
			.filter { productTypeId -> productTypeId > 0 } // We cannot delete “General” product type
			.forEach { productTypeId ->
				apiClient.deleteProductType(ProductTypeDeleteRequest(productTypeId))
			}
	}

	protected fun removeAllCustomers() {
		apiClient
			.searchCustomersAsSequence(CustomersSearchRequest())
			.map(FetchedCustomer::id)
			.filterNotNull()
			.forEach { customerId ->
				apiClient.deleteCustomer(CustomerDeleteRequest(customerId))
			}
	}

	protected fun removeAllCustomerGroups() {
		apiClient
			.searchCustomerGroupsAsSequence(CustomerGroupsSearchRequest())
			.map(FetchedCustomerGroup::id)
			.filterNotNull()
			.filter { customerGroupId -> customerGroupId > 0 } // We cannot delete “General” customer group
			.forEach { customerGroupId ->
				apiClient.deleteCustomerGroup(CustomerGroupDeleteRequest(customerGroupId))
			}
	}

	protected fun removeAllCoupons() {
		apiClient
			.searchCouponsAsSequence(CouponSearchRequest())
			.map(FetchedCoupon::code)
			.filterNotNull()
			.forEach { couponIdentifier ->
				apiClient.deleteCoupon(CouponDeleteRequest(couponIdentifier))
			}
	}

	protected fun getTestPngFilePath(): Path = Paths.get(javaClass.getResource("/logo-ecwid-small.png").toURI())

	protected fun waitForIndexedProducts(
		productsSearchRequest: ProductsSearchRequest.ByFilters,
		desiredProductCount: Int
	): ProductsSearchResult {
		return processDelay(1500L, 10) {
			val productsSearchResult = apiClient.searchProducts(productsSearchRequest)
			if (productsSearchResult.items.size == desiredProductCount) productsSearchResult else null
		}
	}

	protected fun waitForIndexedCategories(
		categoriesSearchRequest: CategoriesSearchRequest,
		desiredCategoriesCount: Int
	): CategoriesSearchResult {
		return processDelay(1500L, 10) {
			val productsSearchResult = apiClient.searchCategories(categoriesSearchRequest)
			if (productsSearchResult.items.size == desiredCategoriesCount) productsSearchResult else null
		}
	}

	protected inline fun <T> processDelay(delay: Long, totalTries: Int, crossinline block: () -> T?): T {
		var tries = 0
		if (tries >= totalTries) return Assertions.fail("Expected 'totalTries' value must be > 0, but actual is '$totalTries'")
		do {
			val result = block()
			if (result != null) return result
			tries++
			Thread.sleep(delay * tries)
		} while (tries < totalTries)
		return Assertions.fail("After $tries tries, item was not processed")
	}
}
