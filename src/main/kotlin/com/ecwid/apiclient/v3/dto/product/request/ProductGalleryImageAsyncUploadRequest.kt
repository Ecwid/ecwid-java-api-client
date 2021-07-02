package com.ecwid.apiclient.v3.dto.product.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.dto.common.AsyncPictureData
import com.ecwid.apiclient.v3.httptransport.HttpBody
import com.ecwid.apiclient.v3.impl.RequestInfo

data class ProductGalleryImageAsyncUploadRequest(
	val productId: Int = 0,
	val asyncPictureData: AsyncPictureData = AsyncPictureData()

) : ApiRequest {
	override fun toRequestInfo(): RequestInfo {
		return RequestInfo.createPostRequest(
			pathSegments = listOf(
				"products",
				"$productId",
				"gallery",
				"async"
			),
			params = mapOf(),
			httpBody = HttpBody.JsonBody(obj = asyncPictureData)
		)
	}
}
