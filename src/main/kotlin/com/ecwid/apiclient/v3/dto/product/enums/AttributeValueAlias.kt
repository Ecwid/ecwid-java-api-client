package com.ecwid.apiclient.v3.dto.product.enums

import com.ecwid.apiclient.v3.dto.producttype.enums.AttributeType

enum class AttributeValueAlias {
	UPC,
	BRAND
}

fun AttributeType.toAttributeValueAlias(): AttributeValueAlias? {
	return when (this) {
		AttributeType.UPC -> AttributeValueAlias.UPC
		AttributeType.BRAND -> AttributeValueAlias.BRAND
		else -> null
	}
}
