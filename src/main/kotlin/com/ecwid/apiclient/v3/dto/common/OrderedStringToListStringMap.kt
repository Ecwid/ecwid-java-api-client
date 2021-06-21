package com.ecwid.apiclient.v3.dto.common

import java.util.*

class OrderedStringToListStringMap : TreeMap<String, ArrayList<String>> {
	@Suppress("unused")
	constructor() : super()

	constructor(map: Map<String, ArrayList<String>>?) {
		if (map != null) {
			putAll(map)
		}
	}
}
