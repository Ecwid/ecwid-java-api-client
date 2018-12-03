package com.ecwid.apiclient.v3.dto.customergroup.request

data class CustomerGroupCreateRequest(
		var newCustomerGroup: UpdatedCustomerGroup = UpdatedCustomerGroup()
)