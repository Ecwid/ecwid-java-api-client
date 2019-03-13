package com.ecwid.apiclient.v3.dto.product.request

data class ProductUpdateRequest(
		val productId: Int = 0,
		val updatedProduct: UpdatedProduct = UpdatedProduct()
)