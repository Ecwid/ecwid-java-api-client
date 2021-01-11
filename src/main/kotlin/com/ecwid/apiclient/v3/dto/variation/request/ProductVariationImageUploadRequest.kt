package com.ecwid.apiclient.v3.dto.variation.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.dto.common.UploadFileData
import com.ecwid.apiclient.v3.httptransport.HttpBody
import com.ecwid.apiclient.v3.impl.MIME_TYPE_OCTET_STREAM
import com.ecwid.apiclient.v3.impl.RequestInfo

data class ProductVariationImageUploadRequest(
		val productId: Int = 0,
		val variationId: Int = 0,
		val fileData: UploadFileData = UploadFileData.ExternalUrlData("")
) : ApiRequest {

	override fun toRequestInfo() = when (fileData) {
		is UploadFileData.ExternalUrlData -> RequestInfo.createPostRequest(
				endpoint = endpoint,
				params = mapOf(
						"externalUrl" to fileData.externalUrl
				),
				httpBody = HttpBody.EmptyBody
		)
		is UploadFileData.ByteArrayData -> RequestInfo.createPostRequest(
				endpoint = endpoint,
				httpBody = HttpBody.ByteArrayBody(
						bytes = fileData.bytes,
						mimeType = MIME_TYPE_OCTET_STREAM
				)
		)
		is UploadFileData.LocalFileData -> RequestInfo.createPostRequest(
				endpoint = endpoint,
				httpBody = HttpBody.LocalFileBody(
						file = fileData.file,
						mimeType = MIME_TYPE_OCTET_STREAM
				)
		)
		is UploadFileData.InputStreamData -> RequestInfo.createPostRequest(
				endpoint = endpoint,
				httpBody = HttpBody.InputStreamBody(
						stream = fileData.stream,
						mimeType = MIME_TYPE_OCTET_STREAM
				)
		)
	}

	private val endpoint = "products/$productId/combinations/$variationId/image"

}
