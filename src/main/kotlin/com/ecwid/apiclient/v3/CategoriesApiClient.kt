package com.ecwid.apiclient.v3

import com.ecwid.apiclient.v3.dto.category.request.*
import com.ecwid.apiclient.v3.dto.category.result.*

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
}
