package com.ecwid.apiclient.v3.dto.customer.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.httptransport.HttpBody
import com.ecwid.apiclient.v3.impl.RequestInfo

data class CustomerUpdateRequest(
		var customerId: Int = 0,
		var updatedCustomer: UpdatedCustomer = UpdatedCustomer()
) : ApiRequest {
	override fun toRequestInfo() = RequestInfo.createPutRequest(
			endpoint = "customers/$customerId",
			httpBody = HttpBody.JsonBody(
					obj = updatedCustomer
			)
	)
}