package com.ecwid.apiclient.v3.responsefields

import com.ecwid.apiclient.v3.jsontransformer.JsonFieldName
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.*

internal class ResponseFieldsBuilderUnitTest {
	private val generator = ResponseFieldsBuilder()

	@Test
	fun `must throw if not data class provided`() {
		assertThrows<IllegalArgumentException> {
			generator.buildResponseFields(String::class)
		}
	}

	@Test
	fun `must generate same result for inline and not inline call`() {
		val first = generator.buildResponseFields<TestProduct>()
		val second = generator.buildResponseFields<TestProduct>()

		// new instance for ignore cache
		val third = ResponseFieldsBuilder().buildResponseFields<TestProduct>()

		assertEquals(first, second)
		assertEquals(first, third)
	}

	@Test
	fun `must generate fields for simple data class`() {
		val actual = generator.buildResponseFields<TestProduct>()

		val expected = newResponseFields {
			field("id")
			field("name")
			field("created")
			field("visibility")
			field("taxInfo") {
				field("customMap") {
					field("customId")
				}
				field("details") {
					field("name")
				}
				field("javaDetails") {
					field("name")
				}
				field("tax")
			}

			field("enabled")

			// GetResponseFields field annotation and custom json name
			field("named_category") {
				field("name")
			}

			field("emptyCategory") // GetResponseFields field annotation with empty fields

			// GetResponseFields class annotation
			field("categories") {
				field("id")
			}

			field("taxes") {
				field("customMap") {
					field("customId")
				}
				field("details") {
					field("name")
				}
				field("javaDetails") {
					field("name")
				}
				field("tax")
			}
			field("options") // Sealed class skipped
			field("checkboxes") {
				fields("name", "type")
			}
			field("categoryIds") // List of primitive type

			field("generic1") { field("id") } // data class generic list
			field("generic2") { field("id") } // data class generic map

			// members from superclass
			field("superclass") {
				fields("id", "name", "description")
			}
		}

		assertEquals(expected, actual)
	}
}

@Suppress("unused")
private data class TestProduct(
	val id: Int,
	val name: String,
	val created: Date,
	val visibility: Visibility,
	val taxInfo: TaxInfo?,
	val enabled: Boolean,

	@JsonFieldName("named_category")
	@ResponseFieldsOverride(["name"])
	val namedCategory: Category?,

	@ResponseFieldsOverride([])
	val emptyCategory: Category,

	val categories: List<Category>,
	val taxes: List<TaxInfo>,
	val options: List<ProductOption>?,
	val checkboxes: List<ProductOption.Checkbox>?,
	val categoryIds: ArrayList<Long>,

	val generic1: Simple1Generic<Int>,
	val generic2: Simple2Generic<Int, Category>,
	val superclass: InheritedClass
) {
	sealed class ProductOption {
		abstract val type: String

		data class Checkbox(
			override val type: String,
			val name: String
		) : ProductOption()
	}

	@ResponseFieldsOverride(["id"])
	data class Category(
		val id: Int,
		val name: String,
		val description: String
	)

	data class TaxInfo(
		val tax: Double?,
		val details: Map<String, TaxDetails>,
		val javaDetails: HashMap<String, TaxDetails>,
		@ResponseFieldsOverride(["customId"])
		val customMap: Map<String, TaxDetails>,
	) {
		data class TaxDetails(
			val name: String,
		)
	}

	data class Simple1Generic<T>(val id: T)
	data class Simple2Generic<T, R>(val id: T)

	enum class Visibility {
		SHOW,
		HIDE
	}

	open class Superclass(open val id: Int) {
		var description = "Description"
	}

	data class InheritedClass(override val id: Int, val name: String) : Superclass(id)
}
