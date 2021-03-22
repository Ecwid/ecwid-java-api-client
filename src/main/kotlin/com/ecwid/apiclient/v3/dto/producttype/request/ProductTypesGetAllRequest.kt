package com.ecwid.apiclient.v3.dto.producttype.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.impl.RequestInfo

class ProductTypesGetAllRequest : ApiRequest {
	override fun toRequestInfo() = RequestInfo.createGetRequest(
			pathSegments = listOf(
				"classes"
			)
	)
}
