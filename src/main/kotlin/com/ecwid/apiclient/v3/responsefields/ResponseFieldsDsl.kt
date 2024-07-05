package com.ecwid.apiclient.v3.responsefields

/**
 * [ResponseFields] builder
 */
fun newResponseFields(block: ResponseFieldsContext.() -> Unit): ResponseFields {
	val context = ChildrenFieldContextImpl("")
	block(context)
	return context.buildResponseFields()
}

interface ResponseFieldsContext {
	/**
	 * Add [name] field
	 */
	fun field(name: String)

	/**
	 * Add [name] field with prepared [responseFields]
	 */
	fun field(name: String, responseFields: ResponseFields)

	/**
	 * Add fields from [names] array
	 */
	fun fields(vararg names: String)

	/**
	 * Add composite [name] field
	 */
	fun field(name: String, block: ResponseFieldsContext.() -> Unit)
}

private sealed class FieldContextImpl {
	abstract fun build(): Pair<String, ResponseFields.Field>
}

private data class FiniteFieldContextImpl(val name: String, val responseFields: ResponseFields) : FieldContextImpl() {
	override fun build(): Pair<String, ResponseFields.Field> {
		val field = if (responseFields.isAll()) {
			ResponseFields.Field.All
		} else {
			ResponseFields.Field(responseFields.fields)
		}

		return name to field
	}
}

private data class ChildrenFieldContextImpl(
	private val name: String,
	private val children: MutableList<FieldContextImpl> = mutableListOf()
) : FieldContextImpl(), ResponseFieldsContext {
	override fun field(name: String) {
		children.add(FiniteFieldContextImpl(name, ResponseFields.All))
	}

	override fun field(name: String, responseFields: ResponseFields) {
		children.add(FiniteFieldContextImpl(name, responseFields))
	}

	override fun field(name: String, block: ResponseFieldsContext.() -> Unit) {
		val fieldContext = ChildrenFieldContextImpl(name)
		block(fieldContext)
		children.add(fieldContext)
	}

	override fun fields(vararg names: String) {
		names.forEach { name ->
			children.add(FiniteFieldContextImpl(name, ResponseFields.All))
		}
	}

	fun buildResponseFields(): ResponseFields {
		if (children.isEmpty()) {
			return ResponseFields.All
		}

		val fields = children.associate(FieldContextImpl::build)
		return ResponseFields(fields)
	}

	override fun build(): Pair<String, ResponseFields.Field> {
		val field = if (children.isEmpty()) {
			ResponseFields.Field.All
		} else {
			val fields = children.associate(FieldContextImpl::build)
			ResponseFields.Field(fields)
		}

		return name to field
	}
}
