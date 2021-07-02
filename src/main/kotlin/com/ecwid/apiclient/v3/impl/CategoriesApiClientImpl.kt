package com.ecwid.apiclient.v3.impl

import com.ecwid.apiclient.v3.ApiClientHelper
import com.ecwid.apiclient.v3.CategoriesApiClient
import com.ecwid.apiclient.v3.dto.category.request.*
import com.ecwid.apiclient.v3.dto.category.result.*

internal class CategoriesApiClientImpl(
	private val apiClientHelper: ApiClientHelper
) : CategoriesApiClient {

	override fun searchCategories(request: CategoriesSearchRequest) =
		apiClientHelper.makeObjectResultRequest<CategoriesSearchResult>(request)

	override fun searchCategoriesAsSequence(request: CategoriesSearchRequest) = sequence {
		var offsetRequest = request
		do {
			val searchResult = searchCategories(offsetRequest)
			yieldAll(searchResult.items)
			offsetRequest = offsetRequest.copy(offset = offsetRequest.offset + searchResult.count)
		} while (searchResult.count >= searchResult.limit)
	}

	override fun getCategoryDetails(request: CategoryDetailsRequest) =
		apiClientHelper.makeObjectResultRequest<FetchedCategory>(request)

	override fun createCategory(request: CategoryCreateRequest) =
		apiClientHelper.makeObjectResultRequest<CategoryCreateResult>(request)

	override fun updateCategory(request: CategoryUpdateRequest) =
		apiClientHelper.makeObjectResultRequest<CategoryUpdateResult>(request)

	override fun deleteCategory(request: CategoryDeleteRequest) =
		apiClientHelper.makeObjectResultRequest<CategoryDeleteResult>(request)

	override fun uploadCategoryImage(request: CategoryImageUploadRequest) =
		apiClientHelper.makeObjectResultRequest<CategoryImageUploadResult>(request)

	override fun uploadCategoryImageAsync(request: CategoryImageAsyncUploadRequest) =
		apiClientHelper.makeObjectResultRequest<CategoryImageAsyncUploadResult>(request)

	override fun deleteCategoryImage(request: CategoryImageDeleteRequest) =
		apiClientHelper.makeObjectResultRequest<CategoryImageDeleteResult>(request)
}
