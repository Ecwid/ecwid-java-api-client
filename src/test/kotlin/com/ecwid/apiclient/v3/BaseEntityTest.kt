package com.ecwid.apiclient.v3

import com.ecwid.apiclient.v3.config.ApiServerDomain
import com.ecwid.apiclient.v3.config.ApiStoreCredentials
import com.ecwid.apiclient.v3.config.LoggingSettings
import com.ecwid.apiclient.v3.dto.customer.request.CustomerDeleteRequest
import com.ecwid.apiclient.v3.dto.customer.request.CustomersSearchRequest
import com.ecwid.apiclient.v3.dto.customer.result.FetchedCustomer
import com.ecwid.apiclient.v3.dto.customergroup.request.CustomerGroupDeleteRequest
import com.ecwid.apiclient.v3.dto.customergroup.request.CustomerGroupsSearchRequest
import com.ecwid.apiclient.v3.dto.customergroup.result.FetchedCustomerGroup
import com.ecwid.apiclient.v3.dto.order.request.OrderDeleteRequest
import com.ecwid.apiclient.v3.dto.order.request.OrdersSearchRequest
import com.ecwid.apiclient.v3.dto.order.result.FetchedOrder
import com.ecwid.apiclient.v3.dto.producttype.request.ProductTypeDeleteRequest
import com.ecwid.apiclient.v3.dto.producttype.request.ProductTypesGetAllRequest
import com.ecwid.apiclient.v3.dto.producttype.result.FetchedProductType
import com.ecwid.apiclient.v3.util.PropertiesLoader

abstract class BaseEntityTest {

	protected lateinit var apiClient: ApiClient

	protected open fun beforeEach() {
		apiClient = ApiClient(
				apiServerDomain = ApiServerDomain(),
				storeCredentials = ApiStoreCredentials(
						storeId = PropertiesLoader.storeId,
						apiToken = PropertiesLoader.apiToken
				),
				loggingSettings = LoggingSettings().copy(
						logRequestBody = true,
						logSuccessfulResponseBody = true
				)
		)
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
				.filter { customerGroupId -> customerGroupId > 0} // We cannot delete “General” customer group
				.forEach { customerGroupId ->
					apiClient.deleteCustomerGroup(CustomerGroupDeleteRequest(customerGroupId))
				}
	}

}
