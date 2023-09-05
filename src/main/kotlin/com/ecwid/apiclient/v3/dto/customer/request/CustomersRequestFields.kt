package com.ecwid.apiclient.v3.dto.customer.request

import com.ecwid.apiclient.v3.dto.common.ApiRequestDTO

data class CustomersRequestFields(
	val keyword: String? = null,
	val minOrderCount: Int? = null,
	val maxOrderCount: Int? = null,
	val minSalesValue: Int? = null,
	val maxSalesValue: Int? = null,
	val taxExempt: Boolean? = null,
	val acceptMarketing: Boolean? = null,
	val purchasedProductIds: String? = null,
	val customerGroupIds: String? = null,
	val countryCodes: String? = null,
	val lang: String? = null,
) : ApiRequestDTO
