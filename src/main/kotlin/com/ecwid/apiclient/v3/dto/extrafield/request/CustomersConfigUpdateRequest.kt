package com.ecwid.apiclient.v3.dto.extrafield.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.httptransport.HttpBody
import com.ecwid.apiclient.v3.impl.RequestInfo

data class CustomersConfigUpdateRequest(
	val extrafieldKey: String = "",
	val updatedConfig: UpdatedCustomersConfig = UpdatedCustomersConfig()
) : ApiRequest {
	override fun toRequestInfo() = RequestInfo.createPutRequest(
		pathSegments = listOf(
			"store_extrafields",
			"customers",
			extrafieldKey
		),
		httpBody = HttpBody.JsonBody(
			obj = updatedConfig
		)
	)
}
