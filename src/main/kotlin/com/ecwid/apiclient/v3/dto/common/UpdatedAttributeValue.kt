package com.ecwid.apiclient.v3.dto.common

import com.ecwid.apiclient.v3.dto.product.enums.AttributeValueAlias
import com.ecwid.apiclient.v3.dto.product.enums.AttributeValueLocation
import com.ecwid.apiclient.v3.dto.product.request.UpdatedProduct
import com.ecwid.apiclient.v3.dto.variation.request.UpdatedVariation

interface UpdatedAttributeValue {
	val id: Int?
	val alias: AttributeValueAlias?
	val name: String?
	val value: String?
	val valueTranslated: LocalizedValueMap?
	val show: AttributeValueLocation?

	fun toProductAttribute() = UpdatedProduct.AttributeValue(
		id = id,
		alias = alias,
		name = name,
		value = value,
		valueTranslated = valueTranslated,
		show = show,
	)

	fun toVariationAttribute() = UpdatedVariation.AttributeValue(
		id = id,
		alias = alias,
		name = name,
		value = value,
		valueTranslated = valueTranslated,
		show = show,
	)

}
