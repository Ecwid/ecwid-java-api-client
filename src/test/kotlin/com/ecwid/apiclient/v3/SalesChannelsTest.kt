package com.ecwid.apiclient.v3

import com.ecwid.apiclient.v3.dto.saleschannels.enums.Marketplace
import com.ecwid.apiclient.v3.dto.saleschannels.request.GoogleShoppingFeedConfigGetRequest
import com.ecwid.apiclient.v3.dto.saleschannels.request.UpdateProductFeedRequest
import com.ecwid.apiclient.v3.dto.saleschannels.request.UpdatedProductFeed
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import java.util.*

class SalesChannelsTest: BaseEntityTest() {

	@BeforeEach
	override fun beforeEach() {
		super.beforeEach()
	}

	@Disabled("Игнорируем до релиза ECWID-73774")
	@Test
	fun updateFeedInfo() {
		val lastGenerated = Date()
		val feedInfoToUpdate = UpdatedProductFeed(10, 10, lastGenerated)
		val result = apiClient.updateProductFeedInfo(UpdateProductFeedRequest(Marketplace.google_shopping, feedInfoToUpdate))
		assertEquals(1, result.updateCount)
		val realFeedInfo = apiClient.getGoogleShoppingFeedConfig(GoogleShoppingFeedConfigGetRequest()).productFeedInfo
		assertEquals(feedInfoToUpdate.productsIncluded, realFeedInfo?.productsIncluded)
		assertEquals(feedInfoToUpdate.productsProcessed, realFeedInfo?.productsProcessed)
		assertEquals(feedInfoToUpdate.lastGenerated, realFeedInfo?.lastGenerated)
	}
}
