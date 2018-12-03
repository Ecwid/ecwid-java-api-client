package com.ecwid.api.v3.dto.order.enums

enum class OrderFulfillmentStatus {
	AWAITING_PROCESSING,
	PROCESSING,
	SHIPPED,
	DELIVERED,
	WILL_NOT_DELIVER,
	RETURNED,
	READY_FOR_PICKUP
}