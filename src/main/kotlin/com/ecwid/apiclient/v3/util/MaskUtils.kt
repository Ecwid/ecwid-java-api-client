package com.ecwid.apiclient.v3.util

private const val TOKEN_UNMASKED_LENGTH = 3

internal fun maskApiToken(apiToken: String): String {
	val availablePrefixes = listOf("secret_", "public_", "")
	for (availablePrefix in availablePrefixes) {
		if (apiToken.startsWith(availablePrefix)) {
			return if (apiToken.length > availablePrefix.length + 2 * TOKEN_UNMASKED_LENGTH) {
				val prefix = apiToken.substring(0, availablePrefix.length + TOKEN_UNMASKED_LENGTH)
				val suffix = apiToken.substring(apiToken.length - TOKEN_UNMASKED_LENGTH, apiToken.length)
				"$prefix***$suffix"
			} else {
				apiToken
			}
		}
	}
	return ""
}
