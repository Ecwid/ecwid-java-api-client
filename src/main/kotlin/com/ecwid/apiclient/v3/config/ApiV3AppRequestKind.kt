package com.ecwid.apiclient.v3.config

private const val APP_CLIENT_ID_HEADER_NAME = "X-Ecwid-App-Client-Id"
private const val APP_CLIENT_SECRET_HEADER_NAME = "X-Ecwid-App-Secret-Key"

class ApiV3AppRequestKind(
	private val clientId: String,
	private val clientSecret: String,
) : RequestKind() {
	override fun buildBaseEndpointPath(): String {
		return "/api/v3"
	}

	override fun buildHeaders(): Map<String, String> {
		return mapOf(
			APP_CLIENT_ID_HEADER_NAME to clientId,
			APP_CLIENT_SECRET_HEADER_NAME to clientSecret
		)
	}
}
