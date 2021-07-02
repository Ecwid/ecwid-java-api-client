package com.ecwid.apiclient.v3.dto.cart.result

import com.ecwid.apiclient.v3.dto.common.ApiResultDTO

data class CartUpdateResult(
	val updateCount: Int? = null
) : ApiResultDTO
