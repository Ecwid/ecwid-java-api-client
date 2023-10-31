package com.ecwid.apiclient.v3

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.dto.common.ApiFetchedDTO
import com.ecwid.apiclient.v3.dto.common.ApiRequestDTO
import com.ecwid.apiclient.v3.dto.common.ApiResultDTO
import com.ecwid.apiclient.v3.dto.common.ApiUpdatedDTO
import com.ecwid.apiclient.v3.jsontransformer.JsonTransformer
import com.ecwid.apiclient.v3.rule.*
import com.ecwid.apiclient.v3.rule.NonnullPropertyRule.AllowNonnull
import com.ecwid.apiclient.v3.rule.NonnullPropertyRule.IgnoreNonnull
import com.ecwid.apiclient.v3.rule.NullablePropertyRule.AllowNullable
import com.ecwid.apiclient.v3.rule.NullablePropertyRule.IgnoreNullable
import com.ecwid.apiclient.v3.util.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.reflections.Reflections
import org.reflections.scanners.Scanners
import org.reflections.scanners.Scanners.SubTypes
import org.reflections.util.ConfigurationBuilder
import org.reflections.util.FilterBuilder
import uk.co.jemos.podam.api.PodamFactoryImpl
import java.io.File
import java.io.InputStream
import java.lang.reflect.*
import kotlin.reflect.*
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.primaryConstructor
import kotlin.reflect.jvm.javaField
import kotlin.reflect.jvm.javaGetter

@TestMethodOrder(OrderAnnotation::class)
class DtoContractUnitTest {

	private val convertersReflections = lazy {
		Reflections(
			ConfigurationBuilder()
				.forPackage("com.ecwid.apiclient.v3.converter")
				.filterInputsBy(
					FilterBuilder()
						.includePattern("com\\.ecwid\\.apiclient\\.v3\\.converter\\..*")
				)
				.setScanners(Scanners.MethodsReturn)
		)
	}

	private val apiRequestClassesReflections = lazy {
		Reflections(
			ConfigurationBuilder()
				.forPackage(ApiRequest::class.java.packageName)
				.filterInputsBy(
					FilterBuilder()
						.includePattern(ApiRequest::class.java.packageName + "\\..*")
				)
				.setScanners(SubTypes.filterResultsBy { true })
		)
	}

	private val getDtoClassesToCheck = lazy {
		apiRequestClassesReflections
			.value
			.getSubTypesOf(Object::class.java)
			.filterNot { clazz -> clazz.isInterface || clazz.isAnonymousClass || clazz.isLocalClass }
			.filterNot { clazz ->
				try {
					clazz.kotlin.isCompanion
				} catch (ignore: UnsupportedOperationException) {
					// Filtering file facades classes (*Kt classes) and synthetic classes (i.e. when-mappings classes)
					true
				}
			}
			.sortedBy { clazz -> clazz.canonicalName }
	}

	@Test
	@Order(0)
	fun `test all DTOs marked as data classes`() {
		val dtoClassesToCheck = getDtoClassesToCheck.value
		assertFalse(dtoClassesToCheck.isEmpty())

		val problemDtoClasses = dtoClassesToCheck.filter { dtoClass ->
			!dtoClass.kotlin.isData && isDtoShouldBeMarkedAsDataClass(dtoClass)
		}
		assertTrue(problemDtoClasses.isEmpty()) {
			"Some DTO classes are not marked as `data class`:\n" + classesToLoggableString(problemDtoClasses)
		}
	}

	@Test
	@Order(1)
	fun `test all data classes DTOs has default constructor`() {
		val dtoClassesToCheck = getDtoClassesToCheck.value
			.filter { dtoClass -> dtoClass.kotlin.isData }
		assertFalse(dtoClassesToCheck.isEmpty())

		val problemDtoClasses = dtoClassesToCheck.filter { dtoDataClass ->
			val constructors = dtoDataClass.constructors
			val hasZeroArgConstructor = constructors.any { constructor -> constructor.parameters.isEmpty() }
			!hasZeroArgConstructor && isDtoShouldHaveZeroArgConstructor(constructors)
		}
		assertTrue(problemDtoClasses.isEmpty()) {
			"Some DTO data classes does not have zero-arg constructors " +
				"(you need to add default values for all primary constructor arguments):\n" +
				classesToLoggableString(problemDtoClasses)
		}
	}

