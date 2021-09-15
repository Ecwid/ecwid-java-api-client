package com.ecwid.apiclient.v3.impl

import com.ecwid.apiclient.v3.dto.common.UploadFileData
import com.ecwid.apiclient.v3.httptransport.HttpBody

class RequestInfo private constructor(
	val pathSegments: List<String>,
	val method: HttpMethod,
	val params: Map<String, String>,
	val httpBody: HttpBody
) {
	companion object {

		fun createGetRequest(
			pathSegments: List<String>,
			params: Map<String, String> = mapOf()
		) = RequestInfo(
			pathSegments = pathSegments,
			method = HttpMethod.GET,
			params = params,
			httpBody = HttpBody.EmptyBody
		)

		fun createDeleteRequest(
			pathSegments: List<String>,
			params: Map<String, String> = mapOf()
		) = RequestInfo(
			pathSegments = pathSegments,
			method = HttpMethod.DELETE,
			params = params,
			httpBody = HttpBody.EmptyBody
		)

		fun createPostRequest(
			pathSegments: List<String>,
			params: Map<String, String> = mapOf(),
			httpBody: HttpBody
		) = RequestInfo(
			pathSegments = pathSegments,
			method = HttpMethod.POST,
			params = params,
			httpBody = httpBody
		)

		fun createPutRequest(
			pathSegments: List<String>,
			params: Map<String, String> = mapOf(),
			httpBody: HttpBody
		) = RequestInfo(
			pathSegments = pathSegments,
			method = HttpMethod.PUT,
			params = params,
			httpBody = httpBody
		)

		fun buildUploadRequestInfo(
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

	}
}

enum class HttpMethod {
	GET,
	POST,
	PUT,
	DELETE
}
