package com.ecwid.apiclient.v3.dto.profile.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.httptransport.HttpBody
import com.ecwid.apiclient.v3.impl.RequestInfo

data class PaymentOptionUpdateRequest(
	private val id: String = "",
	private val updatedPaymentOption: UpdatedPaymentOption = UpdatedPaymentOption()
) : ApiRequest {
	override fun toRequestInfo() = RequestInfo.createPutRequest(
		pathSegments = listOf(
			"profile",
			"paymentOptions",
			id,
		),
		httpBody = HttpBody.JsonBody(
			obj = updatedPaymentOption
		),
	)
}
