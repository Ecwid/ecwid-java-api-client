package com.ecwid.apiclient.v3.dto.storage.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.httptransport.HttpBody
import com.ecwid.apiclient.v3.impl.MIME_TYPE_APPLICATION_JSON
import com.ecwid.apiclient.v3.impl.RequestInfo

data class StorageEntityUpdateRequest(
	private val updatedStorageEntity: UpdatedStorageEntity = UpdatedStorageEntity(),
) : ApiRequest {

	override fun toRequestInfo() = RequestInfo.createPutRequest(
		pathSegments = listOf(
			"storage",
			updatedStorageEntity.key,
		),
		httpBody = HttpBody.ByteArrayBody(
			bytes = updatedStorageEntity.value.toByteArray(),
			mimeType = MIME_TYPE_APPLICATION_JSON,
		),
	)
}

