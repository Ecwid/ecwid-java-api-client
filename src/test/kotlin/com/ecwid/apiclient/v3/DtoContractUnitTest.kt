package com.ecwid.apiclient.v3

import com.ecwid.apiclient.v3.dto.ApiRequest
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.reflections.Reflections
import org.reflections.scanners.SubTypesScanner
import kotlin.reflect.KClass


class DtoContractUnitTest {

	@Test
	fun `test all DTOs marked as data classes`() {
		val dtoClasses = getDtoClassesToCheck()
		assertFalse(dtoClasses.isEmpty())

		val problemDtoClasses = dtoClasses.filter { dtoClass ->
			val kclass = dtoClass.kotlin
			!kclass.isData && isDtoShouldBeMarkedAsDataClass(kclass)
		}
		assertTrue(problemDtoClasses.isEmpty()) {
			"Some DTO classes are not marked as `data class`:\n" +
					problemDtoClasses.joinToString(
							separator = "\n",
							transform = { clazz -> "\t* ${clazz.name}" }
					)
		}
	}

}


private fun isDtoShouldBeMarkedAsDataClass(kclass: KClass<*>): Boolean {
	if (kclass.isSealed) {
		// Sealed classes must not be instantiated by themself but their inheritors must be marked as data classes
		return false
	}

	if (kclass.objectInstance != null) {
		// Singleton classes has no explicit constructor arguments so it cannot be marked as data class
		return false
	}

	val constructors = kclass.constructors
	if (constructors.size == 1) {
		val constructor = constructors.iterator().next()
		if (constructor.parameters.isEmpty()) {
			// If class has only one zero-arg constructor then it cannot be marked as data class
			return false
		}
	}

	return true
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
