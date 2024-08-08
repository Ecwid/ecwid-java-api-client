package com.ecwid.apiclient.v3.dto.profile.result

import com.ecwid.apiclient.v3.dto.common.ApiFetchedDTO
import com.ecwid.apiclient.v3.dto.common.ApiResultDTO
import com.ecwid.apiclient.v3.dto.common.LocalizedValueMap
import com.ecwid.apiclient.v3.dto.profile.request.UpdatedOrderStatusSettings

data class FetchedOrderStatusSettings(
	val statusId: String = "",
	val orderStatusType: String = "",
	val defaultStatus: Boolean? = null,
	val enabled: Boolean = true,
	val name: String? = null,
	val nameTranslations: LocalizedValueMap? = null,
	val sendNotificationWhenStatusIsAssigned: Boolean = true,
	val lastNameChangeDate: String? = null,
) : ApiFetchedDTO, ApiResultDTO {
	override fun getModifyKind() = ApiFetchedDTO.ModifyKind.ReadWrite(UpdatedOrderStatusSettings::class)
}
