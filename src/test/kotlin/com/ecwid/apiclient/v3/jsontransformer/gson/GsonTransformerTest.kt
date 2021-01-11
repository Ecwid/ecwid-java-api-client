package com.ecwid.apiclient.v3.jsontransformer.gson

import com.ecwid.apiclient.v3.dto.order.request.UpdatedOrder
import com.google.gson.JsonParser
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test


internal class GsonTransformerTest {

	private val transformer = GsonTransformer(emptyList())

	@Test
	fun `test serialization with extension - top level fields`() {
		val order = UpdatedOrder(
			email = "original@gmail.com",
			total = 12.34
		)

		val orderExt = OrderExt(
			email = "replaced@gmail.com",
			simpleExtraField = "simple extra field value 1",
			complexExtraField = ComplexExtraField("complex extra field value 1")
		)

		assertJsonEquals(
			"""
				{
					"email": "replaced@gmail.com",
					"total": 12.34,
					"simpleExtraField": "simple extra field value 1",
					"complexExtraField": { "value": "complex extra field value 1" }
				}
			""".trimIndent(),
			transformer.serialize(order, orderExt)
		)
	}

	@Test
	fun `test serialization with extension - deep level fields`() {
		val order = UpdatedOrder(
			items = listOf(
				UpdatedOrder.OrderItem(
					price = 1.56,
					sku = "SKU_TO_BE_REPLACED"
				)
			)
		)

		val orderExt = OrderExt(
			items = listOf(
				OrderItemExt(
					sku = "REPLACED_SKU",
					simpleExtraField = "simple extra field value 3",
					complexExtraField = ComplexExtraField("complex extra field value 3")
				)
			)
		)

		assertJsonEquals(
			"""
				{
					"items": [
						{
							"price": 1.56,
							"sku": "REPLACED_SKU",
							"simpleExtraField": "simple extra field value 3",
							"complexExtraField": { "value": "complex extra field value 3" }
						}
					]
				}
			""".trimIndent(),
			transformer.serialize(order, orderExt)
		)
	}

	@Test
	fun `test serialization with extension - deep level fields - no original`() {
		val order = UpdatedOrder()

		val orderExt = OrderExt(
			items = listOf(
				OrderItemExt(
					sku = "SKU1",
					simpleExtraField = "simple extra field value 2",
					complexExtraField = ComplexExtraField("complex extra field value 2")
				)
			)
		)

		assertJsonEquals(
			"""
				{
					"items": [
						{
							"sku": "SKU1",
							"simpleExtraField": "simple extra field value 2",
							"complexExtraField": { "value": "complex extra field value 2" }
						}
					]
				}
			""".trimIndent(),
			transformer.serialize(order, orderExt)
		)
	}

	@Test
	fun `test serialization with extension - deep level fields - original array longer than ext`() {
		val order = UpdatedOrder(
			items = listOf(
				UpdatedOrder.OrderItem(sku = "SKU_TO_BE_REPLACED"),
				UpdatedOrder.OrderItem(sku = "SKU_TO_BE_UNTOUCHED")
			)
		)

		val orderExt = OrderExt(
			items = listOf(
				OrderItemExt(sku = "REPLACED_SKU")
			)
		)

		assertJsonEquals(
			"""
				{
					"items": [
						{
							"sku": "REPLACED_SKU"
						},
						{
							"sku": "SKU_TO_BE_UNTOUCHED"
						}
					]
				}
			""".trimIndent(),
			transformer.serialize(order, orderExt)
		)
	}

	@Test
	fun `test serialization with extension - deep level fields - original array shorter than ext`() {
		val order = UpdatedOrder(
			items = listOf(
				UpdatedOrder.OrderItem(sku = "SKU_TO_BE_REPLACED")
			)
		)

		val orderExt = OrderExt(
			items = listOf(
				OrderItemExt(sku = "REPLACED_SKU"),
				OrderItemExt(sku = "SKU_TO_BE_APPENDED")
			)
		)

		assertJsonEquals(
			"""
				{
					"items": [
						{
							"sku": "REPLACED_SKU"
						},
						{
							"sku": "SKU_TO_BE_APPENDED"
						}
					]
				}
			""".trimIndent(),
			transformer.serialize(order, orderExt)
		)
	}
}

private fun assertJsonEquals(expected: String, actual: String) {
	val expectedJson = JsonParser.parseString(expected)
	val actualJson = JsonParser.parseString(actual)
	assertEquals(expectedJson, actualJson)
}

private data class OrderExt(
	var email: String? = null,
	var simpleExtraField: String? = null,
	var complexExtraField: ComplexExtraField? = null,
	var items: List<OrderItemExt>? = null
)

private data class OrderItemExt(
	var sku: String? = null,
	var simpleExtraField: String? = null,
	var complexExtraField: ComplexExtraField? = null
)

private data class ComplexExtraField(val value: String? = null)



