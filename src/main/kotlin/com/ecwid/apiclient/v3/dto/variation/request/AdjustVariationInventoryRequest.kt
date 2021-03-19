package com.ecwid.apiclient.v3.dto.variation.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.httptransport.HttpBody
import com.ecwid.apiclient.v3.impl.RequestInfo

data class AdjustVariationInventoryRequest(
		val productId: Int = 0,
		val variationId: Int = 0,
		val checkLowStockNotification: Boolean = false,
		val quantityDelta: Int = 0
) : ApiRequest {
	override fun toRequestInfo() = RequestInfo.createPutRequest(
			pathSegments = listOf(
					"products",
					"$productId",
					"combinations",
					"$variationId",
					"inventory"
			),
			params = mapOf("checkLowStockNotification" to checkLowStockNotification.toString()),
			httpBody = HttpBody.JsonBody(
					obj = mapOf("quantityDelta" to quantityDelta)
			)
	)
}
