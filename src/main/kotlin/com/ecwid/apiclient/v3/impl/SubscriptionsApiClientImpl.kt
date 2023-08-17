package com.ecwid.apiclient.v3.impl

import com.ecwid.apiclient.v3.ApiClientHelper
import com.ecwid.apiclient.v3.SubscriptionsApiClient
import com.ecwid.apiclient.v3.dto.subscriptions.request.SubscriptionsSearchRequest
import com.ecwid.apiclient.v3.dto.subscriptions.result.SubscriptionsSearchResult
import com.ecwid.apiclient.v3.responsefields.AS_SEQUENCE_SEARCH_RESULT_REQUIRED_FIELDS

class SubscriptionsApiClientImpl(
	private val apiClientHelper: ApiClientHelper
) : SubscriptionsApiClient {

	override fun searchSubscriptions(request: SubscriptionsSearchRequest) =
		apiClientHelper.makeObjectResultRequest<SubscriptionsSearchResult>(request)

	override fun searchSubscriptionsAsSequence(request: SubscriptionsSearchRequest) = sequence {
		var offsetRequest = request.copy(
			responseFields = request.responseFields + AS_SEQUENCE_SEARCH_RESULT_REQUIRED_FIELDS
		)
		do {
			val searchResult = searchSubscriptions(offsetRequest)
			yieldAll(searchResult.items)
			offsetRequest = offsetRequest.copy(offset = offsetRequest.offset + searchResult.count)
		} while (searchResult.count >= searchResult.limit)
	}

}
