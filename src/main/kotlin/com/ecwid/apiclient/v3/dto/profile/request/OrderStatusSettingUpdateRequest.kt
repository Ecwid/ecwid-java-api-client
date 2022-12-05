package com.ecwid.apiclient.v3.dto.profile.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.httptransport.HttpBody
import com.ecwid.apiclient.v3.impl.RequestInfo

data class OrderStatusSettingUpdateRequest(
	val statusId: String = "",
	val updatedOrderStatusSetting: UpdatedOrderStatusSetting = UpdatedOrderStatusSetting()
) : ApiRequest {
	override fun toRequestInfo() = RequestInfo.createPutRequest(
		pathSegments = listOf(
			"profile",
			"order_status",
			statusId
		),
		httpBody = HttpBody.JsonBody(
			obj = updatedOrderStatusSetting
		)
	)
}
