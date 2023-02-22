package com.ecwid.apiclient.v3.config

data class LoggingSettings(
	val logRequest: Boolean = true,
	val logRequestParams: Boolean = true,
	val logRequestBody: Boolean = false,
	val maskRequestApiToken: Boolean = true, /* DISABLE FOR DEBUG PURPOSES ONLY! */
	val maskRequestApiSecretKey: Boolean = true, /* DISABLE FOR DEBUG PURPOSES ONLY! */

	val logResponse: Boolean = true,
	val logSuccessfulResponseBody: Boolean = false,
	val logFailedResponseBody: Boolean = true
)
