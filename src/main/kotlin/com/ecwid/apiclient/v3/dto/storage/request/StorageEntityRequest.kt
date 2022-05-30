package com.ecwid.apiclient.v3.dto.storage.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.impl.RequestInfo

data class StorageEntityRequest(
	val key: String = "",
) : ApiRequest {

	override fun toRequestInfo() = RequestInfo.createGetRequest(
		pathSegments = listOf(
			"storage",
			key
		),
	)

}
