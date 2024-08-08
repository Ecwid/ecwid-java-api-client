package com.ecwid.apiclient.v3.dto.extrafield.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.impl.RequestInfo

data class CustomersConfigDeleteRequest(
	val extrafieldKey: String = ""
) : ApiRequest {
	override fun toRequestInfo() = RequestInfo.createDeleteRequest(
		pathSegments = listOf(
			"store_extrafields",
			"customers",
			extrafieldKey
		),
		params = mapOf()
	)
}
