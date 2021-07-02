package com.ecwid.apiclient.v3.converter

import com.ecwid.apiclient.v3.dto.producttype.request.UpdatedProductType
import com.ecwid.apiclient.v3.dto.producttype.result.FetchedProductType

fun FetchedProductType.toUpdated(): UpdatedProductType {
	return UpdatedProductType(
		name = name,
		attributes = attributes?.map(FetchedProductType.Attribute::toUpdated)
	)
}

fun FetchedProductType.Attribute.toUpdated(): UpdatedProductType.Attribute {
	return UpdatedProductType.Attribute(
		id = id,
		name = name,
		type = type,
		show = show
	)
}
