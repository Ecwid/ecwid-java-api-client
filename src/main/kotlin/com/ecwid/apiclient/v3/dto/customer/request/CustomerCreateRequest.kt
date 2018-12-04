package com.ecwid.apiclient.v3.dto.customer.request

data class CustomerCreateRequest(
		var newCustomer: UpdatedCustomer = UpdatedCustomer()
)