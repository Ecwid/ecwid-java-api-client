package com.ecwid.apiclient.v3

import com.ecwid.apiclient.v3.util.FieldProblem
import com.ecwid.apiclient.v3.util.FieldProblemKind
import com.ecwid.apiclient.v3.util.checkFetchedUpdatedDTOsFields
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class FetchedUpdatedDTOsCheckerUnitTest {

	@Test
	fun `test field found in Fetched DTO but not found in Updated DTO`() {

		data class FetchedDTO(
			val field1: Int,
			val field2: String
		)

		data class UpdatedDTO(
			val field1: Int
		)

		val problems = checkFetchedUpdatedDTOsFields(FetchedDTO::class.java, UpdatedDTO::class.java)

		val expectedProblems = listOf(
			FieldProblem(
				kind = FieldProblemKind.FIELD_NOT_FOUND,
				fetchedDTOClass = FetchedDTO::class.java,
				updatedDTOClass = UpdatedDTO::class.java,
				fieldName = "field2",
				expectedFieldClass = String::class.java,
				actualFieldClass = null
			)
		)
		Assertions.assertEquals(expectedProblems, problems)

	}

	@Test
	fun `test field in Fetched sub DTO but not found in Updated sub DTO`() {

		data class FetchedSubDTO(
			val subField1: Double,
			val subField2: String
		)

		data class FetchedDTO(
			val field1: Int,
			val field2: FetchedSubDTO
		)

		data class UpdatedSubDTO(
			val subField1: Double
		)

		data class UpdatedDTO(
			val field1: Int,
			val field2: UpdatedSubDTO
		)

		val problems = checkFetchedUpdatedDTOsFields(FetchedDTO::class.java, UpdatedDTO::class.java)

		val expectedProblems = listOf(
			FieldProblem(
				kind = FieldProblemKind.FIELD_NOT_FOUND,
				fetchedDTOClass = FetchedSubDTO::class.java,
				updatedDTOClass = UpdatedSubDTO::class.java,
				fieldName = "subField2",
				expectedFieldClass = String::class.java,
				actualFieldClass = null
			)
		)
		Assertions.assertEquals(expectedProblems, problems)

	}

	@Test
	fun `test incompatible field types`() {

		data class FetchedDTO(
			val field1: Int,
			val field2: String,
			val field3: String
		)

		data class UpdatedSubDTO(
			val subField2: Int
		)

		data class UpdatedDTO(
			val field1: Int,
			val field2: Double,
			val field3: UpdatedSubDTO
		)

		val problems = checkFetchedUpdatedDTOsFields(FetchedDTO::class.java, UpdatedDTO::class.java)

		val expectedProblems = listOf(
			FieldProblem(
				kind = FieldProblemKind.PRIMITIVE_FIELDS_INCOMPATIBLE_TYPE,
				fetchedDTOClass = FetchedDTO::class.java,
				updatedDTOClass = UpdatedDTO::class.java,
				fieldName = "field2",
				expectedFieldClass = String::class.java,
				actualFieldClass = Double::class.java
			),
			FieldProblem(
				kind = FieldProblemKind.PRIMITIVE_FIELDS_INCOMPATIBLE_TYPE,
				fetchedDTOClass = FetchedDTO::class.java,
				updatedDTOClass = UpdatedDTO::class.java,
				fieldName = "field3",
				expectedFieldClass = String::class.java,
				actualFieldClass = UpdatedSubDTO::class.java
			)
		)
		Assertions.assertEquals(expectedProblems, problems)
	}

	@Test
	fun `test field is list in Fetched DTO but is not in Updated DTO`() {

		data class FetchedDTO(
			val field1: Int,
			val field2: List<Int>
		)

		data class UpdatedSubDTO(
			val subField1: Int
		)

		data class UpdatedDTO(
			val field1: Int,
			val field2: UpdatedSubDTO
		)

		val problems = checkFetchedUpdatedDTOsFields(FetchedDTO::class.java, UpdatedDTO::class.java)

		val expectedProblems = listOf(
			FieldProblem(
				kind = FieldProblemKind.FIELD_IS_NOT_LIST,
				fetchedDTOClass = FetchedDTO::class.java,
				updatedDTOClass = UpdatedDTO::class.java,
				fieldName = "field2",
				expectedFieldClass = List::class.java,
				actualFieldClass = UpdatedSubDTO::class.java
			)
		)
		Assertions.assertEquals(expectedProblems, problems)
	}

	@Test
	fun `test field is map in Fetched DTO but is not in Updated DTO`() {

		data class FetchedDTO(
			val field1: Int,
			val field2: Map<String, Double>
		)

		data class UpdatedSubDTO(
			val subField1: Int
		)

		data class UpdatedDTO(
			val field1: Int,
			val field2: UpdatedSubDTO
		)

		val problems = checkFetchedUpdatedDTOsFields(FetchedDTO::class.java, UpdatedDTO::class.java)

		val expectedProblems = listOf(
			FieldProblem(
				kind = FieldProblemKind.FIELD_IS_NOT_MAP,
				fetchedDTOClass = FetchedDTO::class.java,
				updatedDTOClass = UpdatedDTO::class.java,
				fieldName = "field2",
				expectedFieldClass = Map::class.java,
				actualFieldClass = UpdatedSubDTO::class.java
			)
		)
		Assertions.assertEquals(expectedProblems, problems)
	}

	@Test
	fun `test incompatible list generic type for field in Fetched and Updated DTOs`() {

		class FetchedListWrapper : ArrayList<Int>()

		data class FetchedDTO(
			val field1: Int,
			val field2: List<Int>,
			val field3: FetchedListWrapper
		)

		class UpdatedListWrapper : ArrayList<String>()

		data class UpdatedDTO(
			val field1: Int,
			val field2: List<String>,
			val field3: UpdatedListWrapper
		)

		val problems = checkFetchedUpdatedDTOsFields(FetchedDTO::class.java, UpdatedDTO::class.java)

		val expectedProblems = listOf(
			FieldProblem(
				kind = FieldProblemKind.PRIMITIVE_FIELDS_INCOMPATIBLE_TYPE,
				fetchedDTOClass = FetchedDTO::class.java,
				updatedDTOClass = UpdatedDTO::class.java,
				fieldName = "field2 (list value)",
				expectedFieldClass = Int::class.javaObjectType,
				actualFieldClass = String::class.java
			),
			FieldProblem(
				kind = FieldProblemKind.PRIMITIVE_FIELDS_INCOMPATIBLE_TYPE,
				fetchedDTOClass = FetchedDTO::class.java,
				updatedDTOClass = UpdatedDTO::class.java,
				fieldName = "field3 (list value)",
				expectedFieldClass = Int::class.javaObjectType,
				actualFieldClass = String::class.java
			)
		)
		Assertions.assertEquals(expectedProblems, problems)

	}

	@Test
	fun `test incompatible map generic types for field in Fetched and Updated DTOs`() {

		class FetchedMapWrapper : HashMap<Int, Int>()

		data class FetchedDTO(
			val field1: Int,
			val field2: Map<Int, Int>,
			val field3: Map<Int, Int>,
			val field4: FetchedMapWrapper,
			val field5: FetchedMapWrapper
		)

		class UpdatedMapWrapper1 : HashMap<Int, String>()
		class UpdatedMapWrapper2 : HashMap<String, Int>()

		data class UpdatedDTO(
			val field1: Int,
			val field2: Map<Int, String>,
			val field3: Map<String, Int>,
			val field4: UpdatedMapWrapper1,
			val field5: UpdatedMapWrapper2
		)

		val problems = checkFetchedUpdatedDTOsFields(FetchedDTO::class.java, UpdatedDTO::class.java)

		val expectedProblems = listOf(
			FieldProblem(
				kind = FieldProblemKind.PRIMITIVE_FIELDS_INCOMPATIBLE_TYPE,
				fetchedDTOClass = FetchedDTO::class.java,
				updatedDTOClass = UpdatedDTO::class.java,
				fieldName = "field2 (map value)",
				expectedFieldClass = Int::class.javaObjectType,
				actualFieldClass = String::class.java
			),
			FieldProblem(
				kind = FieldProblemKind.PRIMITIVE_FIELDS_INCOMPATIBLE_TYPE,
				fetchedDTOClass = FetchedDTO::class.java,
				updatedDTOClass = UpdatedDTO::class.java,
				fieldName = "field3 (map key)",
				expectedFieldClass = Int::class.javaObjectType,
				actualFieldClass = String::class.java
			),
			FieldProblem(
				kind = FieldProblemKind.PRIMITIVE_FIELDS_INCOMPATIBLE_TYPE,
				fetchedDTOClass = FetchedDTO::class.java,
				updatedDTOClass = UpdatedDTO::class.java,
				fieldName = "field4 (map value)",
				expectedFieldClass = Int::class.javaObjectType,
				actualFieldClass = String::class.java
			),
			FieldProblem(
				kind = FieldProblemKind.PRIMITIVE_FIELDS_INCOMPATIBLE_TYPE,
				fetchedDTOClass = FetchedDTO::class.java,
				updatedDTOClass = UpdatedDTO::class.java,
				fieldName = "field5 (map key)",
				expectedFieldClass = Int::class.javaObjectType,
				actualFieldClass = String::class.java
			)
		)
		Assertions.assertEquals(expectedProblems, problems)

	}

	@Test
	fun `test non primitive fields types must not be shared between Fetched and Updated DTOs`() {

		data class FetchedDTO(
			val field1: Int,
			val field2: SharedDTO,
			val field3: SharedEnum
		)

		data class UpdatedDTO(
			val field1: Int,
			val field2: SharedDTO,
			val field3: SharedEnum
		)

		val problems = checkFetchedUpdatedDTOsFields(FetchedDTO::class.java, UpdatedDTO::class.java)

		val expectedProblems = listOf(
			FieldProblem(
				kind = FieldProblemKind.TYPE_MUST_NOT_BE_REUSED,
				fetchedDTOClass = FetchedDTO::class.java,
				updatedDTOClass = UpdatedDTO::class.java,
				fieldName = "field2",
				expectedFieldClass = null,
				actualFieldClass = SharedDTO::class.java
			)
		)
		Assertions.assertEquals(expectedProblems, problems)
	}

	@Test
	fun `test success`() {

		class FetchedListWrapper: ArrayList<Float>()

		class FetchedMapWrapper: HashMap<Double, String>()

		data class FetchedSubDTO(
			val subField1: List<Int>,
			val subField2: FetchedListWrapper,
			val subField3: Map<Double, String>,
			val subField4: FetchedMapWrapper
		)

		data class FetchedDTO(
			val field1: Int,
			val field2: String,
			val field3: FetchedSubDTO
		)

		class UpdatedListWrapper: ArrayList<Float>()

		class UpdatedMapWrapper: HashMap<Double, String>()

		data class UpdatedSubDTO(
			val subField1: List<Int>,
			val subField2: UpdatedListWrapper,
			val subField3: Map<Double, String>,
			val subField4: UpdatedMapWrapper
		)

		data class UpdatedDTO(
			val field1: Int,
			val field2: String,
			val field3: UpdatedSubDTO
		)

		val problems = checkFetchedUpdatedDTOsFields(FetchedDTO::class.java, UpdatedDTO::class.java)
		Assertions.assertEquals(listOf<FieldProblem>(), problems)

	}

}

data class SharedDTO(
	val subField1: Int
)

private enum class SharedEnum {
	VALUE_ONE,
	VALUE_TWO
}
