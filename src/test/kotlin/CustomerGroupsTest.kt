import com.ecwid.api.v3.ApiClient
import com.ecwid.api.v3.config.ApiServerDomain
import com.ecwid.api.v3.config.ApiStoreCredentials
import com.ecwid.api.v3.config.LoggingSettings
import com.ecwid.api.v3.dto.customergroup.request.*
import com.ecwid.api.v3.dto.customergroup.result.FetchedCustomerGroup
import com.ecwid.api.v3.exception.EcwidApiException
import org.junit.Assert.assertTrue
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import util.PropertiesLoader
import util.randomAlphanumeric


class CustomerGroupsTest {

	private lateinit var apiClient: ApiClient

	@BeforeEach
	fun beforeEach() {
		apiClient = ApiClient(
				apiServerDomain = ApiServerDomain(),
				storeCredentials = ApiStoreCredentials(
						storeId = PropertiesLoader.storeId,
						apiToken = PropertiesLoader.apiToken
				),
				loggingSettings = LoggingSettings().copy(
						logRequestBody = true,
						logSuccessfulResponseBody = true
				)
		)

		// We need to start from scratch each time
		removeAllCustomerGroups()
	}

	@Test
	fun orderLifecycle() {
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

		// Checking that ustomer group was successfully updated with necessary parameters
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
		// Create two customer groups additionally to always existing “General” group
		for (i in 1..3) {
			val customerGroupCreateRequest = CustomerGroupCreateRequest(
					newCustomerGroup = generateTestCustomerGroup()
			)
			val customerGroupCreateResult = apiClient.createCustomerGroup(customerGroupCreateRequest)
			assertTrue(customerGroupCreateResult.id > 0)
		}

		// Trying to request first page 
		val customerGroupsSearchRequest1 = CustomerGroupsSearchRequest(offset = 0, limit = 2)
		val customerGroupsSearchResult1 = apiClient.searchCustomerGroups(customerGroupsSearchRequest1)
		assertEquals(2 + 1, customerGroupsSearchResult1.count) // “General” group exists is on every page
		assertEquals(3, customerGroupsSearchResult1.total)

		// Trying to request second and the last page 
		val customerGroupsSearchRequest2 = CustomerGroupsSearchRequest(offset = 2, limit = 2)
		val customerGroupsSearchResult2 = apiClient.searchCustomerGroups(customerGroupsSearchRequest2)
		assertEquals(1 + 1, customerGroupsSearchResult2.count) // “General” group exists is on every page
		assertEquals(3, customerGroupsSearchResult2.total)
	}

	private fun removeAllCustomerGroups() {
		apiClient
				.searchCustomerGroupsAsSequence(CustomerGroupsSearchRequest())
				.map(FetchedCustomerGroup::id)
				.filterNotNull()
				.filter { customerGroupId -> customerGroupId > 0} // We cannot delete “General” customer group
				.forEach { customerGroupId ->
					apiClient.deleteCustomerGroup(CustomerGroupDeleteRequest(customerGroupId))
				}
	}

}

private fun generateTestCustomerGroup(): UpdatedCustomerGroup {
	return UpdatedCustomerGroup(
			name = "Customer group " + randomAlphanumeric(8)
	)
}

private fun FetchedCustomerGroup.toUpdated(): UpdatedCustomerGroup {
	return UpdatedCustomerGroup(
			name = name
	)
}
