package com.ecwid.apiclient.v3.dto.productreview.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.impl.RequestInfo
import com.ecwid.apiclient.v3.responsefields.ResponseFields

data class ProductReviewDetailsRequest(
	val reviewId: Int = 0,
	val responseFields: ResponseFields = ResponseFields.All,
) : ApiRequest {
	override fun toRequestInfo() = RequestInfo.createGetRequest(
		pathSegments = listOf(
			"reviews",
			"$reviewId"
		),
		responseFields = responseFields,
	)
}
