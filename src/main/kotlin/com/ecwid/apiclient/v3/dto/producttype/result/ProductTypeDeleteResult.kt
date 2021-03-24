package com.ecwid.apiclient.v3.dto.producttype.result

import com.ecwid.apiclient.v3.dto.common.ApiResultDTO

data class ProductTypeDeleteResult(
		val deleteCount: Int = 0
) : ApiResultDTO
