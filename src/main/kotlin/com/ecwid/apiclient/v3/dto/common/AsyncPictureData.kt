package com.ecwid.apiclient.v3.dto.common

data class AsyncPictureData(
	val url: String = "",
	val width: Int = 0,
	val height: Int = 0,
	val alt: PictureAlt? = null
)

data class PictureAlt (
	var main: String? = null,
	var translated: Map<String, String>? = null
)
