package com.ecwid.apiclient.v3.dto.product.request

data class ProductFileUpdateRequest(
		val productId: Int = 0,
		val fileId: Int = 0,
		val updatedProductFile: UpdatedProductFile = UpdatedProductFile()
) {

	data class UpdatedProductFile(
			val description: String = ""
	)

}