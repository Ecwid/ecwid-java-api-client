package com.ecwid.apiclient.v3.impl

import com.ecwid.apiclient.v3.ApiClientHelper
import com.ecwid.apiclient.v3.ProductsApiClient
import com.ecwid.apiclient.v3.dto.common.PagingResult
import com.ecwid.apiclient.v3.dto.common.PartialResult
import com.ecwid.apiclient.v3.dto.common.fetchPagesAsItemSequence
import com.ecwid.apiclient.v3.dto.product.request.*
import com.ecwid.apiclient.v3.dto.product.result.*
import com.ecwid.apiclient.v3.responsefields.AS_SEQUENCE_SEARCH_RESULT_REQUIRED_FIELDS
import kotlin.reflect.KClass

internal class ProductsApiClientImpl(
	private val apiClientHelper: ApiClientHelper
) : ProductsApiClient {

	override fun searchProducts(request: ProductsSearchRequest.ByFilters) =
		apiClientHelper.makeObjectResultRequest<ProductsSearchResult>(request)

	override fun searchProducts(request: ProductsSearchRequest.ByIds) =
		apiClientHelper.makeObjectResultRequest<ProductsSearchResult>(request)

	override fun searchProductsAsSequence(request: ProductsSearchRequest.ByFilters) = sequence {
		var offsetRequest = request.copy(
			responseFields = request.responseFields + AS_SEQUENCE_SEARCH_RESULT_REQUIRED_FIELDS
		)
		do {
			val searchResult = searchProducts(offsetRequest)
			yieldAll(searchResult.items)
			offsetRequest = offsetRequest.copy(offset = offsetRequest.offset + searchResult.count)
		} while (searchResult.count >= searchResult.limit)
	}

	override fun searchProductsAsSequence(request: ProductsSearchRequest.ByIds): Sequence<FetchedProduct> {
		return searchProducts(request).items.asSequence()
	}

	override fun getProductDetails(request: ProductDetailsRequest) =
		apiClientHelper.makeObjectResultRequest<FetchedProduct>(request)

	override fun createProduct(request: ProductCreateRequest) =
		apiClientHelper.makeObjectResultRequest<ProductCreateResult>(request)

	override fun updateProduct(request: ProductUpdateRequest) =
		apiClientHelper.makeObjectResultRequest<ProductUpdateResult>(request)

	override fun updateProductInventory(request: ProductInventoryUpdateRequest) =
		apiClientHelper.makeObjectResultRequest<ProductInventoryUpdateResult>(request)

	override fun getProductFilters(request: GetProductFiltersRequest) =
		apiClientHelper.makeObjectResultRequest<GetProductFiltersResult>(request)

	override fun deleteProduct(request: ProductDeleteRequest) =
		apiClientHelper.makeObjectResultRequest<ProductDeleteResult>(request)

	override fun uploadProductImage(request: ProductImageUploadRequest) =
		apiClientHelper.makeObjectResultRequest<ProductImageUploadResult>(request)

	override fun uploadProductImageAsync(request: ProductImageAsyncUploadRequest) =
		apiClientHelper.makeObjectResultRequest<ProductImageAsyncUploadResult>(request)

	override fun deleteProductImage(request: ProductImageDeleteRequest) =
		apiClientHelper.makeObjectResultRequest<ProductImageDeleteResult>(request)

	override fun uploadProductGalleryImage(request: ProductGalleryImageUploadRequest) =
		apiClientHelper.makeObjectResultRequest<ProductGalleryImageUploadResult>(request)

	override fun uploadProductGalleryImageAsync(request: ProductGalleryImageAsyncUploadRequest) =
		apiClientHelper.makeObjectResultRequest<ProductGalleryImageAsyncUploadResult>(request)

	override fun deleteProductGalleryImage(request: ProductGalleryImageDeleteRequest) =
		apiClientHelper.makeObjectResultRequest<ProductGalleryImageDeleteResult>(request)

	override fun deleteProductGalleryImages(request: ProductGalleryImagesDeleteRequest) =
		apiClientHelper.makeObjectResultRequest<ProductGalleryImagesDeleteResult>(request)

	override fun downloadProductFile(request: ProductFileDownloadRequest) =
		apiClientHelper.makeByteArrayResultRequest(request)

	override fun uploadProductFile(request: ProductFileUploadRequest) =
		apiClientHelper.makeObjectResultRequest<ProductFileUploadResult>(request)

	override fun updateProductFile(request: ProductFileUpdateRequest) =
		apiClientHelper.makeObjectResultRequest<ProductFileUpdateResult>(request)

	override fun deleteProductFile(request: ProductFileDeleteRequest) =
		apiClientHelper.makeObjectResultRequest<ProductFileDeleteResult>(request)

	override fun deleteProductFiles(request: ProductFilesDeleteRequest) =
		apiClientHelper.makeObjectResultRequest<ProductFilesDeleteResult>(request)

	override fun searchDeletedProducts(request: DeletedProductsSearchRequest) =
		apiClientHelper.makeObjectResultRequest<DeletedProductsSearchResult>(request)

	override fun searchDeletedProductsAsSequence(request: DeletedProductsSearchRequest): Sequence<DeletedProduct> =
		sequence {
			var offsetRequest = request.copy(
				responseFields = request.responseFields + AS_SEQUENCE_SEARCH_RESULT_REQUIRED_FIELDS
			)
			do {
				val searchResult = searchDeletedProducts(offsetRequest)
				yieldAll(searchResult.items)
				offsetRequest = offsetRequest.copy(offset = offsetRequest.offset + searchResult.count)
			} while (searchResult.count >= searchResult.limit)
		}

	override fun <Result : PartialResult<FetchedProduct>> getProductDetails(request: ProductDetailsRequest, resultClass: KClass<Result>): Result {
		return apiClientHelper.makeObjectPartialResultRequest(request, resultClass)
	}

	override fun <Result : PartialResult<ProductsSearchResult>> searchProducts(request: ProductsSearchRequest.ByIds, resultClass: KClass<Result>): Result {
		return apiClientHelper.makeObjectPartialResultRequest(request, resultClass)
	}

	override fun <Result : PartialResult<ProductsSearchResult>> searchProducts(request: ProductsSearchRequest.ByFilters, resultClass: KClass<Result>): Result {
		return apiClientHelper.makeObjectPartialResultRequest(request, resultClass)
	}

	override fun <Result, Item> searchProductsAsSequence(
		request: ProductsSearchRequest.ByFilters,
		resultClass: KClass<Result>,
	): Sequence<Item> where Result : PartialResult<ProductsSearchResult>, Result : PagingResult<Item> {
		return fetchPagesAsItemSequence(request) { searchProducts(it, resultClass) }
	}
}
