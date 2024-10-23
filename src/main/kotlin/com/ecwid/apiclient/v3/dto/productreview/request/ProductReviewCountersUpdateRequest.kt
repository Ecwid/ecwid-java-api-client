package com.ecwid.apiclient.v3.dto.productreview.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.httptransport.HttpBody
import com.ecwid.apiclient.v3.impl.RequestInfo

data class ProductReviewCountersUpdateRequest(
	val updateInfo: ProductReviewCountersUpdate = ProductReviewCountersUpdate(),
) : ApiRequest {
	override fun toRequestInfo() = RequestInfo.createPostRequest(
		pathSegments = listOf(
			"reviews",
			"update_counters",
		),
		params = mapOf(),
		httpBody = HttpBody.JsonBody(
			obj = updateInfo
		)
	)
}
