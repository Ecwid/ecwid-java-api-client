package com.ecwid.apiclient.v3.dto.storage.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.impl.RequestInfo

class StorageEntitiesRequest : ApiRequest {

	override fun toRequestInfo() = RequestInfo.createGetRequest(
		pathSegments = listOf(
			"storage"
		)
	)

}
