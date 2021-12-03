package com.ecwid.apiclient.v3.dto.saleschannels.response

import com.ecwid.apiclient.v3.dto.common.ApiFetchedDTO
import com.ecwid.apiclient.v3.dto.common.ApiFetchedDTO.ModifyKind
import com.ecwid.apiclient.v3.dto.saleschannels.MarketplaceConfig
import com.ecwid.apiclient.v3.dto.saleschannels.ProductFeedInfo
import com.ecwid.apiclient.v3.dto.saleschannels.enums.SizeChart

data class FetchedYandexMarketDbsFeedConfig(
	val marketplaceConfig: MarketplaceConfig = MarketplaceConfig(),
	val store: Boolean = false,
	val manufacturerWarranty: Boolean = false,
	val pickup: Boolean = false,
	val adult: Boolean = false,
	val productFeedInfo: ProductFeedInfo = ProductFeedInfo(),
	val enableAutoDiscounts: Boolean = false,
	val sizeChart: SizeChart = SizeChart.RU
) : ApiFetchedDTO {

	override fun getModifyKind() = ModifyKind.ReadOnly
}
