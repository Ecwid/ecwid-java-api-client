package com.ecwid.apiclient.v3.dto.brand.result

import com.ecwid.apiclient.v3.dto.common.ApiFetchedDTO
import com.ecwid.apiclient.v3.dto.common.ApiResultDTO

data class FetchedBrand(
	val name: String = "",
	val nameTranslated: Map<String, String>? = null,
	val productsFilteredByBrandUrl: String? = null,
): ApiFetchedDTO, ApiResultDTO {
	override fun getModifyKind() = ApiFetchedDTO.ModifyKind.ReadOnly
}
