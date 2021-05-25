package com.ecwid.apiclient.v3.dto.order.enums

@Suppress("unused")
enum class OrderFulfillmentStatus {
	AWAITING_PROCESSING,
	PROCESSING,
	SHIPPED,
	DELIVERED,
	WILL_NOT_DELIVER,
	RETURNED,
	READY_FOR_PICKUP
}
