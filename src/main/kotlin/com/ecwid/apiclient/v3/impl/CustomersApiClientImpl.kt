package com.ecwid.apiclient.v3.impl

import com.ecwid.apiclient.v3.ApiClientHelper
import com.ecwid.apiclient.v3.CustomersApiClient
import com.ecwid.apiclient.v3.dto.customer.request.*
import com.ecwid.apiclient.v3.dto.customer.result.*
import com.ecwid.apiclient.v3.responsefields.AS_SEQUENCE_SEARCH_RESULT_REQUIRED_FIELDS

internal data class CustomersApiClientImpl(
	private val apiClientHelper: ApiClientHelper
) : CustomersApiClient {

	override fun searchCustomers(request: CustomersSearchRequest) =
		apiClientHelper.makeObjectResultRequest<CustomersSearchResult>(request)

	override fun searchCustomersAsSequence(request: CustomersSearchRequest) = sequence {
		var offsetRequest = request.copy(
			responseFields = request.responseFields + AS_SEQUENCE_SEARCH_RESULT_REQUIRED_FIELDS
		)
		do {
			val searchResult = searchCustomers(offsetRequest)
			yieldAll(searchResult.items)
			offsetRequest = offsetRequest.copy(offset = offsetRequest.offset + searchResult.count)
		} while (searchResult.count >= searchResult.limit)
	}

	override fun getCustomerDetails(request: CustomerDetailsRequest) =
		apiClientHelper.makeObjectResultRequest<FetchedCustomer>(request)

	override fun createCustomer(request: CustomerCreateRequest) =
		apiClientHelper.makeObjectResultRequest<CustomerCreateResult>(request)

	override fun updateCustomer(request: CustomerUpdateRequest) =
		apiClientHelper.makeObjectResultRequest<CustomerUpdateResult>(request)

	override fun deleteCustomer(request: CustomerDeleteRequest) =
		apiClientHelper.makeObjectResultRequest<CustomerDeleteResult>(request)

	override fun searchDeletedCustomers(request: DeletedCustomersSearchRequest) =
		apiClientHelper.makeObjectResultRequest<DeletedCustomersSearchResult>(request)

	override fun searchDeletedCustomersAsSequence(request: DeletedCustomersSearchRequest) = sequence {
		var offsetRequest = request.copy(
			responseFields = request.responseFields + AS_SEQUENCE_SEARCH_RESULT_REQUIRED_FIELDS
		)
		do {
			val searchResult = searchDeletedCustomers(offsetRequest)
			yieldAll(searchResult.items)
			offsetRequest = offsetRequest.copy(offset = offsetRequest.offset + searchResult.count)
		} while (searchResult.count >= searchResult.limit)
	}

	override fun searchCustomersProducts(request: CustomersProductsSearchRequest) =
		apiClientHelper.makeObjectResultRequest<CustomersProductsSearchResult>(request)

	override fun searchCustomersLocations(request: CustomersLocationsSearchRequest) =
		apiClientHelper.makeObjectResultRequest<CustomersLocationsSearchResult>(request)

	override fun searchCustomersFilters(request: CustomerFiltersDataSearchRequest) =
		apiClientHelper.makeObjectResultRequest<CustomersFiltersDataSearchResult>(request)

	override fun massUpdate(request: CustomersMassUpdateRequest) =
		apiClientHelper.makeObjectResultRequest<CustomerUpdateResult>(request)

}
