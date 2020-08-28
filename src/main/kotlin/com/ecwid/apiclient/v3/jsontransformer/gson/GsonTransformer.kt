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
	for (entry in from.entrySet()) {
		add(entry.key, entry.value)
	}
}

