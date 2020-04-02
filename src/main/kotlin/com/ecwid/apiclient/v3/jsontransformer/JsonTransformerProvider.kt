package com.ecwid.apiclient.v3.jsontransformer

interface JsonTransformerProvider {
	fun build(polymorphicTypes: List<PolymorphicType<*>>): JsonTransformer
}
