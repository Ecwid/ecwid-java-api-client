package com.ecwid.apiclient.v3.config

class ApiV3StoreRequestKind(
	private val storeId: Long,
	private val apiToken: String,
) : RequestKind() {
	override fun buildBaseEndpointPath(): String {
		return "/api/v3/$storeId"
	}

	override fun buildHeaders(): Map<String, String> {
		return mapOf("Authorization" to "Bearer $apiToken")
	}
}
