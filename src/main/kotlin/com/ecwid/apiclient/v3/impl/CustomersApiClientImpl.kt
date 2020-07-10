package com.ecwid.apiclient.v3.impl

import com.ecwid.apiclient.v3.ApiClientHelper
import com.ecwid.apiclient.v3.CustomersApiClient
import com.ecwid.apiclient.v3.dto.customer.request.*
import com.ecwid.apiclient.v3.dto.customer.result.*

internal data class CustomersApiClientImpl(
		private val apiClientHelper: ApiClientHelper
) : CustomersApiClient {

	override fun searchCustomers(request: CustomersSearchRequest) = apiClientHelper.makeRequest<CustomersSearchResult>(request)

	override fun searchCustomersAsSequence(request: CustomersSearchRequest) = sequence {
		var offsetRequest = request
		do {
			val searchResult = searchCustomers(offsetRequest)
			yieldAll(searchResult.items)
			offsetRequest = offsetRequest.copy(offset = offsetRequest.offset + searchResult.count)
		} while (searchResult.count >= searchResult.limit)
	}

	override fun getCustomerDetails(request: CustomerDetailsRequest) = apiClientHelper.makeRequest<FetchedCustomer>(request)
	override fun createCustomer(request: CustomerCreateRequest) = apiClientHelper.makeRequest<CustomerCreateResult>(request)
	override fun updateCustomer(request: CustomerUpdateRequest) = apiClientHelper.makeRequest<CustomerUpdateResult>(request)
	override fun deleteCustomer(request: CustomerDeleteRequest) = apiClientHelper.makeRequest<CustomerDeleteResult>(request)
	override fun searchDeletedCustomers(request: DeletedCustomersSearchRequest) = apiClientHelper.makeRequest<DeletedCustomersSearchResult>(request)

	override fun searchDeletedCustomersAsSequence(request: DeletedCustomersSearchRequest) = sequence {
		var offsetRequest = request
		do {
			val searchResult = searchDeletedCustomers(offsetRequest)
			yieldAll(searchResult.items)
			offsetRequest = offsetRequest.copy(offset = offsetRequest.offset + searchResult.count)
		} while (searchResult.count >= searchResult.limit)
	}

}
