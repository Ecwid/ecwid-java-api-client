package com.ecwid.apiclient.v3.dto.extrafield.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.impl.RequestInfo
import com.ecwid.apiclient.v3.responsefields.ResponseFields


data class CustomersConfigsSearchRequest(
	val responseFields: ResponseFields = ResponseFields.All,
) : ApiRequest {
	override fun toRequestInfo() = RequestInfo.createGetRequest(
		pathSegments = listOf(
			"store_extrafields",
			"customers"
		),
		params = mapOf(),
		responseFields = responseFields,
	)
}
