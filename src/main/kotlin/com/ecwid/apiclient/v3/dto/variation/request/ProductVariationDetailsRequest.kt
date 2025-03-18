package com.ecwid.apiclient.v3.dto.variation.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.impl.RequestInfo
import com.ecwid.apiclient.v3.responsefields.ResponseFields

data class ProductVariationDetailsRequest(
	val productId: Int = 0,
	val variationId: Long = 0,
	val responseFields: ResponseFields = ResponseFields.All,
) : ApiRequest {
	override fun toRequestInfo() = RequestInfo.createGetRequest(
		pathSegments = listOf(
			"products",
			"$productId",
			"combinations",
			"$variationId"
		),
		responseFields = responseFields,
	)
}
