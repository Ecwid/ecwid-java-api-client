package com.ecwid.apiclient.v3.dto.saleschannels.request

import java.util.*

data class UpdatedProductFeed (
	var productsProcessed: Int? = null,
	val productsIncluded: Int? = null,
	val lastGenerated: Date? = null
)
