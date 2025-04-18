package com.ecwid.apiclient.v3.config

class ApiV3InstantSiteRequestKind(
	private val apiToken: String,
) : RequestKind() {
	override fun buildBaseEndpointPath(): String {
		return "/instantsite/api/v1"
	}

	override fun buildHeaders(): Map<String, String> {
		return mapOf("Authorization" to "Bearer $apiToken")
	}
}
