package com.ecwid.apiclient.v3.dto.product.request

data class ProductInventoryUpdateRequest(
		val productId: Int = 0,
		val inventoryAdjustment: InventoryAdjustment = InventoryAdjustment(0),
		val checkLowStockNotification: Boolean? = null
) {

	data class InventoryAdjustment(
			val quantityDelta: Int = 0
	)

}