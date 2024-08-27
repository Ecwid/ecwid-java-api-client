package com.ecwid.apiclient.v3.converter

import com.ecwid.apiclient.v3.dto.category.request.UpdatedCategory
import com.ecwid.apiclient.v3.dto.category.result.FetchedCategory

fun FetchedCategory.toUpdated(): UpdatedCategory {
	return UpdatedCategory(
		parentId = parentId,
		orderBy = orderBy,
		isSampleCategory = isSampleCategory,
		name = name,
		nameTranslated = nameTranslated,
		description = description,
		descriptionTranslated = descriptionTranslated,
		enabled = enabled,
		productIds = productIds,
		seoTitle = seoTitle,
		seoTitleTranslated = seoTitleTranslated,
		seoDescription = seoDescription,
		seoDescriptionTranslated = seoDescriptionTranslated,
		alt = alt?.toUpdated(),
		customSlug = customSlug,
	)
}
