package com.ecwid.apiclient.v3.config

const val DEFAULT_HTTPS_PORT = 443

data class ApiServerDomain(
	val host: String = "app.ecwid.com",
	val securePort: Int = DEFAULT_HTTPS_PORT
)
