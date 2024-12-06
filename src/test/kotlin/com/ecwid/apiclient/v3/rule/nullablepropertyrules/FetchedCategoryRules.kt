package com.ecwid.apiclient.v3.rule.nullablepropertyrules

import com.ecwid.apiclient.v3.dto.category.result.FetchedCategory
import com.ecwid.apiclient.v3.dto.common.FetchedAlt
import com.ecwid.apiclient.v3.rule.NullablePropertyRule
import com.ecwid.apiclient.v3.rule.NullablePropertyRule.AllowNullable
import com.ecwid.apiclient.v3.rule.NullablePropertyRule.IgnoreNullable

val fetchedCategoryNullablePropertyRules: List<NullablePropertyRule<*, *>> = listOf(
	IgnoreNullable(FetchedCategory::description),
	IgnoreNullable(FetchedCategory::descriptionTranslated),
	IgnoreNullable(FetchedCategory::enabledProductCount),
	IgnoreNullable(FetchedCategory::hdThumbnailUrl),
	IgnoreNullable(FetchedCategory::imageUrl),
	IgnoreNullable(FetchedCategory::isSampleCategory),
	IgnoreNullable(FetchedCategory::nameTranslated),
	IgnoreNullable(FetchedCategory::orderBy),
	IgnoreNullable(FetchedCategory::originalImage),
	IgnoreNullable(FetchedCategory::originalImageUrl),
	IgnoreNullable(FetchedCategory::parentId),
	IgnoreNullable(FetchedCategory::productCount),
	IgnoreNullable(FetchedCategory::productIds),
	IgnoreNullable(FetchedCategory::thumbnailUrl),
	IgnoreNullable(FetchedCategory::url),
	AllowNullable(FetchedCategory::productCountWithoutSubcategories),
	AllowNullable(FetchedCategory::seoTitle),
	AllowNullable(FetchedCategory::seoTitleTranslated),
	AllowNullable(FetchedCategory::seoDescription),
	AllowNullable(FetchedCategory::customSlug),
	AllowNullable(FetchedCategory::seoDescriptionTranslated),
	AllowNullable(FetchedCategory::alt),
	AllowNullable(FetchedAlt::main),
	AllowNullable(FetchedAlt::translated),
	AllowNullable(FetchedCategory::imageExternalId),
)
