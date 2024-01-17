package com.ecwid.apiclient.v3.dto.subscriptions.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.dto.subscriptions.enums.SortOrder
import com.ecwid.apiclient.v3.dto.subscriptions.enums.SubscriptionInterval
import com.ecwid.apiclient.v3.dto.subscriptions.enums.SubscriptionStatus
import com.ecwid.apiclient.v3.impl.RequestInfo
import com.ecwid.apiclient.v3.responsefields.ResponseFields
import java.util.*
import java.util.concurrent.TimeUnit

data class SubscriptionsSearchRequest(
	val id: Int? = null,
	val createdFrom: Date? = null,
	val createdTo: Date? = null,
	val cancelledFrom: Date? = null,
	val cancelledTo: Date? = null,
	val updatedFrom: Date? = null,
	val updatedTo: Date? = null,
	val customerId: Int? = null,
	val status: List<SubscriptionStatus>? = null,
	val nextChargeFrom: Date? = null,
	val nextChargeTo: Date? = null,
	val recurringInterval: SubscriptionInterval? = null,
	val recurringIntervalCount: Int? = null,
	val productId: Int? = null,
	val email: String? = null,
	val orderId: String? = null,
	val orderTotal: Double? = null,
	val orderCreatedFrom: Date? = null,
	val orderCreatedTo: Date? = null,
	val offset: Int = 0,
	val limit: Int = 100,
	val sortBy: SortOrder = SortOrder.DATE_CREATED_DESC,
	val lang: String? = null,
	val responseFields: ResponseFields = ResponseFields.All,
) : ApiRequest {

	override fun toRequestInfo() = RequestInfo.createGetRequest(
		pathSegments = listOf(
			"subscriptions"
		),
		params = toParams(),
		responseFields = responseFields,
	)

	private fun toParams(): Map<String, String> {
		return mutableMapOf<String, String>().apply {
			id?.let { put("id", it.toString()) }
			createdFrom?.let { put("createdFrom", TimeUnit.MILLISECONDS.toSeconds(it.time).toString()) }
			createdTo?.let { put("createdTo", TimeUnit.MILLISECONDS.toSeconds(it.time).toString()) }
			cancelledFrom?.let { put("cancelledFrom", TimeUnit.MILLISECONDS.toSeconds(it.time).toString()) }
			cancelledTo?.let { put("cancelledTo", TimeUnit.MILLISECONDS.toSeconds(it.time).toString()) }
			updatedFrom?.let { put("updatedFrom", TimeUnit.MILLISECONDS.toSeconds(it.time).toString()) }
			updatedTo?.let { put("updatedTo", TimeUnit.MILLISECONDS.toSeconds(it.time).toString()) }
			customerId?.let { put("customerId", it.toString()) }
			status?.let { put("status", it.joinToString(",")) }
			nextChargeFrom?.let { put("nextChargeFrom", TimeUnit.MILLISECONDS.toSeconds(it.time).toString()) }
			nextChargeTo?.let { put("nextChargeTo", TimeUnit.MILLISECONDS.toSeconds(it.time).toString()) }
			recurringInterval?.let { put("recurringInterval", it.name) }
			recurringIntervalCount?.let { put("recurringIntervalCount", it.toString()) }
			productId?.let { put("productId", it.toString()) }
			email?.let { put("email", it) }
			orderId?.let { put("orderId", it) }
			orderTotal?.let { put("orderTotal", it.toString()) }
			orderCreatedFrom?.let { put("orderCreatedFrom", TimeUnit.MILLISECONDS.toSeconds(it.time).toString()) }
			orderCreatedTo?.let { put("orderCreatedTo", TimeUnit.MILLISECONDS.toSeconds(it.time).toString()) }
			offset.let { put("offset", it.toString()) }
			limit.let { put("limit", it.toString()) }
			put("sortBy", sortBy.name)
			lang?.let { put("lang", it) }
		}.toMap()
	}
}
