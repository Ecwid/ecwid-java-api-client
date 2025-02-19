package com.ecwid.apiclient.v3.util

private const val PARAM_VALUE_PATTERN = "([^;,)]+)"

private val GLOBAL_SECURE_PATTERNS = listOf(
	createSecurePatterns("email"),
	createSecurePatterns("token"),
	createSecurePatterns("name"),
	createSecurePatterns("firstName"),
	createSecurePatterns("lastName"),
	createSecurePatterns("street"),
	createSecurePatterns("city"),
	createSecurePatterns("postalCode"),
	createSecurePatterns("phone"),
).flatten()

fun createSecurePatterns(paramName: String): List<SecurePattern> = listOf(
	createKeyValueSecurePattern(paramName),
	createJsonSecurePattern(paramName),
)

fun createKeyValueSecurePattern(paramName: String) = SecurePattern(
	regex = Regex("$paramName=$PARAM_VALUE_PATTERN"),
	unmaskedLength = 6
)

fun createJsonSecurePattern(paramName: String) = SecurePattern(
	regex = Regex(""""$paramName":\s*"([^"]*)""""),
	unmaskedLength = 6
)

fun createSecurePatterns() = GLOBAL_SECURE_PATTERNS.toMutableList()
