package com.ecwid.apiclient.v3

import com.ecwid.apiclient.v3.config.ApiServerDomain
import com.ecwid.apiclient.v3.config.ApiStoreCredentials
import com.ecwid.apiclient.v3.config.LoggingSettings
import com.ecwid.apiclient.v3.dto.customer.request.*
import com.ecwid.apiclient.v3.dto.customer.result.*
import com.ecwid.apiclient.v3.dto.customergroup.result.FetchedCustomerGroup
import com.ecwid.apiclient.v3.dto.customergroup.request.*
import com.ecwid.apiclient.v3.dto.customergroup.result.*
import com.ecwid.apiclient.v3.dto.order.result.FetchedOrder
import com.ecwid.apiclient.v3.dto.order.request.*
import com.ecwid.apiclient.v3.dto.order.result.*
import com.ecwid.apiclient.v3.dto.product.request.*
import com.ecwid.apiclient.v3.dto.product.result.*
import com.ecwid.apiclient.v3.dto.producttype.request.*
import com.ecwid.apiclient.v3.dto.producttype.result.*
import com.ecwid.apiclient.v3.impl.*

class ApiClient(
		apiServerDomain: ApiServerDomain,
		storeCredentials: ApiStoreCredentials,
		loggingSettings: LoggingSettings = LoggingSettings()
): ProductsApiClient, OrdersApiClient, ProductTypesApiClient, CustomersApiClient, CustomerGroupsApiClient {

	private val apiClientHelper: ApiClientHelper = ApiClientHelper(apiServerDomain, storeCredentials, loggingSettings)

	private val productsApiClient: ProductsApiClient = ProductsApiClientImpl(apiClientHelper)
	private val ordersApiClient: OrdersApiClient = OrdersApiClientImpl(apiClientHelper)
	private val productTypesApiClient: ProductTypesApiClient = ProductTypesApiClientImpl(apiClientHelper)
	private val customersApiClient: CustomersApiClient = CustomersApiClientImpl(apiClientHelper)
	private val customerGroupsApiClient: CustomerGroupsApiClient = CustomerGroupsApiClientImpl(apiClientHelper)

	// Store information
	// https://developers.ecwid.com/api-documentation/store-information
	// TODO

	// Products
	// https://developers.ecwid.com/api-documentation/products
	override fun searchProducts(request: ProductsSearchRequest.ByFilters) = productsApiClient.searchProducts(request)
	override fun searchProducts(request: ProductsSearchRequest.ByIds) = productsApiClient.searchProducts(request)
	override fun searchProductsAsSequence(request: ProductsSearchRequest.ByFilters) = productsApiClient.searchProductsAsSequence(request)
	override fun searchProductsAsSequence(request: ProductsSearchRequest.ByIds) = productsApiClient.searchProductsAsSequence(request)
	override fun getProductDetails(request: ProductDetailsRequest) = productsApiClient.getProductDetails(request)
	override fun createProduct(request: ProductCreateRequest): ProductCreateResult = productsApiClient.createProduct(request)
	override fun updateProduct(request: ProductUpdateRequest): ProductUpdateResult = productsApiClient.updateProduct(request)
	override fun updateProductInventory(request: ProductInventoryUpdateRequest): ProductInventoryUpdateResult = productsApiClient.updateProductInventory(request)
	override fun deleteProduct(request: ProductDeleteRequest): ProductDeleteResult = productsApiClient.deleteProduct(request)
	override fun uploadProductImage(request: ProductImageUploadRequest): ProductImageUploadResult = productsApiClient.uploadProductImage(request)
	override fun deleteProductImage(request: ProductImageDeleteRequest): ProductImageDeleteResult = productsApiClient.deleteProductImage(request)
	override fun uploadProductGalleryImage(request: ProductGalleryImageUploadRequest): ProductGalleryImageUploadResult = productsApiClient.uploadProductGalleryImage(request)
	override fun deleteProductGalleryImage(request: ProductGalleryImageDeleteRequest): ProductGalleryImageDeleteResult = productsApiClient.deleteProductGalleryImage(request)
	override fun deleteProductGalleryImages(request: ProductGalleryImagesDeleteRequest): ProductGalleryImagesDeleteResult = productsApiClient.deleteProductGalleryImages(request)
	override fun downloadProductFile(request: ProductFileDownloadRequest): ByteArray = productsApiClient.downloadProductFile(request)
	override fun uploadProductFile(request: ProductFileUploadRequest): ProductFileUploadResult = productsApiClient.uploadProductFile(request)
	override fun updateProductFile(request: ProductFileUpdateRequest): ProductFileUpdateResult = productsApiClient.updateProductFile(request)
	override fun deleteProductFile(request: ProductFileDeleteRequest): ProductFileDeleteResult = productsApiClient.deleteProductFile(request)
	override fun deleteProductFiles(request: ProductFilesDeleteRequest): ProductFilesDeleteResult = productsApiClient.deleteProductFiles(request)
	override fun searchDeletedProducts(request: DeletedProductsSearchRequest): DeletedProductsSearchResult = productsApiClient.searchDeletedProducts(request)
	override fun searchDeletedProductsAsSequence(request: DeletedProductsSearchRequest): Sequence<DeletedProduct> = productsApiClient.searchDeletedProductsAsSequence(request)

	// Categories
	// https://developers.ecwid.com/api-documentation/categories
	// TODO

	// Product variations
	// https://developers.ecwid.com/api-documentation/product-variations
	// TODO

	// Product types
	// https://developers.ecwid.com/api-documentation/product-types
	override fun getAllProductTypes(request: ProductTypesGetAllRequest) = productTypesApiClient.getAllProductTypes(request)
	override fun getProductTypeDetails(request: ProductTypeDetailsRequest) = productTypesApiClient.getProductTypeDetails(request)
	override fun createProductType(request: ProductTypeCreateRequest) = productTypesApiClient.createProductType(request)
	override fun updateProductType(request: ProductTypeUpdateRequest) = productTypesApiClient.updateProductType(request)
	override fun deleteProductType(request: ProductTypeDeleteRequest) = productTypesApiClient.deleteProductType(request)

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
	override fun searchDeletedOrders(request: DeletedOrdersSearchRequest) = ordersApiClient.searchDeletedOrders(request)
	override fun searchDeletedOrdersAsSequence(request: DeletedOrdersSearchRequest) = ordersApiClient.searchDeletedOrdersAsSequence(request)

	// Carts
	// https://developers.ecwid.com/api-documentation/carts
	// TODO

	// Customers
	// https://developers.ecwid.com/api-documentation/customers
	override fun searchCustomers(request: CustomersSearchRequest) = customersApiClient.searchCustomers(request)
	override fun searchCustomersAsSequence(request: CustomersSearchRequest) = customersApiClient.searchCustomersAsSequence(request)
	override fun getCustomerDetails(request: CustomerDetailsRequest) = customersApiClient.getCustomerDetails(request)
	override fun createCustomer(request: CustomerCreateRequest) = customersApiClient.createCustomer(request)
	override fun updateCustomer(request: CustomerUpdateRequest) = customersApiClient.updateCustomer(request)
	override fun deleteCustomer(request: CustomerDeleteRequest) = customersApiClient.deleteCustomer(request)
	override fun searchDeletedCustomers(request: DeletedCustomersSearchRequest) = customersApiClient.searchDeletedCustomers(request)
	override fun searchDeletedCustomersAsSequence(request: DeletedCustomersSearchRequest) = customersApiClient.searchDeletedCustomersAsSequence(request)

	// Customer groups
	// https://developers.ecwid.com/api-documentation/customer-groups
	override fun searchCustomerGroups(request: CustomerGroupsSearchRequest) = customerGroupsApiClient.searchCustomerGroups(request)
	override fun searchCustomerGroupsAsSequence(request: CustomerGroupsSearchRequest) = customerGroupsApiClient.searchCustomerGroupsAsSequence(request)
	override fun getCustomerGroupDetails(request: CustomerGroupDetailsRequest) = customerGroupsApiClient.getCustomerGroupDetails(request)
	override fun createCustomerGroup(request: CustomerGroupCreateRequest) = customerGroupsApiClient.createCustomerGroup(request)
	override fun updateCustomerGroup(request: CustomerGroupUpdateRequest) = customerGroupsApiClient.updateCustomerGroup(request)
	override fun deleteCustomerGroup(request: CustomerGroupDeleteRequest) = customerGroupsApiClient.deleteCustomerGroup(request)

	// Discount coupons
	// https://developers.ecwid.com/api-documentation/discount-coupons + deleted
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

interface ProductsApiClient {
	fun searchProducts(request: ProductsSearchRequest.ByFilters): ProductsSearchResult
	fun searchProducts(request: ProductsSearchRequest.ByIds): ProductsSearchResult
	fun searchProductsAsSequence(request: ProductsSearchRequest.ByFilters): Sequence<FetchedProduct>
	fun searchProductsAsSequence(request: ProductsSearchRequest.ByIds): Sequence<FetchedProduct>
	fun getProductDetails(request: ProductDetailsRequest): FetchedProduct
	fun createProduct(request: ProductCreateRequest): ProductCreateResult
	fun updateProduct(request: ProductUpdateRequest): ProductUpdateResult
	fun updateProductInventory(request: ProductInventoryUpdateRequest): ProductInventoryUpdateResult
	fun deleteProduct(request: ProductDeleteRequest): ProductDeleteResult
	fun uploadProductImage(request: ProductImageUploadRequest): ProductImageUploadResult
	fun deleteProductImage(request: ProductImageDeleteRequest): ProductImageDeleteResult
	fun uploadProductGalleryImage(request: ProductGalleryImageUploadRequest): ProductGalleryImageUploadResult
	fun deleteProductGalleryImage(request: ProductGalleryImageDeleteRequest): ProductGalleryImageDeleteResult
	fun deleteProductGalleryImages(request: ProductGalleryImagesDeleteRequest): ProductGalleryImagesDeleteResult
	fun downloadProductFile(request: ProductFileDownloadRequest): ByteArray
	fun uploadProductFile(request: ProductFileUploadRequest): ProductFileUploadResult
	fun updateProductFile(request: ProductFileUpdateRequest): ProductFileUpdateResult
	fun deleteProductFile(request: ProductFileDeleteRequest): ProductFileDeleteResult
	fun deleteProductFiles(request: ProductFilesDeleteRequest): ProductFilesDeleteResult
	fun searchDeletedProducts(request: DeletedProductsSearchRequest): DeletedProductsSearchResult
	fun searchDeletedProductsAsSequence(request: DeletedProductsSearchRequest): Sequence<DeletedProduct>
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
	fun searchDeletedOrders(request: DeletedOrdersSearchRequest): DeletedOrdersSearchResult
	fun searchDeletedOrdersAsSequence(request: DeletedOrdersSearchRequest): Sequence<DeletedOrder>
}

interface ProductTypesApiClient {
	fun getAllProductTypes(request: ProductTypesGetAllRequest): ProductTypesGetAllResult
	fun getProductTypeDetails(request: ProductTypeDetailsRequest): FetchedProductType
	fun createProductType(request: ProductTypeCreateRequest): ProductTypeCreateResult
	fun updateProductType(request: ProductTypeUpdateRequest): ProductTypeUpdateResult
	fun deleteProductType(request: ProductTypeDeleteRequest): ProductTypeDeleteResult
}

interface CustomersApiClient {
	fun searchCustomers(request: CustomersSearchRequest): CustomersSearchResult
	fun searchCustomersAsSequence(request: CustomersSearchRequest): Sequence<FetchedCustomer>
	fun getCustomerDetails(request: CustomerDetailsRequest): FetchedCustomer
	fun createCustomer(request: CustomerCreateRequest): CustomerCreateResult
	fun updateCustomer(request: CustomerUpdateRequest): CustomerUpdateResult
	fun deleteCustomer(request: CustomerDeleteRequest): CustomerDeleteResult
	fun searchDeletedCustomers(request: DeletedCustomersSearchRequest): DeletedCustomersSearchResult
	fun searchDeletedCustomersAsSequence(request: DeletedCustomersSearchRequest): Sequence<DeletedCustomer>
}

interface CustomerGroupsApiClient {
	fun searchCustomerGroups(request: CustomerGroupsSearchRequest): CustomerGroupsSearchResult
	fun searchCustomerGroupsAsSequence(request: CustomerGroupsSearchRequest): Sequence<FetchedCustomerGroup>
	fun getCustomerGroupDetails(request: CustomerGroupDetailsRequest): FetchedCustomerGroup
	fun createCustomerGroup(request: CustomerGroupCreateRequest): CustomerGroupCreateResult
	fun updateCustomerGroup(request: CustomerGroupUpdateRequest): CustomerGroupUpdateResult
	fun deleteCustomerGroup(request: CustomerGroupDeleteRequest): CustomerGroupDeleteResult
}