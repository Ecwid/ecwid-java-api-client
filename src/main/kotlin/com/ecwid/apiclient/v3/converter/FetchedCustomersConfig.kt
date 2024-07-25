package com.ecwid.apiclient.v3.converter

import com.ecwid.apiclient.v3.dto.extrafield.request.UpdatedCustomersConfig
import com.ecwid.apiclient.v3.dto.extrafield.result.FetchedCustomersConfig


fun FetchedCustomersConfig.toUpdated(): UpdatedCustomersConfig {
	return UpdatedCustomersConfig(
		key = key,
		title = title,
		type = type,
		shownOnOrderDetails = shownOnOrderDetails,
	)
}
