package com.ecwid.apiclient.v3.jsontransformer

interface JsonTransformer {
	fun serialize(src: Any?): String
	fun <V> deserialize(json: String, clazz: Class<V>): V?
}

