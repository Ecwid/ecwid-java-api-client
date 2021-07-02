package com.ecwid.apiclient.v3.impl

import com.ecwid.apiclient.v3.ApiClientHelper
import com.ecwid.apiclient.v3.SalesChannelsApiClient
import com.ecwid.apiclient.v3.dto.saleschannels.request.GoogleShoppingFeedConfigGetRequest
import com.ecwid.apiclient.v3.dto.saleschannels.request.ShopzillaFeedConfigGetRequest
import com.ecwid.apiclient.v3.dto.saleschannels.request.YahooShoppingFeedConfigGetRequest
import com.ecwid.apiclient.v3.dto.saleschannels.request.YandexMarketFeedConfigGetRequest
import com.ecwid.apiclient.v3.dto.saleschannels.response.FetchedGoogleShoppingFeedConfig
import com.ecwid.apiclient.v3.dto.saleschannels.response.FetchedShopzillaFeedConfig
import com.ecwid.apiclient.v3.dto.saleschannels.response.FetchedYahooShoppingFeedConfig
import com.ecwid.apiclient.v3.dto.saleschannels.response.FetchedYandexMarketFeedConfig

class SalesChannelsApiClientImpl(
	private val apiClientHelper: ApiClientHelper
) : SalesChannelsApiClient {

	override fun getGoogleShoppingFeedConfig(request: GoogleShoppingFeedConfigGetRequest) =
		apiClientHelper.makeObjectResultRequest<FetchedGoogleShoppingFeedConfig>(request)

	override fun getShopzillaFeedConfig(request: ShopzillaFeedConfigGetRequest) =
		apiClientHelper.makeObjectResultRequest<FetchedShopzillaFeedConfig>(request)

	override fun getYahooShoppingFeedConfig(request: YahooShoppingFeedConfigGetRequest) =
		apiClientHelper.makeObjectResultRequest<FetchedYahooShoppingFeedConfig>(request)

	override fun getYandexMarketFeedConfig(request: YandexMarketFeedConfigGetRequest) =
		apiClientHelper.makeObjectResultRequest<FetchedYandexMarketFeedConfig>(request)
}
