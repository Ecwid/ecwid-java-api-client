package com.ecwid.apiclient.v3.dto.product.request

data class ProductCreateRequest(
		val newProduct: UpdatedProduct = UpdatedProduct()
)