	@Test
	@Order(2)
	fun `test all data classes DTOs has only val parameters in their primary constructors`() {
		val dtoClassesToCheck = getDtoClassesToCheck.value
			.filter { dtoClass -> dtoClass.kotlin.isData }
		assertFalse(dtoClassesToCheck.isEmpty())

		val problemDtoClasses = dtoClassesToCheck.filter { dtoDataClass ->
			isPrimaryConstructorHasMutableProperties(dtoDataClass)
		}
		assertTrue(problemDtoClasses.isEmpty()) {
			"Some DTO data classes does have mutable properties in their primary constructors " +
				"(you need to replace all parameters from `var` to `val`):\n" +
				classesToLoggableString(problemDtoClasses)
		}
	}

	@Test
	@Order(3)
	fun `test all top level data classes DTOs implement one of DTO marker interface`() {
		val dtoMarkerInterfaces = arrayOf(
			ApiFetchedDTO::class.java,
			ApiUpdatedDTO::class.java,
			ApiRequestDTO::class.java,
			ApiResultDTO::class.java
		)

		val dtoClassesToCheck = getDtoClassesToCheck.value
			.filterNot { dtoClass -> dtoClass.isEnum }
			.filterNot { dtoClass -> dtoClass.packageName.startsWith("com.ecwid.apiclient.v3.dto.common") }
		assertFalse(dtoClassesToCheck.isEmpty())

		val problemDtoClasses = dtoClassesToCheck
			.filterNot { dtoClass -> dtoClass.isClassifiedDTOOrEnclosingClass(*dtoMarkerInterfaces) }
		assertTrue(problemDtoClasses.isEmpty()) {
			val interfacesStr = dtoMarkerInterfaces.joinToString(separator = ", ") { int -> int.simpleName }
			"Some of top level DTO data classes does implement one of marker interfaces [$interfacesStr]:\n" +
				classesToLoggableString(problemDtoClasses)
		}
	}

	@Test
	@Order(4)
	fun `test all DTOs marked as 'preferably having non-nullable fields' have only non-nullable fields or fields added to exclusion list`() {
		val dtoClassesToCheck = getDtoClassesToCheck.value
			.filter { dtoClass ->
				dtoClass.isClassifiedDTOOrEnclosingClass(
					ApiFetchedDTO::class.java,
					ApiRequestDTO::class.java,
					ApiResultDTO::class.java
				)
			}
			.filterNot { dtoClass -> dtoClass.kotlin.visibility == KVisibility.PRIVATE }
		assertFalse(dtoClassesToCheck.isEmpty())

		val allowedOrIgnoredNullableProperties = nullablePropertyRules
			.filter { rule -> rule is AllowNullable || rule is IgnoreNullable }
			.map { rule -> rule.property }
			.toSet()

		val nullableProperties = dtoClassesToCheck
			.flatMap { dtoDataClass ->
				getPrimaryConstructorProperties(dtoDataClass)
					.filter { property -> property.returnType.isMarkedNullable }
			}
			.toSet()

		val problemProperties = nullableProperties - allowedOrIgnoredNullableProperties
		assertTrue(problemProperties.isEmpty()) {
			"Some of DTO data classes have nullable properties but should not:\n" +
				propertiesToLoggableString(problemProperties) + "\n" +
				"Please make this properties non-nullable if possible.\n" +
				"If Ecwid API sometimes return null as value for this property you CAN add it to as `AllowNullable()` exclusion in file `NullablePropertyRules.kt`\n" +
				"You MUST NOT add exclusion with type IgnoreNullable() which is used only for old fields until they are fixed.\n"
		}
	}

	@Test
	@Order(5)
	fun `test no new exclusions added to file NullablePropertyRules`() {
		val ignoreNullablePropertiesCount = nullablePropertyRules
			.filterIsInstance<IgnoreNullable<*, *>>()
			.size
		assertEquals(977, ignoreNullablePropertiesCount) {
			"You MUST NOT add exclusion with type IgnoreNullable() which is used only for old fields until they are fixed.\n" +
				"Please make added property non-nullable if possible.\n" +
				"If Ecwid API sometimes return null as value for this property you CAN add it to as `AllowNullable()` exclusion type instead."
		}
	}

