package com.ecwid.apiclient.v3.dto.profile.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.impl.RequestInfo
import com.ecwid.apiclient.v3.responsefields.ResponseFields

data class LatestStatsRequest @JvmOverloads constructor(
	val productCountRequired: Boolean = false,
	val categoryCountRequired: Boolean = false,
	val responseFields: ResponseFields = ResponseFields.All,
) : ApiRequest {

	override fun toRequestInfo() = RequestInfo.createGetRequest(
		pathSegments = listOf(
			"latest-stats"
		),
		params = mapOf(
			"productCountRequired" to productCountRequired.toString(),
			"categoryCountRequired" to categoryCountRequired.toString(),
		),
		responseFields = responseFields,
	)
}
