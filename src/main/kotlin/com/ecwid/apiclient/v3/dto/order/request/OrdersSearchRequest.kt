package com.ecwid.apiclient.v3.dto.order.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.dto.order.enums.OrderFulfillmentStatus
import com.ecwid.apiclient.v3.dto.order.enums.OrderPaymentStatus
import com.ecwid.apiclient.v3.impl.RequestInfo
import java.util.*
import java.util.concurrent.TimeUnit

data class OrdersSearchRequest(
	val ids: String? = null,
	val keywords: String? = null,
	val totalFrom: Double? = null,
	val totalTo: Double? = null,
	val createdFrom: Date? = null,
	val createdTo: Date? = null,
	val updatedFrom: Date? = null,
	val updatedTo: Date? = null,
	val couponCode: String? = null,
	val orderNumber: Int? = null,
	val vendorOrderNumber: String? = null,
	val customer: String? = null,
	val paymentMethod: String? = null,
	val shippingMethod: String? = null,
	val paymentStatus: OrderPaymentStatus? = null,
	val fulfillmentStatus: OrderFulfillmentStatus? = null,
	val offset: Int = 0,
	val limit: Int = 100
) : ApiRequest {
	override fun toRequestInfo() = RequestInfo.createGetRequest(
		pathSegments = listOf(
			"orders"
		),
		params = toParams()
	)

	private fun toParams(): Map<String, String> {
		val request = this
		return mutableMapOf<String, String>().apply {
			request.ids?.let { put("ids", it) }
			request.keywords?.let { put("keywords", it) }
			request.totalFrom?.let { put("totalFrom", it.toString()) }
			request.totalTo?.let { put("totalTo", it.toString()) }
			request.createdFrom?.let { put("createdFrom", TimeUnit.MILLISECONDS.toSeconds(it.time).toString()) }
			request.createdTo?.let { put("createdTo", TimeUnit.MILLISECONDS.toSeconds(it.time).toString()) }
			request.updatedFrom?.let { put("updatedFrom", TimeUnit.MILLISECONDS.toSeconds(it.time).toString()) }
			request.updatedTo?.let { put("updatedTo", TimeUnit.MILLISECONDS.toSeconds(it.time).toString()) }
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
