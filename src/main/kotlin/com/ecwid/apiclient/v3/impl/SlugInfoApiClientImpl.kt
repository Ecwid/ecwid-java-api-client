package com.ecwid.apiclient.v3.impl

import com.ecwid.apiclient.v3.ApiClientHelper
import com.ecwid.apiclient.v3.SlugInfoApiClient
import com.ecwid.apiclient.v3.dto.sluginfo.FetchedSlugInfo
import com.ecwid.apiclient.v3.dto.sluginfo.SlugInfoRequest

internal class SlugInfoApiClientImpl(
	private val apiClientHelper: ApiClientHelper
): SlugInfoApiClient {
	override fun getSlugInfo(request: SlugInfoRequest): FetchedSlugInfo =
		apiClientHelper.makeObjectResultRequest<FetchedSlugInfo>(request)

}
