package com.ecwid.apiclient.v3.impl

import com.ecwid.apiclient.v3.ProductVariationsApiClient
import com.ecwid.apiclient.v3.dto.variation.request.CreateProductVariationRequest
import com.ecwid.apiclient.v3.dto.variation.result.CreateProductVariationResult

internal class ProductVariationsApiClientImpl(
		private val apiClientHelper: ApiClientHelper
) : ProductVariationsApiClient {
	override fun createProductVariation(request: CreateProductVariationRequest) = apiClientHelper.makeRequest<CreateProductVariationResult>(request)
}