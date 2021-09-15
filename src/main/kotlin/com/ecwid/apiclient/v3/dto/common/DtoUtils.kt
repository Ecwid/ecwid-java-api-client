package com.ecwid.apiclient.v3.dto.common

import com.ecwid.apiclient.v3.httptransport.HttpBody
import com.ecwid.apiclient.v3.impl.MIME_TYPE_OCTET_STREAM
import com.ecwid.apiclient.v3.impl.RequestInfo

internal fun buildUploadRequestInfo(
	pathSegments: List<String>,
	commonParams: Map<String, String>,
	fileData: UploadFileData
): RequestInfo {
	return when (fileData) {
		is UploadFileData.ExternalUrlData -> RequestInfo.createPostRequest(
			pathSegments = pathSegments,
			params = commonParams + mapOf(
				"externalUrl" to fileData.externalUrl
			),
			httpBody = HttpBody.EmptyBody
		)
		is UploadFileData.ByteArrayData -> RequestInfo.createPostRequest(
			pathSegments = pathSegments,
			params = commonParams,
			httpBody = HttpBody.ByteArrayBody(
				bytes = fileData.bytes,
				mimeType = MIME_TYPE_OCTET_STREAM
			)
		)
		is UploadFileData.LocalFileData -> RequestInfo.createPostRequest(
			pathSegments = pathSegments,
			params = commonParams,
			httpBody = HttpBody.LocalFileBody(
				file = fileData.file,
				mimeType = MIME_TYPE_OCTET_STREAM
			)
		)
		is UploadFileData.InputStreamData -> RequestInfo.createPostRequest(
			pathSegments = pathSegments,
			params = commonParams,
			httpBody = HttpBody.InputStreamBody(
				stream = fileData.stream,
				mimeType = MIME_TYPE_OCTET_STREAM
			)
		)
	}
}
