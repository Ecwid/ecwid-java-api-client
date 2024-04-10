package com.ecwid.apiclient.v3.dto.application.result

import com.ecwid.apiclient.v3.dto.common.ApiResultDTO
import java.util.*

data class ApplicationTokenInfoResult(
	val clientId: String? = null,
	var permissions: List<String>? = null,
	var created: Date? = null,
	var expires: Date? = null,
) : ApiResultDTO
