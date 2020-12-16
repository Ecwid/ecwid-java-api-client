package com.ecwid.apiclient.v3.dto.product.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.dto.common.UploadFileData
import com.ecwid.apiclient.v3.httptransport.HttpBody
import com.ecwid.apiclient.v3.impl.MIME_TYPE_OCTET_STREAM
import com.ecwid.apiclient.v3.impl.RequestInfo

data class ProductGalleryImageUploadRequest(
		val productId: Int = 0,
		val fileName: String = "",
		val fileData: UploadFileData = UploadFileData.ExternalUrlData("")
) : ApiRequest {
	override fun toRequestInfo(): RequestInfo {
		val commonParams = mapOf(
				"fileName" to fileName
		)

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

	private val endpoint = "products/$productId/gallery"
}
