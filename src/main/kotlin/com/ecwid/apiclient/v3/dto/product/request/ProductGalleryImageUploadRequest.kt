package com.ecwid.apiclient.v3.dto.product.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.dto.common.UploadFileData
import com.ecwid.apiclient.v3.dto.common.buildUploadRequestInfo
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

		return buildUploadRequestInfo(pathSegments, commonParams, fileData)
	}

	private val pathSegments = listOf(
		"products",
		"$productId",
		"gallery"
	)
}
