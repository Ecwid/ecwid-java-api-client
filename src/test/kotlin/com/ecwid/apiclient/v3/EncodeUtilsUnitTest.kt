package com.ecwid.apiclient.v3

import com.ecwid.apiclient.v3.util.buildEndpointPath
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class EncodeUtilsUnitTest {

	@Test
	fun `test buildEndpointPath with single segment`() {
		assertEquals(
			"orders",
			buildEndpointPath(listOf(
				"orders"
			))
		)
	}

	@Test
	fun `test buildEndpointPath with multiple segments`() {
		val expectedEncodedOptionName =
			"option+with%25spaces%24and%23other%26special%2Bsymbols%5E%D0%B2%D0%BA%D0%BB%D1%8E%D1%87" +
					"%D0%B0%D1%8F%28%D0%BD%D0%95%D0%BB%D0%90%D0%A2%D0%B8%D0%9D%D1%81%D0%9A%D0%B8%D0%95%29"
		assertEquals(
			"orders/order%2Fentity%2Fwith%2Fslashes%2F123/items/456/options/$expectedEncodedOptionName/files/789",
			buildEndpointPath(listOf(
				"orders",
				"order/entity/with/slashes/123",
				"items",
				"456",
				"options",
				"option with%spaces\$and#other&special+symbols^включая(нЕлАТиНсКиЕ)",
				"files",
				"789"
			))
		)
	}

}
