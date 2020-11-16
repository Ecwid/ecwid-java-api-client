package com.ecwid.apiclient.v3.dto.saleschannels.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.dto.saleschannels.enums.Marketplace
import com.ecwid.apiclient.v3.httptransport.HttpBody
import com.ecwid.apiclient.v3.impl.RequestInfo

class UpdateProductFeedRequest(
		private val marketplace: Marketplace,
		private val updatedProductFeed: UpdatedProductFeed
) : ApiRequest {
	override fun toRequestInfo() = RequestInfo.createPutRequest(
			endpoint = "saleschannels/${marketplace.name}/feedInfo",
			httpBody = HttpBody.JsonBody(
					obj = updatedProductFeed
			)
	)
}
