package com.ecwid.apiclient.v3.dto.common

/**
 * Wrapper for request values, which can be sent as explicit nulls in resulting json.
 */
data class NullableUpdatedValue<T : Any>(
	val value: T?,
)
