package com.ecwid.apiclient.v3.entity

import com.ecwid.apiclient.v3.dto.customer.request.CustomerCreateRequest
import com.ecwid.apiclient.v3.dto.customer.request.CustomerDetailsRequest
import com.ecwid.apiclient.v3.dto.customer.request.UpdatedCustomer
import com.ecwid.apiclient.v3.responsefields.newResponseFields
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ResponseFieldsTest : BaseEntityTest() {
	@BeforeEach
	override fun beforeEach() {
		super.beforeEach()
		removeAllCustomers()
	}

	@Test
	fun testCustomerAndResponseFieldsParam() {
		val request = CustomerCreateRequest(
			newCustomer = UpdatedCustomer(
				email = "test@mail.com",
				billingPerson = UpdatedCustomer.BillingPerson(
					name = "billing person",
					companyName = "company name",
					phone = "123456789"
				)
			)
		)

		val result = apiClient.createCustomer(request)

		// Checks the customer was successfully created.
		val customerDetailsRequest = CustomerDetailsRequest(customerId = result.id)
		val customerDetails = apiClient.getCustomerDetails(customerDetailsRequest)

		Assertions.assertEquals(customerDetails.id, result.id)
		Assertions.assertEquals(customerDetails.email, "test@mail.com")
		Assertions.assertEquals(customerDetails.billingPerson?.name, "billing person")
		Assertions.assertEquals(customerDetails.billingPerson?.companyName, "company name")
		Assertions.assertEquals(customerDetails.billingPerson?.phone, "123456789")

		val responseFields = newResponseFields {
			field("email")
			field("billingPerson") {
				field("phone")
			}
		}
		val customerDetailsRequestWithResponseField =
			CustomerDetailsRequest(customerId = result.id, responseFields = responseFields)
		val customerDetails2 = apiClient.getCustomerDetails(customerDetailsRequestWithResponseField)

		Assertions.assertEquals(customerDetails2.id, 0)
		Assertions.assertEquals(customerDetails2.email, "test@mail.com")
		Assertions.assertEquals(customerDetails2.billingPerson?.name, null)
		Assertions.assertEquals(customerDetails2.billingPerson?.companyName, null)
		Assertions.assertEquals(customerDetails2.billingPerson?.phone, "123456789")
	}
}
