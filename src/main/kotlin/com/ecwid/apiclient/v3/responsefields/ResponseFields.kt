package com.ecwid.apiclient.v3.responsefields

data class ResponseFields(
	internal val fields: Map<String, Field>
) {
	private val httpParameter = lazy { toHttpParameter(fields) }

	fun toHttpParameter(): String {
		if (isAll()) {
			return ""
		}

		return httpParameter.value
	}

	override fun toString(): String {
		return toHttpParameter()
	}

	operator fun plus(other: ResponseFields): ResponseFields {
		return if (this.isAll() || other.isAll()) {
			// merge with All always returns All
			All
		} else {
			val newFields = mergeFieldMaps(this.fields, other.fields)
			ResponseFields(newFields)
		}
	}

	fun isAll(): Boolean {
		return fields.isEmpty()
	}

	data class Field(
		val children: Map<String, Field>
	) {
		companion object {
			internal val All = Field(emptyMap())
		}
	}

	companion object {
		// All fields allowed const
		val All = ResponseFields(emptyMap())
	}
}

private fun toHttpParameter(map: Map<String, ResponseFields.Field>): String {
	return map.entries.joinToString(",") { (name, field) ->
		if (field.children.isEmpty()) {
			name
		} else {
			val fields = toHttpParameter(field.children)
			"$name($fields)"
		}
	}
}

private fun mergeFieldMaps(
	left: Map<String, ResponseFields.Field>,
	right: Map<String, ResponseFields.Field>
): Map<String, ResponseFields.Field> {
	val newMap = left.toMutableMap()
	right.forEach { (name, value) ->
		newMap.merge(name, value, ::mergeFields)
	}
	return newMap
}

private fun mergeFields(left: ResponseFields.Field, right: ResponseFields.Field): ResponseFields.Field {
	if (left.children.isEmpty() || right.children.isEmpty()) {
		return ResponseFields.Field.All
	}

	val newMap = left.children.toMutableMap()
	right.children.entries.forEach { (name, field) ->
		newMap.merge(name, field, ::mergeFields)
	}

	return ResponseFields.Field(newMap)
}
