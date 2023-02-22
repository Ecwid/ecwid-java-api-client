package com.ecwid.apiclient.v3

import com.ecwid.apiclient.v3.util.SecurePattern
import com.ecwid.apiclient.v3.util.maskLogString
import com.ecwid.apiclient.v3.util.maskSensitive
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MaskUtilsUnitTest {

	@Test
	fun testMaskLogString() {
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
	fun testMaskSensitive() {
		assertEquals("te***ng", "test string".maskSensitive(4))
		assertEquals("***", "test string".maskSensitive(8))
	}

}
