package com.ecwid.apiclient.v3.dto.saleschannels.response

import com.ecwid.apiclient.v3.dto.saleschannels.MarketplaceConfig
import com.ecwid.apiclient.v3.dto.saleschannels.ProductFeedInfo

data class FetchedYandexMarketFeedConfig(
		val marketplaceConfig: MarketplaceConfig = MarketplaceConfig(),
		val store: Boolean = false,
		val manufacturerWarranty: Boolean = false,
		val pickup: Boolean = false,
		val adult: Boolean = false,
		val salesNotes: String = "",
		val productFeedInfo: ProductFeedInfo = ProductFeedInfo()
)
