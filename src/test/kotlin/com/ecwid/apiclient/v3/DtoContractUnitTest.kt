package com.ecwid.apiclient.v3

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.dto.common.ApiFetchedDTO
import com.ecwid.apiclient.v3.dto.common.ApiRequestDTO
import com.ecwid.apiclient.v3.dto.common.ApiResultDTO
import com.ecwid.apiclient.v3.dto.common.ApiUpdatedDTO
import com.ecwid.apiclient.v3.jsontransformer.JsonTransformer
import com.ecwid.apiclient.v3.rule.NullablePropertyRule.AllowNullable
import com.ecwid.apiclient.v3.rule.NullablePropertyRule.IgnoreNullable
import com.ecwid.apiclient.v3.rule.nullablePropertyRules
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.reflections.Reflections
import org.reflections.scanners.SubTypesScanner
import java.io.File
import java.io.InputStream
import java.lang.reflect.Constructor
import java.lang.reflect.Member
import kotlin.reflect.KMutableProperty
import kotlin.reflect.KProperty
import kotlin.reflect.KProperty1
import kotlin.reflect.KVisibility
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.primaryConstructor
import kotlin.reflect.jvm.javaField
import kotlin.reflect.jvm.javaGetter

private val dtoMarkerInterfaces = arrayOf(
	ApiFetchedDTO::class.java,
	ApiUpdatedDTO::class.java,
	ApiRequestDTO::class.java,
	ApiResultDTO::class.java
)

@TestMethodOrder(OrderAnnotation::class)
class DtoContractUnitTest {

	@Test
	@Order(0)
	fun `test all DTOs marked as data classes`() {
		val dtoClasses = getDtoClassesToCheck()
		assertFalse(dtoClasses.isEmpty())

		val problemDtoClasses = dtoClasses.filter { dtoClass ->
			!dtoClass.kotlin.isData && isDtoShouldBeMarkedAsDataClass(dtoClass)
		}
		assertTrue(problemDtoClasses.isEmpty()) {
			"Some DTO classes are not marked as `data class`:\n" + classesToLoggableString(problemDtoClasses)
		}
	}

	@Test
	@Order(1)
	fun `test all data classes DTOs has default constructor`() {
		val dtoDataClasses = getDtoClassesToCheck()
			.filter { dtoClass -> dtoClass.kotlin.isData }
		assertFalse(dtoDataClasses.isEmpty())

		val problemDtoClasses = dtoDataClasses.filter { dtoDataClass ->
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
		val dtoDataClasses = getDtoClassesToCheck()
			.filter { dtoClass -> dtoClass.kotlin.isData }
		assertFalse(dtoDataClasses.isEmpty())

		val problemDtoClasses = dtoDataClasses.filter { dtoDataClass ->
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
		val dtoDataClasses = getDtoClassesToCheck()
			.filterNot { dtoClass -> dtoClass.isEnum }
			.filterNot { dtoClass -> dtoClass.packageName.startsWith("com.ecwid.apiclient.v3.dto.common") }
		assertFalse(dtoDataClasses.isEmpty())

		val problemDtoClasses = dtoDataClasses
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
		val dtoDataClasses = getDtoClassesToCheck()
			.filter { dtoClass ->
				dtoClass.isClassifiedDTOOrEnclosingClass(
					ApiFetchedDTO::class.java,
					ApiRequestDTO::class.java,
					ApiResultDTO::class.java
				)
			}
			.filterNot { dtoClass -> dtoClass.kotlin.visibility == KVisibility.PRIVATE }
		assertFalse(dtoDataClasses.isEmpty())

		val allowedOrIgnoredNullableProperties = nullablePropertyRules
			.filter { rule -> rule is AllowNullable || rule is IgnoreNullable }
			.map { rule -> rule.property }
			.toSet()

		val nullableProperties = dtoDataClasses
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
		assertTrue(ignoreNullablePropertiesCount <= 1062) {
			"You MUST NOT add exclusion with type IgnoreNullable() which is used only for old fields until they are fixed.\n" +
					"Please make added property non-nullable if possible.\n" +
					"If Ecwid API sometimes return null as value for this property you CAN add it to as `AllowNullable()` exclusion type instead."
		}
	}

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

private fun isDtoShouldBeMarkedAsDataClass(dtoClass: Class<*>): Boolean {
	val kclass = dtoClass.kotlin

	if (kclass.isSealed) {
		// Sealed classes must not be instantiated by themself but their inheritors must be marked as data classes
		return false
	}

	if (kclass.objectInstance != null) {
		// Singleton classes has no explicit constructor arguments so it cannot be marked as data class
		return false
	}

	val constructors = dtoClass.constructors
	if (constructors.size == 1) {
		if (constructors.first().parameters.isEmpty()) {
			// If class has only one zero-arg constructor then it cannot be marked as data class
			return false
		}
	}

	return true
}

private fun isDtoShouldHaveZeroArgConstructor(constructors: Array<Constructor<*>>): Boolean {
	val maxParametersConstructor = constructors.maxBy { constructor -> constructor.parameters.size }
	if (maxParametersConstructor == null) {
		// Strange things
		return true
	}

	val hasSpecialParameterType = maxParametersConstructor.parameters.any { parameter ->
		// We have some DTOs with special primary constructor parameter types.
		// We cannot assign a default value to them so will ignore them
		parameter.type.isAssignableFrom(JsonTransformer::class.java)
				|| parameter.type.isAssignableFrom(File::class.java)
				|| parameter.type.isAssignableFrom(InputStream::class.java)
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

private fun getDtoClassesToCheck() = Reflections(ApiRequest::class.java.packageName, SubTypesScanner(false))
	.getSubTypesOf(Object::class.java)
	.filterNot { clazz -> clazz.isInterface || clazz.isAnonymousClass }
	.filterNot { clazz ->
		try {
			clazz.kotlin.isCompanion
		} catch (e: UnsupportedOperationException) {
			// Filtering file facades classes (*Kt classes) and synthetic classes (i.e. when-mappings classes)
			true
		}
	}
	.sortedBy { clazz -> clazz.canonicalName }

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
