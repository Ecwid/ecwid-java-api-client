package com.ecwid.apiclient.v3.dto.storage.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.impl.RequestInfo
import com.ecwid.apiclient.v3.responsefields.ResponseFields

data class StorageDataRequest(
	val key: String = "",
	val responseFields: ResponseFields = ResponseFields.All,
) : ApiRequest {

	override fun toRequestInfo() = RequestInfo.createGetRequest(
		pathSegments = listOf(
			"storage",
			key
		),
		responseFields = responseFields,
	)

}
