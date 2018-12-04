package com.ecwid.apiclient.v3.dto.customer.request

data class CustomerUpdateRequest(
		var customerId: Int = 0,
		var updatedCustomer: UpdatedCustomer = UpdatedCustomer()
)