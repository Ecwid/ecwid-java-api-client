package com.ecwid.apiclient.v3.dto.application.result

import com.ecwid.apiclient.v3.dto.common.ApiResultDTO

class ApplicationDeleteResult(
	val clientId: String,
	val namespace: String,
) : ApiResultDTO
