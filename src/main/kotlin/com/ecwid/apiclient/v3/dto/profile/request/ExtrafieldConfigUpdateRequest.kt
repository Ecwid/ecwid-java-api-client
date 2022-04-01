package com.ecwid.apiclient.v3.dto.profile.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.httptransport.HttpBody
import com.ecwid.apiclient.v3.impl.RequestInfo

data class ExtrafieldConfigUpdateRequest(
	val extrafieldKey: String = "",
	val updatedConfig: UpdatedExtrafieldConfig = UpdatedExtrafieldConfig()
) : ApiRequest {
	override fun toRequestInfo() = RequestInfo.createPutRequest(
		pathSegments = listOf(
			"profile",
			"extrafields",
			"$extrafieldKey"
		),
		httpBody = HttpBody.JsonBody(
			obj = updatedConfig
		)
	)
}
