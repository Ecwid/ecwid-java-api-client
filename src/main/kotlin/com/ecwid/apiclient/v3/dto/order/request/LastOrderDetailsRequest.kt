package com.ecwid.apiclient.v3.dto.order.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.impl.RequestInfo
import com.ecwid.apiclient.v3.responsefields.ResponseFields

data class LastOrderDetailsRequest(
	val customerEmail: String = "",
	val withAddress: Boolean = false,
	val responseFields: ResponseFields = ResponseFields.All,
) : ApiRequest {
	override fun toRequestInfo() = RequestInfo.createGetRequest(
		pathSegments = listOf(
			"orders",
			"last",
		),
		params = mapOf(
			"customerEmail" to customerEmail,
			"withAddress" to withAddress.toString(),
		),
		responseFields = responseFields,
	)
}
