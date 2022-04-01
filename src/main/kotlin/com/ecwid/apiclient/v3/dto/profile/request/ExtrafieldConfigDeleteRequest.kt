package com.ecwid.apiclient.v3.dto.profile.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.impl.RequestInfo

data class ExtrafieldConfigDeleteRequest(
	val extrafieldKey: String = ""
) : ApiRequest {
	override fun toRequestInfo() = RequestInfo.createDeleteRequest(
		pathSegments = listOf(
			"profile",
			"extrafields",
			extrafieldKey
		),
		params = mapOf()
	)
}
