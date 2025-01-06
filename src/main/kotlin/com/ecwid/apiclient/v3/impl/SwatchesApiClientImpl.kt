package com.ecwid.apiclient.v3.impl

import com.ecwid.apiclient.v3.ApiClientHelper
import com.ecwid.apiclient.v3.SwatchesApiClient
import com.ecwid.apiclient.v3.dto.swatches.request.RecentSwatchColorsGetRequest
import com.ecwid.apiclient.v3.dto.swatches.result.FetchedSwatchColorsResult

class SwatchesApiClientImpl(
	private val apiClientHelper: ApiClientHelper,
) : SwatchesApiClient {

	override fun getRecentSwatchColors(request: RecentSwatchColorsGetRequest) =
		apiClientHelper.makeObjectResultRequest<FetchedSwatchColorsResult>(request)
}
