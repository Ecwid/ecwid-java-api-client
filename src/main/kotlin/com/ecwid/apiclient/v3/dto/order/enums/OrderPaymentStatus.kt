package com.ecwid.apiclient.v3.dto.order.enums

@Suppress("unused")
enum class OrderPaymentStatus {
	AWAITING_PAYMENT,
	PAID,
	CANCELLED,
	REFUNDED,
	PARTIALLY_REFUNDED,
	INCOMPLETE,
	CUSTOM_PAYMENT_STATUS_1,
	CUSTOM_PAYMENT_STATUS_2,
	CUSTOM_PAYMENT_STATUS_3;
}
