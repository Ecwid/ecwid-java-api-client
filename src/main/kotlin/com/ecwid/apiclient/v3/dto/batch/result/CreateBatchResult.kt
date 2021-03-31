package com.ecwid.apiclient.v3.dto.batch.result

import com.ecwid.apiclient.v3.dto.common.ApiResultDTO

data class CreateBatchResult(
		val ticket: String = ""
) : ApiResultDTO
