package com.ecwid.apiclient.v3.converter

import com.ecwid.apiclient.v3.dto.customergroup.request.UpdatedCustomerGroup
import com.ecwid.apiclient.v3.dto.customergroup.result.FetchedCustomerGroup

fun FetchedCustomerGroup.toUpdated(): UpdatedCustomerGroup {
	return UpdatedCustomerGroup(
			name = name
	)
}