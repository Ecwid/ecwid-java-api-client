package com.ecwid.apiclient.v3.dto.product.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.impl.RequestInfo

data class ProductFileDownloadRequest(
		val productId: Int = 0,
		val fileId: Int = 0
) : ApiRequest {

	override fun toRequestInfo() = RequestInfo.createGetRequest(
			endpoint = "products/$productId/files/$fileId"
	)
}