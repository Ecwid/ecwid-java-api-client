package ru.cdev.xnext.api.v3.model

import java.util.*

data class ProductFeedInfo(
		var productsProcessed: Int = 0,
		val productsIncluded: Int = 0,
		val lastGenerated: Date? = null,
		val nextGenerated: Date? = null
)

