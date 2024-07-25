package com.ecwid.apiclient.v3.dto.extrafield.result

import com.ecwid.apiclient.v3.dto.common.ApiResultDTO

data class CustomersConfigCreateResult(
	val createCount: Int = 0,
	val key: String = ""
) : ApiResultDTO
