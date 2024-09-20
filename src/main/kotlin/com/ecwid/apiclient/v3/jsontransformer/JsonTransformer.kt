package com.ecwid.apiclient.v3.jsontransformer

interface JsonTransformer {
	fun serialize(src: Any?, srcExt: Any? = null): String
	fun <V> deserialize(json: String, clazz: Class<V>): V
	fun <V> deserializeOrNull(json: String, clazz: Class<V>): V?
}
