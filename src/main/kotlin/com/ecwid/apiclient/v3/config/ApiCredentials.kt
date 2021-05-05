package com.ecwid.apiclient.v3.config

sealed class ApiCredentials

data class ApiStoreCredentials(
	val storeId: Int,
	val apiToken: String
) : ApiCredentials()

data class ApiAppCredentials(
	val clientId: String,
	val clientSecret: String
) : ApiCredentials()