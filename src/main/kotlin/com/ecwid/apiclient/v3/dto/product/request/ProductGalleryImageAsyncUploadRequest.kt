package com.ecwid.apiclient.v3.dto.product.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.dto.common.AsyncPictureData
import com.ecwid.apiclient.v3.httptransport.HttpBody
import com.ecwid.apiclient.v3.impl.RequestInfo

data class ProductGalleryImageAsyncUploadRequest(
		val productId: Int,
		val asyncPictureData: AsyncPictureData

) : ApiRequest {
	override fun toRequestInfo(): RequestInfo {
		return RequestInfo.createPostRequest(
				endpoint = endpoint,
				params = mapOf(),
				httpBody = HttpBody.JsonBody(obj = asyncPictureData)
		)
	}

	private val endpoint = "products/$productId/gallery/async"
}
