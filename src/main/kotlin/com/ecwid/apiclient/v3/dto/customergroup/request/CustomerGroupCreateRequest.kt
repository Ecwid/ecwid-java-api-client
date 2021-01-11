package com.ecwid.apiclient.v3.dto.customergroup.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.httptransport.HttpBody
import com.ecwid.apiclient.v3.impl.RequestInfo

data class CustomerGroupCreateRequest(
		val newCustomerGroup: UpdatedCustomerGroup = UpdatedCustomerGroup()
) : ApiRequest {
	override fun toRequestInfo() = RequestInfo.createPostRequest(
			endpoint = "customer_groups",
			httpBody = HttpBody.JsonBody(
					obj = newCustomerGroup
			)
	)
}
