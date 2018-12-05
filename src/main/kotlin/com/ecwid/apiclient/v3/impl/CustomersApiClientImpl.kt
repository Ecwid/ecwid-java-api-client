package com.ecwid.apiclient.v3.impl

import com.ecwid.apiclient.v3.CustomersApiClient
import com.ecwid.apiclient.v3.dto.customer.request.*
import com.ecwid.apiclient.v3.dto.customer.result.*
import com.ecwid.apiclient.v3.httptransport.HttpBody

internal data class CustomersApiClientImpl(
		private val apiClientHelper: ApiClientHelper
) : CustomersApiClient {

	override fun searchCustomers(request: CustomersSearchRequest) = apiClientHelper.makeGetRequest<CustomersSearchResult>(
			endpoint = request.toEndpoint(),
			params = request.toParams()
	)

	override fun searchCustomersAsSequence(request: CustomersSearchRequest) = sequence {
		var offsetRequest = request
		do {
			val searchResult = searchCustomers(offsetRequest)
			yieldAll(searchResult.items)
			offsetRequest = offsetRequest.copy(offset = offsetRequest.offset + searchResult.count)
		} while (searchResult.count >= searchResult.limit);
	}

	override fun getCustomerDetails(request: CustomerDetailsRequest) = apiClientHelper.makeGetRequest<FetchedCustomer>(
			endpoint = request.toEndpoint(),
			params = mapOf()
	)

	override fun createCustomer(request: CustomerCreateRequest) = apiClientHelper.makePostRequest<CustomerCreateResult>(
			endpoint = request.toEndpoint(),
			params = mapOf(),
			httpBody = HttpBody.StringBody(
					body = apiClientHelper.serializeJson(request.newCustomer),
					mimeType = MIME_TYPE_APPLICATION_JSON
			)
	)

	override fun updateCustomer(request: CustomerUpdateRequest) = apiClientHelper.makePutRequest<CustomerUpdateResult>(
			endpoint = request.toEndpoint(),
			params = mapOf(),
			httpBody = HttpBody.StringBody(
					body = apiClientHelper.serializeJson(request.updatedCustomer),
					mimeType = MIME_TYPE_APPLICATION_JSON
			)
	)

	override fun deleteCustomer(request: CustomerDeleteRequest) = apiClientHelper.makeDeleteRequest<CustomerDeleteResult>(
			endpoint = request.toEndpoint(),
			params = mapOf()
	)

	override fun searchDeletedCustomers(request: DeletedCustomersSearchRequest) = apiClientHelper.makeGetRequest<DeletedCustomersSearchResult>(
			endpoint = request.toEndpoint(),
			params = request.toParams()
	)

	override fun searchDeletedCustomersAsSequence(request: DeletedCustomersSearchRequest) = sequence {
		var offsetRequest = request
		do {
			val searchResult = searchDeletedCustomers(offsetRequest)
			yieldAll(searchResult.items)
			offsetRequest = offsetRequest.copy(offset = offsetRequest.offset + searchResult.count)
		} while (searchResult.count >= searchResult.limit);
	}

}

private fun CustomersSearchRequest.toEndpoint() = "customers"
private fun CustomerDetailsRequest.toEndpoint() = "customers/$customerId"
private fun CustomerCreateRequest.toEndpoint() = "customers"
private fun CustomerUpdateRequest.toEndpoint() = "customers/$customerId"
private fun CustomerDeleteRequest.toEndpoint() = "customers/$customerId"
private fun DeletedCustomersSearchRequest.toEndpoint() = "customers/deleted"

private fun CustomersSearchRequest.toParams(): Map<String, String> {
	val request = this
	return mutableMapOf<String, String>().apply {
		request.keyword?.let { put("keyword", it) }
		request.name?.let { put("name", it) }
		request.email?.let { put("email", it) }
		request.customerGroupId?.let { put("customerGroup", it.toString()) }
		request.minOrderCount?.let { put("minOrderCount", it.toString()) }
		request.maxOrderCount?.let { put("maxOrderCount", it.toString()) }
		request.createdFrom?.let { put("createdFrom", (it.time / 1000).toString()) }
		request.createdTo?.let { put("createdTo", (it.time / 1000).toString()) }
		request.updatedFrom?.let { put("updatedFrom", (it.time / 1000).toString()) }
		request.updatedTo?.let { put("updatedTo", (it.time / 1000).toString()) }
		request.sortBy?.let { put("sortBy", it.name) }
		put("offset", request.offset.toString())
		put("limit", request.limit.toString())
	}.toMap()
}

private fun DeletedCustomersSearchRequest.toParams(): Map<String, String> {
	val request = this
	return mutableMapOf<String, String>().apply {
		request.deletedFrom?.let { put("from_date", (it.time / 1000).toString()) }
		request.deletedTo?.let { put("to_date", (it.time / 1000).toString()) }
		put("offset", request.offset.toString())
		put("limit", request.limit.toString())
	}.toMap()
}