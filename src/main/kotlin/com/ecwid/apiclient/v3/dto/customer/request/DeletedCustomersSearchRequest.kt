package com.ecwid.apiclient.v3.dto.customer.request

import java.util.*

data class DeletedCustomersSearchRequest(
		var deletedFrom: Date? = null,
		var deletedTo: Date? = null,
		var offset: Int = 0,
		var limit: Int = 100
)