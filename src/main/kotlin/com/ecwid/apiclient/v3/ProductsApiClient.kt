package com.ecwid.apiclient.v3

import com.ecwid.apiclient.v3.dto.common.PagingResult
import com.ecwid.apiclient.v3.dto.common.PartialResult
import com.ecwid.apiclient.v3.dto.product.request.*
import com.ecwid.apiclient.v3.dto.product.result.*
import kotlin.reflect.KClass

// Products
// https://developers.ecwid.com/api-documentation/products
interface ProductsApiClient {
	fun searchProducts(request: ProductsSearchRequest.ByFilters): ProductsSearchResult
	fun searchProducts(request: ProductsSearchRequest.ByIds): ProductsSearchResult
	fun searchProductsAsSequence(request: ProductsSearchRequest.ByFilters): Sequence<FetchedProduct>
	fun searchProductsAsSequence(request: ProductsSearchRequest.ByIds): Sequence<FetchedProduct>
	fun getProductDetails(request: ProductDetailsRequest): FetchedProduct
	fun createProduct(request: ProductCreateRequest): ProductCreateResult
	fun updateProduct(request: ProductUpdateRequest): ProductUpdateResult
	fun updateProductInventory(request: ProductInventoryUpdateRequest): ProductInventoryUpdateResult
	fun getProductFilters(request: GetProductFiltersRequest): GetProductFiltersResult
	fun deleteProduct(request: ProductDeleteRequest): ProductDeleteResult
	fun uploadProductImage(request: ProductImageUploadRequest): ProductImageUploadResult
	fun uploadProductImageAsync(request: ProductImageAsyncUploadRequest): ProductImageAsyncUploadResult
	fun deleteProductImage(request: ProductImageDeleteRequest): ProductImageDeleteResult
	fun uploadProductGalleryImage(request: ProductGalleryImageUploadRequest): ProductGalleryImageUploadResult
	fun uploadProductGalleryImageAsync(request: ProductGalleryImageAsyncUploadRequest): ProductGalleryImageAsyncUploadResult
	fun deleteProductGalleryImage(request: ProductGalleryImageDeleteRequest): ProductGalleryImageDeleteResult
	fun deleteProductGalleryImages(request: ProductGalleryImagesDeleteRequest): ProductGalleryImagesDeleteResult
	fun downloadProductFile(request: ProductFileDownloadRequest): ByteArray
	fun uploadProductFile(request: ProductFileUploadRequest): ProductFileUploadResult
	fun updateProductFile(request: ProductFileUpdateRequest): ProductFileUpdateResult
	fun deleteProductFile(request: ProductFileDeleteRequest): ProductFileDeleteResult
	fun deleteProductFiles(request: ProductFilesDeleteRequest): ProductFilesDeleteResult
	fun searchDeletedProducts(request: DeletedProductsSearchRequest): DeletedProductsSearchResult
	fun searchDeletedProductsAsSequence(request: DeletedProductsSearchRequest): Sequence<DeletedProduct>

	fun <Result : PartialResult<FetchedProduct>> getProductDetails(request: ProductDetailsRequest, resultClass: KClass<Result>): Result
	fun <Result : PartialResult<ProductsSearchResult>> searchProducts(request: ProductsSearchRequest.ByIds, resultClass: KClass<Result>): Result
	fun <Result : PartialResult<ProductsSearchResult>> searchProducts(request: ProductsSearchRequest.ByFilters, resultClass: KClass<Result>): Result

	fun <Result, Item> searchProductsAsSequence(request: ProductsSearchRequest.ByFilters, resultClass: KClass<Result>): Sequence<Item>
		where Result : PartialResult<ProductsSearchResult>, Result : PagingResult<Item>
}

@Suppress("EXTENSION_SHADOWED_BY_MEMBER")
inline fun <reified Result : PartialResult<FetchedProduct>> ProductsApiClient.getProductDetails(request: ProductDetailsRequest): Result {
	return getProductDetails(request, Result::class)
}

@Suppress("EXTENSION_SHADOWED_BY_MEMBER")
inline fun <reified Result : PartialResult<ProductsSearchResult>> ProductsApiClient.searchProducts(request: ProductsSearchRequest.ByIds): Result {
	return searchProducts(request, Result::class)
}

@Suppress("EXTENSION_SHADOWED_BY_MEMBER")
inline fun <reified Result : PartialResult<ProductsSearchResult>> ProductsApiClient.searchProducts(request: ProductsSearchRequest.ByFilters): Result {
	return searchProducts(request, Result::class)
}

@Suppress("EXTENSION_SHADOWED_BY_MEMBER")
inline fun <reified Result, Item> ProductsApiClient.searchProductsAsSequence(
	request: ProductsSearchRequest.ByFilters
): Sequence<Item> where Result : PartialResult<ProductsSearchResult>, Result : PagingResult<Item> {
	return searchProductsAsSequence(request, Result::class)
}
