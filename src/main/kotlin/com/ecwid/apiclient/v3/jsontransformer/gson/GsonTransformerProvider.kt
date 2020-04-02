package com.ecwid.apiclient.v3.jsontransformer.gson

import com.ecwid.apiclient.v3.jsontransformer.JsonTransformer
import com.ecwid.apiclient.v3.jsontransformer.JsonTransformerProvider
import com.ecwid.apiclient.v3.jsontransformer.PolymorphicType

class GsonTransformerProvider : JsonTransformerProvider {
	override fun build(polymorphicTypes: List<PolymorphicType<*>>): JsonTransformer {
		return GsonTransformer(polymorphicTypes)
	}
}
