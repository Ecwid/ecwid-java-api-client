package com.ecwid.apiclient.v3.rule.nullablepropertyrules

import com.ecwid.apiclient.v3.dto.subscriptions.request.SubscriptionsSearchRequest
import com.ecwid.apiclient.v3.rule.NullablePropertyRule
import com.ecwid.apiclient.v3.rule.NullablePropertyRule.AllowNullable

val subscriptionsSearchRequestNullablePropertyRules: List<NullablePropertyRule<*, *>> = listOf(
	AllowNullable(SubscriptionsSearchRequest::id),
	AllowNullable(SubscriptionsSearchRequest::createdFrom),
	AllowNullable(SubscriptionsSearchRequest::createdTo),
	AllowNullable(SubscriptionsSearchRequest::cancelledFrom),
	AllowNullable(SubscriptionsSearchRequest::cancelledTo),
	AllowNullable(SubscriptionsSearchRequest::updatedFrom),
	AllowNullable(SubscriptionsSearchRequest::updatedTo),
	AllowNullable(SubscriptionsSearchRequest::customerId),
	AllowNullable(SubscriptionsSearchRequest::status),
	AllowNullable(SubscriptionsSearchRequest::nextChargeFrom),
	AllowNullable(SubscriptionsSearchRequest::nextChargeTo),
	AllowNullable(SubscriptionsSearchRequest::recurringInterval),
	AllowNullable(SubscriptionsSearchRequest::recurringIntervalCount),
	AllowNullable(SubscriptionsSearchRequest::productId),
	AllowNullable(SubscriptionsSearchRequest::email),
	AllowNullable(SubscriptionsSearchRequest::orderId),
	AllowNullable(SubscriptionsSearchRequest::orderTotal),
	AllowNullable(SubscriptionsSearchRequest::orderCreatedFrom),
	AllowNullable(SubscriptionsSearchRequest::orderCreatedTo),
	AllowNullable(SubscriptionsSearchRequest::offset),
	AllowNullable(SubscriptionsSearchRequest::limit),
)
