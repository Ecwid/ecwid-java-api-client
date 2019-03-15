package com.ecwid.apiclient.v3.dto.category.request

data class CategoryCreateRequest(
		val newCategory: UpdatedCategory = UpdatedCategory()
)