	@Test
	@Order(6)
	fun `test all DTOs marked as 'preferably having nullable fields' have only nullable fields or fields added to exclusion list`() {
		val dtoClassesToCheck = getDtoClassesToCheck.value
			.filter { dtoClass ->
				dtoClass.isClassifiedDTOOrEnclosingClass(
					ApiUpdatedDTO::class.java
				)
			}
		assertFalse(dtoClassesToCheck.isEmpty())

		val allowedOrIgnoredNonnullProperties = nonnullPropertyRules
			.filter { rule -> rule is AllowNonnull || rule is IgnoreNonnull }
			.map { rule -> rule.property }
			.toSet()

		val nonnullProperties = dtoClassesToCheck
			.flatMap { dtoDataClass ->
				getPrimaryConstructorProperties(dtoDataClass)
					.filterNot { property -> property.returnType.isMarkedNullable }
			}
			.toSet()

		val problemProperties = nonnullProperties - allowedOrIgnoredNonnullProperties
		assertTrue(problemProperties.isEmpty()) {
			"Some of DTO data classes have nonnull properties but should not:\n" +
				propertiesToLoggableString(problemProperties) + "\n" +
				"Please make this properties nonnull if possible.\n" +
				"If Ecwid API requires value for this property to be passed you CAN add it to as `AllowNonnull()` exclusion in file `NonnullPropertyRules.kt`\n" +
				"You MUST NOT add exclusion with type IgnoreNonnull() which is used only for old fields until they are fixed.\n"
		}
	}

	@Test
	@Order(7)
	fun `test no new exclusions added to file NonnullPropertyRules`() {
		val ignoreNullablePropertiesCount = nonnullPropertyRules
			.filterIsInstance<IgnoreNonnull<*, *>>()
			.size
		assertTrue(ignoreNullablePropertiesCount <= 42) {
			"You MUST NOT add exclusion with type IgnoreNonnull() which is used only for old fields until they are fixed.\n" +
				"Please make this properties nonnull if possible.\n" +
				"If Ecwid API requires value for this property to be passed you CAN add it to as `AllowNonnull()` exclusion in file `NonnullPropertyRules.kt`"
		}
	}

	@Test
	@Order(8)
	fun `test fetched and updated DTOs correctly linked to each other`() {
		val dtoClassesToCheck = getDtoClassesToCheck.value
		assertFalse(dtoClassesToCheck.isEmpty())

		val fetchedDTOClassesToModifyKindMap = getFetchedDTOClassesToModifyKindMap(dtoClassesToCheck)
		val updatedDTOClassesToModifyKindMap = getUpdatedDTOClassesToModifyKindMap(dtoClassesToCheck)

		fetchedDTOClassesToModifyKindMap.forEach { (dtoClass, kind) ->
			@Suppress("UNUSED_VARIABLE")
			val guard = when (kind) {
				ApiFetchedDTO.ModifyKind.ReadOnly -> {
					// No UpdatedDTO to check
				}

				is ApiFetchedDTO.ModifyKind.ReadWrite -> {
					val updatedDTOClass = kind.updatedDTOClass
					val updatedDtoModifyKind = updatedDTOClassesToModifyKindMap[updatedDTOClass]
					val guard = when (updatedDtoModifyKind) {
						is ApiUpdatedDTO.ModifyKind.ReadWrite -> {
							assertEquals(
								dtoClass, updatedDtoModifyKind.fetchedDTOClass,
								"Classes ${dtoClass.qualifiedName} and ${updatedDTOClass.qualifiedName} does not links to each other"
							)
						}

						null -> {
							fail("Impossible situation")
						}
					}
				}
			}
		}

		updatedDTOClassesToModifyKindMap.forEach { (dtoClass, kind) ->
			@Suppress("UNUSED_VARIABLE")
			val guard = when (kind) {
				is ApiUpdatedDTO.ModifyKind.ReadWrite -> {
					val fetchedDTOClass = kind.fetchedDTOClass
					val fetchedDtoModifyKind = fetchedDTOClassesToModifyKindMap[fetchedDTOClass]
					val guard = when (fetchedDtoModifyKind) {
						ApiFetchedDTO.ModifyKind.ReadOnly -> {
							fail("Updatable class ${dtoClass.qualifiedName} links to class ${fetchedDTOClass.qualifiedName} which is marked as read-only ")
						}

						is ApiFetchedDTO.ModifyKind.ReadWrite -> {
							// Backlink was checked before
						}

						null -> {
							fail("Impossible situation")
						}
					}
				}
			}
		}
	}

