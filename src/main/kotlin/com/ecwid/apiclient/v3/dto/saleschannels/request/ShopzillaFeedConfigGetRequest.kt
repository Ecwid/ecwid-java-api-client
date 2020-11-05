package com.ecwid.apiclient.v3.dto.saleschannels.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.impl.RequestInfo

class ShopzillaFeedConfigGetRequest: ApiRequest {
	override fun toRequestInfo() = RequestInfo.createGetRequest(
			endpoint = "saleschannels/googleshoppingfeed"
	)
}
