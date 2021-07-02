package com.ecwid.apiclient.v3.dto.customergroup.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.httptransport.HttpBody
import com.ecwid.apiclient.v3.impl.RequestInfo

data class CustomerGroupUpdateRequest(
	val customerGroupId: Int = 0,
	val updatedCustomerGroup: UpdatedCustomerGroup = UpdatedCustomerGroup()
) : ApiRequest {
	override fun toRequestInfo() = RequestInfo.createPutRequest(
		pathSegments = listOf(
			"customer_groups",
			"$customerGroupId"
		),
		params = mapOf(),
		httpBody = HttpBody.JsonBody(
			obj = updatedCustomerGroup
		)
	)
}
