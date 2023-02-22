package com.ecwid.apiclient.v3.util

import kotlin.math.max
import kotlin.math.min

private const val FULL_MASKING_THRESHOLD = 4

data class SecurePattern(
	val regex: Regex,
	val unmaskedLength: Int
)

fun String.maskLogString(securePatterns: List<SecurePattern>): String {
	var result = this
	for ((regex, unmaskedLength) in securePatterns) {
		result = result.replace(regex) { matchResult ->
			val patternValue = matchResult.groupValues[0]
			val secureValue = matchResult.groupValues[1]
			val maskedSecureValue = secureValue.maskSensitive(unmaskedLength)
			return@replace patternValue.replace(secureValue, maskedSecureValue)
		}
	}
	return result
}

fun String?.maskSensitive(unmaskedLength: Int): String {
	if (this == null) {
		return ""
	}

	if (length - unmaskedLength < FULL_MASKING_THRESHOLD) {
		return "***"
	}

	var maskLength = length - min(unmaskedLength, length)
	maskLength = max(maskLength, length / 2)

	val maskedFirst = (length - maskLength) / 2
	val maskedLast = maskedFirst + maskLength
	val prefix = substring(0, maskedFirst)
	val suffix = substring(maskedLast)
	return "$prefix***$suffix"
}
