package com.ecwid.apiclient.v3.dto.common

import com.ecwid.apiclient.v3.dto.product.enums.AttributeValueLocation
import com.ecwid.apiclient.v3.dto.producttype.enums.AttributeType

interface FetchedAttributeValue<T : FetchedAttributeValue<T>> {
	val id: Int?
	val name: String?
	val type: AttributeType?
	val value: String?
	val show: AttributeValueLocation?

	fun cast(): T

}
