package com.ecwid.apiclient.v3.impl

import com.ecwid.apiclient.v3.ApiClientHelper
import com.ecwid.apiclient.v3.SwatchesApiClient
import com.ecwid.apiclient.v3.dto.swatches.request.RecentSwatchColorsGetRequest
import com.ecwid.apiclient.v3.dto.swatches.result.FetchedSwatchColor

class SwatchesApiClientImpl(
	private val apiClientHelper: ApiClientHelper,
) : SwatchesApiClient {

	override fun getRecentSwatchColors(request: RecentSwatchColorsGetRequest) =
		apiClientHelper.makeObjectResultRequest<Sequence<FetchedSwatchColor>>(request)
}
