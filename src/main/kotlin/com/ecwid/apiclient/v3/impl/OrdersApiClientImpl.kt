package com.ecwid.apiclient.v3.impl

import com.ecwid.apiclient.v3.OrdersApiClient
import com.ecwid.apiclient.v3.dto.order.request.*
import com.ecwid.apiclient.v3.dto.order.result.*

internal class OrdersApiClientImpl(
		private val apiClientHelper: ApiClientHelper
) : OrdersApiClient {

	override fun searchOrders(request: OrdersSearchRequest) = apiClientHelper.makeRequest<OrdersSearchResult>(request)

	override fun searchOrdersAsSequence(request: OrdersSearchRequest) = sequence {
		var offsetRequest = request
		do {
			val searchResult = searchOrders(offsetRequest)
			yieldAll(searchResult.items)
			offsetRequest = offsetRequest.copy(offset = offsetRequest.offset + searchResult.count)
		} while (searchResult.count >= searchResult.limit)
	}

	override fun getOrderDetails(request: OrderDetailsRequest) = apiClientHelper.makeRequest<FetchedOrder>(request)

	override fun getOrderInvoice(request: OrderInvoiceRequest) = apiClientHelper.makeRequest<String>(request)

	override fun createOrder(request: OrderCreateRequest) = apiClientHelper.makeRequest<OrderCreateResult>(request)

	override fun updateOrder(request: OrderUpdateRequest) = apiClientHelper.makeRequest<OrderUpdateResult>(request)

	override fun deleteOrder(request: OrderDeleteRequest) = apiClientHelper.makeRequest<OrderDeleteResult>(request)

	override fun uploadOrderItemOptionFile(request: OrderItemOptionFileUploadRequest) = apiClientHelper.makeRequest<OrderItemOptionFileUploadResult>(request)

	override fun deleteOrderItemOptionFile(request: OrderItemOptionFileDeleteRequest) = apiClientHelper.makeRequest<OrderItemOptionFileDeleteResult>(request)

	override fun deleteOrderItemOptionFiles(request: OrderItemOptionFilesDeleteRequest) = apiClientHelper.makeRequest<OrderItemOptionFileDeleteResult>(request)

	override fun searchDeletedOrders(request: DeletedOrdersSearchRequest) = apiClientHelper.makeRequest<DeletedOrdersSearchResult>(request)

	override fun searchDeletedOrdersAsSequence(request: DeletedOrdersSearchRequest) = sequence {
		var offsetRequest = request
		do {
			val searchResult = searchDeletedOrders(offsetRequest)
			yieldAll(searchResult.items)
			offsetRequest = offsetRequest.copy(offset = offsetRequest.offset + searchResult.count)
		} while (searchResult.count >= searchResult.limit)
	}

}
