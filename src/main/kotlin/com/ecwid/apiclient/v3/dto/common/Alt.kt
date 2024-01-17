package com.ecwid.apiclient.v3.dto.common

data class FetchedAlt(
	val main: String? = null,
	val translated: LocalizedValueMap? = null,
) {
	fun toUpdated(): UpdatedAlt{
		return UpdatedAlt(
			main = main,
			translated = translated
		)
	}
}

data class UpdatedAlt(
	val main: String? = null,
	val translated: LocalizedValueMap? = null,
)
