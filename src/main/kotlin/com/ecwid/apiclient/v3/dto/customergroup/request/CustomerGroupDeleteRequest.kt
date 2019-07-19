package com.ecwid.apiclient.v3.dto.customergroup.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.impl.RequestInfo

data class CustomerGroupDeleteRequest(
		var customerGroupId: Int = 0
) : ApiRequest {
	override fun toRequestInfo() = RequestInfo.createDeleteRequest(
			endpoint = "customer_groups/$customerGroupId"
	)
}