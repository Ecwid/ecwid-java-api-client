package com.ecwid.apiclient.v3

import com.ecwid.apiclient.v3.converter.toUpdated
import com.ecwid.apiclient.v3.dto.customer.request.*
import com.ecwid.apiclient.v3.dto.customer.request.CustomersSearchRequest.SortOrder
import com.ecwid.apiclient.v3.dto.customer.result.FetchedCustomer
import com.ecwid.apiclient.v3.dto.customergroup.request.CustomerGroupCreateRequest
import com.ecwid.apiclient.v3.dto.customergroup.request.UpdatedCustomerGroup
import com.ecwid.apiclient.v3.dto.order.request.OrderCreateRequest
import com.ecwid.apiclient.v3.dto.order.request.UpdatedOrder
import com.ecwid.apiclient.v3.exception.EcwidApiException
import com.ecwid.apiclient.v3.util.randomAlphanumeric
import com.ecwid.apiclient.v3.util.randomBoolean
import com.ecwid.apiclient.v3.util.randomEmail
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*


class CustomersTest: BaseEntityTest() {

	@BeforeEach
	override fun beforeEach() {
		super.beforeEach()

		// We need to start from scratch each time
		removeAllCustomerGroups()
		removeAllCustomers()
		removeAllOrders()
	}

	@Test
	fun testCustomerLifecycle() {
		// Creating one customer group
		val customerGroupCreateRequest1 = CustomerGroupCreateRequest(
				newCustomerGroup = UpdatedCustomerGroup(
						name = "Customer group " + randomAlphanumeric(8)
				)
		)
		val customerGroupCreateResult1 = apiClient.createCustomerGroup(customerGroupCreateRequest1)
		assertTrue(customerGroupCreateResult1.id > 0)

		// Creating another one customer group
		val customerGroupCreateRequest2 = CustomerGroupCreateRequest(
				newCustomerGroup = UpdatedCustomerGroup(
						name = "Customer group " + randomAlphanumeric(8)
				)
		)
		val customerGroupCreateResult2 = apiClient.createCustomerGroup(customerGroupCreateRequest2)
		assertTrue(customerGroupCreateResult2.id > 0)

		// Creating new customer
		val customerCreateRequest = CustomerCreateRequest(
				newCustomer = generateTestCustomerForCreate(customerGroupId = customerGroupCreateResult1.id)
		)
		val customerCreateResult = apiClient.createCustomer(customerCreateRequest)
		assertTrue(customerCreateResult.id > 0)

		// Checking that customer was successfully created with necessary parameters

		val customerDetailsRequest = CustomerDetailsRequest(customerId = customerCreateResult.id)
		val customerDetails1 = apiClient.getCustomerDetails(customerDetailsRequest)
		assertEquals(
				customerCreateRequest.newCustomer,
				customerDetails1.withSortedShippingAddresses().toUpdated().cleanupForComparison(customerCreateRequest)
		)
		assertEquals(customerGroupCreateRequest1.newCustomerGroup.name, customerDetails1.customerGroupName)

		assertNotNull(customerDetails1.id)
		assertNotNull(customerDetails1.registered)
		assertNotNull(customerDetails1.updated)

		val billingPerson = customerDetails1.billingPerson
		require(billingPerson != null)
		assertNotNull(billingPerson.countryName)
		assertNotNull(billingPerson.stateOrProvinceName)

		val shippingAddresses = customerDetails1.shippingAddresses
		require(shippingAddresses != null)
		shippingAddresses.onEach { shippingAddress ->
			assertNotNull(shippingAddress.id)
			assertNotNull(shippingAddress.countryName)
			assertNotNull(shippingAddress.stateOrProvinceName)
		}

		// Completely updating newly created customer, leaving shipping addresses ids to update not recreate them
		val customerUpdateRequest = CustomerUpdateRequest(
				customerId = customerDetails1.id,
				updatedCustomer = generateTestCustomerForUpdate(
						customerGroupId = customerGroupCreateResult2.id,
						oldShippingAddresses = customerDetails1.withSortedShippingAddresses().shippingAddresses
				)
		)
		val customerUpdateResult1 = apiClient.updateCustomer(customerUpdateRequest)
		assertEquals(1, customerUpdateResult1.updateCount)

		// Checking that customer was successfully updated with necessary parameters
		val customerDetails2 = apiClient.getCustomerDetails(customerDetailsRequest)
		assertEquals(
				customerUpdateRequest.updatedCustomer,
				customerDetails2.withSortedShippingAddresses().toUpdated()
		)

		// Deleting customer
		val customerDeleteRequest = CustomerDeleteRequest(customerId = customerDetails1.id)
		val customerDeleteResult = apiClient.deleteCustomer(customerDeleteRequest)
		assertEquals(1, customerDeleteResult.deleteCount)

		// Checking that deleted customer is not accessible anymore
		try {
			apiClient.getCustomerDetails(customerDetailsRequest)
		} catch (e: EcwidApiException) {
			assertEquals(404, e.statusCode)
			assertEquals("Customer #${customerCreateResult.id} not found", e.message)
		}
	}

