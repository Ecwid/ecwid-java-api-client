package com.ecwid.apiclient.v3.impl

import com.ecwid.apiclient.v3.ApiClientHelper
import com.ecwid.apiclient.v3.InstantSiteRedirectsApiClient
import com.ecwid.apiclient.v3.dto.instantsite.redirects.request.*
import com.ecwid.apiclient.v3.dto.instantsite.redirects.result.*

class InstantSiteRedirectsApiClientImpl(
	private val apiClientHelper: ApiClientHelper
) : InstantSiteRedirectsApiClient {
	override fun searchInstantSiteRedirects(request: InstantSiteRedirectsSearchRequest) =
		apiClientHelper.makeObjectResultRequest<InstantSiteRedirectsSearchResult>(request)

	override fun getInstantSiteRedirect(request: InstantSiteRedirectGetRequest) =
		apiClientHelper.makeObjectResultRequest<FetchedInstantSiteRedirect>(request)

	override fun updateInstantSiteRedirect(request: InstantSiteRedirectUpdateRequest) =
		apiClientHelper.makeObjectResultRequest<InstantSiteRedirectsUpdateResult>(request)

	override fun createInstantSiteRedirectList(request: InstantSiteRedirectListCreateRequest) =
		apiClientHelper.makeObjectResultRequest<InstantSiteRedirectListCreateResult>(request)

	override fun deleteInstantSiteRedirect(request: InstantSiteRedirectDeleteRequest) =
		apiClientHelper.makeObjectResultRequest<InstantSiteRedirectListDeleteResult>(request)
}
