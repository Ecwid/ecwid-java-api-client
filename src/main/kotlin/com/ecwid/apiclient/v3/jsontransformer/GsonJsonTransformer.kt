package com.ecwid.apiclient.v3.jsontransformer

import com.ecwid.apiclient.v3.exception.JsonDeserializationException
import com.ecwid.apiclient.v3.jsontransformer.typeadapters.PolymorphicDeserializer
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonParseException

class GsonJsonTransformer(
		polymorphicTypes: Set<PolymorphicType<*>>
) : AbstractJsonTransformer(polymorphicTypes) {

	private val gson: Gson

	init {
		val gsonBuilder = GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss z")
		polymorphicTypes.forEach { polymorphType ->
			gsonBuilder.registerTypeAdapter(polymorphType.rootClass, PolymorphicDeserializer(polymorphType))
		}
		gson = gsonBuilder.create()
	}

	override fun serialize(src: Any?): String = gson.toJson(src)

	override fun <V> deserialize(json: String, clazz: Class<V>): V? {
		try {
			return gson.fromJson(json, clazz)
		} catch (e: JsonParseException) {
			throw JsonDeserializationException(e.message, e)
		}
	}

}

