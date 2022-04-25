package com.ecwid.apiclient.v3.util

import com.ecwid.apiclient.v3.dto.common.NullableUpdatedValue
import com.ecwid.apiclient.v3.rule.NonUpdatablePropertyRule
import java.lang.reflect.Field
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import kotlin.reflect.jvm.kotlinProperty

data class FetchedUpdatedDTO(
	val fetchedClass: Class<*>,
	val updatedClass: Class<*>
)

internal fun checkFetchedUpdatedDTOsFields(
	fetchedDTOClass: Class<*>,
	updatedDTOClass: Class<*>,
	nonUpdatablePropertyRules: List<NonUpdatablePropertyRule<*, *>>
): List<FieldProblem> {
	val fetchedUpdatedDTOs = listOf(
		FetchedUpdatedDTO(
			fetchedClass = fetchedDTOClass,
			updatedClass = updatedDTOClass
		)
	)
	return checkFetchedUpdatedDTOsFields(fetchedUpdatedDTOs, nonUpdatablePropertyRules)
}

internal fun checkFetchedUpdatedDTOsFields(
	fetchedUpdatedDTOs: List<FetchedUpdatedDTO>,
	nonUpdatablePropertyRules: List<NonUpdatablePropertyRule<*, *>>
): List<FieldProblem> {
	val problemsCollector = mutableListOf<FieldProblem>()
	fetchedUpdatedDTOs.forEach { fetchedUpdatedDTO ->
		checkClass(
			fetchedDTOClass = fetchedUpdatedDTO.fetchedClass,
			updatedDTOClass = fetchedUpdatedDTO.updatedClass,
			nonUpdatablePropertyRules = nonUpdatablePropertyRules,
			problemsCollector = problemsCollector
		)
	}
	return problemsCollector.toList()
}

private fun checkClass(
	fetchedDTOClass: Class<*>,
	updatedDTOClass: Class<*>,
	nonUpdatablePropertyRules: List<NonUpdatablePropertyRule<*, *>>,
	problemsCollector: MutableList<FieldProblem>
) {
	fetchedDTOClass.declaredFields
		.forEach { fetchedDTOField ->
			val updatedDTOField = updatedDTOClass.declaredFields
				.find { declaredField ->
					declaredField.name == fetchedDTOField.name
				}
			if (updatedDTOField == null) {
				val kotlinProperty = fetchedDTOField.kotlinProperty
				val isIgnored = nonUpdatablePropertyRules.any { rule ->
					rule.property == kotlinProperty
				}
				if (!isIgnored) {
					problemsCollector.add(
						FieldProblem(
							kind = FieldProblemKind.FIELD_NOT_FOUND,
							fetchedDTOClass = fetchedDTOClass,
							updatedDTOClass = updatedDTOClass,
							fieldName = fetchedDTOField.name,
							expectedFieldClass = fetchedDTOField.type,
							actualFieldClass = null
						)
					)
				} else {
					// That's fine
				}
			} else {
				checkField(
					fetchedDTOClass = fetchedDTOClass,
					updatedDTOClass = updatedDTOClass,
					fetchedDTOField = fetchedDTOField,
					updatedDTOField = updatedDTOField,
					fieldName = fetchedDTOField.name,
					nonUpdatablePropertyRules = nonUpdatablePropertyRules,
					problemsCollector = problemsCollector
				)
			}
		}
}

