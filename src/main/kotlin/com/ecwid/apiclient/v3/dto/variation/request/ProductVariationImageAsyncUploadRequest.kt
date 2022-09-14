package com.ecwid.apiclient.v3.dto.variation.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.dto.common.AsyncPictureData
import com.ecwid.apiclient.v3.httptransport.HttpBody
import com.ecwid.apiclient.v3.impl.RequestInfo

data class ProductVariationImageAsyncUploadRequest(
	val productId: Int,
	val variationId: Int,
	val asyncPictureData: AsyncPictureData
) : ApiRequest {
	override fun toRequestInfo(): RequestInfo {
		return RequestInfo.createPostRequest(
			pathSegments = listOf(
				"products",
				"$productId",
				"combinations",
				"$variationId",
				"image",
				"async"
			),
			params = mapOf(),
			httpBody = HttpBody.JsonBody(obj = asyncPictureData)
		)
	}
}
