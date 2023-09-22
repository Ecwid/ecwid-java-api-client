package com.ecwid.apiclient.v3

import com.ecwid.apiclient.v3.dto.category.request.*
import com.ecwid.apiclient.v3.dto.category.result.*
import com.ecwid.apiclient.v3.dto.common.PagingResult
import com.ecwid.apiclient.v3.dto.common.PartialResult
import kotlin.reflect.KClass

// Categories
// https://developers.ecwid.com/api-documentation/categories
interface CategoriesApiClient {
	fun searchCategories(request: CategoriesSearchRequest): CategoriesSearchResult
	fun searchCategoriesAsSequence(request: CategoriesSearchRequest): Sequence<FetchedCategory>
	fun searchCategoriesByPath(request: CategoriesByPathRequest): CategoriesSearchResult
	fun searchCategoriesByPathAsSequence(request: CategoriesByPathRequest): Sequence<FetchedCategory>
	fun getCategoryDetails(request: CategoryDetailsRequest): FetchedCategory
	fun createCategory(request: CategoryCreateRequest): CategoryCreateResult
	fun updateCategory(request: CategoryUpdateRequest): CategoryUpdateResult
	fun deleteCategory(request: CategoryDeleteRequest): CategoryDeleteResult
	fun uploadCategoryImage(request: CategoryImageUploadRequest): CategoryImageUploadResult
	fun uploadCategoryImageAsync(request: CategoryImageAsyncUploadRequest): CategoryImageAsyncUploadResult
	fun deleteCategoryImage(request: CategoryImageDeleteRequest): CategoryImageDeleteResult
	fun assignProductsToCategory(request: AssignProductsToCategoryRequest): CategoryUpdateResult
	fun unassignProductsFromCategory(request: UnassignProductsFromCategoryRequest): CategoryDeleteResult

	fun <Result : PartialResult<FetchedCategory>> getCategoryDetailsPartial(request: CategoryDetailsRequest, resultClass: KClass<Result>): Result
	fun <Result : PartialResult<CategoriesSearchResult>> searchCategoriesPartial(request: CategoriesSearchRequest, resultClass: KClass<Result>): Result
	fun <Result : PartialResult<CategoriesSearchResult>> searchCategoriesByPathPartial(request: CategoriesByPathRequest, resultClass: KClass<Result>): Result

	fun <Result, Item> searchCategoriesPartialAsSequence(request: CategoriesSearchRequest, resultClass: KClass<Result>): Sequence<Item>
		where Result : PartialResult<CategoriesSearchResult>, Result : PagingResult<Item>

	fun <Result, Item> searchCategoriesByPathPartialAsSequence(request: CategoriesByPathRequest, resultClass: KClass<Result>): Sequence<Item>
		where Result : PartialResult<CategoriesSearchResult>, Result : PagingResult<Item>
}

inline fun <reified Result : PartialResult<FetchedCategory>> CategoriesApiClient.getCategoryDetailsPartial(request: CategoryDetailsRequest): Result {
	return getCategoryDetailsPartial(request, Result::class)
}

inline fun <reified Result : PartialResult<CategoriesSearchResult>> CategoriesApiClient.searchCategoriesPartial(request: CategoriesSearchRequest): Result {
	return searchCategoriesPartial(request, Result::class)
}

inline fun <reified Result : PartialResult<CategoriesSearchResult>> CategoriesApiClient.searchCategoriesByPathPartial(request: CategoriesByPathRequest): Result {
	return searchCategoriesByPathPartial(request, Result::class)
}

inline fun <reified Result, Item> CategoriesApiClient.searchCategoriesPartialAsSequence(
	request: CategoriesSearchRequest,
): Sequence<Item> where Result : PartialResult<CategoriesSearchResult>, Result : PagingResult<Item> {
	return searchCategoriesPartialAsSequence(
		request = request,
		resultClass = Result::class,
	)
}

inline fun <reified Result, Item> CategoriesApiClient.searchCategoriesByPathPartialAsSequence(
	request: CategoriesByPathRequest,
): Sequence<Item> where Result : PartialResult<CategoriesSearchResult>, Result : PagingResult<Item> {
	return searchCategoriesByPathPartialAsSequence(
		request = request,
		resultClass = Result::class,
	)
}
