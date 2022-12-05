package com.ecwid.apiclient.v3.dto.profile.result

import com.ecwid.apiclient.v3.dto.common.ApiFetchedDTO
import com.ecwid.apiclient.v3.dto.common.LocalizedValueMap
import com.ecwid.apiclient.v3.dto.profile.request.UpdatedOrderStatusSetting

data class FetchedOrderStatusSetting(
	val statusId: String = "",
	val orderStatusType: String = "",
	val defaultStatus: Boolean? = null,
	val enabled: Boolean = true,
	val name: String? = null,
	val nameTranslations: LocalizedValueMap? = null,
	val sendNotificationWhenStatusIsAssigned: Boolean = true,
	val lastNameChangeDate: String? = null,
) : ApiFetchedDTO {
	override fun getModifyKind() = ApiFetchedDTO.ModifyKind.ReadWrite(UpdatedOrderStatusSetting::class)
}
