package com.ecwid.apiclient.v3.dto.productreview.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.httptransport.HttpBody
import com.ecwid.apiclient.v3.impl.RequestInfo

data class ProductReviewMassUpdateRequest(
	val updateInfo: ProductReviewMassUpdate = ProductReviewMassUpdate(),
) : ApiRequest {
	override fun toRequestInfo() = RequestInfo.createPutRequest(
		pathSegments = listOf(
			"reviews",
			"mass_update",
		),
		params = mapOf(),
		httpBody = HttpBody.JsonBody(
			obj = updateInfo
		)
	)
}
