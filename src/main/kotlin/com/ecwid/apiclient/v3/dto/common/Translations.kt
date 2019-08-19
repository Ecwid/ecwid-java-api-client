package com.ecwid.apiclient.v3.dto.common

typealias LocaleName = String

class LocalizedValueMap() : HashMap<LocaleName, String>() {
	constructor(vararg pairs: Pair<LocaleName, String>) : this() {
		this.putAll(pairs)
	}
}