	@Test
	@Order(9)
	fun `test fetched and updated DTOs fields list and their types are synchronized`() {
		val dtoClassesToCheck = getDtoClassesToCheck.value
		assertFalse(dtoClassesToCheck.isEmpty())

		val fetchedUpdatedDTOs = dtoClassesToCheck
			.filter { dtoClass ->
				ApiFetchedDTO::class.java.isAssignableFrom(dtoClass)
			}.mapNotNull { fetchedDtoClass ->
				val instance = fetchedDtoClass.getConstructor().newInstance() as ApiFetchedDTO
				val dtoModifyKind = instance.getModifyKind()
				if (dtoModifyKind is ApiFetchedDTO.ModifyKind.ReadWrite) {
					FetchedUpdatedDTO(
						fetchedClass = fetchedDtoClass,
						updatedClass = dtoModifyKind.updatedDTOClass.java
					)
				} else {
					null
				}
			}

		val fieldsProblems = checkFetchedUpdatedDTOsFields(fetchedUpdatedDTOs, nonUpdatablePropertyRules)

		assertTrue(fieldsProblems.isEmpty()) {
			fieldsProblems.joinToString(prefix = "\n\n", separator = "\n\n") { fieldsProblem ->
				fieldsProblem.buildMessage()
			}
		}
	}

	@Test
	@Order(10)
	fun `test no new exclusions added to file NonUpdatablePropertyRules`() {
		val ignoreNonUpdatablePropertyRulesCount = nonUpdatablePropertyRules
			.filterIsInstance<NonUpdatablePropertyRule.Ignored<*, *>>()
			.size
		assertTrue(ignoreNonUpdatablePropertyRulesCount <= 161) {
			"You MUST NOT add exclusion with type Ignored() which is used only for old fields until they are fixed.\n" +
				"Please add this field to Updated DTO if possible.\n" +
				"If this field is read-only in Ecwid API you CAN add it as `ReadOnly()` exclusion to file `NonUpdatablePropertyRules.kt`."
		}
	}

	@Test
	@Order(11)
	fun `test toUpdated extension functions are implemented correctly for all updatable FetchedDTOs`() {
		val dataStrategy = DTORandomDataProviderStrategy()
		val factory = PodamFactoryImpl(dataStrategy)

		val dtoClassesToCheck = getDtoClassesToCheck.value
		assertFalse(dtoClassesToCheck.isEmpty())

		val fetchedDTOClassesToModifyKindMap = getFetchedDTOClassesToModifyKindMap(dtoClassesToCheck)

		val problemMessages = mutableListOf<String>()

		val updatedDTOs = fetchedDTOClassesToModifyKindMap.mapNotNull { (fetchedDtoClass, kind) ->
			when (kind) {
				ApiFetchedDTO.ModifyKind.ReadOnly -> {
					// No UpdatedDTO to check
					null
				}

				is ApiFetchedDTO.ModifyKind.ReadWrite -> {
					@Suppress("UNCHECKED_CAST")
					val fetchedDtoKClass = fetchedDtoClass as KClass<out ApiFetchedDTO>
					val updatedDTOClass = kind.updatedDTOClass

					val toUpdatedMethod: Method? =
						findToUpdatedMethod(convertersReflections.value, fetchedDtoKClass, updatedDTOClass)
					if (toUpdatedMethod == null) {
						problemMessages += "Extension function with signature `${fetchedDtoKClass.java.canonicalName}.toUpdated(): ${updatedDTOClass.java.canonicalName}` is not implemented"
						null
					} else {
						val fetchedDTO = factory.manufacturePojo(fetchedDtoKClass.java)
						try {
							toUpdatedMethod.invoke(null, fetchedDTO) as ApiUpdatedDTO
						} catch (e: InvocationTargetException) {
							problemMessages += "Exception occurred during invoking method `${fetchedDtoKClass.java.canonicalName}.toUpdated(): ${e.targetException.message}"
							null
						}
					}
				}
			}
		}

		problemMessages += checkDTOFieldsEmptiness(
			values = updatedDTOs,
			ignoredFields = nonDuplicablePropertyRules.map(NonDuplicablePropertyRule<*, *>::property)
		)
			.map(FieldEmptinessProblem::buildMessage)

		if (problemMessages.isNotEmpty()) {
			fail<Unit> {
				"Some of Fetched DTOs' fields are not copied to Updated DTOs in proper way:\n" +
					messagesToLoggableString(problemMessages) + "\n" +
					"Please implement proper toUpdate() extension function for this fields.\n" +
					"If this field is really write-only according to Ecwid API documentation you CAN add it as `WriteOnly()` exclusion in file `NonDuplicablePropertyRules.kt`.\n" +
					"You MUST NOT add exclusion of any other type to this list.\n"
			}
		}
	}

