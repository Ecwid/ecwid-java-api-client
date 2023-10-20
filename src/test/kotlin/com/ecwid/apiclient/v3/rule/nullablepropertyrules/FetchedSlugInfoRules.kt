package com.ecwid.apiclient.v3.rule.nullablepropertyrules

import com.ecwid.apiclient.v3.dto.sluginfo.FetchedSlugInfo
import com.ecwid.apiclient.v3.rule.NullablePropertyRule
import com.ecwid.apiclient.v3.rule.NullablePropertyRule.AllowNullable

val fetchedSlugInfoNullablePropertyRules: List<NullablePropertyRule<*, *>> = listOf(
	AllowNullable(FetchedSlugInfo::canonicalSlug),
	AllowNullable(FetchedSlugInfo::type),
	AllowNullable(FetchedSlugInfo::staticContent),
	AllowNullable(FetchedSlugInfo::storeEntityData),
)

val fetchedSlugInfoClassesNullablePropertyRules: List<NullablePropertyRule<*, *>> = listOf(
	AllowNullable(FetchedSlugInfo.EntityData::id),
	AllowNullable(FetchedSlugInfo.StaticContent::canonicalUrl),
	AllowNullable(FetchedSlugInfo.StaticContent::cssFiles),
	AllowNullable(FetchedSlugInfo.StaticContent::jsCode),
	AllowNullable(FetchedSlugInfo.StaticContent::hrefLangHtml),
	AllowNullable(FetchedSlugInfo.StaticContent::htmlCode),
	AllowNullable(FetchedSlugInfo.StaticContent::jsonLDHtml),
	AllowNullable(FetchedSlugInfo.StaticContent::lastUpdated),
	AllowNullable(FetchedSlugInfo.StaticContent::metaDescriptionHtml),
	AllowNullable(FetchedSlugInfo.StaticContent::ogTagsHtml),
)
