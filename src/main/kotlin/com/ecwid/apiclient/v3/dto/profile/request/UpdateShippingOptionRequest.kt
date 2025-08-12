package com.ecwid.apiclient.v3.dto.profile.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.httptransport.HttpBody
import com.ecwid.apiclient.v3.impl.RequestInfo

data class UpdateShippingOptionRequest(
	private val optionId: String = "",
	private val updatedShippingOption: UpdatedShippingOption = UpdatedShippingOption()
) : ApiRequest {
	override fun toRequestInfo(): RequestInfo {
		return RequestInfo.createPutRequest(
			pathSegments = listOf(
				"profile",
				"shippingOptions",
				optionId
			),
			httpBody = HttpBody.JsonBody(
				obj = updatedShippingOption
			)
		)
	}
}
