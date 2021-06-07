package com.ecwid.apiclient.v3.dto.saleschannels

import com.ecwid.apiclient.v3.dto.common.ApiFetchedDTO
import com.ecwid.apiclient.v3.dto.common.ApiFetchedDTO.ModifyKind

data class MarketplaceConfig(
		val enabled: Boolean = false,
		val taxonomyId: String = "",
		val firstInit: Boolean = false
) : ApiFetchedDTO {

	override fun getModifyKind() = ModifyKind.ReadOnly

}
