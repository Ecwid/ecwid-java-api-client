package com.ecwid.apiclient.v3.entity

import com.ecwid.apiclient.v3.converter.toUpdated
import com.ecwid.apiclient.v3.dto.customergroup.request.*
import com.ecwid.apiclient.v3.exception.EcwidApiException
import com.ecwid.apiclient.v3.util.randomAlphanumeric
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

const val TEST_SEARCH_PHRASE = "Test"
const val TEST_CUSTOMER_GROUP = "Customer group"

class CustomerGroupsTest : BaseEntityTest() {

	@BeforeEach
	override fun beforeEach() {
		super.beforeEach()

		// We need to start from scratch each time
		removeAllCustomerGroups()
	}

	@Test
	fun testCustomerGroupLifecycle() {
		// Creating new customer group
		val customerGroupCreateRequest = CustomerGroupCreateRequest(
			newCustomerGroup = generateTestCustomerGroup()
		)
		val customerGroupCreateResult = apiClient.createCustomerGroup(customerGroupCreateRequest)
		assertTrue(customerGroupCreateResult.id > 0)

		// Checking that customer group was successfully created with necessary parameters
		val customerGroupDetailsRequest = CustomerGroupDetailsRequest(customerGroupId = customerGroupCreateResult.id)
		val customerGroupDetails1 = apiClient.getCustomerGroupDetails(customerGroupDetailsRequest)
		assertEquals(customerGroupCreateRequest.newCustomerGroup, customerGroupDetails1.toUpdated())

		// Completely updating newly created customer group
		val customerGroupUpdateRequest = CustomerGroupUpdateRequest(
			customerGroupId = customerGroupDetails1.id,
			updatedCustomerGroup = generateTestCustomerGroup()
		)
		val customerGroupUpdateResult1 = apiClient.updateCustomerGroup(customerGroupUpdateRequest)
		assertEquals(1, customerGroupUpdateResult1.updateCount)

		// Checking that customer group was successfully updated with necessary parameters
		val customerGroupDetails2 = apiClient.getCustomerGroupDetails(customerGroupDetailsRequest)
		assertEquals(customerGroupUpdateRequest.updatedCustomerGroup, customerGroupDetails2.toUpdated())

		// Deleting customer group
		val customerGroupDeleteRequest = CustomerGroupDeleteRequest(customerGroupId = customerGroupDetails1.id)
		val customerGroupDeleteResult = apiClient.deleteCustomerGroup(customerGroupDeleteRequest)
		assertEquals(1, customerGroupDeleteResult.deleteCount)

		// Checking that deleted customer group is not accessible anymore
		try {
			apiClient.getCustomerGroupDetails(customerGroupDetailsRequest)
		} catch (e: EcwidApiException) {
			assertEquals(404, e.statusCode)
			assertEquals("Customer group #${customerGroupCreateResult.id} not found", e.message)
		}
	}

	@Test
	fun testSearchPaging() {
		// Create 6 customer groups to test paging
		val customerGroups = generateSearchTestCustomerGroups()
		repeat(customerGroups.size) {
			val customerGroupCreateRequest = CustomerGroupCreateRequest(
				newCustomerGroup = customerGroups[it]
			)
			val customerGroupCreateResult = apiClient.createCustomerGroup(customerGroupCreateRequest)
			assertTrue(customerGroupCreateResult.id > 0)
		}

		// Trying to request first page
		val customerGroupsSearchRequest1 = CustomerGroupsSearchRequest(offset = 0, limit = 2)
		val customerGroupsSearchResult1 = apiClient.searchCustomerGroups(customerGroupsSearchRequest1)
		assertEquals(2, customerGroupsSearchResult1.count) // “General” group exists only of first page
		assertEquals(7, customerGroupsSearchResult1.total)

		// Trying to request second and the last page
		val customerGroupsSearchRequest2 = CustomerGroupsSearchRequest(offset = 6, limit = 2)
		val customerGroupsSearchResult2 = apiClient.searchCustomerGroups(customerGroupsSearchRequest2)
		assertEquals(1, customerGroupsSearchResult2.count) // “General” group exists only of first page
		assertEquals(7, customerGroupsSearchResult2.total)

		// test by keyword "Customer group"
		val customerGroupsSearchRequest3 = CustomerGroupsSearchRequest(
			keyword = TEST_CUSTOMER_GROUP,
		)
		val customerGroupsSearchResult3 = apiClient.searchCustomerGroups(customerGroupsSearchRequest3)
		assertEquals(true, customerGroupsSearchResult3.items.all { it.name.contains(TEST_CUSTOMER_GROUP) })

		// test by keyword "Test"
		val customerGroupsSearchRequest4 = CustomerGroupsSearchRequest(
			keyword = TEST_SEARCH_PHRASE,
		)
		val customerGroupsSearchResult4 = apiClient.searchCustomerGroups(customerGroupsSearchRequest4)
		assertEquals(true, customerGroupsSearchResult4.items.all { it.name.contains(TEST_SEARCH_PHRASE) })

		val testGroupIds = customerGroupsSearchResult4.items.map { it.id }

		// test by customerGroupIds
		val customerGroupsSearchRequest5 = CustomerGroupsSearchRequest(
			customerGroupIds = testGroupIds,
		)
		val customerGroupsSearchResult5 = apiClient.searchCustomerGroups(customerGroupsSearchRequest5)
		assertEquals(testGroupIds.size, customerGroupsSearchResult5.total)
		assertEquals(testGroupIds, customerGroupsSearchResult5.items.map { it.id })

	}
}

private fun generateTestCustomerGroup(): UpdatedCustomerGroup {
	return UpdatedCustomerGroup(
		name = "$TEST_CUSTOMER_GROUP " + randomAlphanumeric(8)
	)
}

private fun generateSearchTestCustomerGroups(): List<UpdatedCustomerGroup> {
	val result = mutableListOf<UpdatedCustomerGroup>()
	repeat(3) {
		result.add(UpdatedCustomerGroup("$TEST_CUSTOMER_GROUP $it"))
		result.add(UpdatedCustomerGroup("$TEST_SEARCH_PHRASE $it"))
	}
	return result
}
