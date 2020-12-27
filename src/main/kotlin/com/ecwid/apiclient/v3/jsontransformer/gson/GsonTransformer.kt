package com.ecwid.apiclient.v3.jsontransformer.gson

import com.ecwid.apiclient.v3.exception.JsonDeserializationException
import com.ecwid.apiclient.v3.jsontransformer.JsonTransformer
import com.ecwid.apiclient.v3.jsontransformer.PolymorphicType
import com.ecwid.apiclient.v3.jsontransformer.gson.typeadapters.GsonPolymorphicDeserializer
import com.google.gson.*

class GsonTransformer(polymorphicTypes: List<PolymorphicType<*>>) : JsonTransformer {

	private val gson: Gson

	init {
		val gsonBuilder = GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss z")

		polymorphicTypes.forEach { polymorphType ->
			gsonBuilder.registerTypeAdapter(polymorphType.rootClass, GsonPolymorphicDeserializer(polymorphType))
		}

		gson = gsonBuilder.create()
	}

	override fun serialize(src: Any?, srcExt: Any?): String {
		return if (srcExt == null) {
			gson.toJson(src)
		} else {
			val target = gson.toJsonTree(src).asJsonObject
			target.mergeJsonObject(gson.toJsonTree(srcExt).asJsonObject)
			gson.toJson(target)
		}
	}

	override fun <V> deserialize(json: String, clazz: Class<V>): V? {
		try {
			return gson.fromJson(json, clazz)
		} catch (e: JsonParseException) {
			throw JsonDeserializationException(e.message, e)
		}
	}
}

private fun JsonObject.mergeJsonObject(from: JsonObject) {
	for ((key, element) in from.entrySet()) {
		when (element) {
			is JsonArray -> {
				when (val oldElement = this[key]) {
					is JsonArray -> oldElement.mergeJsonArray(element)
					else -> add(key, element)
				}
			}

			is JsonObject -> {
				when (val oldElement = this[key]) {
					is JsonObject -> oldElement.mergeJsonObject(element)
					else -> add(key, element)
				}
			}

			is JsonPrimitive -> {
				add(key, element)
			}
		}
	}
}

private fun JsonArray.mergeJsonArray(from: JsonArray) {
	for (index in 0 until minOf(size(), from.size())) {
		when (val element = from[index]) {
			is JsonArray -> {
				when (val oldElement = this[index]) {
					is JsonArray -> oldElement.mergeJsonArray(element)
					else -> this[index] = element
				}
			}

			is JsonObject -> {
				when (val oldElement = this[index]) {
					is JsonObject -> oldElement.mergeJsonObject(element)
					else -> this[index] = element
				}
			}

			is JsonPrimitive -> {
				this[index] = element
			}
		}
	}

	if (from.size() > size()) {
		// If the second array is longer than the first one, append the second's tail to the first.
		for (index in size() until from.size()) {
			add(from[index])
		}
	}
}
