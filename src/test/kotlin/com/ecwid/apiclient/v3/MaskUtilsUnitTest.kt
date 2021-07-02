package com.ecwid.apiclient.v3

import com.ecwid.apiclient.v3.util.maskApiToken
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MaskUtilsUnitTest {

	@Test
	fun testSecretTokens() {
		assertEquals("secret_012***890", maskApiToken("secret_012345678901234567890"))
		assertEquals("secret_012***456", maskApiToken("secret_0123456"))
		assertEquals("secret_012345", maskApiToken("secret_012345"))
		assertEquals("secret_01234", maskApiToken("secret_01234"))
		assertEquals("secret_012", maskApiToken("secret_012"))
	}

	@Test
	fun testPublicTokens() {
		assertEquals("public_012***890", maskApiToken("public_012345678901234567890"))
		assertEquals("public_012***456", maskApiToken("public_0123456"))
		assertEquals("public_012345", maskApiToken("public_012345"))
		assertEquals("public_01234", maskApiToken("public_01234"))
		assertEquals("public_012", maskApiToken("public_012"))
	}

	@Test
	fun testLegacyTokens() {
		assertEquals("012***890", maskApiToken("012345678901234567890"))
		assertEquals("012***456", maskApiToken("0123456"))
		assertEquals("012345", maskApiToken("012345"))
		assertEquals("01234", maskApiToken("01234"))
		assertEquals("012", maskApiToken("012"))
	}
}
