package com.ecwid.apiclient.v3.impl

import com.ecwid.apiclient.v3.ApiClientHelper
import com.ecwid.apiclient.v3.ApplicationApiClient
import com.ecwid.apiclient.v3.dto.application.request.AppDeleteRequest
import com.ecwid.apiclient.v3.dto.application.result.AppDeleteResult

class ApplicationApiClientImpl(
	private val apiClientHelper: ApiClientHelper
) : ApplicationApiClient {
	override fun deleteApp(request: AppDeleteRequest) =
		apiClientHelper.makeObjectResultRequest<AppDeleteResult>(request)
}