	@Test
	@Order(12)
	fun `test no new non write-only exclusions added to file NonDuplicablePropertyRules`() {
		val nonWriteOnlyPropertyRulesCount = nonDuplicablePropertyRules
			.filter { it !is NonDuplicablePropertyRule.WriteOnly }
			.size
		if (nonWriteOnlyPropertyRulesCount > 1) {
			fail<Unit> {
				"You MUST NOT add exclusion with types other than `WriteOnly()` to file `NonDuplicablePropertyRules.kt`.\n" +
					"If this field is really write-only according to Ecwid API documentation you CAN add it as `WriteOnly()` exclusion.\n" +
					"Otherwise you MUST add proper implementation of method toUpdate() for this field."
			}
		}
	}
}

private fun FieldProblem.buildMessage(): String {
	val problemKind = kind
	val fieldName = fieldName

	val fetchedDTOClassName = fetchedDTOClass.canonicalName
	val updatedDTOClassName = updatedDTOClass.canonicalName

	val actualFieldClass = actualFieldClass
	val actualFieldClassStr = if (actualFieldClass != null) actualFieldClass.canonicalName else "<NOT FOUND>"

	val expectedFieldClass = expectedFieldClass
	val expectedFieldClassStr = if (expectedFieldClass != null) expectedFieldClass.canonicalName else "<N/A>"

	val problemTitle = when (problemKind) {
		FieldProblemKind.FIELD_NOT_FOUND -> {
			"All updatable fields from Fetched DTO must have corresponding field in Updated DTO."
		}

		FieldProblemKind.TYPE_MUST_NOT_BE_REUSED -> {
			"Fields with the same name in Fetched and Updated DTOs must not share the same DTO (except for primitives and enums)"
		}

		FieldProblemKind.FIELD_IS_NOT_MAP,
		FieldProblemKind.FIELD_IS_NOT_LIST,
		FieldProblemKind.PRIMITIVE_FIELDS_INCOMPATIBLE_TYPE -> {
			"n/a"
		}
	}

	val problemDescription = when (problemKind) {
		FieldProblemKind.FIELD_NOT_FOUND -> {
			"Please add this field to Updated DTO if possible.\n" +
				"If this field is read-only in Ecwid API you CAN add it as `ReadOnly()` exclusion to file `NonUpdatablePropertyRules.kt`.\n" +
				"You MUST NOT add exclusion with type Ignored() which is used only for old fields until they are fixed."
		}

		FieldProblemKind.FIELD_IS_NOT_MAP,
		FieldProblemKind.FIELD_IS_NOT_LIST,
		FieldProblemKind.PRIMITIVE_FIELDS_INCOMPATIBLE_TYPE,
		FieldProblemKind.TYPE_MUST_NOT_BE_REUSED -> "n/a"
	}

	return """
$problemKind: $problemTitle
Description:
	${problemDescription.split("\n").joinToString(separator = "\n\t\t")}
Details:
	fetchedDTOField: $fetchedDTOClassName::$fieldName
	updatedDTOField: $updatedDTOClassName::$fieldName
	expectedFieldClass: $expectedFieldClassStr
	actualFieldClass: $actualFieldClassStr
"""
}

private fun FieldEmptinessProblem.buildMessage(): String {
	val fieldName = "${fieldClass?.canonicalName}::$fieldName"
	val fieldProblemMessage = when (kind) {
		FieldEmptinessProblemKind.NULL_VALUE -> "has null value"
		FieldEmptinessProblemKind.DEFAULT_VALUE -> "has null value"
		FieldEmptinessProblemKind.EMPTY_LIST -> "is an empty list"
		FieldEmptinessProblemKind.EMPTY_MAP -> "is an empty map"
	}
	return "Field `$fieldName` $fieldProblemMessage"
}

private fun propertiesToLoggableString(properties: Collection<KProperty1<*, *>>): String {
	return properties
		.sortedBy { property -> property.toString() }
		.joinToString(
			separator = "\n",
			transform = { property -> "\t* ${property.declaringClass().canonicalName}::${property.name}" }
		)
}

private fun classesToLoggableString(classes: Collection<Class<*>>): String {
	return classes.joinToString(
		separator = "\n",
		transform = { clazz -> "\t* ${clazz.name}" }
	)
}

private fun messagesToLoggableString(messages: Collection<String>): String {
	return messages.joinToString(
		separator = "\n",
		transform = { message -> "\t* $message" }
	)
}

