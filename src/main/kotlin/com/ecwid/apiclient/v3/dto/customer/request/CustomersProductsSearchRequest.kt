package com.ecwid.apiclient.v3.dto.customer.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.impl.RequestInfo

data class CustomersProductsSearchRequest(
	val name: String = "",
) : ApiRequest {
	override fun toRequestInfo() = RequestInfo.createGetRequest(
		pathSegments = listOf(
			"customers",
			"products"
		),
		params = mapOf(
			"name" to name,
		)
	)
}
