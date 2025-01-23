package com.ecwid.apiclient.v3.dto.category.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.dto.common.AsyncPictureData
import com.ecwid.apiclient.v3.httptransport.HttpBody
import com.ecwid.apiclient.v3.impl.RequestInfo

data class CategoryImageAsyncUploadRequest(
	val categoryId: Long = 0L,
	val asyncPictureData: AsyncPictureData = AsyncPictureData()

) : ApiRequest {
	override fun toRequestInfo(): RequestInfo {
		return RequestInfo.createPostRequest(
			pathSegments = listOf(
				"categories",
				"$categoryId",
				"image",
				"async"
			),
			params = mapOf(),
			httpBody = HttpBody.JsonBody(obj = asyncPictureData)
		)
	}
}
