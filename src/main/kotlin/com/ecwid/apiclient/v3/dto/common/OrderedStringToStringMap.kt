package com.ecwid.apiclient.v3.dto.common

import java.util.*

open class OrderedStringToStringMap : TreeMap<String, String> {

	@Suppress("unused")
	constructor() : super()

	constructor(map: Map<String, String>?) {
		if (map != null) {
			super.putAll(map)
		}
	}
}
