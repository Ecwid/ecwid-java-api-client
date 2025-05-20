package com.ecwid.apiclient.v3.util

private const val PARAM_VALUE_PATTERN = "([^;,)]+)"

private val GLOBAL_SECURE_PATTERNS = listOf(
	createKeyValueSecurePattern("email"),
	createKeyValueSecurePattern("customerEmail"),
	createKeyValueSecurePattern("token"),
	createKeyValueSecurePattern("customerTaxId"),
	createKeyValueSecurePattern("password"),
	createKeyValueSecurePattern("customerGroupId"),
	createKeyValueSecurePattern("privateAdminNotes"),
	createKeyValueSecurePattern("primaryPhone"),
	createKeyValueSecurePattern("companyName"),
	createKeyValueSecurePattern("street"),
	createKeyValueSecurePattern("city"),
	createKeyValueSecurePattern("countryCode"),
	createKeyValueSecurePattern("postalCode"),
	createKeyValueSecurePattern("stateOrProvinceCode"),
	createKeyValueSecurePattern("phone"),
	createKeyValueSecurePattern("name"),
	createKeyValueSecurePattern("contact"),
	createJsonSecurePattern("email"),
	createJsonSecurePattern("customerEmail"),
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
