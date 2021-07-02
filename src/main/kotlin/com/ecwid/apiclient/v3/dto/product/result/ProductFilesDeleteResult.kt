package com.ecwid.apiclient.v3.dto.product.result

import com.ecwid.apiclient.v3.dto.common.ApiResultDTO

data class ProductFilesDeleteResult(
	val deleteCount: Int = 0
) : ApiResultDTO
