package com.ecwid.apiclient.v3.dto.variation.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.impl.RequestInfo

data class DeleteProductVariationRequest(
	val productId: Int = 0,
	val variationId: Int = 0
) : ApiRequest {
	override fun toRequestInfo() = RequestInfo.createDeleteRequest(
		pathSegments = listOf(
			"products",
			"$productId",
			"combinations",
			"$variationId"
		)
	)
}
