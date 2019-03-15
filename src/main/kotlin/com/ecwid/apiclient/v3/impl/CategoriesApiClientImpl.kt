package com.ecwid.apiclient.v3.impl

import com.ecwid.apiclient.v3.*
import com.ecwid.apiclient.v3.dto.UploadFileData
import com.ecwid.apiclient.v3.dto.category.request.CategoriesSearchRequest.*
import com.ecwid.apiclient.v3.dto.category.request.*
import com.ecwid.apiclient.v3.dto.category.result.*
import com.ecwid.apiclient.v3.httptransport.HttpBody

internal class CategoriesApiClientImpl(
		private val apiClientHelper: ApiClientHelper
): CategoriesApiClient {

	override fun searchCategories(request: CategoriesSearchRequest) = apiClientHelper.makeGetRequest<CategoriesSearchResult>(
			endpoint = request.toEndpoint(),
			params = request.toParams()
	)

	override fun searchCategoriesAsSequence(request: CategoriesSearchRequest) = sequence {
		var offsetRequest = request
		do {
			val searchResult = searchCategories(offsetRequest)
			yieldAll(searchResult.items)
			offsetRequest = offsetRequest.copy(offset = offsetRequest.offset + searchResult.count)
		} while (searchResult.count >= searchResult.limit)
	}

	override fun getCategoryDetails(request: CategoryDetailsRequest) = apiClientHelper.makeGetRequest<FetchedCategory>(
			endpoint = request.toEndpoint(),
			params = mapOf()
	)

	override fun createCategory(request: CategoryCreateRequest) = apiClientHelper.makePostRequest<CategoryCreateResult>(
			endpoint = request.toEndpoint(),
			params = mapOf(),
			httpBody = HttpBody.StringBody(
					body = apiClientHelper.serializeJson(request.newCategory),
					mimeType = MIME_TYPE_APPLICATION_JSON
			)
	)

	override fun updateCategory(request: CategoryUpdateRequest) = apiClientHelper.makePutRequest<CategoryUpdateResult>(
			endpoint = request.toEndpoint(),
			params = mapOf(),
			httpBody = HttpBody.StringBody(
					body = apiClientHelper.serializeJson(request.updatedCategory),
					mimeType = MIME_TYPE_APPLICATION_JSON
			)
	)

	override fun deleteCategory(request: CategoryDeleteRequest) = apiClientHelper.makeDeleteRequest<CategoryDeleteResult>(
			endpoint = request.toEndpoint(),
			params = mapOf()
	)

	override fun uploadCategoryImage(request: CategoryImageUploadRequest): CategoryImageUploadResult {
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

	override fun deleteCategoryImage(request: CategoryImageDeleteRequest) = apiClientHelper.makeDeleteRequest<CategoryImageDeleteResult>(
			endpoint = request.toEndpoint(),
			params = mapOf()
	)

}

private fun CategoriesSearchRequest.toEndpoint() = "categories"
private fun CategoryCreateRequest.toEndpoint() = "categories"
private fun CategoryUpdateRequest.toEndpoint() = "categories/$categoryId"
private fun CategoryDeleteRequest.toEndpoint() = "categories/$categoryId"
private fun CategoryDetailsRequest.toEndpoint() = "categories/$categoryId"
private fun CategoryImageUploadRequest.toEndpoint() = "categories/$categoryId/image"
private fun CategoryImageDeleteRequest.toEndpoint() = "categories/$categoryId/image"

private fun CategoriesSearchRequest.toParams(): Map<String, String> {
	val parentCategoryId = when (parentCategoryId) {
		is ParentCategory.Root ->
			0
		is ParentCategory.WithId ->
			parentCategoryId.id
		else ->
			null
	}

	val request = this
	return mutableMapOf<String, String>().apply {
		parentCategoryId?.let { put("parent", it.toString()) }
		request.hiddenCategories?.let { put("hidden_categories", it.toString()) }
		request.returnProductIds?.let { put("productIds", it.toString()) }
		request.baseUrl?.let { put("baseUrl", it) }
		request.cleanUrls?.let { put("cleanUrls", it.toString()) }
		put("offset", request.offset.toString())
		put("limit", request.limit.toString())
	}.toMap()
}
