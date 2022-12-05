package com.ecwid.apiclient.v3.converter

import com.ecwid.apiclient.v3.dto.common.LocalizedValueMap
import com.ecwid.apiclient.v3.dto.profile.request.UpdatedOrderStatusSetting
import com.ecwid.apiclient.v3.dto.profile.result.FetchedOrderStatusSetting

fun FetchedOrderStatusSetting.toUpdated(): UpdatedOrderStatusSetting {
	return UpdatedOrderStatusSetting(
		enabled = enabled,
		name = name,
		nameTranslations = if (nameTranslations != null) LocalizedValueMap(nameTranslations) else null,
		sendNotificationWhenStatusIsAssigned = sendNotificationWhenStatusIsAssigned,
	)
}

