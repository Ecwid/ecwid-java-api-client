package com.ecwid.apiclient.v3.dto.producttype.request

import com.ecwid.apiclient.v3.dto.producttype.enums.AttributeDisplayType
import com.ecwid.apiclient.v3.dto.producttype.enums.AttributeType

data class UpdatedProductType(
		var name: String? = null,
		var attributes: List<Attribute>? = null
) {

	data class Attribute(
			var id: Int? = null,
			var name: String? = null,
			var type: AttributeType? = null,
			var show: AttributeDisplayType? = null
	)

}