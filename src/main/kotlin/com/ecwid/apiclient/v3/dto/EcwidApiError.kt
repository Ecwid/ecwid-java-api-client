package com.ecwid.apiclient.v3.dto

data class EcwidApiError(
	var errorMessage: String? = null,
	var errorCode: String? = null
)