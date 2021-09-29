package com.ecwid.apiclient.v3.dto.product.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.httptransport.HttpBody
import com.ecwid.apiclient.v3.impl.RequestInfo

data class ProductUpdateRequest(
	val productId: Int = 0,
	val updatedProduct: UpdatedProduct = UpdatedProduct(),
	val checkLowStockNotification: Boolean? = null
) : ApiRequest {
	override fun toRequestInfo() = RequestInfo.createPutRequest(
		pathSegments = listOf(
			"products",
			"$productId"
		),
		params = toParams(),
		httpBody = HttpBody.JsonBody(
			obj = updatedProduct
		)
	)

	private fun toParams(): Map<String, String> {
		return mutableMapOf<String, String>().apply {
			checkLowStockNotification?.let { put("checkLowStockNotification", it.toString()) }
		}.toMap()
	}
}
