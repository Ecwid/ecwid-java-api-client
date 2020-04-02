package com.ecwid.apiclient.v3.dto.order.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.dto.UploadFileData
import com.ecwid.apiclient.v3.httptransport.HttpBody
import com.ecwid.apiclient.v3.impl.MIME_TYPE_OCTET_STREAM
import com.ecwid.apiclient.v3.impl.RequestInfo

data class OrderItemOptionFileUploadRequest(
		var orderNumber: Int = 0,
		var orderItemId: Int = 0,
		var optionName: String = "",
		var fileName: String = "",
		var fileData: UploadFileData = UploadFileData.ExternalUrlData("")
) : ApiRequest {
	override fun toRequestInfo(): RequestInfo {
		val commonParams = mapOf(
				"fileName" to fileName
		)
		val fileData = fileData

		return when (fileData) {
			is UploadFileData.ExternalUrlData -> RequestInfo.createPostRequest(
					endpoint = endpoint,
					params = commonParams + mapOf(
							"externalUrl" to fileData.externalUrl
					),
					httpBody = HttpBody.EmptyBody
			)
			is UploadFileData.ByteArrayData -> RequestInfo.createPostRequest(
					endpoint = endpoint,
					params = commonParams,
					httpBody = HttpBody.ByteArrayBody(
							bytes = fileData.bytes,
							mimeType = MIME_TYPE_OCTET_STREAM
					)
			)
			is UploadFileData.LocalFileData -> RequestInfo.createPostRequest(
					endpoint = endpoint,
					params = commonParams,
					httpBody = HttpBody.LocalFileBody(
							file = fileData.file,
							mimeType = MIME_TYPE_OCTET_STREAM
					)
			)
			is UploadFileData.InputStreamData -> RequestInfo.createPostRequest(
					endpoint = endpoint,
					params = commonParams,
					httpBody = HttpBody.InputStreamBody(
							stream = fileData.stream,
							mimeType = MIME_TYPE_OCTET_STREAM
					)
			)
		}
	}

	private val endpoint = "orders/$orderNumber/items/$orderItemId/options/$optionName"
}