private fun checkClassOrPrimitive(
	fetchedDTOClass: Class<*>,
	updatedDTOClass: Class<*>,
	parentFetchedDTOClass: Class<*>,
	parentUpdatedDTOClass: Class<*>,
	fieldName: String,
	nonUpdatablePropertyRules: List<NonUpdatablePropertyRule<*, *>>,
	problemsCollector: MutableList<FieldProblem>
) {
	if (fetchedDTOClass.isJreType() || fetchedDTOClass.isEnum) {
		// Primitive type
		val isCompatibleJreTypes =
			fetchedDTOClass.isJreType() && fetchedDTOClass.kotlin.javaObjectType == updatedDTOClass.kotlin.javaObjectType
		val isCompatibleEnumTypes = fetchedDTOClass.isEnum && updatedDTOClass.isEnum
		if (!isCompatibleJreTypes && !isCompatibleEnumTypes) {
			problemsCollector.add(
				FieldProblem(
					kind = FieldProblemKind.PRIMITIVE_FIELDS_INCOMPATIBLE_TYPE,
					fetchedDTOClass = parentFetchedDTOClass,
					updatedDTOClass = parentUpdatedDTOClass,
					fieldName = fieldName,
					expectedFieldClass = fetchedDTOClass,
					actualFieldClass = updatedDTOClass
				)
			)
		} else {
			// That's fine
		}
	} else {
		// Complex DTO
		if (fetchedDTOClass == updatedDTOClass) {
			problemsCollector.add(
				FieldProblem(
					kind = FieldProblemKind.TYPE_MUST_NOT_BE_REUSED,
					fetchedDTOClass = parentFetchedDTOClass,
					updatedDTOClass = parentUpdatedDTOClass,
					fieldName = fieldName,
					expectedFieldClass = null,
					actualFieldClass = fetchedDTOClass
				)
			)
		} else {
			checkClass(
				fetchedDTOClass = fetchedDTOClass,
				updatedDTOClass = updatedDTOClass,
				nonUpdatablePropertyRules = nonUpdatablePropertyRules,
				problemsCollector = problemsCollector
			)
		}
	}
}

private fun checkField(
	fetchedDTOClass: Class<*>,
	updatedDTOClass: Class<*>,
	fetchedDTOField: Field,
	updatedDTOField: Field,
	fieldName: String,
	nonUpdatablePropertyRules: List<NonUpdatablePropertyRule<*, *>>,
	problemsCollector: MutableList<FieldProblem>
) {
	val fetchedDTOFieldClass = fetchedDTOField.type
	val updatedDTOFieldClass = updatedDTOField.type
	when {
		fetchedDTOFieldClass.isMap() -> {
			if (!updatedDTOFieldClass.isMap()) {
				problemsCollector.add(
					FieldProblem(
						kind = FieldProblemKind.FIELD_IS_NOT_MAP,
						fetchedDTOClass = fetchedDTOClass,
						updatedDTOClass = updatedDTOClass,
						fieldName = fieldName,
						expectedFieldClass = fetchedDTOFieldClass,
						actualFieldClass = updatedDTOFieldClass
					)
				)
			} else {
				val (fetchedKeyClass, fetchedValueClass) = fetchedDTOField.extractMapGenericArgumentClasses()
				val (updatedKeyClass, updatedValueClass) = updatedDTOField.extractMapGenericArgumentClasses()

				checkClassOrPrimitive(
					fetchedDTOClass = fetchedKeyClass,
					updatedDTOClass = updatedKeyClass,
					parentFetchedDTOClass = fetchedDTOClass,
					parentUpdatedDTOClass = updatedDTOClass,
					fieldName = "$fieldName (map key)",
					nonUpdatablePropertyRules = nonUpdatablePropertyRules,
					problemsCollector = problemsCollector
				)
				checkClassOrPrimitive(
					fetchedDTOClass = fetchedValueClass,
					updatedDTOClass = updatedValueClass,
					parentFetchedDTOClass = fetchedDTOClass,
					parentUpdatedDTOClass = updatedDTOClass,
					fieldName = "$fieldName (map value)",
					nonUpdatablePropertyRules = nonUpdatablePropertyRules,
					problemsCollector = problemsCollector
				)
			}
		}
		fetchedDTOFieldClass.isList() -> {
			if (!updatedDTOFieldClass.isList()) {
				problemsCollector.add(
					FieldProblem(
						kind = FieldProblemKind.FIELD_IS_NOT_LIST,
						fetchedDTOClass = fetchedDTOClass,
						updatedDTOClass = updatedDTOClass,
						fieldName = fieldName,
						expectedFieldClass = fetchedDTOFieldClass,
						actualFieldClass = updatedDTOFieldClass
					)
				)
				return
			} else {
				val fetchedValueClass = fetchedDTOField.extractListGenericArgumentClass()
				val updatedValueClass = updatedDTOField.extractListGenericArgumentClass()
				checkClassOrPrimitive(
					fetchedDTOClass = fetchedValueClass,
					updatedDTOClass = updatedValueClass,
					parentFetchedDTOClass = fetchedDTOClass,
					parentUpdatedDTOClass = updatedDTOClass,
					fieldName = "$fieldName (list value)",
					nonUpdatablePropertyRules = nonUpdatablePropertyRules,
					problemsCollector = problemsCollector
				)
			}
		}
		else -> {
			val updatedDTOFieldClassCorrected = if (updatedDTOFieldClass == NullableUpdatedValue::class.java) {
				(updatedDTOField.genericType as ParameterizedType).actualTypeArguments[0] as Class<*>
			} else {
				updatedDTOFieldClass
			}

			checkClassOrPrimitive(
				fetchedDTOClass = fetchedDTOFieldClass,
				updatedDTOClass = updatedDTOFieldClassCorrected,
				parentFetchedDTOClass = fetchedDTOClass,
				parentUpdatedDTOClass = updatedDTOClass,
				fieldName = fieldName,
				nonUpdatablePropertyRules = nonUpdatablePropertyRules,
				problemsCollector = problemsCollector
			)
		}
	}
}

