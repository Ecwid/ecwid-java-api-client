package com.ecwid.apiclient.v3

import com.ecwid.apiclient.v3.util.buildQueryString
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream


class BatchSupportUnitTest {

	@ParameterizedTest
	@MethodSource("expectedQueryToParamsMapProvider")
	fun queryBuilderTest(expectedQuery: String, params: Map<String, String>) {
		Assertions.assertEquals(expectedQuery, buildQueryString(params))
	}

	companion object {
		@JvmStatic
		fun expectedQueryToParamsMapProvider(): Stream<Arguments> {
			return Stream.of(
					arguments("", mapOf<String, String>()),
					arguments("?test=test", mapOf("test" to "test")),
					arguments("?test=t%C3%A8st", mapOf("test" to "tèst")),
					arguments("?test_1=1&test_2=t%C3%A9st", mapOf("test_1" to "1", "test_2" to "tést")),
					arguments("?baseUrl=http%3A%2F%2Fexample.com%3Ftest%3Dexample&test_2=t%C3%A9st", mapOf("baseUrl" to "http://example.com?test=example", "test_2" to "tést"))
			)
		}
	}

}
