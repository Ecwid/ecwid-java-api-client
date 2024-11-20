package com.ecwid.apiclient.v3.dto.category.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.httptransport.HttpBody
import com.ecwid.apiclient.v3.impl.RequestInfo

data class CategoryUpdateRequest(
	val categoryId: Long = 0L,
	val updatedCategory: UpdatedCategory = UpdatedCategory()
) : ApiRequest {
	override fun toRequestInfo() = RequestInfo.createPutRequest(
		pathSegments = listOf(
			"categories",
			"$categoryId"
		),
		httpBody = HttpBody.JsonBody(
			obj = updatedCategory
		)
	)
}
