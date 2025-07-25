package com.ecwid.apiclient.v3.dto.customfromemail

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.impl.RequestInfo
import com.ecwid.apiclient.v3.responsefields.ResponseFields

data class GetCustomFromEmailSettingsRequest(
	val responseFields: ResponseFields = ResponseFields.All,
) : ApiRequest {
	override fun toRequestInfo() = RequestInfo.createGetRequest(
		pathSegments = listOf(
			"custom-from-email-settings",
		),
		responseFields = responseFields,
	)
}
