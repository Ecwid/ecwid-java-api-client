package com.ecwid.apiclient.v3.dto.category.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.dto.common.UploadFileData
import com.ecwid.apiclient.v3.httptransport.HttpBody
import com.ecwid.apiclient.v3.impl.MIME_TYPE_OCTET_STREAM
import com.ecwid.apiclient.v3.impl.RequestInfo

data class CategoryImageUploadRequest(
	val categoryId: Long = 0L,
	val fileData: UploadFileData = UploadFileData.ExternalUrlData("")
) : ApiRequest {

	override fun toRequestInfo() = when (fileData) {
		is UploadFileData.ExternalUrlData -> RequestInfo.createPostRequest(
			pathSegments = pathSegments,
			params = mapOf(
				"externalUrl" to fileData.externalUrl
			),
			httpBody = HttpBody.EmptyBody
		)
		is UploadFileData.ByteArrayData -> RequestInfo.createPostRequest(
			pathSegments = pathSegments,
			httpBody = HttpBody.ByteArrayBody(
				bytes = fileData.bytes,
				mimeType = MIME_TYPE_OCTET_STREAM
			)
		)
		is UploadFileData.LocalFileData -> RequestInfo.createPostRequest(
			pathSegments = pathSegments,
			httpBody = HttpBody.LocalFileBody(
				file = fileData.file,
				mimeType = MIME_TYPE_OCTET_STREAM
			)
		)
		is UploadFileData.InputStreamData -> RequestInfo.createPostRequest(
			pathSegments = pathSegments,
			httpBody = HttpBody.InputStreamBody(
				stream = fileData.stream,
				mimeType = MIME_TYPE_OCTET_STREAM
			)
		)
	}

	private val pathSegments = listOf(
		"categories",
		"$categoryId",
		"image"
	)
}
