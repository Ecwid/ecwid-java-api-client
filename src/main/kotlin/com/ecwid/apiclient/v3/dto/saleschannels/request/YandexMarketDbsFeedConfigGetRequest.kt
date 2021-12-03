package com.ecwid.apiclient.v3.dto.saleschannels.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.impl.RequestInfo

class YandexMarketDbsFeedConfigGetRequest : ApiRequest {
	override fun toRequestInfo() = RequestInfo.createGetRequest(
		pathSegments = listOf(
			"saleschannels",
			"yandex_market_dbs"
		)
	)
}
