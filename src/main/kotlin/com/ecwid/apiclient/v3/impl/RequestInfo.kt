package com.ecwid.apiclient.v3.impl

import com.ecwid.apiclient.v3.dto.common.UploadFileData
import com.ecwid.apiclient.v3.httptransport.HttpBody
import com.ecwid.apiclient.v3.responsefields.ResponseFields
import com.ecwid.apiclient.v3.withResponseFieldsParam

class RequestInfo private constructor(
	val pathSegments: List<String>,
	val method: HttpMethod,
	val params: Map<String, String>,
	val headers: Map<String, String>,
	val httpBody: HttpBody
) {
	companion object {

		fun createGetRequest(
			pathSegments: List<String>,
			params: Map<String, String> = mapOf(),
			headers: Map<String, String> = mapOf(),
			responseFields: ResponseFields = ResponseFields.All, // default value will be removed in next releases
		) = RequestInfo(
			pathSegments = pathSegments,
			method = HttpMethod.GET,
			params = params.withResponseFieldsParam(responseFields),
			headers = headers,
			httpBody = HttpBody.EmptyBody,
		)

		fun createDeleteRequest(
			pathSegments: List<String>,
			params: Map<String, String> = mapOf(),
			headers: Map<String, String> = mapOf(),
		) = RequestInfo(
			pathSegments = pathSegments,
			method = HttpMethod.DELETE,
			params = params,
			headers = headers,
			httpBody = HttpBody.EmptyBody
		)

		fun createPostRequest(
			pathSegments: List<String>,
			params: Map<String, String> = mapOf(),
			headers: Map<String, String> = mapOf(),
			httpBody: HttpBody
		) = RequestInfo(
			pathSegments = pathSegments,
			method = HttpMethod.POST,
			params = params,
			headers = headers,
			httpBody = httpBody
		)

		fun createPutRequest(
			pathSegments: List<String>,
			params: Map<String, String> = mapOf(),
			headers: Map<String, String> = mapOf(),
			httpBody: HttpBody
		) = RequestInfo(
			pathSegments = pathSegments,
			method = HttpMethod.PUT,
			params = params,
			headers = headers,
			httpBody = httpBody
		)

		fun buildUploadRequestInfo(
			pathSegments: List<String>,
			commonParams: Map<String, String>,
			fileData: UploadFileData
		): RequestInfo {
			return when (fileData) {
				is UploadFileData.ExternalUrlData -> createPostRequest(
					pathSegments = pathSegments,
					params = commonParams + mapOf(
						"externalUrl" to fileData.externalUrl
					),
					httpBody = HttpBody.EmptyBody
				)

				is UploadFileData.ByteArrayData -> createPostRequest(
					pathSegments = pathSegments,
					params = commonParams,
					httpBody = HttpBody.ByteArrayBody(
						bytes = fileData.bytes,
						mimeType = MIME_TYPE_OCTET_STREAM
					)
				)

				is UploadFileData.LocalFileData -> createPostRequest(
					pathSegments = pathSegments,
					params = commonParams,
					httpBody = HttpBody.LocalFileBody(
						file = fileData.file,
						mimeType = MIME_TYPE_OCTET_STREAM
					)
				)

				is UploadFileData.InputStreamData -> createPostRequest(
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
