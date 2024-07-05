package com.ecwid.apiclient.v3.responsefields

/**
 * Response fields override
 *
 * [fields] - internal representation of object, if empty allow all fields
 *
 * Override response field parameter in data class.
 * Used in [ResponseFieldsBuilder]
 */
@Target(AnnotationTarget.CLASS, AnnotationTarget.FIELD, AnnotationTarget.PROPERTY)
annotation class ResponseFieldsOverride(
	val fields: Array<String>
)