	@Test
	fun testSearchFields() {
		// Creating new customer group
		val customerGroupCreateRequest = CustomerGroupCreateRequest(
				newCustomerGroup = UpdatedCustomerGroup(
						name = "Customer group " + randomAlphanumeric(8)
				)
		)
		val customerGroupCreateResult = apiClient.createCustomerGroup(customerGroupCreateRequest)
		assertTrue(customerGroupCreateResult.id > 0)

		// Creating new customer attached to this customer group 
		val customerCreateRequest = CustomerCreateRequest(
				newCustomer = UpdatedCustomer(
						email = randomEmail(),
						billingPerson = UpdatedCustomer.BillingPerson(
								name = randomAlphanumeric(16)
						),
						customerGroupId = customerGroupCreateResult.id
				)
		)
		val customerCreateResult = apiClient.createCustomer(customerCreateRequest)
		assertTrue(customerCreateResult.id > 0)

		// Creating new order for this customer
		val orderCreateRequest = OrderCreateRequest(
				newOrder = UpdatedOrder(
						email = customerCreateRequest.newCustomer.email
				)
		)
		val orderCreateResult = apiClient.createOrder(orderCreateRequest)
		assertTrue(orderCreateResult.id > 0)

		// Trying to search by different fields

		assertCustomersSearch(
				positiveCustomerId = customerCreateResult.id,
				positiveSearchRequest = CustomersSearchRequest(keyword = customerCreateRequest.newCustomer.billingPerson?.name),
				negativeSearchRequest = CustomersSearchRequest(keyword = customerCreateRequest.newCustomer.billingPerson?.name + "foo")
		)

		assertCustomersSearch(
				positiveCustomerId = customerCreateResult.id,
				positiveSearchRequest = CustomersSearchRequest(name = customerCreateRequest.newCustomer.billingPerson?.name),
				negativeSearchRequest = CustomersSearchRequest(name = customerCreateRequest.newCustomer.billingPerson?.name + "foo")
		)

		assertCustomersSearch(
				positiveCustomerId = customerCreateResult.id,
				positiveSearchRequest = CustomersSearchRequest(email = customerCreateRequest.newCustomer.email),
				negativeSearchRequest = CustomersSearchRequest(email = customerCreateRequest.newCustomer.email + "foo")
		)

		assertCustomersSearch(
				positiveCustomerId = customerCreateResult.id,
				positiveSearchRequest = CustomersSearchRequest(customerGroupId = customerCreateRequest.newCustomer.customerGroupId),
				negativeSearchRequest = CustomersSearchRequest(customerGroupId = 0)
		)

		assertCustomersSearch(
				positiveCustomerId = customerCreateResult.id,
				positiveSearchRequest = CustomersSearchRequest(
						minOrderCount = 1,
						maxOrderCount = 1
				),
				negativeSearchRequest = CustomersSearchRequest(
						minOrderCount = 2,
						maxOrderCount = 2
				)
		)

		assertCustomersSearch(
				positiveCustomerId = customerCreateResult.id,
				positiveSearchRequest = CustomersSearchRequest(
						createdFrom = Date.from(Date().toInstant().minusSeconds(60)),
						createdTo = Date.from(Date().toInstant().plusSeconds(60))
				),
				negativeSearchRequest = CustomersSearchRequest(
						createdFrom = Date(0),
						createdTo = Date(0)
				)
		)

		assertCustomersSearch(
				positiveCustomerId = customerCreateResult.id,
				positiveSearchRequest = CustomersSearchRequest(
						updatedFrom = Date.from(Date().toInstant().minusSeconds(60)),
						updatedTo = Date.from(Date().toInstant().plusSeconds(60))
				),
				negativeSearchRequest = CustomersSearchRequest(
						updatedFrom = Date(0),
						updatedTo = Date(0)
				)
		)
	}

