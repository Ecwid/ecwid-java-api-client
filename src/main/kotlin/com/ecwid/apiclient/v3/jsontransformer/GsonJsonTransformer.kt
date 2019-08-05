package com.ecwid.apiclient.v3.jsontransformer

import com.ecwid.apiclient.v3.exception.JsonDeserializationException
import com.google.gson.GsonBuilder
import com.google.gson.JsonParseException

class GsonJsonTransformer {

	private val gson = GsonBuilder()
			.setDateFormat("yyyy-MM-dd HH:mm:ss z")
			.create()

	fun serialize(src: Any?): String = gson.toJson(src)

	fun <V> deserialize(json: String, clazz: Class<V>): V? {
		try {
			return gson.fromJson(json, clazz)
		} catch (e: JsonParseException) {
			throw JsonDeserializationException(e.message, e)
		}
	}

}

