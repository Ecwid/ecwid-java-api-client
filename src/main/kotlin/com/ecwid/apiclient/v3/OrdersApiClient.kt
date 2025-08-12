package com.ecwid.apiclient.v3

import com.ecwid.apiclient.v3.dto.common.PartialResult
import com.ecwid.apiclient.v3.dto.order.request.*
import com.ecwid.apiclient.v3.dto.order.result.*
import kotlin.reflect.KClass

// Orders
// https://developers.ecwid.com/api-documentation/orders
interface OrdersApiClient {
	fun searchOrders(request: OrdersSearchRequest): OrdersSearchResult
	fun <Result> searchOrders(request: OrdersSearchRequest, resultClass: KClass<Result>): Result
		where Result : PartialResult<OrdersSearchResult>
	fun searchOrdersAsSequence(request: OrdersSearchRequest): Sequence<FetchedOrder>
	fun getOrderDetails(request: OrderDetailsRequest): FetchedOrder
	fun getLastOrderDetails(request: LastOrderDetailsRequest): FetchedOrder
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

@Suppress("EXTENSION_SHADOWED_BY_MEMBER")
inline fun <reified Result : PartialResult<OrdersSearchResult>> OrdersApiClient.searchOrders(request: OrdersSearchRequest): Result {
	return searchOrders(request, Result::class)
}
