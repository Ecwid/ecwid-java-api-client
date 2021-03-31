package com.ecwid.apiclient.v3.dto.producttype.request

import com.ecwid.apiclient.v3.dto.common.ApiUpdatedDTO
import com.ecwid.apiclient.v3.dto.common.ApiUpdatedDTO.ModifyKind
import com.ecwid.apiclient.v3.dto.producttype.enums.AttributeDisplayType
import com.ecwid.apiclient.v3.dto.producttype.enums.AttributeType
import com.ecwid.apiclient.v3.dto.producttype.result.FetchedProductType

data class UpdatedProductType(
		val name: String? = null,
		val attributes: List<Attribute>? = null
) : ApiUpdatedDTO {

	data class Attribute(
			val id: Int? = null,
			val name: String? = null,
			val type: AttributeType? = null,
			val show: AttributeDisplayType? = null
	)

	override fun getModifyKind() = ModifyKind.ReadWrite(FetchedProductType::class)

}
