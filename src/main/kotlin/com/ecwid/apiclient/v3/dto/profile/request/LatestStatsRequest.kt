package com.ecwid.apiclient.v3.dto.profile.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.impl.RequestInfo

class LatestStatsRequest : ApiRequest {

	override fun toRequestInfo() = RequestInfo.createGetRequest(
			endpoint = "latest-stats"
	)

}
