package com.ecwid.apiclient.v3.dto.order.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.dto.order.enums.OrderFulfillmentStatus
import com.ecwid.apiclient.v3.dto.order.enums.OrderPaymentStatus
import com.ecwid.apiclient.v3.impl.RequestInfo
import java.util.*

data class OrdersSearchRequest(
		var ids: String? = null,
		var keywords: String? = null,
		var totalFrom: Double? = null,
		var totalTo: Double? = null,
		var createdFrom: Date? = null,
		var createdTo: Date? = null,
		var updatedFrom: Date? = null,
		var updatedTo: Date? = null,
		var couponCode: String? = null,
		var orderNumber: Int? = null,
		var vendorOrderNumber: String? = null,
		var customer: String? = null,
		var paymentMethod: String? = null,
		var shippingMethod: String? = null,
		var paymentStatus: OrderPaymentStatus? = null,
		var fulfillmentStatus: OrderFulfillmentStatus? = null,
		var offset: Int = 0,
		var limit: Int = 100
) : ApiRequest {
	override fun toRequestInfo() = RequestInfo.createGetRequest(
			endpoint = "orders",
			params = toParams()
	)

	private fun toParams(): Map<String, String> {
		val request = this
		return mutableMapOf<String, String>().apply {
			request.ids?.let { put("ids", it) }
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
			request.paymentStatus?.let { put("paymentStatus", it.name) }
			request.fulfillmentStatus?.let { put("fulfillmentStatus", it.name) }
			put("offset", request.offset.toString())
			put("limit", request.limit.toString())
		}.toMap()
	}
}
