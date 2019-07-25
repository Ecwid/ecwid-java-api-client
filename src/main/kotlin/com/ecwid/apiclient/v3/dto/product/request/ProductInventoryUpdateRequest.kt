package com.ecwid.apiclient.v3.dto.product.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.httptransport.HttpBody
import com.ecwid.apiclient.v3.impl.RequestInfo

data class ProductInventoryUpdateRequest(
		val productId: Int = 0,
		val inventoryAdjustment: InventoryAdjustment = InventoryAdjustment(0),
		val checkLowStockNotification: Boolean? = null
) : ApiRequest {
	override fun toRequestInfo() = RequestInfo.createPutRequest(
			endpoint = "products/$productId/inventory",
			params = toParams(),
			httpBody = HttpBody.JsonBody(
					obj = inventoryAdjustment
			)
	)

	data class InventoryAdjustment(
			val quantityDelta: Int = 0
	)

	private fun toParams(): Map<String, String> {
		return mutableMapOf<String, String>().apply {
			checkLowStockNotification?.let { put("checkLowStockNotification", it.toString()) }
		}.toMap()
	}
}
