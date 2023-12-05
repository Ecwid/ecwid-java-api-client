package com.ecwid.apiclient.v3.util

import com.ecwid.apiclient.v3.API_TOKEN_PARAM_NAME
import com.ecwid.apiclient.v3.APP_CLIENT_SECRET_PARAM_NAME
import com.ecwid.apiclient.v3.config.LoggingSettings

private const val PARAM_VALUE_PATTERN = "([^;,)]+)"

private val API_TOKEN_SECURE_PATTERN = SecurePattern(
	regex = Regex("$API_TOKEN_PARAM_NAME=(?:secret_|public_|)$PARAM_VALUE_PATTERN"),
	unmaskedLength = 6
)

private val API_SECRET_KEY_SECURE_PATTERN = createKeyValueSecurePattern(APP_CLIENT_SECRET_PARAM_NAME)

private val GLOBAL_SECURE_PATTERNS = listOf(
	createKeyValueSecurePattern("hash"),
	createKeyValueSecurePattern("email"),
	createJsonSecurePattern("email"),
	createJsonSecurePattern("name"),
	createJsonSecurePattern("firstName"),
	createJsonSecurePattern("lastName"),
	createJsonSecurePattern("street"),
	createJsonSecurePattern("city"),
	createJsonSecurePattern("postalCode"),
	createJsonSecurePattern("phone"),
)

fun createKeyValueSecurePattern(paramName: String) = SecurePattern(
	regex = Regex("$paramName=$PARAM_VALUE_PATTERN"),
	unmaskedLength = 6
)

fun createJsonSecurePattern(paramName: String) = SecurePattern(
	regex = Regex(""""$paramName":\s*"([^"]*)""""),
	unmaskedLength = 6
)

fun createSecurePatterns(loggingSettings: LoggingSettings) = mutableListOf<SecurePattern>().apply {
	if (loggingSettings.maskRequestApiToken) {
		add(API_TOKEN_SECURE_PATTERN)
	}
	if (loggingSettings.maskRequestApiSecretKey) {
		add(API_SECRET_KEY_SECURE_PATTERN)
	}
	addAll(GLOBAL_SECURE_PATTERNS)
}.toList()
