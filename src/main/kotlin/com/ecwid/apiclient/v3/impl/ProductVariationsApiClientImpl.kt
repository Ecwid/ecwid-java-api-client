package com.ecwid.apiclient.v3.impl

import com.ecwid.apiclient.v3.ApiClientHelper
import com.ecwid.apiclient.v3.ProductVariationsApiClient
import com.ecwid.apiclient.v3.dto.common.PartialResult
import com.ecwid.apiclient.v3.dto.variation.request.*
import com.ecwid.apiclient.v3.dto.variation.result.*
import kotlin.reflect.KClass

internal class ProductVariationsApiClientImpl(
	private val apiClientHelper: ApiClientHelper
) : ProductVariationsApiClient {
	override fun createProductVariation(request: CreateProductVariationRequest) =
		apiClientHelper.makeObjectResultRequest<CreateProductVariationResult>(request)

	override fun uploadVariationImage(request: ProductVariationImageUploadRequest) =
		apiClientHelper.makeObjectResultRequest<ProductVariationImageUploadResult>(request)

	override fun uploadProductVariationImageAsync(request: ProductVariationImageAsyncUploadRequest) =
		apiClientHelper.makeObjectResultRequest<ProductVariationImageAsyncUploadResult>(request)

	override fun deleteVariationImage(request: ProductVariationImageDeleteRequest) =
		apiClientHelper.makeObjectResultRequest<ProductVariationImageDeleteResult>(request)

	override fun getAllProductVariations(request: ProductVariationsRequest) =
		apiClientHelper.makeObjectResultRequest<ProductVariationsResult>(request)

	override fun <Result : PartialResult<FetchedVariation>> getAllProductVariations(request: ProductVariationsRequest, resultClass: KClass<Result>): List<Result> {
		return apiClientHelper.makeObjectPartialResultRequestList(request, resultClass)
	}

	override fun getProductVariation(request: ProductVariationDetailsRequest) =
		apiClientHelper.makeObjectResultRequest<FetchedVariation>(request)

	override fun <Result : PartialResult<FetchedVariation>> getProductVariation(request: ProductVariationDetailsRequest, resultClass: KClass<Result>): Result {
		return apiClientHelper.makeObjectPartialResultRequest(request, resultClass)
	}

	override fun updateProductVariation(request: UpdateProductVariationRequest) =
		apiClientHelper.makeObjectResultRequest<UpdateProductVariationResult>(request)

	override fun deleteProductVariation(request: DeleteProductVariationRequest) =
		apiClientHelper.makeObjectResultRequest<DeleteProductVariationsResult>(request)

	override fun deleteAllProductVariations(request: DeleteAllProductVariationsRequest) =
		apiClientHelper.makeObjectResultRequest<DeleteProductVariationsResult>(request)

	override fun adjustVariationInventory(request: AdjustVariationInventoryRequest) =
		apiClientHelper.makeObjectResultRequest<AdjustVariationInventoryResult>(request)
}
