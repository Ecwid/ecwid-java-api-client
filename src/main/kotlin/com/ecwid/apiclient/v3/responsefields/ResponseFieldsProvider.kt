package com.ecwid.apiclient.v3.responsefields

import com.ecwid.apiclient.v3.dto.common.PartialResult
import com.ecwid.apiclient.v3.jsontransformer.JsonFieldName
import java.util.concurrent.ConcurrentHashMap
import kotlin.reflect.KClass
import kotlin.reflect.KProperty1
import kotlin.reflect.KType
import kotlin.reflect.full.isSubclassOf
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.javaField

private val responseFieldsCache = ConcurrentHashMap<KClass<out PartialResult<*>>, ResponseFields>()

/**
 * @throws IllegalArgumentException if [result class][partialResultClass] validation fails
 */
fun responseFieldsOf(partialResultClass: KClass<out PartialResult<*>>): ResponseFields {
	return responseFieldsCache.getOrPut(partialResultClass) {
		checkPartialResultClass(partialResultClass)
		ResponseFieldsBuilder().buildResponseFields(partialResultClass)
	}
}

/**
 * @throws IllegalArgumentException if [result class][Result] validation fails
 */
inline fun <reified Result : PartialResult<*>> responseFieldsOf(): ResponseFields {
	return responseFieldsOf(Result::class)
}

private fun checkPartialResultClass(partialResultClass: KClass<out PartialResult<*>>) {
	ensureDataClassWithoutTypeParameters(partialResultClass)

	val fullResultClass = partialResultClass.supertypes
		.single { it.classifier == PartialResult::class }
		.arguments.single().type?.classifier as KClass<*>

	ensureResultClassesCompatible(partialResultClass, fullResultClass)
}

private fun ensureResultClassesCompatible(partialResultClass: KClass<*>, fullResultClass: KClass<*>) {
	ensureDataClassWithoutTypeParameters(partialResultClass)
	ensureDataClassWithoutTypeParameters(fullResultClass)

	val fullResultPropertiesMap = fullResultClass.memberProperties.associateBy { it.name }

	partialResultClass.memberProperties.forEach { partialResultProperty ->
		val fullResultProperty = fullResultPropertiesMap[partialResultProperty.name]
			?: throw IncompatiblePartialResultClassException.ExtraneousProperty(partialResultProperty)

		if (partialResultProperty.javaField?.getAnnotation(JsonFieldName::class.java) != fullResultProperty.javaField?.getAnnotation(JsonFieldName::class.java)) {
			throw IncompatiblePartialResultClassException.IncompatiblePropertyAnnotations(partialResultProperty, fullResultProperty)
		}

		if (!areTypesCompatible(partialResultProperty.returnType, fullResultProperty.returnType)) {
			throw IncompatiblePartialResultClassException.IncompatiblePropertyTypes(partialResultProperty, fullResultProperty)
		}
	}
}

private fun areTypesCompatible(partialResultType: KType?, fullResultType: KType?): Boolean {
	if (partialResultType == fullResultType) {
		return true
	}

	if (partialResultType == null || fullResultType == null) {
		return false
	}

	if (!partialResultType.isMarkedNullable && fullResultType.isMarkedNullable) {
		return false
	}

	val partialResultClass = partialResultType.classifier as KClass<*>
	val fullResultClass = fullResultType.classifier as KClass<*>

	if (partialResultClass == fullResultClass) {
		// Same class but different type arguments. Ensure all arguments are compatible.
		assert(partialResultType.arguments.size == fullResultType.arguments.size)
		return partialResultType.arguments
			.asSequence()
			.zip(fullResultType.arguments.asSequence())
			.all { (arg1, arg2) -> arg1.variance == arg2.variance && areTypesCompatible(arg1.type, arg2.type) }
	}

	if (partialResultClass == Long::class && fullResultClass == Int::class) {
		return true
	}

	if (partialResultClass.isSubclassOf(Enum::class) && fullResultClass.isSubclassOf(Enum::class)) {
		val partialResultEnumConstants = partialResultClass.java.enumConstants.map { (it as Enum<*>).name }.toSet()
		val fullResultEnumConstants = partialResultClass.java.enumConstants.map { (it as Enum<*>).name }.toSet()
		return partialResultEnumConstants.containsAll(fullResultEnumConstants)
	}

	if (partialResultClass.isData && fullResultClass.isData) {
		ensureResultClassesCompatible(partialResultClass, fullResultClass)
		return true
	}

	return false
}

private fun ensureDataClassWithoutTypeParameters(klass: KClass<*>) {
	if (!klass.isData) {
		throw IncompatiblePartialResultClassException.NotDataClass(klass)
	}

	if (klass.typeParameters.isNotEmpty()) {
		throw IncompatiblePartialResultClassException.HasTypeParameters(klass)
	}
}

internal sealed class IncompatiblePartialResultClassException(
	override val message: String
) : IllegalArgumentException() {
	final override fun toString() = super.toString()

	data class NotDataClass(
		val klass: KClass<*>,
	) : IncompatiblePartialResultClassException("Not a data class: $klass")

	data class HasTypeParameters(
		val klass: KClass<*>,
	) : IncompatiblePartialResultClassException("Class has type parameters: $klass")

	data class ExtraneousProperty(
		val partialResultProperty: KProperty1<*, *>,
	) : IncompatiblePartialResultClassException("Extraneous property: $partialResultProperty")

	data class IncompatiblePropertyTypes(
		val partialResultProperty: KProperty1<*, *>,
		val fullResultProperty: KProperty1<*, *>,
	) : IncompatiblePartialResultClassException("Property types incompatible: $partialResultProperty <-> $fullResultProperty")

	data class IncompatiblePropertyAnnotations(
		val partialResultProperty: KProperty1<*, *>,
		val fullResultProperty: KProperty1<*, *>,
	) : IncompatiblePartialResultClassException("Property annotations incompatible: $partialResultProperty <-> $fullResultProperty")
}
