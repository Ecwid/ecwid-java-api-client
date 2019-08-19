package com.ecwid.apiclient.v3.dto.category.request

import com.ecwid.apiclient.v3.dto.common.LocalizedValueMap

data class UpdatedCategory(
		val parentId: Int? = null,
		val orderBy: Int? = null,
		val name: String? = null,
		val isSampleCategory: Boolean? = null,
		val nameTranslated: LocalizedValueMap? = null,
		val description: String? = null,
		val descriptionTranslated: LocalizedValueMap? = null,
		val enabled: Boolean? = null,
		val productIds: List<Int>? = null
)