package com.ecwid.apiclient.v3.dto.startersite.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.impl.RequestInfo

data class StarterSiteSettingsRequest(
	private val namespace: String = "",
) : ApiRequest {
	override fun toRequestInfo() = RequestInfo.createGetRequest(
		pathSegments = listOf(
			"startersite"
		),
		params = mapOf("namespace" to namespace)
	)
}
