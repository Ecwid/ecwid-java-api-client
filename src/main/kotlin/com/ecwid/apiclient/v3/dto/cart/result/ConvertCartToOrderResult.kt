package com.ecwid.apiclient.v3.dto.cart.result

import com.ecwid.apiclient.v3.dto.common.ApiResultDTO

data class ConvertCartToOrderResult(
		val id: String? = null,
		val orderNumber: Int? = null,
		val vendorOrderNumber: String? = null
) : ApiResultDTO