	@Test
	fun testSearchPaging() {
		// Create some customers
		for (i in 1..3) {
			val customerCreateRequest = CustomerCreateRequest(
					newCustomer = generateTestCustomerForCreate(customerGroupId = null)
			)
			val customerCreateResult = apiClient.createCustomer(customerCreateRequest)
			assertTrue(customerCreateResult.id > 0)
		}

		// Trying to request first page 
		val customersSearchRequest1 = CustomersSearchRequest(offset = 0, limit = 2)
		val customersSearchResult1 = apiClient.searchCustomers(customersSearchRequest1)
		assertEquals(2, customersSearchResult1.count)
		assertEquals(3, customersSearchResult1.total)

		// Trying to request second and the last page 
		val customersSearchRequest2 = CustomersSearchRequest(offset = 2, limit = 2)
		val customersSearchResult2 = apiClient.searchCustomers(customersSearchRequest2)
		assertEquals(1, customersSearchResult2.count)
		assertEquals(3, customersSearchResult2.total)
	}

	@Test
	fun testSearchSorting() {
		// Creating some customers

		val customerCreateRequest1 = CustomerCreateRequest(
				newCustomer = UpdatedCustomer(
						email = "aaa@example.com",
						billingPerson = UpdatedCustomer.BillingPerson(
								name = "aaa"
						)
				)
		)
		val customerCreateResult1 = apiClient.createCustomer(customerCreateRequest1)
		assertTrue(customerCreateResult1.id > 0)

		val customerCreateRequest2 = CustomerCreateRequest(
				newCustomer = UpdatedCustomer(
						email = "bbb@example.com",
						billingPerson = UpdatedCustomer.BillingPerson(
								name = "bbb"
						)
				)
		)
		val customerCreateResult2 = apiClient.createCustomer(customerCreateRequest2)
		assertTrue(customerCreateResult2.id > 0)

		// Creating order for one of those customers
		val orderCreateRequest = OrderCreateRequest(
				newOrder = UpdatedOrder(
						email = customerCreateRequest2.newCustomer.email
				)
		)
		val orderCreateResult = apiClient.createOrder(orderCreateRequest)
		assertTrue(orderCreateResult.id > 0)

		// Trying to search using different sorts

		assertCustomersSortBySearch(customerCreateResult1.id, SortOrder.NAME_ASC)
		assertCustomersSortBySearch(customerCreateResult2.id, SortOrder.NAME_DESC)

		assertCustomersSortBySearch(customerCreateResult1.id, SortOrder.EMAIL_ASC)
		assertCustomersSortBySearch(customerCreateResult2.id, SortOrder.EMAIL_DESC)

		assertCustomersSortBySearch(customerCreateResult1.id, SortOrder.ORDER_COUNT_ASC)
		assertCustomersSortBySearch(customerCreateResult2.id, SortOrder.ORDER_COUNT_DESC)

		assertCustomersSortBySearch(customerCreateResult1.id, SortOrder.REGISTERED_DATE_ASC)
		assertCustomersSortBySearch(customerCreateResult2.id, SortOrder.REGISTERED_DATE_DESC)

		assertCustomersSortBySearch(customerCreateResult1.id, SortOrder.UPDATED_DATE_ASC)
		assertCustomersSortBySearch(customerCreateResult2.id, SortOrder.UPDATED_DATE_DESC)
	}

	@Test
	fun testDeletedCustomers() {
		// Creating new customer
		val customerCreateRequest = CustomerCreateRequest(
				newCustomer = UpdatedCustomer(
						email = randomEmail()
				)
		)
		val customerCreateResult = apiClient.createCustomer(customerCreateRequest)
		assertTrue(customerCreateResult.id > 0)

		// Deleting customer
		val customerDeleteRequest = CustomerDeleteRequest(customerId = customerCreateResult.id)
		val customerDeleteResult = apiClient.deleteCustomer(customerDeleteRequest)
		assertEquals(1, customerDeleteResult.deleteCount)

		val instant = Date().toInstant()
		val instantFrom = instant.minusSeconds(10)
		val instantTo = instant.plusSeconds(10)

		// Checking that just deleted customer returned from api
		val deletedCustomersSearchRequest = DeletedCustomersSearchRequest(
				deletedFrom = Date.from(instantFrom),
				deletedTo = Date.from(instantTo)
		)
		val deletedCustomers = apiClient.searchDeletedCustomersAsSequence(deletedCustomersSearchRequest)
		val deletedCustomer = deletedCustomers.firstOrNull { deletedCustomer -> deletedCustomer.id == customerCreateResult.id }
		require(deletedCustomer != null)
		assertTrue(instantFrom.isBefore(deletedCustomer.date.toInstant()))
		assertTrue(instantTo.isAfter(deletedCustomer.date.toInstant()))
	}

