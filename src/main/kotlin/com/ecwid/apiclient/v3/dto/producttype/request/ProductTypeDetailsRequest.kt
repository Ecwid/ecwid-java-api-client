package com.ecwid.apiclient.v3.dto.producttype.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.impl.RequestInfo

data class ProductTypeDetailsRequest(
		var productTypeId: Int = 0
) : ApiRequest {
	override fun toRequestInfo() = RequestInfo.createGetRequest(
			endpoint = "classes/$productTypeId"
	)
}
