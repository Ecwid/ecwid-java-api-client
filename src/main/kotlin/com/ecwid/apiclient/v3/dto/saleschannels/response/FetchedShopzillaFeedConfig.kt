package com.ecwid.apiclient.v3.dto.saleschannels.response

import com.ecwid.apiclient.v3.dto.saleschannels.MarketplaceConfig
import com.ecwid.apiclient.v3.dto.saleschannels.ProductFeedInfo
import com.ecwid.apiclient.v3.dto.saleschannels.enums.MarketplaceCondition

data class FetchedShopzillaFeedConfig (
		var marketplaceConfig: MarketplaceConfig = MarketplaceConfig(),
		var condition: MarketplaceCondition = MarketplaceCondition.NEW,
		var productFeedInfo: ProductFeedInfo = ProductFeedInfo()
)
