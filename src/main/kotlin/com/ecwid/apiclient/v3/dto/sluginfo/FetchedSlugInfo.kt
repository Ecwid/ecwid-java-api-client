package com.ecwid.apiclient.v3.dto.sluginfo

data class FetchedSlugInfo(
	val status: String = "",
	val type: String? = null,
	val canonicalSlug: String? = null,
	val storeEntityData: EntityData? = null,
	var staticContent: StaticContent? = null,
)

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
