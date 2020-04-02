package com.ecwid.apiclient.v3.dto.variation.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.httptransport.HttpBody
import com.ecwid.apiclient.v3.impl.RequestInfo

data class UpdateProductVariationRequest(
		val productId: Int = 0,
		val variationId: Int = 0,
		val checkLowStockNotification: Boolean = false,
		val variation: UpdatedVariation = UpdatedVariation()
) : ApiRequest {
	override fun toRequestInfo() = RequestInfo.createPutRequest(
			endpoint = "products/$productId/combinations/$variationId",
			params = mapOf("checkLowStockNotification" to checkLowStockNotification.toString()),
			httpBody = HttpBody.JsonBody(
					obj = variation
			)
	)
}
