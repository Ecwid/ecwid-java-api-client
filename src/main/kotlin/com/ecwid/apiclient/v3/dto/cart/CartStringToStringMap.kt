package com.ecwid.apiclient.v3.dto.cart

import java.util.*

class CartStringToStringMap : TreeMap<String, String> {

	constructor() : super()

	constructor(map: Map<String, String>?) {
		if (map != null) {
			putAll(map)
		}
	}

}
