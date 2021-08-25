package com.ecwid.apiclient.v3.dto.product.enums

import com.ecwid.apiclient.v3.dto.producttype.enums.AttributeType

enum class AttributeValueAlias {
	UPC,
	BRAND,
	PRICE_PER_UNIT,
	UNITS_IN_PRODUCT,
}

fun AttributeType.toAttributeValueAlias(): AttributeValueAlias? {
	return when (this) {
		AttributeType.UPC -> AttributeValueAlias.UPC
		AttributeType.BRAND -> AttributeValueAlias.BRAND
		AttributeType.PRICE_PER_UNIT -> AttributeValueAlias.PRICE_PER_UNIT
		AttributeType.UNITS_IN_PRODUCT -> AttributeValueAlias.UNITS_IN_PRODUCT
		else -> null
	}
}
