package com.ecwid.apiclient.v3.dto.profile.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.httptransport.HttpBody
import com.ecwid.apiclient.v3.impl.RequestInfo

class StoreProfileUpdateRequest(
        private val updatedStoreProfile: UpdatedStoreProfile
): ApiRequest {

    override fun toRequestInfo() = RequestInfo.createPutRequest(
            endpoint = "profile",
            httpBody = HttpBody.JsonBody(
                    obj = updatedStoreProfile
            )
    )

}
