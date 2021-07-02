package com.ecwid.apiclient.v3.impl

import com.ecwid.apiclient.v3.ApiClientHelper
import com.ecwid.apiclient.v3.OrdersApiClient
import com.ecwid.apiclient.v3.dto.order.request.*
import com.ecwid.apiclient.v3.dto.order.result.*

internal class OrdersApiClientImpl(
	private val apiClientHelper: ApiClientHelper
) : OrdersApiClient {

	override fun searchOrders(request: OrdersSearchRequest) =
		apiClientHelper.makeObjectResultRequest<OrdersSearchResult>(request)

	override fun searchOrdersAsSequence(request: OrdersSearchRequest) = sequence {
		var offsetRequest = request
		do {
			val searchResult = searchOrders(offsetRequest)
			yieldAll(searchResult.items)
			offsetRequest = offsetRequest.copy(offset = offsetRequest.offset + searchResult.count)
		} while (searchResult.count >= searchResult.limit)
	}

	override fun getOrderDetails(request: OrderDetailsRequest) =
		apiClientHelper.makeObjectResultRequest<FetchedOrder>(request)

	override fun getOrderInvoice(request: OrderInvoiceRequest) = apiClientHelper.makeStringResultRequest(request)

	override fun createOrder(request: OrderCreateRequest) =
		apiClientHelper.makeObjectResultRequest<OrderCreateResult>(request)

	override fun updateOrder(request: OrderUpdateRequest) =
		apiClientHelper.makeObjectResultRequest<OrderUpdateResult>(request)

	override fun deleteOrder(request: OrderDeleteRequest) =
		apiClientHelper.makeObjectResultRequest<OrderDeleteResult>(request)

	override fun uploadOrderItemOptionFile(request: OrderItemOptionFileUploadRequest) =
		apiClientHelper.makeObjectResultRequest<OrderItemOptionFileUploadResult>(request)

	override fun deleteOrderItemOptionFile(request: OrderItemOptionFileDeleteRequest) =
		apiClientHelper.makeObjectResultRequest<OrderItemOptionFileDeleteResult>(request)

	override fun deleteOrderItemOptionFiles(request: OrderItemOptionFilesDeleteRequest) =
		apiClientHelper.makeObjectResultRequest<OrderItemOptionFileDeleteResult>(request)

	override fun searchDeletedOrders(request: DeletedOrdersSearchRequest) =
		apiClientHelper.makeObjectResultRequest<DeletedOrdersSearchResult>(request)

	override fun searchDeletedOrdersAsSequence(request: DeletedOrdersSearchRequest) = sequence {
		var offsetRequest = request
		do {
			val searchResult = searchDeletedOrders(offsetRequest)
			yieldAll(searchResult.items)
			offsetRequest = offsetRequest.copy(offset = offsetRequest.offset + searchResult.count)
		} while (searchResult.count >= searchResult.limit)
	}
}
