package com.ecwid.apiclient.v3

import com.ecwid.apiclient.v3.dto.customfromemail.GetCustomFromEmailSettingsRequest
import com.ecwid.apiclient.v3.dto.customfromemail.GetCustomFromEmailSettingsResult

/**
 * Internal api to manage custom from emails verification procedure
 */
interface CustomFromEmailSettingsApiClient {
	fun getCustomFromEmailSettings(request: GetCustomFromEmailSettingsRequest) : GetCustomFromEmailSettingsResult
}
