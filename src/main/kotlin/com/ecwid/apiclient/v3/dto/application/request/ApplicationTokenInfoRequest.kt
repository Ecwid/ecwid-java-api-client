package com.ecwid.apiclient.v3.dto.application.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.impl.RequestInfo

class ApplicationTokenInfoRequest : ApiRequest {
	override fun toRequestInfo() = RequestInfo.createGetRequest(
		pathSegments = listOf(
			"application",
			"token",
		)
	)
}
