package com.ecwid.apiclient.v3.impl

import com.ecwid.apiclient.v3.*
import com.ecwid.apiclient.v3.dto.product.request.*
import com.ecwid.apiclient.v3.dto.UploadFileData
import com.ecwid.apiclient.v3.dto.product.result.*
import com.ecwid.apiclient.v3.httptransport.HttpBody

internal class ProductsApiClientImpl(
		private val apiClientHelper: ApiClientHelper
): ProductsApiClient {

	override fun searchProducts(request: ProductsSearchRequest.ByFilters) = apiClientHelper.makeGetRequest<ProductsSearchResult>(
			endpoint = request.toEndpoint(),
			params = request.toParams()
	)

	override fun searchProducts(request: ProductsSearchRequest.ByIds) = apiClientHelper.makeGetRequest<ProductsSearchResult>(
			endpoint = request.toEndpoint(),
			params = request.toParams()
	)

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

	override fun getProductDetails(request: ProductDetailsRequest) = apiClientHelper.makeGetRequest<FetchedProduct>(
			endpoint = request.toEndpoint(),
			params = mapOf()
	)

	override fun createProduct(request: ProductCreateRequest) = apiClientHelper.makePostRequest<ProductCreateResult>(
			endpoint = request.toEndpoint(),
			params = mapOf(),
			httpBody = HttpBody.StringBody(
					body = apiClientHelper.serializeJson(request.newProduct),
					mimeType = MIME_TYPE_APPLICATION_JSON
			)
	)

	override fun updateProduct(request: ProductUpdateRequest) = apiClientHelper.makePutRequest<ProductUpdateResult>(
			endpoint = request.toEndpoint(),
			params = mapOf(),
			httpBody = HttpBody.StringBody(
					body = apiClientHelper.serializeJson(request.updatedProduct),
					mimeType = MIME_TYPE_APPLICATION_JSON
			)
	)

	override fun updateProductInventory(request: ProductInventoryUpdateRequest) = apiClientHelper.makePutRequest<ProductInventoryUpdateResult>(
			endpoint = request.toEndpoint(),
			params = request.toParams(),
			httpBody = HttpBody.StringBody(
					body = apiClientHelper.serializeJson(request.inventoryAdjustment),
					mimeType = MIME_TYPE_APPLICATION_JSON
			)
	)

	override fun deleteProduct(request: ProductDeleteRequest) = apiClientHelper.makeDeleteRequest<ProductDeleteResult>(
			endpoint = request.toEndpoint(),
			params = mapOf()
	)

	override fun uploadProductImage(request: ProductImageUploadRequest): ProductImageUploadResult {
		val fileData = request.fileData
		return when (fileData) {
			is UploadFileData.ExternalUrlData -> apiClientHelper.makePostRequest(
					endpoint = request.toEndpoint(),
					params = mapOf(
							"externalUrl" to fileData.externalUrl
					),
					httpBody = HttpBody.EmptyBody()
			)
			is UploadFileData.ByteArrayData -> apiClientHelper.makePostRequest(
					endpoint = request.toEndpoint(),
					params = mapOf(),
					httpBody = HttpBody.ByteArrayBody(
							bytes = fileData.bytes,
							mimeType = MIME_TYPE_OCTET_STREAM
					)
			)
			is UploadFileData.LocalFileData -> apiClientHelper.makePostRequest(
					endpoint = request.toEndpoint(),
					params = mapOf(),
					httpBody = HttpBody.LocalFileBody(
							file = fileData.file,
							mimeType = MIME_TYPE_OCTET_STREAM
					)
			)
			is UploadFileData.InputStreamData -> apiClientHelper.makePostRequest(
					endpoint = request.toEndpoint(),
					params = mapOf(),
					httpBody = HttpBody.InputStreamBody(
							stream = fileData.stream,
							mimeType = MIME_TYPE_OCTET_STREAM
					)
			)
		}
	}

	override fun deleteProductImage(request: ProductImageDeleteRequest) = apiClientHelper.makeDeleteRequest<ProductImageDeleteResult>(
			endpoint = request.toEndpoint(),
			params = mapOf()
	)

	override fun uploadProductGalleryImage(request: ProductGalleryImageUploadRequest): ProductGalleryImageUploadResult {
		val commonParams = mapOf(
				"fileName" to request.fileName
		)
		val fileData = request.fileData
		return when (fileData) {
			is UploadFileData.ExternalUrlData -> apiClientHelper.makePostRequest(
					endpoint = request.toEndpoint(),
					params = commonParams + mapOf(
							"externalUrl" to fileData.externalUrl
					),
					httpBody = HttpBody.EmptyBody()
			)
			is UploadFileData.ByteArrayData -> apiClientHelper.makePostRequest(
					endpoint = request.toEndpoint(),
					params = commonParams,
					httpBody = HttpBody.ByteArrayBody(
							bytes = fileData.bytes,
							mimeType = MIME_TYPE_OCTET_STREAM
					)
			)
			is UploadFileData.LocalFileData -> apiClientHelper.makePostRequest(
					endpoint = request.toEndpoint(),
					params = commonParams,
					httpBody = HttpBody.LocalFileBody(
							file = fileData.file,
							mimeType = MIME_TYPE_OCTET_STREAM
					)
			)
			is UploadFileData.InputStreamData -> apiClientHelper.makePostRequest(
					endpoint = request.toEndpoint(),
					params = commonParams,
					httpBody = HttpBody.InputStreamBody(
							stream = fileData.stream,
							mimeType = MIME_TYPE_OCTET_STREAM
					)
			)
		}
	}

	override fun deleteProductGalleryImage(request: ProductGalleryImageDeleteRequest) = apiClientHelper.makeDeleteRequest<ProductGalleryImageDeleteResult>(
		endpoint = request.toEndpoint(),
		params = mapOf()
	)

	override fun deleteProductGalleryImages(request: ProductGalleryImagesDeleteRequest) = apiClientHelper.makeDeleteRequest<ProductGalleryImagesDeleteResult>(
			endpoint = request.toEndpoint(),
			params = mapOf()
	)

	override fun downloadProductFile(request: ProductFileDownloadRequest) = apiClientHelper.makeGetRequest<ByteArray>(
			endpoint = request.toEndpoint(),
			params = mapOf()
	)

	override fun uploadProductFile(request: ProductFileUploadRequest): ProductFileUploadResult {
		val commonParams = mapOf(
				"fileName" to request.fileName,
				"description" to request.description
		)
		val fileData = request.fileData
		return when (fileData) {
			is UploadFileData.ExternalUrlData -> apiClientHelper.makePostRequest(
					endpoint = request.toEndpoint(),
					params = commonParams + mapOf(
							"externalUrl" to fileData.externalUrl
					),
					httpBody = HttpBody.EmptyBody()
			)
			is UploadFileData.ByteArrayData -> apiClientHelper.makePostRequest(
					endpoint = request.toEndpoint(),
					params = commonParams,
					httpBody = HttpBody.ByteArrayBody(
							bytes = fileData.bytes,
							mimeType = MIME_TYPE_OCTET_STREAM
					)
			)
			is UploadFileData.LocalFileData -> apiClientHelper.makePostRequest(
					endpoint = request.toEndpoint(),
					params = commonParams,
					httpBody = HttpBody.LocalFileBody(
							file = fileData.file,
							mimeType = MIME_TYPE_OCTET_STREAM
					)
			)
			is UploadFileData.InputStreamData -> apiClientHelper.makePostRequest(
					endpoint = request.toEndpoint(),
					params = commonParams,
					httpBody = HttpBody.InputStreamBody(
							stream = fileData.stream,
							mimeType = MIME_TYPE_OCTET_STREAM
					)
			)
		}
	}

	override fun updateProductFile(request: ProductFileUpdateRequest) = apiClientHelper.makePutRequest<ProductFileUpdateResult>(
			endpoint = request.toEndpoint(),
			params = mapOf(),
			httpBody = HttpBody.StringBody(
					body = apiClientHelper.serializeJson(request.updatedProductFile),
					mimeType = MIME_TYPE_APPLICATION_JSON
			)
	)

	override fun deleteProductFile(request: ProductFileDeleteRequest) = apiClientHelper.makeDeleteRequest<ProductFileDeleteResult>(
			endpoint = request.toEndpoint(),
			params = mapOf()
	)

	override fun deleteProductFiles(request: ProductFilesDeleteRequest) = apiClientHelper.makeDeleteRequest<ProductFilesDeleteResult>(
			endpoint = request.toEndpoint(),
			params = mapOf()
	)

	override fun searchDeletedProducts(request: DeletedProductsSearchRequest) = apiClientHelper.makeGetRequest<DeletedProductsSearchResult>(
			endpoint = request.toEndpoint(),
			params = request.toParams()
	)

	override fun searchDeletedProductsAsSequence(request: DeletedProductsSearchRequest): Sequence<DeletedProduct> = sequence {
		var offsetRequest = request
		do {
			val searchResult = searchDeletedProducts(offsetRequest)
			yieldAll(searchResult.items)
			offsetRequest = offsetRequest.copy(offset = offsetRequest.offset + searchResult.count)
		} while (searchResult.count >= searchResult.limit)
	}

}

