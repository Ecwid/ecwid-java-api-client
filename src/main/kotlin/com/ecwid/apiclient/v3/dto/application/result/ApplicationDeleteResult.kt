package com.ecwid.apiclient.v3.dto.application.result

import com.ecwid.apiclient.v3.dto.common.ApiResultDTO

data class ApplicationDeleteResult(
	val deleteCount: Int = 0
) : ApiResultDTO
