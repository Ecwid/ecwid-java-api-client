package com.ecwid.api.v3.dto

import java.util.*

class OrderedStringToStringMap : TreeMap<String, String> {

	constructor() : super()

	constructor(map: Map<String, String>?) {
		if (map != null) {
			putAll(map)
		}
	}

}