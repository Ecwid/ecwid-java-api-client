package com.ecwid.apiclient.v3.dto.sluginfo

import com.ecwid.apiclient.v3.dto.common.ApiFetchedDTO
import com.ecwid.apiclient.v3.dto.common.ApiResultDTO

data class FetchedSlugInfo(
	val status: Status = Status.OK,
	val type: Type? = null,
	val canonicalSlug: String? = null,
	val storeEntityData: EntityData? = null,
	val staticContent: StaticContent? = null,
) : ApiFetchedDTO, ApiResultDTO {

	data class EntityData(val id: String? = null)

	data class StaticContent(
		val cssFiles: List<String>? = null,
		val htmlCode: String? = null,
		val jsCode: String? = null,
		val metaDescriptionHtml: String? = null,
		val canonicalUrl: String? = null,
		val ogTagsHtml: String? = null,
		val jsonLDHtml: String? = null,
		val hrefLangHtml: String? = null,
		val lastUpdated: Long? = null
	)

	override fun getModifyKind(): ApiFetchedDTO.ModifyKind =
		ApiFetchedDTO.ModifyKind.ReadOnly

	enum class Status {
		OK,
		NOT_FOUND,
		NONCANONICAL
	}

	enum class Type {
		ROOT,
		PRODUCT,
		CATEGORY,
		SEARCH,
		LEGAL_PAGE,
		CART,
		CHECKOUT,
		CUSTOMER_ACCOUNT,
		INTERNAL
	}

}
