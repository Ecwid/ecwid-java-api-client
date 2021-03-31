package com.ecwid.apiclient.v3.dto.order.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.httptransport.HttpBody
import com.ecwid.apiclient.v3.impl.RequestInfo

data class OrderCreateRequest(
		val newOrder: UpdatedOrder = UpdatedOrder()
) : ApiRequest {
	override fun toRequestInfo() = RequestInfo.createPostRequest(
			pathSegments = listOf(
					"orders"
			),
			httpBody = HttpBody.JsonBody(
					obj = newOrder
			)
	)
}
