package com.ecwid.apiclient.v3.dto.common

data class AsyncPictureData(
	val url: String = "",
	val width: Int = 0,
	val height: Int = 0,
	val alt: PictureAlt? = null,
	val externalId: String? = null,
)

data class PictureAlt(
	val main: String? = null,
	val translated: Map<String, String>? = null
)
