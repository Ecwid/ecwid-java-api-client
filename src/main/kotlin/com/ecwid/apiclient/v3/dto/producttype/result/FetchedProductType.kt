package com.ecwid.apiclient.v3.dto.producttype.result

import com.ecwid.apiclient.v3.dto.producttype.enums.AttributeDisplayType
import com.ecwid.apiclient.v3.dto.producttype.enums.AttributeType

data class FetchedProductType(
		var id: Int = 0,
		var name: String? = null,
		var googleTaxonomy: String? = null,
		var attributes: List<Attribute>? = null
) {

	data class Attribute(
			var id: Int = 0,
			var name: String? = null,
			var type: AttributeType? = null,
			var show: AttributeDisplayType? = null
	)

}
