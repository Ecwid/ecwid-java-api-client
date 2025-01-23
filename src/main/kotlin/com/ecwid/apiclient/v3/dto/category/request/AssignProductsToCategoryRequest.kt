package com.ecwid.apiclient.v3.dto.category.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.httptransport.HttpBody
import com.ecwid.apiclient.v3.impl.RequestInfo

data class AssignProductsToCategoryRequest(
	val categoryId: Long = 0L,
	val productIds: List<Int> = emptyList()
) : ApiRequest {
	override fun toRequestInfo() = RequestInfo.createPostRequest(
		pathSegments = listOf(
			"categories",
			"$categoryId",
			"assignProducts"
		),
		httpBody = HttpBody.JsonBody(CategoryProductIds(productIds))
	)
}
