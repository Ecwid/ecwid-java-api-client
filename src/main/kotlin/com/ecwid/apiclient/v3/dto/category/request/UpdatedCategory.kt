package com.ecwid.apiclient.v3.dto.category.request

import com.ecwid.apiclient.v3.dto.category.result.FetchedCategory
import com.ecwid.apiclient.v3.dto.common.ApiUpdatedDTO
import com.ecwid.apiclient.v3.dto.common.ApiUpdatedDTO.ModifyKind
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
	val productIds: List<Int>? = null,
	val seoTitle: String? = null,
	val seoTitleTranslated: LocalizedValueMap? = null,
	val seoDescription: String? = null,
	val seoDescriptionTranslated: LocalizedValueMap? = null,
	val imageAlt: Alt? = null
) : ApiUpdatedDTO {

	override fun getModifyKind() = ModifyKind.ReadWrite(FetchedCategory::class)

	data class Alt(
		val main: String? = null,
		val translated: LocalizedValueMap? = null
	)
}
