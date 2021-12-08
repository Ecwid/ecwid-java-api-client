package com.ecwid.apiclient.v3.impl

import com.ecwid.apiclient.v3.ApiClientHelper
import com.ecwid.apiclient.v3.ApplicationApiClient
import com.ecwid.apiclient.v3.dto.application.request.ApplicationDeleteRequest
import com.ecwid.apiclient.v3.dto.application.result.ApplicationDeleteResult

class ApplicationApiClientImpl(
	private val apiClientHelper: ApiClientHelper
) : ApplicationApiClient {
	override fun deleteApplication(request: ApplicationDeleteRequest) =
		apiClientHelper.makeObjectResultRequest<ApplicationDeleteResult>(request)
}
