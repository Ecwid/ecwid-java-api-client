package com.ecwid.apiclient.v3.dto.extrafield.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.httptransport.HttpBody
import com.ecwid.apiclient.v3.impl.RequestInfo

data class CustomersConfigCreateRequest(
	val newConfig: UpdatedCustomersConfig = UpdatedCustomersConfig()
) : ApiRequest {
	override fun toRequestInfo() = RequestInfo.createPostRequest(
		pathSegments = listOf(
			"store_extrafields",
			"customers"
		),
		httpBody = HttpBody.JsonBody(
			obj = newConfig
		)
	)
}
