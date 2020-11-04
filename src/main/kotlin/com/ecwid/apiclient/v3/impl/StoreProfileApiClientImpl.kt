package com.ecwid.apiclient.v3.impl

import com.ecwid.apiclient.v3.ApiClientHelper
import com.ecwid.apiclient.v3.StoreProfileApiClient
import com.ecwid.apiclient.v3.dto.profile.request.LatestStatsRequest
import com.ecwid.apiclient.v3.dto.profile.request.StoreProfileRequest
import com.ecwid.apiclient.v3.dto.profile.request.StoreProfileUpdateRequest
import com.ecwid.apiclient.v3.dto.profile.result.FetchedLatestStats
import com.ecwid.apiclient.v3.dto.profile.result.FetchedStoreProfile
import com.ecwid.apiclient.v3.dto.profile.result.StoreProfileUpdateResult

internal class StoreProfileApiClientImpl(
		private val apiClientHelper: ApiClientHelper
) : StoreProfileApiClient {
	override fun getStoreProfile(request: StoreProfileRequest) = apiClientHelper.makeObjectResultRequest<FetchedStoreProfile>(request)
	override fun updateStoreProfile(request: StoreProfileUpdateRequest) = apiClientHelper.makeObjectResultRequest<StoreProfileUpdateResult>(request)
	override fun getLatestStats(request: LatestStatsRequest) = apiClientHelper.makeObjectResultRequest<FetchedLatestStats>(request)

}
