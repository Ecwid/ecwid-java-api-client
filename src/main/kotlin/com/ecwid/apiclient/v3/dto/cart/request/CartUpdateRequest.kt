package com.ecwid.apiclient.v3.dto.cart.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.httptransport.HttpBody
import com.ecwid.apiclient.v3.impl.RequestInfo

data class CartUpdateRequest(
		var cartId: String = "",
		var updatedCart: UpdatedCart = UpdatedCart()
) : ApiRequest {
	override fun toRequestInfo() = RequestInfo.createPutRequest(
			endpoint = "carts/$cartId",
			httpBody = HttpBody.JsonBody(
					obj = updatedCart
			)
	)
}
