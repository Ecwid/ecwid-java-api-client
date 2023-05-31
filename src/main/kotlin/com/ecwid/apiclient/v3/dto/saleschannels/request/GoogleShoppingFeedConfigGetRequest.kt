package com.ecwid.apiclient.v3.dto.saleschannels.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.impl.RequestInfo
import com.ecwid.apiclient.v3.responsefields.ResponseFields

data class GoogleShoppingFeedConfigGetRequest(
	val responseFields: ResponseFields = ResponseFields.All,
) : ApiRequest {
	override fun toRequestInfo() = RequestInfo.createGetRequest(
		pathSegments = listOf(
			"saleschannels",
			"google_shopping"
		),
		responseFields = responseFields
	)
}
