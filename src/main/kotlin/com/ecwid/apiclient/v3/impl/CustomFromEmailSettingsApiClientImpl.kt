package com.ecwid.apiclient.v3.impl

import com.ecwid.apiclient.v3.ApiClientHelper
import com.ecwid.apiclient.v3.CustomFromEmailSettingsApiClient
import com.ecwid.apiclient.v3.dto.customfromemail.GetCustomFromEmailSettingsRequest
import com.ecwid.apiclient.v3.dto.customfromemail.GetCustomFromEmailSettingsResult

class CustomFromEmailSettingsApiClientImpl(
	private val apiClientHelper: ApiClientHelper
) : CustomFromEmailSettingsApiClient {

	override fun getCustomFromEmailSettings(
		request: GetCustomFromEmailSettingsRequest
	): GetCustomFromEmailSettingsResult {
		return apiClientHelper.makeObjectResultRequest<GetCustomFromEmailSettingsResult>(request)
	}

}
