package com.ecwid.apiclient.v3.jsontransformer.gson.typeadapters

import com.ecwid.apiclient.v3.impl.ParsedResponseWithExt
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class ParsedResponseWithExtDeserializer : JsonDeserializer<ParsedResponseWithExt<*, *>> {

	override fun deserialize(
		json: JsonElement,
		typeOfT: Type,
		context: JsonDeserializationContext
	): ParsedResponseWithExt<*, *> {
		val actualTypeArguments = (typeOfT as ParameterizedType).actualTypeArguments

		val baseResult: Any? = context.deserialize(json, actualTypeArguments[0])
		val extResult: Any? = context.deserialize(json, actualTypeArguments[1])

		return ParsedResponseWithExt(
			baseResult = baseResult,
			extResult = extResult,
		)
	}

}
