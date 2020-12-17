package com.ecwid.apiclient.v3.dto.producttype.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.httptransport.HttpBody
import com.ecwid.apiclient.v3.impl.RequestInfo

data class ProductTypeUpdateRequest(
		val productTypeId: Int = 0,
		val updatedProductType: UpdatedProductType = UpdatedProductType()
) : ApiRequest {
	override fun toRequestInfo() = RequestInfo.createPutRequest(
			endpoint = "classes/$productTypeId",
			httpBody = HttpBody.JsonBody(
					obj = updatedProductType
			)
	)
}
