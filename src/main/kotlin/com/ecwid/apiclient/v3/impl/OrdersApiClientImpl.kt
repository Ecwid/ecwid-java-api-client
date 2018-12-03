package com.ecwid.apiclient.v3.impl

import com.ecwid.apiclient.v3.*
import com.ecwid.apiclient.v3.dto.order.result.FetchedOrder
import com.ecwid.apiclient.v3.dto.order.request.*
import com.ecwid.apiclient.v3.httptransport.HttpBody
import com.ecwid.apiclient.v3.dto.order.request.OrderItemOptionFileUploadRequest.UploadFileData
import com.ecwid.apiclient.v3.dto.order.result.*

internal class OrdersApiClientImpl(
		private val apiClientHelper: ApiClientHelper
): OrdersApiClient {

	override fun searchOrders(request: OrdersSearchRequest) = apiClientHelper.makeGetRequest<OrdersSearchResult>(
			endpoint = request.toEndpoint(),
			params = request.toParams()
	)

	override fun searchOrdersAsSequence(request: OrdersSearchRequest) = sequence {
		var offsetRequest = request
		do {
			val searchResult = searchOrders(offsetRequest)
			yieldAll(searchResult.items)
			offsetRequest = offsetRequest.copy(offset = offsetRequest.offset + searchResult.count)
		} while (searchResult.count >= searchResult.limit);
	}

	override fun getOrderDetails(request: OrderDetailsRequest) = apiClientHelper.makeGetRequest<FetchedOrder>(
			endpoint = request.toEndpoint(),
			params = mapOf()
	)

	override fun getOrderInvoice(request: OrderInvoiceRequest) = apiClientHelper.makeGetRequest<String>(
			endpoint = request.toEndpoint(),
			params = mapOf()
	)

	override fun createOrder(request: OrderCreateRequest) = apiClientHelper.makePostRequest<OrderCreateResult>(
			endpoint = request.toEndpoint(),
			params = mapOf(),
			httpBody = HttpBody.StringBody(
					body = apiClientHelper.serializeJson(request.newOrder),
					mimeType = MIME_TYPE_APPLICATION_JSON
			)
	)

	override fun updateOrder(request: OrderUpdateRequest) = apiClientHelper.makePutRequest<OrderUpdateResult>(
			endpoint = request.toEndpoint(),
			params = mapOf(),
			httpBody = HttpBody.StringBody(
					body = apiClientHelper.serializeJson(request.updatedOrder),
					mimeType = MIME_TYPE_APPLICATION_JSON
			)
	)

	override fun deleteOrder(request: OrderDeleteRequest) = apiClientHelper.makeDeleteRequest<OrderDeleteResult>(
			endpoint = request.toEndpoint(),
			params = mapOf()
	)

	override fun uploadOrderItemOptionFile(request: OrderItemOptionFileUploadRequest): OrderItemOptionFileUploadResult {
		val commonParams = mapOf(
				"fileName" to request.fileName
		)
		return when (request.fileData) {
			is UploadFileData.ExternalUrlData -> apiClientHelper.makePostRequest(
					endpoint = request.toEndpoint(),
					params = commonParams + mapOf(
							"externalUrl" to request.fileData.externalUrl
					),
					httpBody = HttpBody.EmptyBody()
			)
			is UploadFileData.ByteArrayData -> apiClientHelper.makePostRequest(
					endpoint = request.toEndpoint(),
					params = commonParams,
					httpBody = HttpBody.ByteArrayBody(
							bytes = request.fileData.bytes,
							mimeType = MIME_TYPE_OCTET_STREAM
					)
			)
			is UploadFileData.LocalFileData -> apiClientHelper.makePostRequest(
					endpoint = request.toEndpoint(),
					params = commonParams,
					httpBody = HttpBody.LocalFileBody(
							file = request.fileData.file,
							mimeType = MIME_TYPE_OCTET_STREAM
					)
			)
			is UploadFileData.InputStreamData -> apiClientHelper.makePostRequest(
					endpoint = request.toEndpoint(),
					params = commonParams,
					httpBody = HttpBody.InputStreamBody(
							stream = request.fileData.stream,
							mimeType = MIME_TYPE_OCTET_STREAM
					)
			)
		}
	}

	override fun deleteOrderItemOptionFile(request: OrderItemOptionFileDeleteRequest) = apiClientHelper.makeDeleteRequest<OrderItemOptionFileDeleteResult>(
			endpoint = request.toEndpoint(),
			params = mapOf()
	)

	override fun deleteOrderItemOptionFiles(request: OrderItemOptionFilesDeleteRequest) = apiClientHelper.makeDeleteRequest<OrderItemOptionFileDeleteResult>(
			endpoint = request.toEndpoint(),
			params = mapOf()
	)

}

private fun OrdersSearchRequest.toEndpoint() = "orders"
private fun OrderDetailsRequest.toEndpoint() = "orders/$orderNumber"
private fun OrderInvoiceRequest.toEndpoint() = "orders/$orderNumber/invoice"
private fun OrderCreateRequest.toEndpoint() = "orders"
private fun OrderUpdateRequest.toEndpoint() = "orders/$orderNumber"
private fun OrderDeleteRequest.toEndpoint() = "orders/$orderNumber"
private fun OrderItemOptionFileUploadRequest.toEndpoint() = "orders/$orderNumber/items/$orderItemId/options/$optionName"
private fun OrderItemOptionFilesDeleteRequest.toEndpoint() = "orders/$orderNumber/items/$orderItemId/options/$optionName/files"
private fun OrderItemOptionFileDeleteRequest.toEndpoint() = "orders/$orderNumber/items/$orderItemId/options/$optionName/files/$fileId"

private fun OrdersSearchRequest.toParams(): Map<String, String> {
	val request = this
	return mutableMapOf<String, String>().apply {
		request.keywords?.let { put("keywords", it) }
		request.totalFrom?.let { put("totalFrom", it.toString()) }
		request.totalTo?.let { put("totalTo", it.toString()) }
		request.createdFrom?.let { put("createdFrom", (it.time / 1000).toString()) }
		request.createdTo?.let { put("createdTo", (it.time / 1000).toString()) }
		request.updatedFrom?.let { put("updatedFrom", (it.time / 1000).toString()) }
		request.updatedTo?.let { put("updatedTo", (it.time / 1000).toString()) }
		request.couponCode?.let { put("couponCode", it) }
		request.orderNumber?.let { put("orderNumber", it.toString()) }
		request.vendorOrderNumber?.let { put("vendorOrderNumber", it) }
		request.customer?.let { put("customer", it) }
		request.paymentMethod?.let { put("paymentMethod", it) }
		request.shippingMethod?.let { put("shippingMethod", it) }
		request.paymentStatus?.let { put("paymentStatus", it.name ) }
		request.fulfillmentStatus?.let { put("fulfillmentStatus", it.name ) }
		put("offset", request.offset.toString())
		put("limit", request.limit.toString())
	}.toMap()
}
