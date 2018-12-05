package com.ecwid.apiclient.v3.dto.order.request

import java.util.*

data class DeletedOrdersSearchRequest(
		var deletedFrom: Date? = null,
		var deletedTo: Date? = null,
		var offset: Int = 0,
		var limit: Int = 100
)