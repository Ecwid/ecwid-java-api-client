package com.ecwid.apiclient.v3.dto.category.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.dto.AsyncPictureData
import com.ecwid.apiclient.v3.httptransport.HttpBody
import com.ecwid.apiclient.v3.impl.RequestInfo

data class CategoryImageAsyncUploadRequest(
		val categoryId: Int = 0,
		val asyncPictureData: AsyncPictureData = AsyncPictureData()

) : ApiRequest {
	override fun toRequestInfo(): RequestInfo {
		return RequestInfo.createPostRequest(
				endpoint = endpoint,
				params = mapOf(),
				httpBody = HttpBody.JsonBody(obj = asyncPictureData)
		)
	}

	private val endpoint = "categories/$categoryId/image/async"
}
