package com.ecwid.apiclient.v3.dto.order.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.impl.RequestInfo

data class OrderItemOptionFileDeleteRequest(
		val orderNumber: Int = 0,
		val orderIdentity: String = "",
		val orderItemId: Long = 0,
		val optionName: String = "",
		val fileId: Int = 0
) : ApiRequest {

	constructor(
			orderNumber: Int = 0,
			orderItemId: Long = 0,
			optionName: String = "",
			fileId: Int = 0
	) : this(
			orderNumber = orderNumber,
			orderIdentity = orderNumber.toString(),
			orderItemId = orderItemId,
			optionName = optionName,
			fileId = fileId
	)

	override fun toRequestInfo() = RequestInfo.createDeleteRequest(
			pathSegments = listOf(
				"orders",
				orderIdentity,
				"items",
				"$orderItemId",
				"options",
				optionName,
				"files",
				"$fileId"
			)
	)
}
