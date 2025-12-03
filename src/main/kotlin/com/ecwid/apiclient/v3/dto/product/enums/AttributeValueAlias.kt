package com.ecwid.apiclient.v3.dto.product.enums

import com.ecwid.apiclient.v3.dto.producttype.enums.AttributeType

enum class AttributeValueAlias {
	UPC,
	BRAND,
	PRICE_PER_UNIT,
	UNITS_IN_PRODUCT,
	EXTERNAL_EAN,
	EXTERNAL_ISBN,
	EXTERNAL_ITF,
	EXTERNAL_JAN,
	EXTERNAL_CUSTOM,
	EXTERNAL_UPC,
}

fun AttributeType.toAttributeValueAlias(): AttributeValueAlias? {
	return when (this) {
		AttributeType.UPC -> AttributeValueAlias.UPC
		AttributeType.BRAND -> AttributeValueAlias.BRAND
		AttributeType.PRICE_PER_UNIT -> AttributeValueAlias.PRICE_PER_UNIT
		AttributeType.UNITS_IN_PRODUCT -> AttributeValueAlias.UNITS_IN_PRODUCT
		AttributeType.EXTERNAL_EAN -> AttributeValueAlias.EXTERNAL_EAN
		AttributeType.EXTERNAL_ISBN -> AttributeValueAlias.EXTERNAL_ISBN
		AttributeType.EXTERNAL_ITF -> AttributeValueAlias.EXTERNAL_ITF
		AttributeType.EXTERNAL_JAN -> AttributeValueAlias.EXTERNAL_JAN
		AttributeType.EXTERNAL_CUSTOM -> AttributeValueAlias.EXTERNAL_CUSTOM
		AttributeType.EXTERNAL_UPC -> AttributeValueAlias.EXTERNAL_UPC
		else -> null
	}
}
