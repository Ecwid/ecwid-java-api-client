package com.ecwid.apiclient.v3.dto.profile.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.httptransport.HttpBody
import com.ecwid.apiclient.v3.impl.RequestInfo

data class StoreProfileUpdateRequest(
	private val updatedStoreProfile: UpdatedStoreProfile = UpdatedStoreProfile()
) : ApiRequest {

	override fun toRequestInfo() = RequestInfo.createPutRequest(
		pathSegments = listOf(
			"profile"
		),
		httpBody = HttpBody.JsonBody(
			obj = updatedStoreProfile
		)
	)
}
