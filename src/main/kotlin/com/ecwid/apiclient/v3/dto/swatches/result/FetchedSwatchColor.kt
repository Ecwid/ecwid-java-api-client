package com.ecwid.apiclient.v3.dto.swatches.result

import com.ecwid.apiclient.v3.dto.common.ApiFetchedDTO
import com.ecwid.apiclient.v3.dto.common.ApiResultDTO
import com.ecwid.apiclient.v3.dto.common.LocalizedValueMap

data class FetchedSwatchColor(
	val name: String = "",
	val hexCode: String = "",
	val translations: LocalizedValueMap? = null,
) : ApiFetchedDTO, ApiResultDTO {
	override fun getModifyKind() = ApiFetchedDTO.ModifyKind.ReadOnly
}
