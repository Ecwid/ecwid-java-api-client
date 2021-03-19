package com.ecwid.apiclient.v3.dto.category.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.impl.RequestInfo

data class CategoryDeleteRequest(
		val categoryId: Int = 0
) : ApiRequest {
	override fun toRequestInfo() = RequestInfo.createDeleteRequest(
			pathSegments = listOf(
				"categories",
				"$categoryId"
			),
			params = mapOf()
	)
}
