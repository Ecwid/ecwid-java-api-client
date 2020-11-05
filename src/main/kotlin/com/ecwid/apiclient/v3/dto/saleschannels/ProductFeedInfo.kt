package com.ecwid.apiclient.v3.dto.saleschannels

import java.util.*

data class ProductFeedInfo(
		var productsProcessed: Int? = null,
		val productsIncluded: Int? = null,
		val lastGenerated: Date? = null,
		val nextGenerated: Date? = null
)

