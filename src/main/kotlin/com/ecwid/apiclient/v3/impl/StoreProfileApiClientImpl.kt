package com.ecwid.apiclient.v3.impl

import com.ecwid.apiclient.v3.StoreProfileApiClient
import com.ecwid.apiclient.v3.dto.profile.request.StoreProfileRequest
import com.ecwid.apiclient.v3.dto.profile.result.FetchedStoreProfile

internal class StoreProfileApiClientImpl(
        private val apiClientHelper: ApiClientHelper
) : StoreProfileApiClient {
    override fun getStoreProfile(request: StoreProfileRequest) = apiClientHelper.makeRequest<FetchedStoreProfile>(request)

}