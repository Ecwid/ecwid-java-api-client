package com.ecwid.apiclient.v3.jsontransformer.gson

import com.ecwid.apiclient.v3.jsontransformer.JsonFieldName
import com.google.gson.FieldNamingStrategy
import java.lang.reflect.Field

class GsonFieldNamingStrategy : FieldNamingStrategy {

	override fun translateName(f: Field): String {
		val annotation = f.getAnnotation(JsonFieldName::class.java)
		return annotation?.fieldName ?: f.name
	}
}
