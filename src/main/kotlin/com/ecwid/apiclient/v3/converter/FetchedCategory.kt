package com.ecwid.apiclient.v3.converter

import com.ecwid.apiclient.v3.dto.category.request.UpdatedCategory
import com.ecwid.apiclient.v3.dto.category.result.FetchedCategory

fun FetchedCategory.toUpdated(): UpdatedCategory {
	return UpdatedCategory(
			parentId = parentId,
			orderBy = orderBy,
			name = name,
			description = description,
			enabled = enabled,
			productIds = productIds
	)
}