private fun Field.extractListGenericArgumentClass(): Class<*> {
	val genericType = genericType
	return if (genericType is ParameterizedType) {
		// val foo: List<K, V>
		genericType.actualTypeArguments[0] as Class<*>
	} else {
		val genericSuperclass = type.genericSuperclass
		if (genericSuperclass is ParameterizedType) {
			// val foo: T; T : List<V>
			genericSuperclass.actualTypeArguments[0] as Class<*>
		} else {
			throw NotImplementedError("Implement if necessary")
		}
	}
}

private fun Field.extractMapGenericArgumentClasses(): Pair<Class<*>, Class<*>> {
	val genericType = genericType
	return if (genericType is ParameterizedType) {
		// val foo: Map<K, V>
		Pair(
			first = toClass(genericType.actualTypeArguments[0]),
			second = toClass(genericType.actualTypeArguments[1])
		)
	} else {
		val genericSuperclass = type.genericSuperclass
		if (genericSuperclass is ParameterizedType) {
			// val foo: T; T : Map<K, V>
			Pair(
				first = toClass(genericSuperclass.actualTypeArguments[0]),
				second = toClass(genericSuperclass.actualTypeArguments[1])
			)
		} else {
			throw NotImplementedError("Implement if necessary")
		}
	}
}

private tailrec fun toClass(type: Type?): Class<*> {
	return when (type) {
		is Class<*> -> type
		is ParameterizedType -> toClass(type.actualTypeArguments[0])
		else -> throw NotImplementedError("Type $type is not supported")
	}
}

private fun Class<*>.isMap() = Map::class.java.isAssignableFrom(this)

private fun Class<*>.isList() = List::class.java.isAssignableFrom(this)

private fun Class<*>.isJreType() = packageName.startsWith("java.")

internal enum class FieldProblemKind {
	FIELD_NOT_FOUND,
	FIELD_IS_NOT_MAP,
	FIELD_IS_NOT_LIST,
	PRIMITIVE_FIELDS_INCOMPATIBLE_TYPE,
	TYPE_MUST_NOT_BE_REUSED
}

internal data class FieldProblem(
	val kind: FieldProblemKind,
	val fetchedDTOClass: Class<*>,
	val updatedDTOClass: Class<*>,
	val fieldName: String,
	val expectedFieldClass: Class<*>?,
	val actualFieldClass: Class<*>?
)
