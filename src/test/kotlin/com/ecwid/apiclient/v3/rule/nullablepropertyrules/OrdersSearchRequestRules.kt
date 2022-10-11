package com.ecwid.apiclient.v3.rule.nullablepropertyrules

import com.ecwid.apiclient.v3.dto.order.request.OrdersSearchRequest
import com.ecwid.apiclient.v3.rule.NullablePropertyRule
import com.ecwid.apiclient.v3.rule.NullablePropertyRule.AllowNullable

val ordersSearchRequestRequestNullablePropertyRules: List<NullablePropertyRule<*, *>> = listOf(
	AllowNullable(OrdersSearchRequest::couponCode),
	AllowNullable(OrdersSearchRequest::createdFrom),
	AllowNullable(OrdersSearchRequest::createdTo),
	AllowNullable(OrdersSearchRequest::customer),
	AllowNullable(OrdersSearchRequest::customerId),
	AllowNullable(OrdersSearchRequest::email),
	AllowNullable(OrdersSearchRequest::fulfillmentStatus),
	AllowNullable(OrdersSearchRequest::ids),
	AllowNullable(OrdersSearchRequest::keywords),
	AllowNullable(OrdersSearchRequest::orderNumber),
	AllowNullable(OrdersSearchRequest::paymentMethod),
	AllowNullable(OrdersSearchRequest::paymentStatus),
	AllowNullable(OrdersSearchRequest::shippingMethod),
	AllowNullable(OrdersSearchRequest::totalFrom),
	AllowNullable(OrdersSearchRequest::totalTo),
	AllowNullable(OrdersSearchRequest::updatedFrom),
	AllowNullable(OrdersSearchRequest::updatedTo),
	AllowNullable(OrdersSearchRequest::vendorOrderNumber),
	AllowNullable(OrdersSearchRequest::subscriptionIds)
)
