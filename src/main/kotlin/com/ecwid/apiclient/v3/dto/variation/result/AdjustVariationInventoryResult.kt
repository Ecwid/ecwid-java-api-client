package com.ecwid.apiclient.v3.dto.variation.result

import com.ecwid.apiclient.v3.dto.common.ApiResultDTO

data class AdjustVariationInventoryResult(
		val updateCount: Int = 0,
		val warning: String = ""
) : ApiResultDTO
