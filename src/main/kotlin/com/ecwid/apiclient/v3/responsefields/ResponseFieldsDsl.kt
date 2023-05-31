package com.ecwid.apiclient.v3.responsefields

/**
 * [ResponseFields] builder
 */
fun newResponseFields(block: ResponseFieldsContext.() -> Unit): ResponseFields {
	val context = ResponseFieldsContextImpl("")
	block(context)
	return context.buildResponseFields()
}

interface ResponseFieldsContext {
	/**
	 * Add [name] field
	 */
	fun field(name: String)

	/**
	 * Add fields from [names] array
	 */
	fun fields(vararg names: String)

	/**
	 * Add composite [name] field
	 */
	fun field(name: String, block: ResponseFieldsContext.() -> Unit)
}

private data class ResponseFieldsContextImpl(
	private val name: String,
	private val children: MutableList<ResponseFieldsContextImpl> = mutableListOf()
) : ResponseFieldsContext {
	override fun field(name: String) {
		children.add(ResponseFieldsContextImpl(name))
	}

	override fun fields(vararg names: String) {
		names.forEach { name ->
			children.add(ResponseFieldsContextImpl(name))
		}
	}

	override fun field(name: String, block: ResponseFieldsContext.() -> Unit) {
		val fieldContext = ResponseFieldsContextImpl(name)
		block(fieldContext)
		children.add(fieldContext)
	}

	fun buildResponseFields(): ResponseFields {
		if (children.isEmpty()) {
			return ResponseFields.All
		}

		val fields = children.associate(ResponseFieldsContextImpl::buildPair)
		return ResponseFields(fields)
	}

	private fun buildPair(): Pair<String, ResponseFields.Field> {
		val field = if (children.isEmpty()) {
			ResponseFields.Field.All
		} else {
			val fields = children.associate(ResponseFieldsContextImpl::buildPair)
			ResponseFields.Field(fields)
		}

		return name to field
	}
}
