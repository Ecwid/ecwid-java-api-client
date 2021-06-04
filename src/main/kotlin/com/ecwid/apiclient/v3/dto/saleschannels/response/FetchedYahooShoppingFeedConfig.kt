package com.ecwid.apiclient.v3.dto.saleschannels.response

import com.ecwid.apiclient.v3.dto.common.ApiFetchedDTO
import com.ecwid.apiclient.v3.dto.common.ApiFetchedDTO.ModifyKind
import com.ecwid.apiclient.v3.dto.saleschannels.MarketplaceConfig
import com.ecwid.apiclient.v3.dto.saleschannels.ProductFeedInfo
import com.ecwid.apiclient.v3.dto.saleschannels.enums.MarketplaceCondition

data class FetchedYahooShoppingFeedConfig(
		val marketplaceConfig: MarketplaceConfig = MarketplaceConfig(),
		val condition: MarketplaceCondition = MarketplaceCondition.NEW,
		val productFeedInfo: ProductFeedInfo = ProductFeedInfo()
) : ApiFetchedDTO {

	override fun getModifyKind() = ModifyKind.ReadOnly

}
