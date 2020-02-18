package com.ecwid.apiclient.v3.impl

import com.ecwid.apiclient.v3.CartsApiClient
import com.ecwid.apiclient.v3.dto.cart.request.*
import com.ecwid.apiclient.v3.dto.cart.result.*

internal data class CartsApiClientImpl(
        private val apiClientHelper: ApiClientHelper
) : CartsApiClient {
    override fun searchAbandonedCart(request: CartsSearchRequest) = apiClientHelper.makeRequest<CartsSearchResult>(request)

    override fun searchAbandonedCartsAsSequence(request: CartsSearchRequest): List<FetchedCart> {
        var offsetRequest = request
        val totalList: MutableList<FetchedCart> = mutableListOf()
        do {
            val searchResult = searchAbandonedCart(offsetRequest)
            totalList.addAll(searchResult.items)
            offsetRequest = offsetRequest.copy(offset = offsetRequest.offset + searchResult.count)
        } while (searchResult.count >= searchResult.limit)
        return totalList
    }

    override fun getAbandonedCart(request: CartDetailsRequest) = apiClientHelper.makeRequest<FetchedCart>(request)
    override fun updateAbandonedCart(request: CartUpdateRequest) = apiClientHelper.makeRequest<CartUpdateResult>(request)
    override fun calculateOrderDetails(request: CalculateOrderDetailsRequest) = apiClientHelper.makeRequest<CalculateOrderDetailsResult>(request)
    override fun convertAbandonedCartToOrder(request: ConvertCartToOrderRequest) = apiClientHelper.makeRequest<ConvertCartToOrderResult>(request)
}
