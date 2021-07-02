package com.ecwid.apiclient.v3.jsontransformer.gson.typeadapters

import com.ecwid.apiclient.v3.exception.JsonDeserializationException
import com.ecwid.apiclient.v3.jsontransformer.PolymorphicType
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class GsonPolymorphicDeserializer(
	private val polymorphicType: PolymorphicType<*>
) : JsonDeserializer<Any?> {
	override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Any? {
		val type = json.asJsonObject[polymorphicType.jsonFieldName]?.asString?.toLowerCase()
			?: throw JsonDeserializationException(
				"Deserialization polymorphic type ${typeOfT.typeName} failed. Type field ${polymorphicType.jsonFieldName} must be not null",
				null
			)

		val childClass = polymorphicType.childClasses[type]
			?: throw JsonDeserializationException(
				"Deserialization polymorphic type ${typeOfT.typeName} failed. Type field ${polymorphicType.jsonFieldName} has unknown value $type",
				null
			)

		return context.deserialize(json, childClass)
	}
}
