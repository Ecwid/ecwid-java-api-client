package com.ecwid.apiclient.v3.dto.category.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.httptransport.HttpBody
import com.ecwid.apiclient.v3.impl.RequestInfo

data class CategoryUpdateRequest(
		val categoryId: Int = 0,
		val updatedCategory: UpdatedCategory = UpdatedCategory()
) : ApiRequest {
	override fun toRequestInfo() = RequestInfo.createPutRequest(
			endpoint = "categories/$categoryId",
			httpBody = HttpBody.JsonBody(
					obj = updatedCategory
			)
	)
}