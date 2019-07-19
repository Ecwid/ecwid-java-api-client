package com.ecwid.apiclient.v3.dto

import com.ecwid.apiclient.v3.impl.RequestInfo

interface ApiRequest {
	fun toRequestInfo(): RequestInfo
}