	private fun assertCustomersSearch(positiveCustomerId: Int, positiveSearchRequest: CustomersSearchRequest, negativeSearchRequest: CustomersSearchRequest) {
		val positiveCustomersSearchResult = apiClient.searchCustomers(positiveSearchRequest)
		assertEquals(1, positiveCustomersSearchResult.total)
		assertEquals(positiveCustomerId, positiveCustomersSearchResult.items[0].id)

		val negativeCustomersSearchResult = apiClient.searchCustomers(negativeSearchRequest)
		assertEquals(0, negativeCustomersSearchResult.total)
	}

	private fun assertCustomersSortBySearch(expectedCustomerId: Int, sortBy: SortOrder) {
		val customersSearchRequest = CustomersSearchRequest(
				sortBy = sortBy,
				offset = 0,
				limit = 1
		)
		val positiveCustomersSearchResult = apiClient.searchCustomers(customersSearchRequest)
		assertEquals(expectedCustomerId, positiveCustomersSearchResult.items[0].id)
	}

}

private fun UpdatedCustomer.cleanupForComparison(customerCreateRequest: CustomerCreateRequest): UpdatedCustomer {
	return copy(
			// Password is write only field
			password = customerCreateRequest.newCustomer.password,
			// Shipping address ids were just added by server side
			shippingAddresses = shippingAddresses?.map { shippingAddress ->
				shippingAddress.copy(id = null)
			}
	)
}

private fun FetchedCustomer.withSortedShippingAddresses(): FetchedCustomer {
	return copy(
			// TODO Probably API bug (see https://track.ecwid.com/youtrack/issue/ECWID-49859)
			shippingAddresses = shippingAddresses?.sortedBy(FetchedCustomer.ShippingAddress::id)
	)
}

private fun generateTestCustomerForCreate(customerGroupId: Int?): UpdatedCustomer {
	return UpdatedCustomer(
			email = randomEmail(),
			password = randomAlphanumeric(8),
			customerGroupId = customerGroupId,
			billingPerson = generateBillingPerson(),
			shippingAddresses = listOf(
					generateShippingAddress(),
					generateShippingAddress(),
					generateShippingAddress(),
					generateShippingAddress()
			),
			taxId = randomAlphanumeric(8),
			taxIdValid = randomBoolean(),
			taxExempt = randomBoolean()
	)
}

private fun generateTestCustomerForUpdate(customerGroupId: Int, oldShippingAddresses: List<FetchedCustomer.ShippingAddress>?): UpdatedCustomer {
	return UpdatedCustomer(
			email = randomEmail(),
			password = null,
			customerGroupId = customerGroupId,
			billingPerson = generateBillingPerson(),
			shippingAddresses = listOf(
					generateShippingAddress(oldShippingAddresses?.get(0)?.id),
					generateShippingAddress(oldShippingAddresses?.get(1)?.id),
					generateShippingAddress(oldShippingAddresses?.get(2)?.id),
					generateShippingAddress(oldShippingAddresses?.get(3)?.id)
			),
			taxId = randomAlphanumeric(8),
			taxIdValid = randomBoolean(),
			taxExempt = randomBoolean()
	)
}

private fun generateBillingPerson() = UpdatedCustomer.BillingPerson(
		name = "Name " + randomAlphanumeric(8),
		companyName = "Company name " + randomAlphanumeric(8),
		street = "Street " + randomAlphanumeric(16),
		city = "City " + randomAlphanumeric(8),
		countryCode = "US",
		postalCode = randomAlphanumeric(6),
		stateOrProvinceCode = "CA",
		phone = randomAlphanumeric(10)
)

private fun generateShippingAddress(id: Int? = null) = UpdatedCustomer.ShippingAddress(
		id = id,
		name = "Name " + randomAlphanumeric(8),
		companyName = "Company name " + randomAlphanumeric(8),
		street = "Street " + randomAlphanumeric(16),
		city = "City " + randomAlphanumeric(8),
		countryCode = "US",
		postalCode = randomAlphanumeric(6),
		stateOrProvinceCode = "CA",
		phone = randomAlphanumeric(10)
)
