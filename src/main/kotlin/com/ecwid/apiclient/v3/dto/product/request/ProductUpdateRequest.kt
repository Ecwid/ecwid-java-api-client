package com.ecwid.apiclient.v3.dto.product.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.httptransport.HttpBody
import com.ecwid.apiclient.v3.impl.RequestInfo

data class ProductUpdateRequest(
		val productId: Int = 0,
		val updatedProduct: UpdatedProduct = UpdatedProduct()
) : ApiRequest {
	override fun toRequestInfo() = RequestInfo.createPutRequest(
			pathSegments = listOf(
					"products",
					"$productId"
			),
			params = mapOf(),
			httpBody = HttpBody.JsonBody(
					obj = updatedProduct
			)
	)
}
