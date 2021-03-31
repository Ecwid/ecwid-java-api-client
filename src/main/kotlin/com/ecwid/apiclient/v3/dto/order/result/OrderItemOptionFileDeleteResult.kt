package com.ecwid.apiclient.v3.dto.order.result

import com.ecwid.apiclient.v3.dto.common.ApiResultDTO

data class OrderItemOptionFileDeleteResult(
		val deleteCount: Int = 0
) : ApiResultDTO
