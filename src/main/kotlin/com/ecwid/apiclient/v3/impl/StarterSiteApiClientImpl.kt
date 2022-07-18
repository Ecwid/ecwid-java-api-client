package com.ecwid.apiclient.v3.impl

import com.ecwid.apiclient.v3.ApiClientHelper
import com.ecwid.apiclient.v3.StarterSiteApiClient
import com.ecwid.apiclient.v3.dto.startersite.request.StarterSiteSettingsRequest
import com.ecwid.apiclient.v3.dto.startersite.result.StarterSiteSettingsResult

internal class StarterSiteApiClientImpl(
	private val apiClientHelper: ApiClientHelper,
) : StarterSiteApiClient {

	override fun getStarterSiteSettings(request: StarterSiteSettingsRequest) =
		apiClientHelper.makeObjectResultRequest<StarterSiteSettingsResult>(request)

}
