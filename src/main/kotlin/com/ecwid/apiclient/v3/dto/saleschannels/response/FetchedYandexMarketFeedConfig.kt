package com.ecwid.apiclient.v3.dto.saleschannels.response

import com.ecwid.apiclient.v3.dto.saleschannels.MarketplaceConfig
import com.ecwid.apiclient.v3.dto.saleschannels.ProductFeedInfo

data class FetchedYandexMarketFeedConfig(
		var marketplaceConfig: MarketplaceConfig? = null,
		var store: Boolean? = null,
		val manufacturerWarranty: Boolean? = null,
		val pickup: Boolean? = null,
		val adult: Boolean? = null,
		val salesNotes: String? = null,
		var productFeedInfo: ProductFeedInfo? = null
)
