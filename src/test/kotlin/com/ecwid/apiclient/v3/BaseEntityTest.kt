package com.ecwid.apiclient.v3

import com.ecwid.apiclient.v3.config.ApiServerDomain
import com.ecwid.apiclient.v3.config.ApiStoreCredentials
import com.ecwid.apiclient.v3.config.LoggingSettings
import com.ecwid.apiclient.v3.dto.category.request.CategoriesSearchRequest
import com.ecwid.apiclient.v3.dto.category.request.CategoryDeleteRequest
import com.ecwid.apiclient.v3.dto.category.result.FetchedCategory
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
import com.ecwid.apiclient.v3.dto.producttype.request.ProductTypeDeleteRequest
import com.ecwid.apiclient.v3.dto.producttype.request.ProductTypesGetAllRequest
import com.ecwid.apiclient.v3.dto.producttype.result.FetchedProductType
import com.ecwid.apiclient.v3.httptransport.impl.ApacheCommonsHttpClientTransport
import com.ecwid.apiclient.v3.jsontransformer.GsonJsonTransformer
import com.ecwid.apiclient.v3.util.PropertiesLoader
import org.junit.jupiter.api.Assertions
import java.nio.file.Path
import java.nio.file.Paths

abstract class BaseEntityTest {

	protected lateinit var apiClient: ApiClient

	protected open fun beforeEach() {
		apiClient = ApiClient.create(
				apiServerDomain = ApiServerDomain(),
				storeCredentials = ApiStoreCredentials(
						storeId = PropertiesLoader.storeId,
						apiToken = PropertiesLoader.apiToken
				),
				loggingSettings = LoggingSettings().copy(
						logRequestBody = true,
						logSuccessfulResponseBody = true
				),
				httpTransport = ApacheCommonsHttpClientTransport()
		)
	}

	protected fun removeAllProducts() {
		apiClient
				.searchProductsAsSequence(ProductsSearchRequest.ByFilters())
				.map(FetchedProduct::id)
				.filterNotNull()
				.forEach { productId ->
					apiClient.deleteProduct(ProductDeleteRequest(productId))
				}
	}

	protected fun removeAllCategories() {
		apiClient
				.searchCategoriesAsSequence(CategoriesSearchRequest(hiddenCategories = true))
				.map(FetchedCategory::id)
				.filterNotNull()
				.forEach { categoryId ->
					apiClient.deleteCategory(CategoryDeleteRequest(categoryId))
				}
	}

	protected fun removeAllOrders() {
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

	protected fun getTestPngFilePath(): Path = Paths.get(javaClass.getResource("/logo-ecwid-small.png").toURI())

	protected fun waitForProductCount(productsSearchRequest: ProductsSearchRequest.ByFilters, desiredProductCount: Int) {
		var tries = 0
		var lastProductCount: Int
		do {
			val productsSearchResult = apiClient.searchProducts(productsSearchRequest)
			if (productsSearchResult.items.size == desiredProductCount) return
			lastProductCount = productsSearchResult.items.size
			tries++
			Thread.sleep(500L * tries)
		} while (tries < 10)

		return Assertions.fail("After $tries tries was $lastProductCount products found instead of $desiredProductCount")
	}

}
