package com.ecwid.apiclient.v3.dto.order.request

import com.ecwid.apiclient.v3.dto.order.enums.OrderFulfillmentStatus
import com.ecwid.apiclient.v3.dto.order.enums.OrderPaymentStatus
import java.util.*

data class OrdersSearchRequest(
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
)