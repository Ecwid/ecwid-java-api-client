package com.ecwid.apiclient.v3.impl

import com.ecwid.apiclient.v3.ApiClientHelper
import com.ecwid.apiclient.v3.ApplicationApiClient
import com.ecwid.apiclient.v3.dto.application.request.ApplicationDeleteRequest
import com.ecwid.apiclient.v3.dto.application.request.ApplicationTokenInfoRequest
import com.ecwid.apiclient.v3.dto.application.result.ApplicationDeleteResult
import com.ecwid.apiclient.v3.dto.application.result.ApplicationTokenResult

class ApplicationApiClientImpl(
	private val apiClientHelper: ApiClientHelper
) : ApplicationApiClient {
	override fun deleteApplication(request: ApplicationDeleteRequest) =
		apiClientHelper.makeObjectResultRequest<ApplicationDeleteResult>(request)

	override fun getApplicationTokenInfo(request: ApplicationTokenInfoRequest) =
		apiClientHelper.makeObjectResultRequest<ApplicationTokenResult>(request)
}
