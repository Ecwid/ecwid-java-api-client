package com.ecwid.apiclient.v3.dto.customer.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.impl.RequestInfo
import java.util.*

data class DeletedCustomersSearchRequest(
		val deletedFrom: Date? = null,
		val deletedTo: Date? = null,
		val offset: Int = 0,
		val limit: Int = 100
) : ApiRequest {
	override fun toRequestInfo() = RequestInfo.createGetRequest(
			endpoint = "customers/deleted",
			params = toParams()
	)

	private fun toParams(): Map<String, String> {
		val request = this
		return mutableMapOf<String, String>().apply {
			request.deletedFrom?.let { put("from_date", (it.time / 1000).toString()) }
			request.deletedTo?.let { put("to_date", (it.time / 1000).toString()) }
			put("offset", request.offset.toString())
			put("limit", request.limit.toString())
		}.toMap()
	}
}
