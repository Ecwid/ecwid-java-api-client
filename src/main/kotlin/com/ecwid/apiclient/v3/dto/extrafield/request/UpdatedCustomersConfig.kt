package com.ecwid.apiclient.v3.dto.extrafield.request

import com.ecwid.apiclient.v3.dto.common.ApiUpdatedDTO
import com.ecwid.apiclient.v3.dto.common.ApiUpdatedDTO.ModifyKind
import com.ecwid.apiclient.v3.dto.extrafield.enums.ExtrafieldType
import com.ecwid.apiclient.v3.dto.extrafield.result.FetchedCustomersConfig

data class UpdatedCustomersConfig(
	val key: String? = null,
	val title: String? = null,
	val type: ExtrafieldType? = null,
	val shownOnOrderDetails: Boolean? = null,
) : ApiUpdatedDTO {

	override fun getModifyKind() = ModifyKind.ReadWrite(FetchedCustomersConfig::class)
}
