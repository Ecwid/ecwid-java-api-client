package com.ecwid.apiclient.v3.config

data class LoggingSettings(
	val logRequest: Boolean = true,
	val logRequestParams: Boolean = true,
	val logRequestBody: Boolean = false,

	val logResponse: Boolean = true,
	val logSuccessfulResponseBody: Boolean = false,
	val logFailedResponseBody: Boolean = true,

	val maxLogSectionLength: Int = 200,
)
