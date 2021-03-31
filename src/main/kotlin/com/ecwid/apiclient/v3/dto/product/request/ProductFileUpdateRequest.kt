package com.ecwid.apiclient.v3.dto.product.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.httptransport.HttpBody
import com.ecwid.apiclient.v3.impl.RequestInfo

data class ProductFileUpdateRequest(
		val productId: Int = 0,
		val fileId: Int = 0,
		val updatedProductFile: UpdatedProductFile = UpdatedProductFile()
) : ApiRequest {
	override fun toRequestInfo() = RequestInfo.createPutRequest(
			pathSegments = listOf(
					"products",
					"$productId",
					"files",
					"$fileId"
			),
			params = mapOf(),
			httpBody = HttpBody.JsonBody(
					obj = updatedProductFile
			)
	)

	data class UpdatedProductFile(
			val description: String = ""
	)

}
