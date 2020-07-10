package com.ecwid.apiclient.v3.impl

import com.ecwid.apiclient.v3.ApiClientHelper
import com.ecwid.apiclient.v3.ProductVariationsApiClient
import com.ecwid.apiclient.v3.dto.variation.request.*
import com.ecwid.apiclient.v3.dto.variation.result.*

internal class ProductVariationsApiClientImpl(
		private val apiClientHelper: ApiClientHelper
) : ProductVariationsApiClient {
	override fun createProductVariation(request: CreateProductVariationRequest) = apiClientHelper.makeRequest<CreateProductVariationResult>(request)
	override fun uploadVariationImage(request: ProductVariationImageUploadRequest) = apiClientHelper.makeRequest<ProductVariationImageUploadResult>(request)
	override fun deleteVariationImage(request: ProductVariationImageDeleteRequest) = apiClientHelper.makeRequest<ProductVariationImageDeleteResult>(request)
	override fun getAllProductVariations(request: ProductVariationsRequest) = apiClientHelper.makeRequest<ProductVariationsResult>(request)
	override fun getProductVariation(request: ProductVariationDetailsRequest) = apiClientHelper.makeRequest<FetchedVariation>(request)
	override fun updateProductVariation(request: UpdateProductVariationRequest) = apiClientHelper.makeRequest<UpdateProductVariationResult>(request)
	override fun deleteProductVariation(request: DeleteProductVariationRequest) = apiClientHelper.makeRequest<DeleteProductVariationsResult>(request)
	override fun deleteAllProductVariations(request: DeleteAllProductVariationsRequest) = apiClientHelper.makeRequest<DeleteProductVariationsResult>(request)
	override fun adjustVariationInventory(request: AdjustVariationInventoryRequest) = apiClientHelper.makeRequest<AdjustVariationInventoryResult>(request)
}
