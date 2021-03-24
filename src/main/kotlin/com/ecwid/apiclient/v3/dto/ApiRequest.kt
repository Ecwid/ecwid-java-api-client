package com.ecwid.apiclient.v3.dto

import com.ecwid.apiclient.v3.dto.common.ApiRequestDTO
import com.ecwid.apiclient.v3.impl.RequestInfo

interface ApiRequest : ApiRequestDTO {
	fun toRequestInfo(): RequestInfo
}
