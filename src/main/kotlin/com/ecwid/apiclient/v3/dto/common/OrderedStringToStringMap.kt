package com.ecwid.apiclient.v3.dto.common

import java.util.*

class OrderedStringToStringMap : TreeMap<String, String> {

	constructor() : super()

	constructor(map: Map<String, String>?) {
		if (map != null) {
			putAll(map)
		}
	}

}