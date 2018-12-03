package com.ecwid.api.v3.jsontransformer

internal interface JsonTransformer {
	fun serialize(src: Any?): String
	fun <V> deserialize(json: String, clazz: Class<V>): V?
}