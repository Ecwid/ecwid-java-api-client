package com.ecwid.apiclient.v3

import com.ecwid.apiclient.v3.util.SecurePattern
import com.ecwid.apiclient.v3.util.maskLogString
import com.ecwid.apiclient.v3.util.maskSensitive
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MaskUtilsUnitTest {

	@Test
	fun testMaskLogKeyValueString() {
		val logString =
			"token=secret_RandomToken0jwOrgYc5sSKBYcvO0DbP; PasswordCredentials(email=test@example.com, password=123456)"
		val securePatterns = listOf(
			SecurePattern(Regex("token=(?:secret_|public_|)([^;,)]+)"), 6),
			SecurePattern(Regex("email=([^;,)]+)"), 4),
			SecurePattern(Regex("password=([^;,)]+)"), 2),
		)

		val maskedLogString = logString.maskLogString(securePatterns)
		val expectedMaskedLogString = "token=secret_Ran***DbP; PasswordCredentials(email=te***om, password=1***6)"
		assertEquals(expectedMaskedLogString, maskedLogString)
	}

	@Test
	fun testMaskLogJsonString() {
		val logString =
			"""{"billingPerson":{"email":"alexis@ecwid.com","firstName":"John","lastName":"Smith","phone":"123467890"}}"""
		val securePatterns = listOf(
			SecurePattern(Regex(""""email":\s*"([^"]*)""""), 6),
			SecurePattern(Regex(""""firstName":\s*"([^"]*)""""), 6),
			SecurePattern(Regex(""""lastName":\s*"([^"]*)""""), 6),
			SecurePattern(Regex(""""phone":\s*"([^"]*)""""), 6),
		)

		val maskedLogString = logString.maskLogString(securePatterns)
		val expectedMaskedLogString =
			"""{"billingPerson":{"email":"ale***com","firstName":"***","lastName":"***","phone":"***"}}"""
		assertEquals(expectedMaskedLogString, maskedLogString)
	}

	@Test
	fun testMaskSensitive() {
		assertEquals("te***ng", "test string".maskSensitive(4))
		assertEquals("***", "test string".maskSensitive(8))
	}

}
