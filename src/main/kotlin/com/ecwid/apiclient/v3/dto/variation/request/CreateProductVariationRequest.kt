package com.ecwid.apiclient.v3.dto.variation.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.httptransport.HttpBody
import com.ecwid.apiclient.v3.impl.RequestInfo

data class CreateProductVariationRequest(
		val productId: Int = 0,
		val newVariaion: UpdatedVariation = UpdatedVariation()
) : ApiRequest {
	override fun toRequestInfo() = RequestInfo.createPostRequest(
			pathSegments = listOf(
					"products",
					"$productId",
					"combinations"
			),
			params = mapOf(),
			httpBody = HttpBody.JsonBody(
					obj = newVariaion
			)
	)
}
