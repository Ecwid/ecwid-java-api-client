package com.ecwid.apiclient.v3.util

import com.ecwid.apiclient.v3.API_TOKEN_PARAM_NAME
import com.ecwid.apiclient.v3.APP_CLIENT_SECRET_PARAM_NAME
import com.ecwid.apiclient.v3.config.LoggingSettings

private const val PARAM_VALUE_PATTERN = "([^;,)]+)"

private val API_TOKEN_SECURE_PATTERN = SecurePattern(
	regex = Regex("$API_TOKEN_PARAM_NAME=(?:secret_|public_|)$PARAM_VALUE_PATTERN"),
	unmaskedLength = 6
)

private val API_SECRET_KEY_SECURE_PATTERN = SecurePattern(
	regex = Regex("$APP_CLIENT_SECRET_PARAM_NAME=$PARAM_VALUE_PATTERN"),
	unmaskedLength = 6
)

private val GLOBAL_SECURE_PATTERNS = listOf(
	createParamSecurePattern("email"),
	createParamSecurePattern("name"),
	createParamSecurePattern("firstName"),
	createParamSecurePattern("lastName"),
	createParamSecurePattern("street"),
	createParamSecurePattern("city"),
	createParamSecurePattern("postalCode"),
	createParamSecurePattern("phone"),
)

fun createParamSecurePattern(paramName: String) = SecurePattern(
	regex = Regex("$paramName=$PARAM_VALUE_PATTERN"),
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
