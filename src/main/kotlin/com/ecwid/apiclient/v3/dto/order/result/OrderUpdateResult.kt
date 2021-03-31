package com.ecwid.apiclient.v3.dto.order.result

import com.ecwid.apiclient.v3.dto.common.ApiResultDTO

data class OrderUpdateResult(
		val updateCount: Int = 0
) : ApiResultDTO
