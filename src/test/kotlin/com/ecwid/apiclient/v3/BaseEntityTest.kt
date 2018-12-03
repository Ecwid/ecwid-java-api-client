package com.ecwid.apiclient.v3

import com.ecwid.apiclient.v3.config.ApiServerDomain
import com.ecwid.apiclient.v3.config.ApiStoreCredentials
import com.ecwid.apiclient.v3.config.LoggingSettings
import com.ecwid.apiclient.v3.util.PropertiesLoader

abstract class BaseEntityTest {

	protected lateinit var apiClient: ApiClient

	protected open fun beforeEach() {
		apiClient = ApiClient(
				apiServerDomain = ApiServerDomain(),
				storeCredentials = ApiStoreCredentials(
						storeId = PropertiesLoader.storeId,
						apiToken = PropertiesLoader.apiToken
				),
				loggingSettings = LoggingSettings().copy(
						logRequestBody = true,
						logSuccessfulResponseBody = true
				)
		)
	}

}
