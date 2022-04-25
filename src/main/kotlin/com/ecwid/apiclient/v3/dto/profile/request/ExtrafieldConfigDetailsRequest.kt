package com.ecwid.apiclient.v3.dto.profile.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.impl.RequestInfo

data class ExtrafieldConfigDetailsRequest(
	val extrafieldKey: String = ""
) : ApiRequest {
	override fun toRequestInfo() = RequestInfo.createGetRequest(
		pathSegments = listOf(
			"profile",
			"extrafields",
			extrafieldKey
		),
		params = mapOf()
	)
}
