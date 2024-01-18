package com.ecwid.apiclient.v3.dto.variation.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.httptransport.HttpBody
import com.ecwid.apiclient.v3.impl.RequestInfo

data class UpdateProductVariationRequest(
	val productId: Int = 0,
	val variationId: Int = 0,
	val variation: UpdatedVariation = UpdatedVariation(),
	val checkLowStockNotification: Boolean = false,
	val resetLocationInventory: Boolean? = null, // default is determined on server-side, consistent with ProductUpdateRequest
) : ApiRequest {
	override fun toRequestInfo() = RequestInfo.createPutRequest(
		pathSegments = listOf(
			"products",
			"$productId",
			"combinations",
			"$variationId"
		),
		params = listOfNotNull(
			"checkLowStockNotification" to checkLowStockNotification.toString(),
			resetLocationInventory?.let { "resetLocationInventory" to it.toString() }
		).toMap(),
		httpBody = HttpBody.JsonBody(
			obj = variation
		)
	)
}
