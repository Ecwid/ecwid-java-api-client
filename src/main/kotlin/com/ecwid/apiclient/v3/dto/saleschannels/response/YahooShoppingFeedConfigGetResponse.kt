package com.ecwid.apiclient.v3.dto.saleschannels.response

import com.ecwid.apiclient.v3.dto.saleschannels.enums.MarketplaceCondition
import ru.cdev.xnext.api.v3.model.MarketplaceConfig
import ru.cdev.xnext.api.v3.model.ProductFeedInfo

data class YahooShoppingFeedConfigGetResponse (
		var marketplaceConfig: MarketplaceConfig? = null,
		var condition: MarketplaceCondition? = MarketplaceCondition.NEW,
		var productFeedInfo: ProductFeedInfo? = null
)
