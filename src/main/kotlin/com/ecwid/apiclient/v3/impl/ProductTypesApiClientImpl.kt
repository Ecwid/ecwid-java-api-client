package com.ecwid.apiclient.v3.impl

import com.ecwid.apiclient.v3.ProductTypesApiClient
import com.ecwid.apiclient.v3.dto.productype.request.ProductTypeCreateRequest
import com.ecwid.apiclient.v3.dto.productype.result.ProductTypeCreateResult
import com.ecwid.apiclient.v3.httptransport.HttpBody

internal class ProductTypesApiClientImpl(
		private val apiClientHelper: ApiClientHelper
): ProductTypesApiClient {

	override fun createProductType(request: ProductTypeCreateRequest) = apiClientHelper.makePostRequest<ProductTypeCreateResult>(
			endpoint = request.toEndpoint(),
			params = mapOf(),
			httpBody = HttpBody.StringBody(
					body = apiClientHelper.serializeJson(request.newProductType),
					mimeType = MIME_TYPE_APPLICATION_JSON
			)
	)

}

private fun ProductTypeCreateRequest.toEndpoint() = "classes"
