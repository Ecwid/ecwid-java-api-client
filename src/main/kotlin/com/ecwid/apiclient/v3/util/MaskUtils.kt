package com.ecwid.apiclient.v3.util

import kotlin.math.max
import kotlin.math.min

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

internal fun maskAppSecretKey(secretKey: String): String {
	return if (secretKey.length > 2 * TOKEN_UNMASKED_LENGTH) {
		secretKey.take(TOKEN_UNMASKED_LENGTH) + "***" + secretKey.takeLast(
			minOf(
				TOKEN_UNMASKED_LENGTH,
				secretKey.length - 2 * TOKEN_UNMASKED_LENGTH
			)
		)
	} else {
		"***"
	}
}

fun maskSensitive(sensitiveData: String?, unmaskedLength: Int): String {
	if (sensitiveData == null) {
		return ""
	}

	if (sensitiveData.length - unmaskedLength < 4) {
		return "***"
	}

	var maskLength = sensitiveData.length - min(unmaskedLength, sensitiveData.length)
	maskLength = max(maskLength, sensitiveData.length / 2)

	val maskedFirst = (sensitiveData.length - maskLength) / 2
	val maskedLast = maskedFirst + maskLength
	val prefix = sensitiveData.substring(0, maskedFirst)
	val suffix = sensitiveData.substring(maskedLast)
	return "$prefix***$suffix"
}
