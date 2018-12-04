package com.ecwid.apiclient.v3.dto.customer.request

import java.util.*

data class CustomersSearchRequest(
		var keyword: String? = null,
		var name: String? = null,
		var email: String? = null,
		var customerGroupId: Int? = null,
		var minOrderCount: Int? = null,
		var maxOrderCount: Int? = null,
		var createdFrom: Date? = null,
		var createdTo: Date? = null,
		var updatedFrom: Date? = null,
		var updatedTo: Date? = null,
		var sortBy: SortOrder? = null,
		var offset: Int = 0,
		var limit: Int = 100
) {

	enum class SortOrder {
		NAME_ASC,
		NAME_DESC,
		EMAIL_ASC,
		EMAIL_DESC,
		ORDER_COUNT_ASC,
		ORDER_COUNT_DESC,
		REGISTERED_DATE_DESC,
		REGISTERED_DATE_ASC,
		UPDATED_DATE_DESC,
		UPDATED_DATE_ASC
	}

}