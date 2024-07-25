package com.ecwid.apiclient.v3.dto.extrafield.result

import com.ecwid.apiclient.v3.dto.common.ApiFetchedDTO
import com.ecwid.apiclient.v3.dto.extrafield.enums.ExtrafieldEntityType
import com.ecwid.apiclient.v3.dto.extrafield.enums.ExtrafieldType
import com.ecwid.apiclient.v3.dto.extrafield.request.UpdatedCustomersConfig
import java.util.*

data class FetchedCustomersConfig(
	val key: String? = null,
	val title: String? = null,
	val entityTypes: ExtrafieldEntityType? = null,
	val type: ExtrafieldType? = null,
	val shownOnOrderDetails: Boolean? = null,
	val createdDate: Date? = null,
	val lastModifiedDate: Date? = null,
) : ApiFetchedDTO {

	override fun getModifyKind() = ApiFetchedDTO.ModifyKind.ReadWrite(UpdatedCustomersConfig::class)
}
