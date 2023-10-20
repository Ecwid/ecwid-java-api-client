package com.ecwid.apiclient.v3.impl

import com.ecwid.apiclient.v3.ApiClientHelper
import com.ecwid.apiclient.v3.CategoriesApiClient
import com.ecwid.apiclient.v3.dto.category.request.*
import com.ecwid.apiclient.v3.dto.category.result.*
import com.ecwid.apiclient.v3.dto.common.PagingResult
import com.ecwid.apiclient.v3.dto.common.PartialResult
import com.ecwid.apiclient.v3.dto.common.fetchPagesAsItemSequence
import com.ecwid.apiclient.v3.responsefields.AS_SEQUENCE_SEARCH_RESULT_REQUIRED_FIELDS
import kotlin.reflect.KClass

internal class CategoriesApiClientImpl(
	private val apiClientHelper: ApiClientHelper
) : CategoriesApiClient {

	override fun searchCategories(request: CategoriesSearchRequest) =
		apiClientHelper.makeObjectResultRequest<CategoriesSearchResult>(request)

	override fun searchCategoriesAsSequence(request: CategoriesSearchRequest) = sequence {
		var offsetRequest = request.copy(
			responseFields = request.responseFields + AS_SEQUENCE_SEARCH_RESULT_REQUIRED_FIELDS
		)
		do {
			val searchResult = searchCategories(offsetRequest)
			yieldAll(searchResult.items)
			offsetRequest = offsetRequest.copy(offset = offsetRequest.offset + searchResult.count)
		} while (searchResult.count >= searchResult.limit)
	}

	override fun searchCategoriesByPath(request: CategoriesByPathRequest) =
		apiClientHelper.makeObjectResultRequest<CategoriesSearchResult>(request)

	override fun searchCategoriesByPathAsSequence(request: CategoriesByPathRequest) = sequence {
		var offsetRequest = request.copy(
			responseFields = request.responseFields + AS_SEQUENCE_SEARCH_RESULT_REQUIRED_FIELDS
		)
		do {
			val searchResult = searchCategoriesByPath(offsetRequest)
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

	override fun assignProductsToCategory(request: AssignProductsToCategoryRequest): CategoryUpdateResult =
		apiClientHelper.makeObjectResultRequest(request)

	override fun unassignProductsFromCategory(request: UnassignProductsFromCategoryRequest): CategoryDeleteResult =
		apiClientHelper.makeObjectResultRequest(request)

	override fun <Result : PartialResult<FetchedCategory>> getCategoryDetails(
		request: CategoryDetailsRequest,
		resultClass: KClass<Result>,
	): Result {
		return apiClientHelper.makeObjectPartialResultRequest(
			request = request,
			resultClass = resultClass,
		)
	}

	override fun <Result : PartialResult<CategoriesSearchResult>> searchCategories(
		request: CategoriesSearchRequest,
		resultClass: KClass<Result>
	): Result {
		return apiClientHelper.makeObjectPartialResultRequest(
			request = request,
			resultClass = resultClass,
		)
	}

	override fun <Result : PartialResult<CategoriesSearchResult>> searchCategoriesByPath(
		request: CategoriesByPathRequest,
		resultClass: KClass<Result>
	): Result {
		return apiClientHelper.makeObjectPartialResultRequest(
			request = request,
			resultClass = resultClass,
		)
	}

	override fun <Result, Item> searchCategoriesAsSequence(
		request: CategoriesSearchRequest,
		resultClass: KClass<Result>
	): Sequence<Item> where Result : PartialResult<CategoriesSearchResult>, Result : PagingResult<Item> {
		return fetchPagesAsItemSequence(request) { searchCategories(it, resultClass) }
	}

	override fun <Result, Item> searchCategoriesByPathAsSequence(
		request: CategoriesByPathRequest,
		resultClass: KClass<Result>,
	): Sequence<Item> where Result : PartialResult<CategoriesSearchResult>, Result : PagingResult<Item> {
		return fetchPagesAsItemSequence(request) { searchCategoriesByPath(it, resultClass) }
	}
}
