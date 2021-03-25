package com.ecwid.apiclient.v3.dto.order.result

import com.ecwid.apiclient.v3.dto.common.ApiResultDTO

data class OrderCreateResult(
		val id: Int = 0,
		val orderId: String = ""
) : ApiResultDTO
