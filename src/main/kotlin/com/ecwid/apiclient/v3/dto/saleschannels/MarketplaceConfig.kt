package ru.cdev.xnext.api.v3.model

data class MarketplaceConfig(
		var enabled: Boolean = false,
		var taxonomyId: String? = null,
		val firstInit: Boolean = false
)

