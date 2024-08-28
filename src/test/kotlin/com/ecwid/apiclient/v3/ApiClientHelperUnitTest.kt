package com.ecwid.apiclient.v3

import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ApiClientHelperUnitTest {

	@Test
	fun `should return true if input string is valid json`() {
		val testString = "{\"foo\":\"bar\"}"
		assertTrue { isJsonObject(testString) }
	}

	@ParameterizedTest
	@ValueSource(strings = ["some sample text", "\"foo\":\"bar\"}", ""])
	fun `should return false if input string is not json`(input: String) {
		assertFalse { isJsonObject(input) }
	}

	@Test
	fun `should return false if input string is null`() {
		val testString = null
		assertFalse { isJsonObject(testString) }
	}
}
