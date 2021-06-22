package com.ecwid.apiclient.v3.dto.common

import java.util.*
import kotlin.collections.ArrayList

class OrderedStringToListStringMap : TreeMap<String, ArrayList<String>> {
	@Suppress("unused")
	constructor() : super()

	constructor(map: Map<String, List<String>>?) {
		if (map != null) {
			putAll(map.map { e -> e.key to ArrayList(e.value) })
		}
	}
}
