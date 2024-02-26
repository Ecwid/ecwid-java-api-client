package com.ecwid.apiclient.v3.dto.productreview.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.httptransport.HttpBody
import com.ecwid.apiclient.v3.impl.RequestInfo

data class ProductReviewUpdateStatusRequest(
	val reviewId: Int = 0,
	val status: UpdatedProductReviewStatus = UpdatedProductReviewStatus(),
) : ApiRequest {
	override fun toRequestInfo() = RequestInfo.createPutRequest(
		pathSegments = listOf(
			"reviews",
			"$reviewId",
		),
		params = mapOf(),
		httpBody = HttpBody.JsonBody(
			obj = status
		)
	)
}
