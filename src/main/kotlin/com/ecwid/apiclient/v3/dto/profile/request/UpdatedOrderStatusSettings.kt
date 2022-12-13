package com.ecwid.apiclient.v3.dto.profile.request

import com.ecwid.apiclient.v3.dto.common.ApiUpdatedDTO
import com.ecwid.apiclient.v3.dto.common.ApiUpdatedDTO.ModifyKind
import com.ecwid.apiclient.v3.dto.common.LocalizedValueMap
import com.ecwid.apiclient.v3.dto.profile.result.FetchedOrderStatusSettings

data class UpdatedOrderStatusSettings(
	val enabled: Boolean? = null,
	val name: String? = null,
	val nameTranslations: LocalizedValueMap? = null,
	val sendNotificationWhenStatusIsAssigned: Boolean? = null,
) : ApiUpdatedDTO {

	override fun getModifyKind() = ModifyKind.ReadWrite(FetchedOrderStatusSettings::class)
}
