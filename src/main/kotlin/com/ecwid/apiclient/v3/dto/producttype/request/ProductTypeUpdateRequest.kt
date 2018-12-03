package com.ecwid.apiclient.v3.dto.producttype.request

data class ProductTypeUpdateRequest(
	var productTypeId: Int = 0,
	var updatedProductType: UpdatedProductType = UpdatedProductType()
)