package com.ecwid.apiclient.v3.dto.product.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.impl.RequestInfo

data class ProductGalleryImageDeleteRequest(
	val productId: Int = 0,
	val fileId: Long = 0
) : ApiRequest {
	override fun toRequestInfo() = RequestInfo.createDeleteRequest(
		pathSegments = listOf(
			"products",
			"$productId",
			"gallery",
			"$fileId"
		)
	)
}
