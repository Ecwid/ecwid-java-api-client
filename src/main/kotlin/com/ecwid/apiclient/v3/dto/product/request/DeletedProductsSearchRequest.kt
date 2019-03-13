package com.ecwid.apiclient.v3.dto.product.request

import java.util.*

data class DeletedProductsSearchRequest(
		val deletedFrom: Date? = null,
		val deletedTo: Date? = null,
		val offset: Int = 0,
		val limit: Int = 100
)