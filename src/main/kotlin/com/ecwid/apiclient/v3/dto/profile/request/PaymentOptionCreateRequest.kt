package com.ecwid.apiclient.v3.dto.profile.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.httptransport.HttpBody
import com.ecwid.apiclient.v3.impl.RequestInfo

data class PaymentOptionCreateRequest(
	val newPaymentOption: UpdatedPaymentOption = UpdatedPaymentOption(),
) : ApiRequest {
	override fun toRequestInfo() = RequestInfo.createPostRequest(
		pathSegments = listOf(
			"profile",
			"paymentOptions",
		),
		params = mapOf(),
		httpBody = HttpBody.JsonBody(
			obj = newPaymentOption,
		)
	)
}
