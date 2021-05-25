package com.ecwid.apiclient.v3.util

import java.lang.reflect.Modifier
import java.util.*
import kotlin.reflect.KProperty1
import kotlin.reflect.jvm.kotlinProperty

internal fun checkDTOFieldsEmptiness(
	values: Collection<Any?>,
	ignoredFields: List<KProperty1<*, *>>
): Set<FieldEmptinessProblem> {
	val problemsCollector = mutableSetOf<FieldEmptinessProblem>()
	values.forEach { value ->
		checkFieldEmptiness(
			value = value,
			fieldName = "",
			declaringClass = value?.javaClass,
			problemsCollector = problemsCollector,
			ignoredProperties = ignoredFields
		)
	}
	return problemsCollector.toSet()
}

private fun checkFieldEmptiness(
	value: Any?,
	fieldName: String,
	declaringClass: Class<*>?,
	problemsCollector: MutableSet<FieldEmptinessProblem>,
	ignoredProperties: List<KProperty1<*, *>>
) {
	when {
		value == null -> {
			problemsCollector.add(FieldEmptinessProblem(
				kind = FieldEmptinessProblemKind.NULL_VALUE,
				fieldClass = declaringClass,
				fieldName = fieldName
			))
		}
		value.javaClass.kotlin.javaPrimitiveType != null -> {
			if (isPrimitiveAndHasDefaultValue(value)) {
				problemsCollector.add(FieldEmptinessProblem(
					kind = FieldEmptinessProblemKind.DEFAULT_VALUE,
					fieldClass = declaringClass,
					fieldName = fieldName
				))
			} else {
				// That's fine
			}
		}
		value is String -> {
			if (value == "") {
				problemsCollector.add(FieldEmptinessProblem(
					kind = FieldEmptinessProblemKind.DEFAULT_VALUE,
					fieldClass = declaringClass,
					fieldName = fieldName
				))
			} else {
				// Any non-empty string is fine
			}
		}
		value is Date -> {
			if (value == Date(0)) {
				problemsCollector.add(FieldEmptinessProblem(
					kind = FieldEmptinessProblemKind.DEFAULT_VALUE,
					fieldClass = declaringClass,
					fieldName = fieldName
				))
			} else {
				// Any nonnull dates and dates not equal to epoch timestamp are fine
			}
		}
		value is Enum<*> -> {
			// Any nonnull enum value is fine
		}
		value is List<*> -> {
			value.filterNotNull()
				.apply {
					if (isEmpty()) {
						problemsCollector.add(FieldEmptinessProblem(
							kind = FieldEmptinessProblemKind.EMPTY_LIST,
							fieldClass = declaringClass,
							fieldName = fieldName
						))
					}
				}
				.forEach { listValue ->
					checkFieldEmptiness(
						value = listValue,
						fieldName = "$fieldName (list value)",
						declaringClass = declaringClass,
						problemsCollector = problemsCollector,
						ignoredProperties = ignoredProperties
					)
				}
		}
		value is Map<*, *> -> {
			value.entries
				.mapNotNull { (k, v) ->
					if (k != null && v != null) {
						k to v
					} else {
						null
					}
				}
				.apply {
					if (isEmpty()) {
						problemsCollector.add(FieldEmptinessProblem(
							kind = FieldEmptinessProblemKind.EMPTY_MAP,
							fieldClass = declaringClass,
							fieldName = fieldName
						))
					}
				}
				.forEach { (k, v) ->
					checkFieldEmptiness(
						value = k,
						fieldName = "$fieldName (map key)",
						declaringClass = declaringClass,
						problemsCollector = problemsCollector,
						ignoredProperties = ignoredProperties
					)
					checkFieldEmptiness(
						value = v,
						fieldName = "$fieldName (map value)",
						declaringClass = declaringClass,
						problemsCollector = problemsCollector,
						ignoredProperties = ignoredProperties
					)
				}
		}
		else -> {
			value.javaClass.declaredFields
				.forEach { innerField ->
					if (!Modifier.isStatic(innerField.modifiers)) {
						innerField.trySetAccessible()
						val fieldValue = innerField.get(value)
						val isCompanion = fieldValue?.javaClass?.kotlin?.isCompanion ?: false
						val isIgnored = innerField.kotlinProperty in ignoredProperties
						if (!isCompanion && !isIgnored) {
							checkFieldEmptiness(
								value = fieldValue,
								fieldName = innerField.name,
								declaringClass = value.javaClass,
								problemsCollector = problemsCollector,
								ignoredProperties = ignoredProperties
							)
						}
					}
				}
		}
	}
}

private fun isPrimitiveAndHasDefaultValue(value: Any) = when (value) {
	is Byte -> value == 0.toByte()
	is Boolean -> value == false
	is Short -> value == 0.toShort()
	is Int -> value == 0
	is Long -> value == 0L
	is Float -> value == 0.0f
	is Double -> value == 0.0
	else -> false
}

internal enum class FieldEmptinessProblemKind {
	NULL_VALUE,
	DEFAULT_VALUE,
	EMPTY_LIST,
	EMPTY_MAP
}

internal data class FieldEmptinessProblem(
	val kind: FieldEmptinessProblemKind,
	val fieldClass: Class<*>?,
	val fieldName: String?
)
