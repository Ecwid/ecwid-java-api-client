package com.ecwid.apiclient.v3.dto.profile.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.httptransport.HttpBody
import com.ecwid.apiclient.v3.impl.RequestInfo

data class ExtrafieldConfigCreateRequest(
	val newConfig: UpdatedExtrafieldConfig = UpdatedExtrafieldConfig()
) : ApiRequest {
	override fun toRequestInfo() = RequestInfo.createPostRequest(
		pathSegments = listOf(
			"profile",
			"extrafields"
		),
		httpBody = HttpBody.JsonBody(
			obj = newConfig
		)
	)
}
