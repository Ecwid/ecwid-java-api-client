package com.ecwid.apiclient.v3.dto.category.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.httptransport.HttpBody
import com.ecwid.apiclient.v3.impl.RequestInfo

data class CategoryCreateRequest(
		val newCategory: UpdatedCategory = UpdatedCategory()
) : ApiRequest {
	override fun toRequestInfo() = RequestInfo.createPostRequest(
			pathSegments = listOf(
					"categories"
			),
			httpBody = HttpBody.JsonBody(
					obj = newCategory
			)
	)
}
