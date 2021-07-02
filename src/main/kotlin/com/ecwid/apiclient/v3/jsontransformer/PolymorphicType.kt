package com.ecwid.apiclient.v3.jsontransformer

data class PolymorphicType<T>(
	val rootClass: Class<T>,
	val jsonFieldName: String,
	val childClasses: Map<String, Class<out T>>
)
