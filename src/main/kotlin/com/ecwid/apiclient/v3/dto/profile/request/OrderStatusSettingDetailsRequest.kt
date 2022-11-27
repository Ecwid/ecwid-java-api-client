package com.ecwid.apiclient.v3.dto.profile.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.impl.RequestInfo

data class OrderStatusSettingDetailsRequest(
	val statusId: String = ""
) : ApiRequest {
	override fun toRequestInfo() = RequestInfo.createGetRequest(
		pathSegments = listOf(
			"profile",
			"order_status",
			statusId
		),
		params = mapOf()
	)
}
