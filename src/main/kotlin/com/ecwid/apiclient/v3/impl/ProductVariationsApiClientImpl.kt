package com.ecwid.apiclient.v3.impl

import com.ecwid.apiclient.v3.ProductVariationsApiClient
import com.ecwid.apiclient.v3.dto.variation.request.CreateProductVariationRequest
import com.ecwid.apiclient.v3.dto.variation.request.ProductVariationDetailsRequest
import com.ecwid.apiclient.v3.dto.variation.request.ProductVariationImageDeleteRequest
import com.ecwid.apiclient.v3.dto.variation.request.ProductVariationImageUploadRequest
import com.ecwid.apiclient.v3.dto.variation.result.CreateProductVariationResult
import com.ecwid.apiclient.v3.dto.variation.result.FetchedVariation
import com.ecwid.apiclient.v3.dto.variation.result.ProductVariationImageDeleteResult
import com.ecwid.apiclient.v3.dto.variation.result.ProductVariationImageUploadResult

internal class ProductVariationsApiClientImpl(
		private val apiClientHelper: ApiClientHelper
) : ProductVariationsApiClient {
	override fun createProductVariation(request: CreateProductVariationRequest) = apiClientHelper.makeRequest<CreateProductVariationResult>(request)
	override fun uploadVariationImage(request: ProductVariationImageUploadRequest) = apiClientHelper.makeRequest<ProductVariationImageUploadResult>(request)
	override fun deleteVariationImage(request: ProductVariationImageDeleteRequest) = apiClientHelper.makeRequest<ProductVariationImageDeleteResult>(request)
	override fun getProductVariation(request: ProductVariationDetailsRequest) = apiClientHelper.makeRequest<FetchedVariation>(request)
}