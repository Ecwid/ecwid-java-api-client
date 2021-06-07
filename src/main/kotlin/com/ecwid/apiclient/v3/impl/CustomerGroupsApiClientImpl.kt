package com.ecwid.apiclient.v3.impl

import com.ecwid.apiclient.v3.ApiClientHelper
import com.ecwid.apiclient.v3.CustomerGroupsApiClient
import com.ecwid.apiclient.v3.dto.customergroup.request.*
import com.ecwid.apiclient.v3.dto.customergroup.result.*

internal class CustomerGroupsApiClientImpl(
		private val apiClientHelper: ApiClientHelper
) : CustomerGroupsApiClient {

	override fun searchCustomerGroups(request: CustomerGroupsSearchRequest) = apiClientHelper.makeObjectResultRequest<CustomerGroupsSearchResult>(request)

	override fun searchCustomerGroupsAsSequence(request: CustomerGroupsSearchRequest) = sequence {
		var offsetRequest = request
		do {
			val searchResult = searchCustomerGroups(offsetRequest)
			yieldAll(searchResult.items)
			offsetRequest = offsetRequest.copy(offset = offsetRequest.offset + searchResult.count)
		} while (searchResult.count >= searchResult.limit)
	}

	override fun getCustomerGroupDetails(request: CustomerGroupDetailsRequest) = apiClientHelper.makeObjectResultRequest<FetchedCustomerGroup>(request)
	override fun createCustomerGroup(request: CustomerGroupCreateRequest) = apiClientHelper.makeObjectResultRequest<CustomerGroupCreateResult>(request)
	override fun updateCustomerGroup(request: CustomerGroupUpdateRequest) = apiClientHelper.makeObjectResultRequest<CustomerGroupUpdateResult>(request)
	override fun deleteCustomerGroup(request: CustomerGroupDeleteRequest) = apiClientHelper.makeObjectResultRequest<CustomerGroupDeleteResult>(request)

}
