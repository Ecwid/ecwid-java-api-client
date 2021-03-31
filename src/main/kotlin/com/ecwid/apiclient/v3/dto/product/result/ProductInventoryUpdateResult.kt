package com.ecwid.apiclient.v3.dto.product.result

import com.ecwid.apiclient.v3.dto.common.ApiResultDTO

data class ProductInventoryUpdateResult(
		val updateCount: Int = 0,
		val warning: String? = null
) : ApiResultDTO
