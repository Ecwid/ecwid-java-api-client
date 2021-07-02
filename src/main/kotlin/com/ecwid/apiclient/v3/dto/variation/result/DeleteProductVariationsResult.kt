package com.ecwid.apiclient.v3.dto.variation.result

import com.ecwid.apiclient.v3.dto.common.ApiResultDTO

data class DeleteProductVariationsResult(
	val deleteCount: Int = 0
) : ApiResultDTO
