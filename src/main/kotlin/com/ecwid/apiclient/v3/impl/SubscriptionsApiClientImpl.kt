package com.ecwid.apiclient.v3.impl

import com.ecwid.apiclient.v3.ApiClientHelper
import com.ecwid.apiclient.v3.SubscriptionsApiClient
import com.ecwid.apiclient.v3.dto.subscriptions.request.SubscriptionsSearchRequest
import com.ecwid.apiclient.v3.dto.subscriptions.result.SubscriptionsSearchResult

class SubscriptionsApiClientImpl(
	private val apiClientHelper: ApiClientHelper
) : SubscriptionsApiClient {

	override fun searchSubscriptions(request: SubscriptionsSearchRequest) =
		apiClientHelper.makeObjectResultRequest<SubscriptionsSearchResult>(request)

}
