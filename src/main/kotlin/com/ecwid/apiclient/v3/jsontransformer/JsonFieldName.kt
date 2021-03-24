package com.ecwid.apiclient.v3.jsontransformer

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD)
annotation class JsonFieldName(
	val fieldName: String
)
