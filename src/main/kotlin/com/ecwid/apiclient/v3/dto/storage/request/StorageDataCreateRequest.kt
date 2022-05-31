package com.ecwid.apiclient.v3.dto.storage.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.httptransport.HttpBody
import com.ecwid.apiclient.v3.impl.MIME_TYPE_APPLICATION_JSON
import com.ecwid.apiclient.v3.impl.RequestInfo

data class StorageDataCreateRequest(
	private val updatedStorageData: UpdatedStorageData = UpdatedStorageData(),
) : ApiRequest {

	override fun toRequestInfo() = RequestInfo.createPostRequest(
		pathSegments = listOf(
			"storage",
			updatedStorageData.key,
		),
		httpBody = HttpBody.ByteArrayBody(
			bytes = updatedStorageData.value?.toByteArray() ?: ByteArray(0),
			mimeType = MIME_TYPE_APPLICATION_JSON,
		),
	)
}
