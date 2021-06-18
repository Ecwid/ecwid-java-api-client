package com.ecwid.apiclient.v3.dto.common

import java.util.*

class OrderedStringToListStringMap : TreeMap<String, List<String>> {
	@Suppress("unused")
	constructor() : super()

	constructor(map: Map<String, List<String>>?) {
		if (map != null) {
			putAll(map)
		}
	}
}
