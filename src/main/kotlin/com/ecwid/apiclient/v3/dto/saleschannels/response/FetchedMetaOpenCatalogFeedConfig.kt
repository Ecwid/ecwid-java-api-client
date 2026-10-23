package com.ecwid.apiclient.v3.dto.saleschannels.response

import com.ecwid.apiclient.v3.dto.common.ApiFetchedDTO
import com.ecwid.apiclient.v3.dto.common.ApiFetchedDTO.ModifyKind
import com.ecwid.apiclient.v3.dto.saleschannels.MarketplaceConfig
import com.ecwid.apiclient.v3.dto.saleschannels.ProductFeedInfo

data class FetchedMetaOpenCatalogFeedConfig(
	val marketplaceConfig: MarketplaceConfig = MarketplaceConfig(),
	val productFeedInfo: ProductFeedInfo = ProductFeedInfo()
) : ApiFetchedDTO {

	override fun getModifyKind() = ModifyKind.ReadOnly
}
