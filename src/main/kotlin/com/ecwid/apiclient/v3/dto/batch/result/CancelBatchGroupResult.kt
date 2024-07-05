package com.ecwid.apiclient.v3.dto.batch.result

import com.ecwid.apiclient.v3.dto.common.ApiResultDTO

data class CancelBatchGroupResult(
	val updateCount: Int = 0
) : ApiResultDTO
