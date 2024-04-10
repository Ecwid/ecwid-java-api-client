package com.ecwid.apiclient.v3.dto.application.result

import com.ecwid.apiclient.v3.dto.common.ApiResultDTO
import java.util.*

data class ApplicationTokenInfoResult(
	val clientId: String? = null,
	val permissions: List<String>? = null,
	val created: Date? = null,
	val expires: Date? = null,
) : ApiResultDTO
