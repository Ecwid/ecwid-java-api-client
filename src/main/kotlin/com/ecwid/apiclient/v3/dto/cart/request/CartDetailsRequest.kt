package com.ecwid.apiclient.v3.dto.cart.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.impl.RequestInfo

data class CartDetailsRequest(
		var cartId: String = ""
) : ApiRequest {
	override fun toRequestInfo() = RequestInfo.createGetRequest(
			endpoint = "carts/$cartId"
	)
}
