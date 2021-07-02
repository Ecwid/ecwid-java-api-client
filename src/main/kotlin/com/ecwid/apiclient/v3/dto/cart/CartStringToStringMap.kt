package com.ecwid.apiclient.v3.dto.cart

import java.util.*

class CartStringToStringMap : TreeMap<String, String> {

	@Suppress("unused")
	constructor() : super()

	@Suppress("unused")
	constructor(map: Map<String, String>?) {
		if (map != null) {
			putAll(map)
		}
	}
}
