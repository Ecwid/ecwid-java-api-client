package com.ecwid.apiclient.v3.dto.application.result

import com.ecwid.apiclient.v3.dto.common.ApiResultDTO
import java.util.*

data class ApplicationTokenInfoResult(
	val clientId: String = "",
	val permissions: List<String> = emptyList(),
	val created: Date = Date(),
	val expires: Date = Date(),
) : ApiResultDTO
