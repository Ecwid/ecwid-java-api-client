package com.ecwid.apiclient.v3.impl

import com.ecwid.apiclient.v3.ApiClientHelper
import com.ecwid.apiclient.v3.SalesChannelsApiClient
import com.ecwid.apiclient.v3.dto.saleschannels.request.GoogleShoppingFeedConfigGetRequest
import com.ecwid.apiclient.v3.dto.saleschannels.request.ShopzillaFeedConfigGetRequest
import com.ecwid.apiclient.v3.dto.saleschannels.request.YahooShoppingFeedConfigGetRequest
import com.ecwid.apiclient.v3.dto.saleschannels.request.YandexMarketFeedConfigGetRequest
import com.ecwid.apiclient.v3.dto.saleschannels.response.GoogleShoppingFeedConfigGetResponse
import com.ecwid.apiclient.v3.dto.saleschannels.response.ShopzillaFeedConfigGetResponse
import com.ecwid.apiclient.v3.dto.saleschannels.response.YahooShoppingFeedConfigGetResponse
import com.ecwid.apiclient.v3.dto.saleschannels.response.YandexMarketFeedConfigGetResponse

class SalesChannelsApiClientImpl(
		private val apiClientHelper: ApiClientHelper
) : SalesChannelsApiClient {

	override fun getGoogleShoppingFeedConfig(request: GoogleShoppingFeedConfigGetRequest) = apiClientHelper.makeObjectResultRequest<GoogleShoppingFeedConfigGetResponse>(request)
	override fun getShopzillaFeedConfig(request: ShopzillaFeedConfigGetRequest) = apiClientHelper.makeObjectResultRequest<ShopzillaFeedConfigGetResponse>(request)
	override fun getYahooShoppingFeedConfig(request: YahooShoppingFeedConfigGetRequest) = apiClientHelper.makeObjectResultRequest<YahooShoppingFeedConfigGetResponse>(request)
	override fun getYandexMarketFeedConfig(request: YandexMarketFeedConfigGetRequest) = apiClientHelper.makeObjectResultRequest<YandexMarketFeedConfigGetResponse>(request)
}
