package com.ecwid.apiclient.v3.dto.storage.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.httptransport.HttpBody
import com.ecwid.apiclient.v3.impl.MIME_TYPE_APPLICATION_JSON
import com.ecwid.apiclient.v3.impl.RequestInfo

data class StorageDataUpdateRequest(
	private val updatedStorageData: UpdatedStorageData = UpdatedStorageData(),
) : ApiRequest {

	override fun toRequestInfo() = RequestInfo.createPutRequest(
		pathSegments = listOf(
			"storage",
			updatedStorageData.key,
		),
		httpBody = HttpBody.ByteArrayBody(
			bytes = updatedStorageData.value.toByteArray(),
			mimeType = MIME_TYPE_APPLICATION_JSON,
		),
	)
}

