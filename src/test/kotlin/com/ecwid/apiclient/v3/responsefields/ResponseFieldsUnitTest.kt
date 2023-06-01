package com.ecwid.apiclient.v3.responsefields

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ResponseFieldsUnitTest {
	@Test
	fun `plus operator must merge fields`() {
		val idOnly = ResponseFields(
			fields = mapOf(
				"id" to ResponseFields.Field.All,
				"options" to ResponseFields.Field(
					children = mapOf(
						"id" to ResponseFields.Field.All
					)
				),
			)
		)

		val nameOnly = ResponseFields(
			fields = mapOf(
				"name" to ResponseFields.Field.All,
				"options" to ResponseFields.Field(
					children = mapOf(
						"name" to ResponseFields.Field.All
					)
				),
			)
		)

		val expected = ResponseFields(
			fields = mapOf(
				"id" to ResponseFields.Field.All,
				"name" to ResponseFields.Field.All,
				"options" to ResponseFields.Field(
					children = mapOf(
						"id" to ResponseFields.Field.All,
						"name" to ResponseFields.Field.All
					)
				),
			)
		)

		assertEquals(expected, idOnly + nameOnly)
		assertEquals(expected, nameOnly + idOnly)
	}

	@Test
	fun `plus operator with all must returns all`() {
		val idOnly = ResponseFields(
			fields = mapOf(
				"id" to ResponseFields.Field.All,
			)
		)

		assertEquals(ResponseFields.All, idOnly + ResponseFields.All)
		assertEquals(ResponseFields.All, ResponseFields.All + idOnly)
	}

	@Test
	fun `plus operator with duplicated fields`() {
		val first = newResponseFields {
			fields("id", "limit")
			field("items") {
				field("name")
			}
		}

		val expected = newResponseFields {
			fields("id", "limit", "count")
			field("items") {
				field("name")
			}
		}

		assertEquals(expected, first + AS_SEQUENCE_SEARCH_RESULT_REQUIRED_FIELDS)
	}

	@Test
	fun `plus operator with extend field`() {
		val first = newResponseFields {
			fields("id", "name")
			field("options")
			field("taxes") {
				field("id")
			}
		}

		val second = newResponseFields {
			fields("id", "name")
			field("options") {
				field("id")
			}
			field("taxes")
		}

		val expected = newResponseFields {
			fields("id", "name", "options", "taxes")
		}

		assertEquals(expected, first + second)
		assertEquals(expected, second + first)
	}

	@Test
	fun `toParameter test`() {
		val fields = newResponseFields {
			field("email")
			field("billingPerson") {
				field("phone")
			}
		}

		assertEquals("email,billingPerson(phone)", fields.toHttpParameter())
	}

	@Test
	fun `dsl test`() {
		val actual = newResponseFields {
			field("email")
			field("billingPerson") {
				field("phone")
			}
		}

		val expected = ResponseFields(
			fields = mapOf(
				"email" to ResponseFields.Field.All,
				"billingPerson" to ResponseFields.Field(
					children = mapOf(
						"phone" to ResponseFields.Field.All
					)
				)
			)
		)

		assertEquals(expected, actual)
	}

	@Test
	fun `contains test`() {
		val idOnly = newResponseFields { field("id") }
		val nameOnly = newResponseFields { field("name") }
		val idAndNameOnly = newResponseFields { fields("id", "name") }
		val optionsOnly = newResponseFields { field("options") }
		val optionsSpecifiedOnly = newResponseFields {
			field("options") {
				field("id")
			}
		}

		assertTrue(idOnly.contains(idOnly)) {
			"$idOnly must contains $idOnly"
		}
		assertTrue(idAndNameOnly.contains(idOnly)) {
			"$idAndNameOnly must contains $idOnly"
		}
		assertTrue(idAndNameOnly.contains(nameOnly)) {
			"$idAndNameOnly must contains $nameOnly"
		}
		assertTrue(ResponseFields.All.contains(idOnly)) {
			"All must contains $idOnly"
		}
		assertTrue(ResponseFields.All.contains(ResponseFields.All)) {
			"All must contains All"
		}
		assertTrue(optionsOnly.contains(optionsSpecifiedOnly)) {
			"$optionsOnly must contains $optionsSpecifiedOnly"
		}
		assertFalse(idOnly.contains(ResponseFields.All)) {
			"$idOnly must not contains All"
		}
		assertFalse(idOnly.contains(nameOnly)) {
			"$idOnly must not contains $nameOnly"
		}
		assertFalse(idOnly.contains(idAndNameOnly)) {
			"$idOnly must not contains $idAndNameOnly"
		}
		assertFalse(optionsSpecifiedOnly.contains(optionsOnly)) {
			"$optionsSpecifiedOnly must not contains $optionsOnly"
		}
	}
}
