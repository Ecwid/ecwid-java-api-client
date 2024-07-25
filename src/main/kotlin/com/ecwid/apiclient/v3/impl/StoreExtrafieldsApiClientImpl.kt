package com.ecwid.apiclient.v3.impl

import com.ecwid.apiclient.v3.ApiClientHelper
import com.ecwid.apiclient.v3.StoreExtrafieldsApiClient
import com.ecwid.apiclient.v3.dto.extrafield.request.*
import com.ecwid.apiclient.v3.dto.extrafield.result.*

internal class StoreExtrafieldsApiClientImpl(
	private val apiClientHelper: ApiClientHelper
) : StoreExtrafieldsApiClient {

	override fun searchCustomersConfigs(request: CustomersConfigsSearchRequest): CustomersConfigsSearchResult =
		apiClientHelper.makeObjectResultRequest(request)

	override fun getCustomersConfig(request: CustomersConfigDetailsRequest): FetchedCustomersConfig =
		apiClientHelper.makeObjectResultRequest(request)

	override fun createCustomersConfig(request: CustomersConfigCreateRequest): CustomersConfigCreateResult =
		apiClientHelper.makeObjectResultRequest(request)

	override fun updateCustomersConfig(request: CustomersConfigUpdateRequest): CustomersConfigUpdateResult =
		apiClientHelper.makeObjectResultRequest(request)

	override fun deleteCustomersConfig(request: CustomersConfigDeleteRequest): CustomersConfigDeleteResult =
		apiClientHelper.makeObjectResultRequest(request)
}
