package com.ecwid.api.v3

import com.ecwid.api.v3.config.ApiServerDomain
import com.ecwid.api.v3.config.ApiStoreCredentials
import com.ecwid.api.v3.config.LoggingSettings
import com.ecwid.api.v3.dto.customergroup.result.FetchedCustomerGroup
import com.ecwid.api.v3.dto.customergroup.request.*
import com.ecwid.api.v3.dto.customergroup.result.*
import com.ecwid.api.v3.dto.order.result.FetchedOrder
import com.ecwid.api.v3.dto.order.request.*
import com.ecwid.api.v3.dto.order.result.*
import com.ecwid.api.v3.impl.ApiClientHelper
import com.ecwid.api.v3.impl.CustomerGroupsApiClientImpl
import com.ecwid.api.v3.impl.OrdersApiClientImpl

class ApiClient(
		apiServerDomain: ApiServerDomain,
		storeCredentials: ApiStoreCredentials,
		loggingSettings: LoggingSettings = LoggingSettings()
): OrdersApiClient, CustomerGroupsApiClient {

	private val apiClientHelper: ApiClientHelper = ApiClientHelper(apiServerDomain, storeCredentials, loggingSettings)

	private val ordersApiClient: OrdersApiClient = OrdersApiClientImpl(apiClientHelper)
	private val customerGroupsApiClient: CustomerGroupsApiClient = CustomerGroupsApiClientImpl(apiClientHelper)

	// Store information
	// https://developers.ecwid.com/api-documentation/store-information
	// TODO

	// Products
	// https://developers.ecwid.com/api-documentation/products
	// TODO

	// Categories
	// https://developers.ecwid.com/api-documentation/categories
	// TODO

	// Product variations
	// https://developers.ecwid.com/api-documentation/product-variations
	// TODO

	// Product types
	// https://developers.ecwid.com/api-documentation/product-types
	// TODO

	// Orders
	// https://developers.ecwid.com/api-documentation/orders
	override fun searchOrders(request: OrdersSearchRequest) = ordersApiClient.searchOrders(request)
	override fun searchOrdersAsSequence(request: OrdersSearchRequest) = ordersApiClient.searchOrdersAsSequence(request)
	override fun getOrderDetails(request: OrderDetailsRequest) = ordersApiClient.getOrderDetails(request)
	override fun getOrderInvoice(request: OrderInvoiceRequest) = ordersApiClient.getOrderInvoice(request)
	override fun createOrder(request: OrderCreateRequest) = ordersApiClient.createOrder(request)
	override fun updateOrder(request: OrderUpdateRequest) = ordersApiClient.updateOrder(request)
	override fun deleteOrder(request: OrderDeleteRequest) = ordersApiClient.deleteOrder(request)
	override fun uploadOrderItemOptionFile(request: OrderItemOptionFileUploadRequest) = ordersApiClient.uploadOrderItemOptionFile(request)
	override fun deleteOrderItemOptionFile(request: OrderItemOptionFileDeleteRequest) = ordersApiClient.deleteOrderItemOptionFile(request)
	override fun deleteOrderItemOptionFiles(request: OrderItemOptionFilesDeleteRequest) = ordersApiClient.deleteOrderItemOptionFiles(request)

	// Carts
	// https://developers.ecwid.com/api-documentation/carts
	// TODO

	// Customers
	// https://developers.ecwid.com/api-documentation/customers
	// TODO

	// Customer groups
	// https://developers.ecwid.com/api-documentation/customer-groups
	override fun searchCustomerGroups(request: CustomerGroupsSearchRequest) = customerGroupsApiClient.searchCustomerGroups(request)
	override fun searchCustomerGroupsAsSequence(request: CustomerGroupsSearchRequest) = customerGroupsApiClient.searchCustomerGroupsAsSequence(request)
	override fun getCustomerGroupDetails(request: CustomerGroupDetailsRequest) = customerGroupsApiClient.getCustomerGroupDetails(request)
	override fun createCustomerGroup(request: CustomerGroupCreateRequest) = customerGroupsApiClient.createCustomerGroup(request)
	override fun updateCustomerGroup(request: CustomerGroupUpdateRequest) = customerGroupsApiClient.updateCustomerGroup(request)
	override fun deleteCustomerGroup(request: CustomerGroupDeleteRequest) = customerGroupsApiClient.deleteCustomerGroup(request)

	// Discount coupons
	// https://developers.ecwid.com/api-documentation/discount-coupons
	// TODO

	// Application
	// https://developers.ecwid.com/api-documentation/application
	// TODO

	// Starter site
	// https://developers.ecwid.com/api-documentation/starter-site
	// TODO

	// Static store pages
	// https://developers.ecwid.com/api-documentation/static-store-pages
	// TODO

}

interface OrdersApiClient {
	fun searchOrders(request: OrdersSearchRequest): OrdersSearchResult
	fun searchOrdersAsSequence(request: OrdersSearchRequest): Sequence<FetchedOrder>
	fun getOrderDetails(request: OrderDetailsRequest): FetchedOrder
	fun getOrderInvoice(request: OrderInvoiceRequest): String
	fun createOrder(request: OrderCreateRequest): OrderCreateResult
	fun updateOrder(request: OrderUpdateRequest): OrderUpdateResult
	fun deleteOrder(request: OrderDeleteRequest): OrderDeleteResult
	fun uploadOrderItemOptionFile(request: OrderItemOptionFileUploadRequest): OrderItemOptionFileUploadResult
	fun deleteOrderItemOptionFile(request: OrderItemOptionFileDeleteRequest): OrderItemOptionFileDeleteResult
	fun deleteOrderItemOptionFiles(request: OrderItemOptionFilesDeleteRequest): OrderItemOptionFileDeleteResult
}

interface CustomerGroupsApiClient {
	fun searchCustomerGroups(request: CustomerGroupsSearchRequest): CustomerGroupsSearchResult
	fun searchCustomerGroupsAsSequence(request: CustomerGroupsSearchRequest): Sequence<FetchedCustomerGroup>
	fun getCustomerGroupDetails(request: CustomerGroupDetailsRequest): FetchedCustomerGroup
	fun createCustomerGroup(request: CustomerGroupCreateRequest): CustomerGroupCreateResult
	fun updateCustomerGroup(request: CustomerGroupUpdateRequest): CustomerGroupUpdateResult
	fun deleteCustomerGroup(request: CustomerGroupDeleteRequest): CustomerGroupDeleteResult
}