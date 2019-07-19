package com.ecwid.apiclient.v3.dto.product.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.httptransport.HttpBody
import com.ecwid.apiclient.v3.impl.RequestInfo

data class ProductCreateRequest(
		val newProduct: UpdatedProduct = UpdatedProduct()
) : ApiRequest {
	override fun toRequestInfo() = RequestInfo.createPostRequest(
			endpoint = "products",
			params = mapOf(),
			httpBody = HttpBody.JsonBody(
					obj = newProduct
			)
	)
}