package com.ecwid.apiclient.v3

import com.ecwid.apiclient.v3.config.ApiServerDomain
import com.ecwid.apiclient.v3.config.ApiStoreCredentials
import com.ecwid.apiclient.v3.config.LoggingSettings
import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.dto.customer.request.CustomerDetailsRequest
import com.ecwid.apiclient.v3.dto.customer.result.CustomerCreateResult
import com.ecwid.apiclient.v3.httptransport.HttpBody
import com.ecwid.apiclient.v3.httptransport.impl.ApacheCommonsHttpClientTransport
import com.ecwid.apiclient.v3.impl.RequestInfo
import com.ecwid.apiclient.v3.jsontransformer.gson.GsonTransformerProvider
import com.ecwid.apiclient.v3.util.PropertiesLoader
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
		val customerData = CustomerData(email = email)
		val customerDataExt = CustomerDataExt(taxId = taxId)

		val apiClientHelper = createApiClientHelper()
		val customerCreateRequest = CustomerCreateRequestExt(
				newCustomer = customerData,
				newCustomerExt = customerDataExt
		)

		val result = apiClientHelper.makeObjectResultRequest<CustomerCreateResult>(customerCreateRequest)

		// Checks the customer was successfully created.
		val customerDetailsRequest = CustomerDetailsRequest(customerId = result.id)
		val customerDetails = apiClient.getCustomerDetails(customerDetailsRequest)

		assertEquals(customerDetails.email, email)
		assertEquals(customerDetails.taxId, taxId)
	}
}

private fun createApiClientHelper(): ApiClientHelper {
	val properties = PropertiesLoader.load()
	return ApiClientHelper(
			apiServerDomain = ApiServerDomain(
					host = properties.apiHost,
					securePort = properties.apiPort
			),
			storeCredentials = ApiStoreCredentials(
					storeId = properties.storeId,
					apiToken = properties.apiToken
			),
			loggingSettings = LoggingSettings().copy(
					logRequest = true,
					logSuccessfulResponseBody = true
			),
			httpTransport = ApacheCommonsHttpClientTransport(),
			jsonTransformerProvider = GsonTransformerProvider()
	)
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
			endpoint = "customers",
			httpBody = HttpBody.JsonBody(
					obj = newCustomer,
					objExt = newCustomerExt
			)
	)
}


