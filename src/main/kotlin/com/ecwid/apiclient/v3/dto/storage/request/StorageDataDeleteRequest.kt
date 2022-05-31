package com.ecwid.apiclient.v3.dto.storage.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.impl.RequestInfo

data class StorageDataDeleteRequest(
	val key: String = "",
) : ApiRequest {

	override fun toRequestInfo() = RequestInfo.createDeleteRequest(
		pathSegments = listOf(
			"storage",
			key,
		),
	)

}
