package com.ecwid.apiclient.v3

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.jsontransformer.JsonTransformer
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.reflections.Reflections
import org.reflections.scanners.SubTypesScanner
import java.io.File
import java.io.InputStream
import java.lang.reflect.Constructor
import kotlin.reflect.KMutableProperty
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.primaryConstructor


class DtoContractUnitTest {

	@Test
	fun `test all DTOs marked as data classes`() {
		val dtoClasses = getDtoClassesToCheck()
		assertFalse(dtoClasses.isEmpty())

		val problemDtoClasses = dtoClasses.filter { dtoClass ->
			!dtoClass.kotlin.isData && isDtoShouldBeMarkedAsDataClass(dtoClass)
		}
		assertTrue(problemDtoClasses.isEmpty()) {
			"Some DTO classes are not marked as `data class`:\n" + classListToLoggableString(problemDtoClasses)
		}
	}

	@Test
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
					classListToLoggableString(problemDtoClasses)
		}
	}

	@Test
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
					classListToLoggableString(problemDtoClasses)
		}
	}

}

private fun classListToLoggableString(problemDtoClasses: List<Class<*>>) =
		problemDtoClasses.joinToString(
				separator = "\n",
				transform = { clazz -> "\t* ${clazz.name}" }
		)

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
	val kclass = dtoDataClass.kotlin
	val primaryConstructorProperties = kclass.declaredMemberProperties.filter { member ->
		kclass.primaryConstructor?.parameters?.any { parameter -> member.name == parameter.name } ?: false
	}
	return primaryConstructorProperties.any { property ->
		property is KMutableProperty<*>
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
