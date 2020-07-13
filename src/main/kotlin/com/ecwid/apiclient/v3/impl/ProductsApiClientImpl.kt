package com.ecwid.apiclient.v3.impl

import com.ecwid.apiclient.v3.ApiClientHelper
import com.ecwid.apiclient.v3.ProductsApiClient
import com.ecwid.apiclient.v3.dto.product.request.*
import com.ecwid.apiclient.v3.dto.product.result.*

internal class ProductsApiClientImpl(
		private val apiClientHelper: ApiClientHelper
) : ProductsApiClient {

	override fun searchProducts(request: ProductsSearchRequest.ByFilters) = apiClientHelper.makeRequest<ProductsSearchResult>(request)

	override fun searchProducts(request: ProductsSearchRequest.ByIds) = apiClientHelper.makeRequest<ProductsSearchResult>(request)

	override fun searchProductsAsSequence(request: ProductsSearchRequest.ByFilters) = sequence {
		var offsetRequest = request
		do {
			val searchResult = searchProducts(offsetRequest)
			yieldAll(searchResult.items)
			offsetRequest = offsetRequest.copy(offset = offsetRequest.offset + searchResult.count)
		} while (searchResult.count >= searchResult.limit)
	}

	override fun searchProductsAsSequence(request: ProductsSearchRequest.ByIds): Sequence<FetchedProduct> {
		return searchProducts(request).items.asSequence()
	}

	override fun getProductDetails(request: ProductDetailsRequest) = apiClientHelper.makeRequest<FetchedProduct>(request)
	override fun createProduct(request: ProductCreateRequest) = apiClientHelper.makeRequest<ProductCreateResult>(request)
	override fun updateProduct(request: ProductUpdateRequest) = apiClientHelper.makeRequest<ProductUpdateResult>(request)
	override fun updateProductInventory(request: ProductInventoryUpdateRequest) = apiClientHelper.makeRequest<ProductInventoryUpdateResult>(request)
	override fun deleteProduct(request: ProductDeleteRequest) = apiClientHelper.makeRequest<ProductDeleteResult>(request)
	override fun uploadProductImage(request: ProductImageUploadRequest) = apiClientHelper.makeRequest<ProductImageUploadResult>(request)
	override fun uploadProductImageAsync(request: ProductImageAsyncUploadRequest) = apiClientHelper.makeRequest<ProductImageAsyncUploadResult>(request)
	override fun deleteProductImage(request: ProductImageDeleteRequest) = apiClientHelper.makeRequest<ProductImageDeleteResult>(request)
	override fun uploadProductGalleryImage(request: ProductGalleryImageUploadRequest) = apiClientHelper.makeRequest<ProductGalleryImageUploadResult>(request)
	override fun uploadProductGalleryImageAsync(request: ProductGalleryImageAsyncUploadRequest) = apiClientHelper.makeRequest<ProductGalleryImageAsyncUploadResult>(request)
	override fun deleteProductGalleryImage(request: ProductGalleryImageDeleteRequest) = apiClientHelper.makeRequest<ProductGalleryImageDeleteResult>(request)
	override fun deleteProductGalleryImages(request: ProductGalleryImagesDeleteRequest) = apiClientHelper.makeRequest<ProductGalleryImagesDeleteResult>(request)
	override fun downloadProductFile(request: ProductFileDownloadRequest) = apiClientHelper.makeByteArrayResultRequest(request)
	override fun uploadProductFile(request: ProductFileUploadRequest) = apiClientHelper.makeRequest<ProductFileUploadResult>(request)
	override fun updateProductFile(request: ProductFileUpdateRequest) = apiClientHelper.makeRequest<ProductFileUpdateResult>(request)
	override fun deleteProductFile(request: ProductFileDeleteRequest) = apiClientHelper.makeRequest<ProductFileDeleteResult>(request)
	override fun deleteProductFiles(request: ProductFilesDeleteRequest) = apiClientHelper.makeRequest<ProductFilesDeleteResult>(request)
	override fun searchDeletedProducts(request: DeletedProductsSearchRequest) = apiClientHelper.makeRequest<DeletedProductsSearchResult>(request)

	override fun searchDeletedProductsAsSequence(request: DeletedProductsSearchRequest): Sequence<DeletedProduct> = sequence {
		var offsetRequest = request
		do {
			val searchResult = searchDeletedProducts(offsetRequest)
			yieldAll(searchResult.items)
			offsetRequest = offsetRequest.copy(offset = offsetRequest.offset + searchResult.count)
		} while (searchResult.count >= searchResult.limit)
	}

}

