package com.ecwid.apiclient.v3.dto.swatches.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.impl.RequestInfo

class RecentSwatchColorsGetRequest : ApiRequest {
	override fun toRequestInfo(): RequestInfo {
		return RequestInfo.createGetRequest(
			pathSegments = listOf(
				"swatches",
				"recent-colors",
			),
		)
	}

}
