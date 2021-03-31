package com.ecwid.apiclient.v3.dto.producttype.result

import com.ecwid.apiclient.v3.dto.common.ApiFetchedDTO
import com.ecwid.apiclient.v3.dto.common.ApiFetchedDTO.ModifyKind
import com.ecwid.apiclient.v3.dto.producttype.enums.AttributeDisplayType
import com.ecwid.apiclient.v3.dto.producttype.enums.AttributeType
import com.ecwid.apiclient.v3.dto.producttype.request.UpdatedProductType

data class FetchedProductType(
		val id: Int = 0,
		val name: String? = null,
		val googleTaxonomy: String? = null,
		val attributes: List<Attribute>? = null
) : ApiFetchedDTO {

	data class Attribute(
			val id: Int = 0,
			val name: String? = null,
			val type: AttributeType? = null,
			val show: AttributeDisplayType? = null
	)

	override fun getModifyKind() = ModifyKind.ReadWrite(UpdatedProductType::class)

}
