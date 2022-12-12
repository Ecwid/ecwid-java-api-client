package com.ecwid.apiclient.v3.converter

import com.ecwid.apiclient.v3.dto.common.LocalizedValueMap
import com.ecwid.apiclient.v3.dto.profile.request.UpdatedOrderStatusSettings
import com.ecwid.apiclient.v3.dto.profile.result.FetchedOrderStatusSettings

fun FetchedOrderStatusSettings.toUpdated(): UpdatedOrderStatusSettings {
	return UpdatedOrderStatusSettings(
		enabled = enabled,
		name = name,
		nameTranslations = if (nameTranslations != null) LocalizedValueMap(nameTranslations) else null,
		sendNotificationWhenStatusIsAssigned = sendNotificationWhenStatusIsAssigned,
	)
}

