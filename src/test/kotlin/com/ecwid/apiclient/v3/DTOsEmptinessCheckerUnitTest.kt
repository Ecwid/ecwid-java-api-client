package com.ecwid.apiclient.v3

import com.ecwid.apiclient.v3.util.FieldEmptinessProblem
import com.ecwid.apiclient.v3.util.FieldEmptinessProblemKind
import com.ecwid.apiclient.v3.util.checkDTOFieldsEmptiness
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.util.*

class DTOsEmptinessCheckerUnitTest {

	@Test
	fun `test DTOs check for emptiness and default fields value works well`() {
		data class BooleanDTO(
			val booleanField1: Boolean,
			val booleanField2: Boolean,
			val booleanField3: Boolean?,
			val booleanField4: Boolean?,
			val booleanField5: Boolean?,
			val booleanField6: Boolean?
		)

		data class ByteDTO(
			val byteField1: Byte,
			val byteField2: Byte,
			val byteField3: Byte?,
			val byteField4: Byte?,
			val byteField5: Byte?,
			val byteField6: Byte?
		)

		data class ShortDTO(
			val shortField1: Short,
			val shortField2: Short,
			val shortField3: Short?,
			val shortField4: Short?,
			val shortField5: Short?,
			val shortField6: Short?
		)

		data class IntDTO(
			val intField1: Int,
			val intField2: Int,
			val intField3: Int?,
			val intField4: Int?,
			val intField5: Int?,
			val intField6: Int?
		)

		data class LongDTO(
			val longField1: Long,
			val longField2: Long,
			val longField3: Long?,
			val longField4: Long?,
			val longField5: Long?,
			val longField6: Long?
		)

		data class FloatDTO(
			val floatField1: Float,
			val floatField2: Float,
			val floatField3: Float?,
			val floatField4: Float?,
			val floatField5: Float?,
			val floatField6: Float?
		)

		data class DoubleDTO(
			val doubleField1: Double,
			val doubleField2: Double,
			val doubleField3: Double?,
			val doubleField4: Double?,
			val doubleField5: Double?,
			val doubleField6: Double?
		)

		data class StringDTO(
			val stringField1: String?,
			val stringField2: String,
			val stringField3: String
		)

		data class DateDTO(
			val dateField1: Date?,
			val dateField2: Date?,
			val dateField3: Date
		)

		data class EnumDTO(
			val enumField1: EnumClass?,
			val enumField2: EnumClass
		)

		data class ListDTO(
			val listField1: List<Int>?,
			val listField2: List<Int>,
			val listField3: List<Int?>,
			val listField4: List<Int>
		)

		data class MapDTO(
			val mapField1: Map<Int, Int>?,
			val mapField2: Map<Int, Int>,
			val mapField3: Map<Int?, Int>,
			val mapField4: Map<Int, Int?>,
			val mapField5: Map<Int, Int>
		)

		data class NullDTO(
			val intField: Int
		)

		data class TestDTO(
			val booleanDTO: BooleanDTO,
			val byteDTO: ByteDTO,
			val shortDTO: ShortDTO,
			val intDTO: IntDTO,
			val longDTO: LongDTO,
			val floatDTO: FloatDTO,
			val doubleDTO: DoubleDTO,
			val stringDTO: StringDTO,
			val dateDTO: DateDTO,
			val enumDTO: EnumDTO,
			val listDTO: ListDTO,
			val mapDTO: MapDTO,

			val primitiveField: Int?,
			val nullDTOField: NullDTO?
		)

		val dto = TestDTO(
			booleanDTO = BooleanDTO(
				booleanField1 = false,
				booleanField2 = false,
				booleanField3 = null,
				booleanField4 = null,
				booleanField5 = true,
				booleanField6 = true
			),
			byteDTO = ByteDTO(
				byteField1 = 0,
				byteField2 = 0,
				byteField3 = null,
				byteField4 = null,
				byteField5 = 1,
				byteField6 = 1
			),
			shortDTO = ShortDTO(
				shortField1 = 0,
				shortField2 = 0,
				shortField3 = null,
				shortField4 = null,
				shortField5 = 1,
				shortField6 = 1
			),
			intDTO = IntDTO(
				intField1 = 0,
				intField2 = 0,
				intField3 = null,
				intField4 = null,
				intField5 = 1,
				intField6 = 1
			),
			longDTO = LongDTO(
				longField1 = 0L,
				longField2 = 0L,
				longField3 = null,
				longField4 = null,
				longField5 = 1L,
				longField6 = 1L
			),
			floatDTO = FloatDTO(
				floatField1 = 0.0f,
				floatField2 = 0.0f,
				floatField3 = null,
				floatField4 = null,
				floatField5 = 1.0f,
				floatField6 = 1.0f
			),
			doubleDTO = DoubleDTO(
				doubleField1 = 0.0,
				doubleField2 = 0.0,
				doubleField3 = null,
				doubleField4 = null,
				doubleField5 = 1.0,
				doubleField6 = 1.0
			),
			stringDTO = StringDTO(
				stringField1 = null,
				stringField2 = "",
				stringField3 = "stringField3"
			),
			dateDTO = DateDTO(
				dateField1 = null,
				dateField2 = Date(0),
				dateField3 = Date()
			),
			enumDTO = EnumDTO(
				enumField1 = null,
				enumField2 = EnumClass.VALUE1
			),
			listDTO = ListDTO(
				listField1 = null,
				listField2 = listOf(),
				listField3 = listOf(0),
				listField4 = listOf(1)
			),
			mapDTO = MapDTO(
				mapField1 = null,
				mapField2 = mapOf(),
				mapField3 = mapOf(0 to 1),
				mapField4 = mapOf(1 to 0),
				mapField5 = mapOf(1 to 1)
			),
			primitiveField = null,
			nullDTOField = null
		)

		val expectedProblems = setOf(

			FieldEmptinessProblem(
				kind = FieldEmptinessProblemKind.DEFAULT_VALUE,
				fieldClass = BooleanDTO::class.java,
				fieldName = "booleanField1"
			),
			FieldEmptinessProblem(
				kind = FieldEmptinessProblemKind.DEFAULT_VALUE,
				fieldClass = BooleanDTO::class.java,
				fieldName = "booleanField2"
			),
			FieldEmptinessProblem(
				kind = FieldEmptinessProblemKind.NULL_VALUE,
				fieldClass = BooleanDTO::class.java,
				fieldName = "booleanField3"
			),
			FieldEmptinessProblem(
				kind = FieldEmptinessProblemKind.NULL_VALUE,
				fieldClass = BooleanDTO::class.java,
				fieldName = "booleanField4"
			),

			FieldEmptinessProblem(
				kind = FieldEmptinessProblemKind.DEFAULT_VALUE,
				fieldClass = ByteDTO::class.java,
				fieldName = "byteField1"
			),
			FieldEmptinessProblem(
				kind = FieldEmptinessProblemKind.DEFAULT_VALUE,
				fieldClass = ByteDTO::class.java,
				fieldName = "byteField2"
			),
			FieldEmptinessProblem(
				kind = FieldEmptinessProblemKind.NULL_VALUE,
				fieldClass = ByteDTO::class.java,
				fieldName = "byteField3"
			),
			FieldEmptinessProblem(
				kind = FieldEmptinessProblemKind.NULL_VALUE,
				fieldClass = ByteDTO::class.java,
				fieldName = "byteField4"
			),

			FieldEmptinessProblem(
				kind = FieldEmptinessProblemKind.DEFAULT_VALUE,
				fieldClass = ShortDTO::class.java,
				fieldName = "shortField1"
			),
			FieldEmptinessProblem(
				kind = FieldEmptinessProblemKind.DEFAULT_VALUE,
				fieldClass = ShortDTO::class.java,
				fieldName = "shortField2"
			),
			FieldEmptinessProblem(
				kind = FieldEmptinessProblemKind.NULL_VALUE,
				fieldClass = ShortDTO::class.java,
				fieldName = "shortField3"
			),
			FieldEmptinessProblem(
				kind = FieldEmptinessProblemKind.NULL_VALUE,
				fieldClass = ShortDTO::class.java,
				fieldName = "shortField4"
			),

			FieldEmptinessProblem(
				kind = FieldEmptinessProblemKind.DEFAULT_VALUE,
				fieldClass = IntDTO::class.java,
				fieldName = "intField1"
			),
			FieldEmptinessProblem(
				kind = FieldEmptinessProblemKind.DEFAULT_VALUE,
				fieldClass = IntDTO::class.java,
				fieldName = "intField2"
			),
			FieldEmptinessProblem(
				kind = FieldEmptinessProblemKind.NULL_VALUE,
				fieldClass = IntDTO::class.java,
				fieldName = "intField3"
			),
			FieldEmptinessProblem(
				kind = FieldEmptinessProblemKind.NULL_VALUE,
				fieldClass = IntDTO::class.java,
				fieldName = "intField4"
			),

			FieldEmptinessProblem(
				kind = FieldEmptinessProblemKind.DEFAULT_VALUE,
				fieldClass = LongDTO::class.java,
				fieldName = "longField1"
			),
			FieldEmptinessProblem(
				kind = FieldEmptinessProblemKind.DEFAULT_VALUE,
				fieldClass = LongDTO::class.java,
				fieldName = "longField2"
			),
			FieldEmptinessProblem(
				kind = FieldEmptinessProblemKind.NULL_VALUE,
				fieldClass = LongDTO::class.java,
				fieldName = "longField3"
			),
			FieldEmptinessProblem(
				kind = FieldEmptinessProblemKind.NULL_VALUE,
				fieldClass = LongDTO::class.java,
				fieldName = "longField4"
			),

			FieldEmptinessProblem(
				kind = FieldEmptinessProblemKind.DEFAULT_VALUE,
				fieldClass = FloatDTO::class.java,
				fieldName = "floatField1"
			),
			FieldEmptinessProblem(
				kind = FieldEmptinessProblemKind.DEFAULT_VALUE,
				fieldClass = FloatDTO::class.java,
				fieldName = "floatField2"
			),
			FieldEmptinessProblem(
				kind = FieldEmptinessProblemKind.NULL_VALUE,
				fieldClass = FloatDTO::class.java,
				fieldName = "floatField3"
			),
			FieldEmptinessProblem(
				kind = FieldEmptinessProblemKind.NULL_VALUE,
				fieldClass = FloatDTO::class.java,
				fieldName = "floatField4"
			),

			FieldEmptinessProblem(
				kind = FieldEmptinessProblemKind.DEFAULT_VALUE,
				fieldClass = DoubleDTO::class.java,
				fieldName = "doubleField1"
			),
			FieldEmptinessProblem(
				kind = FieldEmptinessProblemKind.DEFAULT_VALUE,
				fieldClass = DoubleDTO::class.java,
				fieldName = "doubleField2"
			),
			FieldEmptinessProblem(
				kind = FieldEmptinessProblemKind.NULL_VALUE,
				fieldClass = DoubleDTO::class.java,
				fieldName = "doubleField3"
			),
			FieldEmptinessProblem(
				kind = FieldEmptinessProblemKind.NULL_VALUE,
				fieldClass = DoubleDTO::class.java,
				fieldName = "doubleField4"
			),

			FieldEmptinessProblem(
				kind = FieldEmptinessProblemKind.NULL_VALUE,
				fieldClass = StringDTO::class.java,
				fieldName = "stringField1"
			),
			FieldEmptinessProblem(
				kind = FieldEmptinessProblemKind.DEFAULT_VALUE,
				fieldClass = StringDTO::class.java,
				fieldName = "stringField2"
			),

			FieldEmptinessProblem(
				kind = FieldEmptinessProblemKind.NULL_VALUE,
				fieldClass = DateDTO::class.java,
				fieldName = "dateField1"
			),
			FieldEmptinessProblem(
				kind = FieldEmptinessProblemKind.DEFAULT_VALUE,
				fieldClass = DateDTO::class.java,
				fieldName = "dateField2"
			),

			FieldEmptinessProblem(
				kind = FieldEmptinessProblemKind.NULL_VALUE,
				fieldClass = EnumDTO::class.java,
				fieldName = "enumField1"
			),

			FieldEmptinessProblem(
				kind = FieldEmptinessProblemKind.NULL_VALUE,
				fieldClass = ListDTO::class.java,
				fieldName = "listField1"
			),
			FieldEmptinessProblem(
				kind = FieldEmptinessProblemKind.EMPTY_LIST,
				fieldClass = ListDTO::class.java,
				fieldName = "listField2"
			),
			FieldEmptinessProblem(
				kind = FieldEmptinessProblemKind.DEFAULT_VALUE,
				fieldClass = ListDTO::class.java,
				fieldName = "listField3 (list value)"
			),

			FieldEmptinessProblem(
				kind = FieldEmptinessProblemKind.NULL_VALUE,
				fieldClass = MapDTO::class.java,
				fieldName = "mapField1"
			),
			FieldEmptinessProblem(
				kind = FieldEmptinessProblemKind.EMPTY_MAP,
				fieldClass = MapDTO::class.java,
				fieldName = "mapField2"
			),
			FieldEmptinessProblem(
				kind = FieldEmptinessProblemKind.DEFAULT_VALUE,
				fieldClass = MapDTO::class.java,
				fieldName = "mapField3 (map key)"
			),
			FieldEmptinessProblem(
				kind = FieldEmptinessProblemKind.DEFAULT_VALUE,
				fieldClass = MapDTO::class.java,
				fieldName = "mapField4 (map value)"
			),

			FieldEmptinessProblem(
				kind = FieldEmptinessProblemKind.NULL_VALUE,
				fieldClass = TestDTO::class.java,
				fieldName = "primitiveField"
			),
			FieldEmptinessProblem(
				kind = FieldEmptinessProblemKind.NULL_VALUE,
				fieldClass = TestDTO::class.java,
				fieldName = "nullDTOField"
			)
		)

		val problems = checkDTOFieldsEmptiness(
			values = listOf(dto),
			ignoredFields = listOf()
		)
		Assertions.assertEquals(expectedProblems, problems)
	}
}

@Suppress("unused")
private enum class EnumClass {
	VALUE1,
	VALUE2
}
