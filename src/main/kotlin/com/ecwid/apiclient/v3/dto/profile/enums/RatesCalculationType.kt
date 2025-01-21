package com.ecwid.apiclient.v3.dto.profile.enums

@Suppress("unused")
enum class RatesCalculationType {

	carrier_calculated,
	table,
	flat,
	app;

	override fun toString(): String {
		return asApiString()
	}

	fun asApiString(): String {
		return super.toString().replace("_", "-")
	}
}
