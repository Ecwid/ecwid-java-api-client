package com.ecwid.apiclient.v3.util

import java.lang.reflect.Field
import java.lang.reflect.ParameterizedType

internal fun checkFetchedUpdatedDTOsFields(
	fetchedDTOClass: Class<*>,
	updatedDTOClass: Class<*>
): List<FieldProblem> {
	val problemsCollector = mutableListOf<FieldProblem>()
	checkClass(
		fetchedDTOClass = fetchedDTOClass,
		updatedDTOClass = updatedDTOClass,
		problemsCollector = problemsCollector
	)
	return problemsCollector.toList()
}

private fun checkClass(
	fetchedDTOClass: Class<*>,
	updatedDTOClass: Class<*>,
	problemsCollector: MutableList<FieldProblem>
) {
	fetchedDTOClass.declaredFields
		.forEach { fetchedDTOField ->
			val updatedDTOField = updatedDTOClass.declaredFields
				.find { declaredField ->
					declaredField.name == fetchedDTOField.name
				}
			if (updatedDTOField == null) {
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
				checkField(
					fetchedDTOClass = fetchedDTOClass,
					updatedDTOClass = updatedDTOClass,
					fetchedDTOField = fetchedDTOField,
					updatedDTOField = updatedDTOField,
					fieldName = fetchedDTOField.name,
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
	problemsCollector: MutableList<FieldProblem>
) {
	if (fetchedDTOClass.isJreType() || fetchedDTOClass.isEnum) {
		// Primitive type
		if (fetchedDTOClass != updatedDTOClass) {
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
			checkClass(fetchedDTOClass, updatedDTOClass, problemsCollector)
		}
	}
}

private fun checkField(
	fetchedDTOClass: Class<*>,
	updatedDTOClass: Class<*>,
	fetchedDTOField: Field,
	updatedDTOField: Field,
	fieldName: String,
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
					problemsCollector = problemsCollector
				)
				checkClassOrPrimitive(
					fetchedDTOClass = fetchedValueClass,
					updatedDTOClass = updatedValueClass,
					parentFetchedDTOClass = fetchedDTOClass,
					parentUpdatedDTOClass = updatedDTOClass,
					fieldName = "$fieldName (map value)",
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
					problemsCollector = problemsCollector
				)
			}
		}
		else -> {
			checkClassOrPrimitive(
				fetchedDTOClass = fetchedDTOFieldClass,
				updatedDTOClass = updatedDTOFieldClass,
				parentFetchedDTOClass = fetchedDTOClass,
				parentUpdatedDTOClass = updatedDTOClass,
				fieldName = fieldName,
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
			first = genericType.actualTypeArguments[0] as Class<*>,
			second = genericType.actualTypeArguments[1] as Class<*>
		)
	} else {
		val genericSuperclass = type.genericSuperclass
		if (genericSuperclass is ParameterizedType) {
			// val foo: T; T : Map<K, V>
			Pair(
				first = genericSuperclass.actualTypeArguments[0] as Class<*>,
				second = genericSuperclass.actualTypeArguments[1] as Class<*>
			)
		} else {
			throw NotImplementedError("Implement if necessary")
		}
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
