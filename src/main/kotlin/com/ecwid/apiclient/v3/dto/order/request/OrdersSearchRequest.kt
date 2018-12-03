package com.ecwid.apiclient.v3.dto.order.request

import com.ecwid.apiclient.v3.dto.order.enums.OrderFulfillmentStatus
import com.ecwid.apiclient.v3.dto.order.enums.OrderPaymentStatus
import java.util.*

data class OrdersSearchRequest(
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
)