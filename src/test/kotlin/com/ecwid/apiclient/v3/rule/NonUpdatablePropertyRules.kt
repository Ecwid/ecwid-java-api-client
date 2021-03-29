package com.ecwid.apiclient.v3.rule

import kotlin.reflect.KProperty1

val nonUpdatablePropertyRules: List<NonUpdatablePropertyRule<*, *>> = listOf(
)

sealed class NonUpdatablePropertyRule<T, R>(
	val property: KProperty1<T, R>
) {

	class Ignored<T, R> internal constructor(
		property: KProperty1<T, R>
	) : NonUpdatablePropertyRule<T, R>(property)

}
