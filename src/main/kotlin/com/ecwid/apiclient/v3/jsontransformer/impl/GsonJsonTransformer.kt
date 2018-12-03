package com.ecwid.apiclient.v3.jsontransformer.impl

import com.ecwid.apiclient.v3.exception.JsonDeserializationException
import com.ecwid.apiclient.v3.jsontransformer.JsonTransformer
import com.google.gson.Gson
import com.google.gson.JsonParseException

internal class GsonJsonTransformer : JsonTransformer {

	private val gson = Gson()
			.newBuilder()
			.setDateFormat("yyyy-MM-dd HH:mm:ss z")
			.create()

	override fun serialize(src: Any?): String = gson.toJson(src)

	override fun <V> deserialize(json: String, clazz: Class<V>): V {
		try {
			return gson.fromJson(json, clazz)
		} catch (e: JsonParseException) {
			throw JsonDeserializationException(e.message, e)
		}
	}

}
