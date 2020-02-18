package com.ecwid.apiclient.v3.dto.cart.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.httptransport.HttpBody
import com.ecwid.apiclient.v3.impl.RequestInfo

data class ConvertCartToOrderRequest(
        var cartId: String = ""
) : ApiRequest {
    override fun toRequestInfo() = RequestInfo.createPostRequest(
            endpoint = "carts/$cartId/place",
            httpBody = HttpBody.EmptyBody
    )
}