private fun isDtoShouldBeMarkedAsDataClass(dtoClass: Class<*>): Boolean {
	val kclass = dtoClass.kotlin

	return when {
		kclass.isSealed -> {
			// Sealed classes must not be instantiated by themself but their inheritors must be marked as data classes
			false
		}

		kclass.objectInstance != null -> {
			// Singleton classes has no explicit constructor arguments so it cannot be marked as data class
			false
		}

		else -> {
			// Classes that has only one zero-arg constructor cannot be marked as data class
			val constructors = dtoClass.constructors
			val classHasOnlyZeroArgConstructor = constructors.size == 1 && constructors.first().parameters.isEmpty()
			!classHasOnlyZeroArgConstructor
		}
	}
}

private fun isDtoShouldHaveZeroArgConstructor(constructors: Array<Constructor<*>>): Boolean {
	val maxParametersConstructor = constructors.maxByOrNull { constructor -> constructor.parameters.size }
	if (maxParametersConstructor == null) {
		// Strange things
		return true
	}

	val hasSpecialParameterType = maxParametersConstructor.parameters.any { parameter ->
		// We have some DTOs with special primary constructor parameter types.
		// We cannot assign a default value to them so will ignore them
		parameter.type.isAssignableFrom(JsonTransformer::class.java) ||
			parameter.type.isAssignableFrom(File::class.java) ||
			parameter.type.isAssignableFrom(InputStream::class.java) ||
			parameter.type.isAssignableFrom(KClass::class.java)
	}

	return !hasSpecialParameterType
}

private fun isPrimaryConstructorHasMutableProperties(dtoDataClass: Class<*>): Boolean {
	val primaryConstructorProperties = getPrimaryConstructorProperties(dtoDataClass)
	return primaryConstructorProperties.any { property ->
		property is KMutableProperty<*>
	}
}

private fun getPrimaryConstructorProperties(dtoDataClass: Class<*>): List<KProperty1<*, *>> {
	val kclass = dtoDataClass.kotlin
	return kclass.declaredMemberProperties.filter { member ->
		kclass.primaryConstructor?.parameters?.any { parameter -> member.name == parameter.name } ?: false
	}
}

internal fun getFetchedDTOClassesToModifyKindMap(dtoClassesToCheck: List<Class<*>>): Map<KClass<*>, ApiFetchedDTO.ModifyKind> {
	return dtoClassesToCheck
		.filter { dtoClass ->
			ApiFetchedDTO::class.java.isAssignableFrom(dtoClass)
		}.associate { dtoClass ->
			val instance = dtoClass.getConstructor().newInstance() as ApiFetchedDTO
			dtoClass.kotlin to instance.getModifyKind()
		}
}

private fun getUpdatedDTOClassesToModifyKindMap(dtoClassesToCheck: List<Class<*>>): Map<KClass<*>, ApiUpdatedDTO.ModifyKind> {
	return dtoClassesToCheck
		.filter { dtoClass ->
			ApiUpdatedDTO::class.java.isAssignableFrom(dtoClass)
		}.associate { dtoClass ->
			val instance = dtoClass.getConstructor().newInstance() as ApiUpdatedDTO
			dtoClass.kotlin as KClass<*> to instance.getModifyKind()
		}
}

private fun findToUpdatedMethod(
	reflections: Reflections,
	fetchedDtoClass: KClass<out ApiFetchedDTO>,
	updatedDtoClass: KClass<out ApiUpdatedDTO>
): Method? {
	return reflections
		.getMethodsReturn(updatedDtoClass.java)
		.filter { method -> method.name == "toUpdated" }
		.filter { method -> method.parameterCount == 1 }
		.firstOrNull { method -> method.parameters[0].type == fetchedDtoClass.java }
}

private fun Class<*>.isClassifiedDTOOrEnclosingClass(vararg dtoMarkerInterfaces: Class<*>): Boolean {
	return dtoMarkerInterfaces.any { dtoMarkerInterface: Class<*> ->
		isClassifiedDTOOrEnclosingClass(dtoMarkerInterface)
	}
}

private fun Class<*>.isClassifiedDTOOrEnclosingClass(dtoMarkerInterface: Class<*>): Boolean {
	var clazz: Class<*>? = this
	while (clazz != null) {
		if (dtoMarkerInterface.isAssignableFrom(clazz)) {
			return true
		}
		clazz = clazz.enclosingClass
	}
	return false
}

fun KProperty<*>.declaringClass(): Class<*> {
	return (this.javaField as Member? ?: this.javaGetter)?.declaringClass ?: error("Unable to access declaring class")
}
