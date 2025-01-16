package com.ecwid.apiclient.v3.dto.profile.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.httptransport.HttpBody
import com.ecwid.apiclient.v3.impl.RequestInfo

data class ShippingOptionUpdateRequest(
	val optionId: String = "",
	val updatedShippingOption: UpdatedShippingOption = UpdatedShippingOption()
) : ApiRequest {
	override fun toRequestInfo() = RequestInfo.createPutRequest(
		pathSegments = listOf(
			"profile",
			"shippingOptions",
			optionId,
		),
		httpBody = HttpBody.JsonBody(
			obj = updatedShippingOption
		)
	)
}
