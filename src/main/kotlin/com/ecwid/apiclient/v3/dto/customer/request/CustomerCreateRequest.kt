package com.ecwid.apiclient.v3.dto.customer.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.httptransport.HttpBody
import com.ecwid.apiclient.v3.impl.RequestInfo

data class CustomerCreateRequest(
		val newCustomer: UpdatedCustomer = UpdatedCustomer()
) : ApiRequest {
	override fun toRequestInfo() = RequestInfo.createPostRequest(
			endpoint = "customers",
			httpBody = HttpBody.JsonBody(
					obj = newCustomer
			)
	)
}
