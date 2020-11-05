package com.ecwid.apiclient.v3.dto.saleschannels.response

import ru.cdev.xnext.api.v3.model.MarketplaceConfig
import ru.cdev.xnext.api.v3.model.ProductFeedInfo

data class YandexMarketFeedConfigGetResponse(
		var marketplaceConfig: MarketplaceConfig? = null,
		var store: Boolean = false,
		val manufacturerWarranty: Boolean = false,
		val pickup: Boolean = false,
		val adult: Boolean = false,
		val salesNotes: String? = null,
		var productFeedInfo: ProductFeedInfo? = null
)
