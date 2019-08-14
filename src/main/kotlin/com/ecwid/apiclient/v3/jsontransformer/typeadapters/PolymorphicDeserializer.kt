package com.ecwid.apiclient.v3.jsontransformer.typeadapters

import com.ecwid.apiclient.v3.jsontransformer.PolymorphicType
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class PolymorphicDeserializer(
		private val polymorphicType: PolymorphicType<*>
) : JsonDeserializer<Any?> {
	override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Any? {
		val type = json.asJsonObject[polymorphicType.jsonFieldName]?.asString?.toLowerCase() ?: return null

		polymorphicType.childClasses.forEach { (klass, jsonFieldValue) ->
			if (type == jsonFieldValue) {
				return context.deserialize(json, klass)
			}
		}

		return null
	}
}