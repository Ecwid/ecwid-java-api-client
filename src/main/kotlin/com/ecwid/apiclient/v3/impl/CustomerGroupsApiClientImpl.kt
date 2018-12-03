package com.ecwid.apiclient.v3.impl

import com.ecwid.apiclient.v3.CustomerGroupsApiClient
import com.ecwid.apiclient.v3.dto.customergroup.request.*
import com.ecwid.apiclient.v3.dto.customergroup.result.*
import com.ecwid.apiclient.v3.httptransport.HttpBody

internal class CustomerGroupsApiClientImpl(
		private val apiClientHelper: ApiClientHelper
): CustomerGroupsApiClient {

	override fun searchCustomerGroups(request: CustomerGroupsSearchRequest) = apiClientHelper.makeGetRequest<CustomerGroupsSearchResult>(
			endpoint = request.toEndpoint(),
			params = request.toParams()
	)

	override fun searchCustomerGroupsAsSequence(request: CustomerGroupsSearchRequest) = sequence {
		var offsetRequest = request
		do {
			val searchResult = searchCustomerGroups(offsetRequest)
			yieldAll(searchResult.items)
			offsetRequest = offsetRequest.copy(offset = offsetRequest.offset + searchResult.count)
		} while (searchResult.count >= searchResult.limit);
	}

	override fun getCustomerGroupDetails(request: CustomerGroupDetailsRequest) = apiClientHelper.makeGetRequest<FetchedCustomerGroup>(
			endpoint = request.toEndpoint(),
			params = mapOf()
	)

	override fun createCustomerGroup(request: CustomerGroupCreateRequest) = apiClientHelper.makePostRequest<CustomerGroupCreateResult>(
			endpoint = request.toEndpoint(),
			params = mapOf(),
			httpBody = HttpBody.StringBody(
					body = apiClientHelper.serializeJson(request.newCustomerGroup),
					mimeType = MIME_TYPE_APPLICATION_JSON
			)
	)

	override fun updateCustomerGroup(request: CustomerGroupUpdateRequest) = apiClientHelper.makePutRequest<CustomerGroupUpdateResult>(
			endpoint = request.toEndpoint(),
			params = mapOf(),
			httpBody = HttpBody.StringBody(
					body = apiClientHelper.serializeJson(request.updatedCustomerGroup),
					mimeType = MIME_TYPE_APPLICATION_JSON
			)
	)

	override fun deleteCustomerGroup(request: CustomerGroupDeleteRequest) = apiClientHelper.makeDeleteRequest<CustomerGroupDeleteResult>(
			endpoint = request.toEndpoint(),
			params = mapOf()
	)

}

private fun CustomerGroupsSearchRequest.toEndpoint() = "customer_groups"
private fun CustomerGroupDetailsRequest.toEndpoint() = "customer_groups/$customerGroupId"
private fun CustomerGroupCreateRequest.toEndpoint() = "customer_groups"
private fun CustomerGroupUpdateRequest.toEndpoint() = "customer_groups/$customerGroupId"
private fun CustomerGroupDeleteRequest.toEndpoint() = "customer_groups/$customerGroupId"

private fun CustomerGroupsSearchRequest.toParams(): Map<String, String> {
	val request = this
	return mutableMapOf<String, String>().apply {
		put("offset", request.offset.toString())
		put("limit", request.limit.toString())
	}.toMap()
}
