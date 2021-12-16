package com.ecwid.apiclient.v3.dto.profile.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.impl.RequestInfo

data class StoreProfileRequest(
	val lang: String? = null,
	val showExtendedInfo: Boolean = false,
) : ApiRequest {

	override fun toRequestInfo() = RequestInfo.createGetRequest(
		pathSegments = listOf(
			"profile"
		),
		params = toParams()
	)

	private fun toParams(): Map<String, String> {
		val params = mutableMapOf<String, String>()
		lang?.let { params["lang"] = it }
		params["showExtendedInfo"] = showExtendedInfo.toString()
		return params
	}
}
