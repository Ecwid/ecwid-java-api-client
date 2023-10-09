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

	fun <Result : PartialResult<FetchedCategory>> getCategoryDetails(request: CategoryDetailsRequest, resultClass: KClass<Result>): Result
	fun <Result : PartialResult<CategoriesSearchResult>> searchCategories(request: CategoriesSearchRequest, resultClass: KClass<Result>): Result
	fun <Result : PartialResult<CategoriesSearchResult>> searchCategoriesByPath(request: CategoriesByPathRequest, resultClass: KClass<Result>): Result

	fun <Result, Item> searchCategoriesAsSequence(request: CategoriesSearchRequest, resultClass: KClass<Result>): Sequence<Item>
		where Result : PartialResult<CategoriesSearchResult>, Result : PagingResult<Item>

	fun <Result, Item> searchCategoriesByPathAsSequence(request: CategoriesByPathRequest, resultClass: KClass<Result>): Sequence<Item>
		where Result : PartialResult<CategoriesSearchResult>, Result : PagingResult<Item>
}

@Suppress("EXTENSION_SHADOWED_BY_MEMBER")
inline fun <reified Result : PartialResult<FetchedCategory>> CategoriesApiClient.getCategoryDetails(request: CategoryDetailsRequest): Result {
	return getCategoryDetails(request, Result::class)
}

@Suppress("EXTENSION_SHADOWED_BY_MEMBER")
inline fun <reified Result : PartialResult<CategoriesSearchResult>> CategoriesApiClient.searchCategories(request: CategoriesSearchRequest): Result {
	return searchCategories(request, Result::class)
}

@Suppress("EXTENSION_SHADOWED_BY_MEMBER")
inline fun <reified Result : PartialResult<CategoriesSearchResult>> CategoriesApiClient.searchCategoriesByPath(request: CategoriesByPathRequest): Result {
	return searchCategoriesByPath(request, Result::class)
}

@Suppress("EXTENSION_SHADOWED_BY_MEMBER")
inline fun <reified Result, Item> CategoriesApiClient.searchCategoriesAsSequence(
	request: CategoriesSearchRequest,
): Sequence<Item> where Result : PartialResult<CategoriesSearchResult>, Result : PagingResult<Item> {
	return searchCategoriesAsSequence(
		request = request,
		resultClass = Result::class,
	)
}

@Suppress("EXTENSION_SHADOWED_BY_MEMBER")
inline fun <reified Result, Item> CategoriesApiClient.searchCategoriesByPathAsSequence(
	request: CategoriesByPathRequest,
): Sequence<Item> where Result : PartialResult<CategoriesSearchResult>, Result : PagingResult<Item> {
	return searchCategoriesByPathAsSequence(
		request = request,
		resultClass = Result::class,
	)
}
