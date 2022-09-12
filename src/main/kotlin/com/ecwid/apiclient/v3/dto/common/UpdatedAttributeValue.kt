package com.ecwid.apiclient.v3.dto.common

import com.ecwid.apiclient.v3.dto.product.enums.AttributeValueAlias

interface UpdatedAttributeValue {
	val id: Int?
	val alias: AttributeValueAlias?
	val name: String?
	val value: String?
}
