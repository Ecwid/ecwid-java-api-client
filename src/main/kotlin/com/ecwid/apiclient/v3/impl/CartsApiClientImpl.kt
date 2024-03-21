package com.ecwid.apiclient.v3.impl

import com.ecwid.apiclient.v3.ApiClientHelper
import com.ecwid.apiclient.v3.CartsApiClient
import com.ecwid.apiclient.v3.dto.cart.request.*
import com.ecwid.apiclient.v3.dto.cart.result.*
import com.ecwid.apiclient.v3.responsefields.AS_SEQUENCE_SEARCH_RESULT_REQUIRED_FIELDS

internal data class CartsApiClientImpl(
	private val apiClientHelper: ApiClientHelper
) : CartsApiClient {
	override fun searchCarts(request: CartsSearchRequest) =
		apiClientHelper.makeObjectResultRequest<CartsSearchResult>(request)

	override fun searchCartsAsSequence(request: CartsSearchRequest): List<FetchedCart> {
		var offsetRequest = request.copy(
			responseFields = request.responseFields + AS_SEQUENCE_SEARCH_RESULT_REQUIRED_FIELDS
		)
		val totalList: MutableList<FetchedCart> = mutableListOf()
		do {
			val searchResult = searchCarts(offsetRequest)
			totalList.addAll(searchResult.items)
			offsetRequest = offsetRequest.copy(offset = offsetRequest.offset + searchResult.count)
		} while (searchResult.count >= searchResult.limit)
		return totalList
	}

	override fun getCartDetails(request: CartDetailsRequest) =
		apiClientHelper.makeObjectResultRequest<FetchedCart>(request)

	override fun updateCart(request: CartUpdateRequest) =
		apiClientHelper.makeObjectResultRequest<CartUpdateResult>(request)

	override fun calculateOrderDetails(request: CalculateOrderDetailsRequest) =
		apiClientHelper.makeObjectResultRequest<CalculateOrderDetailsResult>(request)

	override fun convertCartToOrder(request: ConvertCartToOrderRequest) =
		apiClientHelper.makeObjectResultRequest<ConvertCartToOrderResult>(request)

	override fun getRepeatCartURL(request: RepeatCartURLRequest) =
		apiClientHelper.makeObjectResultRequest<RepeatCartURLResult>(request)
}
