package com.ecwid.apiclient.v3.dto.category.request

data class CategoryUpdateRequest(
		val categoryId: Int = 0,
		val updatedCategory: UpdatedCategory = UpdatedCategory()
)