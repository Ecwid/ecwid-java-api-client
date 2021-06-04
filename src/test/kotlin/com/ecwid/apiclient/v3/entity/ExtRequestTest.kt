package com.ecwid.apiclient.v3.entity

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.dto.customer.request.CustomerDetailsRequest
import com.ecwid.apiclient.v3.dto.customer.result.CustomerCreateResult
import com.ecwid.apiclient.v3.httptransport.HttpBody
import com.ecwid.apiclient.v3.impl.RequestInfo
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ExtRequestTest : BaseEntityTest() {
	@BeforeEach
	override fun beforeEach() {
		super.beforeEach()
		removeAllCustomers()
	}

	@Test
	fun testCreateExtCustomer() {
		val email = "test@mail.com"
		val taxId = "10"
		val newTaxId = "11"
		val customerData = CustomerData(email = email, taxId = taxId)
		val customerDataExt = CustomerDataExt(taxId = newTaxId)

		val customerCreateRequest = CustomerCreateRequestExt(
				newCustomer = customerData,
				newCustomerExt = customerDataExt
		)

		val result = apiClientHelper.makeObjectResultRequest<CustomerCreateResult>(customerCreateRequest)

		// Checks the customer was successfully created.
		val customerDetailsRequest = CustomerDetailsRequest(customerId = result.id)
		val customerDetails = apiClient.getCustomerDetails(customerDetailsRequest)

		assertEquals(customerDetails.email, email)
		assertEquals(customerDetails.taxId, newTaxId)
	}
}

private data class CustomerData(
		var email: String? = null,
		var taxId: String? = null
)

private data class CustomerDataExt(
		var taxId: String? = null
)

private data class CustomerCreateRequestExt(
        var newCustomer: CustomerData = CustomerData(),
        var newCustomerExt: CustomerDataExt = CustomerDataExt()
) : ApiRequest {
	override fun toRequestInfo() = RequestInfo.createPostRequest(
			pathSegments = listOf(
					"customers"
			),
			httpBody = HttpBody.JsonBody(
					obj = newCustomer,
					objExt = newCustomerExt
			)
	)
}
