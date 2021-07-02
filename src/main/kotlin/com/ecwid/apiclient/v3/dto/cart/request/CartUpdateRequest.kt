package com.ecwid.apiclient.v3.dto.cart.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.httptransport.HttpBody
import com.ecwid.apiclient.v3.impl.RequestInfo

data class CartUpdateRequest(
	val cartId: String = "",
	val updatedCart: UpdatedCart = UpdatedCart()
) : ApiRequest {
	override fun toRequestInfo() = RequestInfo.createPutRequest(
		pathSegments = listOf(
			"carts",
			cartId
		),
		httpBody = HttpBody.JsonBody(
			obj = updatedCart
		)
	)
}