private fun ProductsSearchRequest.toEndpoint() = "products"
private fun ProductCreateRequest.toEndpoint() = "products"
private fun ProductUpdateRequest.toEndpoint() = "products/$productId"
private fun ProductDeleteRequest.toEndpoint() = "products/$productId"
private fun ProductDetailsRequest.toEndpoint() = "products/$productId"
private fun ProductInventoryUpdateRequest.toEndpoint() = "products/$productId/inventory"
private fun ProductImageUploadRequest.toEndpoint() = "products/$productId/image"
private fun ProductImageDeleteRequest.toEndpoint() = "products/$productId/image"
private fun ProductGalleryImageUploadRequest.toEndpoint() = "products/$productId/gallery"
private fun ProductGalleryImageDeleteRequest.toEndpoint() = "products/$productId/gallery/$fileId"
private fun ProductGalleryImagesDeleteRequest.toEndpoint() = "products/$productId/gallery"
private fun ProductFileUploadRequest.toEndpoint() = "products/$productId/files"
private fun ProductFileUpdateRequest.toEndpoint() = "products/$productId/files/$fileId"
private fun ProductFileDownloadRequest.toEndpoint() = "products/$productId/files/$fileId"
private fun ProductFileDeleteRequest.toEndpoint() = "products/$productId/files/$fileId"
private fun ProductFilesDeleteRequest.toEndpoint() = "products/$productId/files"
private fun DeletedProductsSearchRequest.toEndpoint() = "products/deleted"

