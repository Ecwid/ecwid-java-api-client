package com.ecwid.apiclient.v3.jsontransformer

abstract class AbstractJsonTransformer(polymorphicTypes: Set<PolymorphicType<*>>) {
	abstract fun serialize(src: Any?): String
	abstract fun <V> deserialize(json: String, clazz: Class<V>): V?
}

interface JsonTransformerProvider {
	fun build(polymorphicTypes: Set<PolymorphicType<*>>): AbstractJsonTransformer
}

data class PolymorphicType<T>(val rootClass: Class<T>, val jsonFieldName: String, val childClasses: Set<Pair<Class<out T>, String>>)