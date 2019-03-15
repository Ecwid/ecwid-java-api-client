package com.ecwid.apiclient.v3.dto.category.request

data class CategoriesSearchRequest(
		val parentCategoryId: ParentCategory = ParentCategory.Any,
		val hiddenCategories: Boolean? = null,
		val returnProductIds: Boolean? = null,
		val baseUrl: String? = null,
		val cleanUrls: Boolean? = null,
		val offset: Int = 0,
		val limit: Int = 100
) {

	sealed class ParentCategory {
		object Any : ParentCategory()
		object Root : ParentCategory() 
		class WithId(val id: Int) : ParentCategory() 
	}

}
