package com.ecwid.apiclient.v3.dto.cart.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.httptransport.HttpBody
import com.ecwid.apiclient.v3.impl.RequestInfo

data class CalculateOrderDetailsRequest(
		var orderForCalculate: OrderForCalculate = OrderForCalculate()
) : ApiRequest {
	override fun toRequestInfo() = RequestInfo.createPostRequest(
			endpoint = "order/calculate",
			httpBody = HttpBody.JsonBody(
					obj = orderForCalculate
			)
	)
}
