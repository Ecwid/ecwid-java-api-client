package com.ecwid.apiclient.v3.exception

data class EcwidApiException(
	val statusCode: Int? = null,
	val reasonPhrase: String? = null,
	val code: String? = null,
	override val message: String? = null,
	val args: Map<String, String>? = null,
	override val cause: Throwable? = null,
) : Exception(message, cause)
