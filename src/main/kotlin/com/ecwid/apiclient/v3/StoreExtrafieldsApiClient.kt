package com.ecwid.apiclient.v3

import com.ecwid.apiclient.v3.dto.extrafield.request.*
import com.ecwid.apiclient.v3.dto.extrafield.result.*


interface StoreExtrafieldsApiClient {
	fun searchCustomersConfigs(request: CustomersConfigsSearchRequest): CustomersConfigsSearchResult
	fun getCustomersConfig(request: CustomersConfigDetailsRequest): FetchedCustomersConfig
	fun createCustomersConfig(request: CustomersConfigCreateRequest): CustomersConfigCreateResult
	fun updateCustomersConfig(request: CustomersConfigUpdateRequest): CustomersConfigUpdateResult
	fun deleteCustomersConfig(request: CustomersConfigDeleteRequest): CustomersConfigDeleteResult
}
