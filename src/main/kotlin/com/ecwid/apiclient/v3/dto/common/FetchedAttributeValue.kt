package com.ecwid.apiclient.v3.dto.common

import com.ecwid.apiclient.v3.dto.custom.CustomAppRequest
import com.ecwid.apiclient.v3.dto.product.enums.AttributeValueLocation
import com.ecwid.apiclient.v3.dto.product.result.FetchedProduct
import com.ecwid.apiclient.v3.dto.producttype.enums.AttributeType
import com.ecwid.apiclient.v3.dto.variation.result.FetchedVariation

interface FetchedAttributeValue {
	val id: Int?
	val name: String?
	val type: AttributeType?
	val value: String?
	val valueTranslated: LocalizedValueMap?
	val show: AttributeValueLocation?

	fun toProductAttribute() = FetchedProduct.AttributeValue(
		id = id,
		name = name,
		type = type,
		value = value,
		valueTranslated = valueTranslated,
		show = show,
	)

	fun toVariationAttribute() = FetchedVariation.AttributeValue(
		id = id,
		name = name,
		type = type,
		value = value,
		valueTranslated = valueTranslated,
		show = show,
	)

	fun toOrderAttribute() = CustomAppRequest.AttributeValue(
		id = id,
		name = name,
		type = type,
		value = value,
		valueTranslated = valueTranslated,
		show = show,
	)

}
