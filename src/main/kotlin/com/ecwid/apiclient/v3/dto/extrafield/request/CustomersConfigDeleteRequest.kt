package com.ecwid.apiclient.v3.dto.extrafield.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.impl.RequestInfo

data class CustomersConfigDeleteRequest(
	val extrafieldKey: String = "",
	val eraseFromStore: Boolean = false
) : ApiRequest {
	override fun toRequestInfo() = RequestInfo.createDeleteRequest(
		pathSegments = listOf(
			"store_extrafields",
			"customers",
			extrafieldKey,
			eraseFromStore.toString()
		),
		params = mapOf()
	)
}
