package com.ecwid.apiclient.v3.dto.customergroup.request

data class CustomerGroupUpdateRequest(
		var customerGroupId: Int = 0,
		var updatedCustomerGroup: UpdatedCustomerGroup = UpdatedCustomerGroup()
)