private fun ProductsSearchRequest.ByFilters.toParams(): Map<String, String> {
	val request = this
	return mutableMapOf<String, String>().apply {
		request.keyword?.let { put("keyword", it) }
		request.sku?.let { put("sku", it) }
		request.priceFrom?.let { put("priceFrom", it.toString()) }
		request.priceTo?.let { put("priceTo", it.toString()) }
		request.createdFrom?.let { put("createdFrom", (it.time / 1000).toString()) }
		request.createdTo?.let { put("createdTo", (it.time / 1000).toString()) }
		request.updatedFrom?.let { put("updatedFrom", (it.time / 1000).toString()) }
		request.updatedTo?.let { put("updatedTo", (it.time / 1000).toString()) }
		request.enabled?.let { put("enabled", it.toString()) }
		request.inStock?.let { put("inStock", it.toString()) }
		request.onSale?.let { put("onsale", if (it) "onsale" else "notonsale") }
		request.attributes?.let { attributes ->
			attributes.attributes.forEach { (attributeName, attributeValue) ->
				put("attribute_$attributeName", attributeValue.joinToString(","))
			}
		}
		request.options?.let { options ->
			options.options.forEach { (optionName, optionValue) ->
				put("option_$optionName", optionValue.joinToString(","))
			}
		}
		request.baseUrl?.let { put("baseUrl", it) }
		request.cleanUrls?.let { put("cleanUrls", it.toString()) }
		// TODO categoryId: Int? = null
		// TODO withSubcategories: Boolean? = null
		request.sortBy?.let { put("sortBy", it.name ) }
		put("offset", request.offset.toString())
		put("limit", request.limit.toString())
	}.toMap()
}

private fun ProductsSearchRequest.ByIds.toParams(): Map<String, String> {
	val request = this
	return mutableMapOf<String, String>().apply {
		put("productId", request.productIds.joinToString(","))
	}.toMap()
}

private fun ProductInventoryUpdateRequest.toParams(): Map<String, String> {
	return mutableMapOf<String, String>().apply {
		checkLowStockNotification?.let { put("checkLowStockNotification", it.toString()) }
	}.toMap()
}

private fun DeletedProductsSearchRequest.toParams(): Map<String, String> {
	val request = this
	return mutableMapOf<String, String>().apply {
		request.deletedFrom?.let { put("from_date", (it.time / 1000).toString()) }
		request.deletedTo?.let { put("to_date", (it.time / 1000).toString()) }
		put("offset", request.offset.toString())
		put("limit", request.limit.toString())
	}.toMap()
}
