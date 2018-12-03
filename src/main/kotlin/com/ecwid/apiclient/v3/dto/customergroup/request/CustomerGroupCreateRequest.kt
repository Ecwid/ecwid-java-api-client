package com.ecwid.apiclient.v3.dto.customergroup.request

data class CustomerGroupCreateRequest(
		val newCustomerGroup: UpdatedCustomerGroup = UpdatedCustomerGroup()
)