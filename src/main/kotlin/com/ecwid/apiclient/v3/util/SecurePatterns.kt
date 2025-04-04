package com.ecwid.apiclient.v3.util

private const val PARAM_VALUE_PATTERN = "([^;,)]+)"

private val GLOBAL_SECURE_PATTERNS = listOf(
	createKeyValueSecurePattern("email"),
	createKeyValueSecurePattern("token"),
	createJsonSecurePattern("email"),
	createJsonSecurePattern("token"),
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

fun createSecurePatterns() = mutableListOf<SecurePattern>().apply {
	addAll(GLOBAL_SECURE_PATTERNS)
}.toList()
