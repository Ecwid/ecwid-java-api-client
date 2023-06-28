package com.ecwid.apiclient.v3.responsefields

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ResponseFieldsDslUnitTest {
	@Test
	fun `test dsl with responseFields`() {
		val productResponseFields = newResponseFields {
			fields("id", "sku")
			field("options") {
				field("name")
			}
		}

		val actual = newResponseFields {
			field("items", productResponseFields)
		}

		val expected = newResponseFields {
			field("items") {
				fields("id", "sku")
				field("options") {
					field("name")
				}
			}
		}

		assertEquals(expected, actual)
		assertEquals("items(id,sku,options(name))", actual.toHttpParameter())
	}
}
