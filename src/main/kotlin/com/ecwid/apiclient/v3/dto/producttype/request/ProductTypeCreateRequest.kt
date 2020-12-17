package com.ecwid.apiclient.v3.dto.producttype.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.httptransport.HttpBody
import com.ecwid.apiclient.v3.impl.RequestInfo

data class ProductTypeCreateRequest(
		val newProductType: UpdatedProductType = UpdatedProductType()
) : ApiRequest {
	override fun toRequestInfo() = RequestInfo.createPostRequest(
			endpoint = "classes",
			httpBody = HttpBody.JsonBody(
					obj = newProductType
			)
	)
}
