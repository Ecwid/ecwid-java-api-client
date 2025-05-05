package com.ecwid.apiclient.v3.config

abstract class RequestKind {
	abstract fun buildBaseEndpointPath(): String
	abstract fun buildHeaders(): Map<String, String>
}
