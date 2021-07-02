package com.ecwid.apiclient.v3.entity

import com.ecwid.apiclient.v3.dto.batch.request.CreateBatchRequestWithIds
import com.ecwid.apiclient.v3.dto.batch.request.GetEscapedBatchRequest
import com.ecwid.apiclient.v3.dto.batch.result.BatchStatus
import com.ecwid.apiclient.v3.dto.product.request.ProductCreateRequest
import com.ecwid.apiclient.v3.dto.product.request.ProductsSearchRequest
import com.ecwid.apiclient.v3.dto.product.request.UpdatedProduct
import com.ecwid.apiclient.v3.dto.product.result.ProductCreateResult
import com.ecwid.apiclient.v3.dto.product.result.ProductsSearchResult
import com.ecwid.apiclient.v3.impl.TypedBatchResponse
import com.ecwid.apiclient.v3.util.randomAlphanumeric
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class BatchApiTest : BaseEntityTest() {

	@BeforeEach
	override fun beforeEach() {
		super.beforeEach()
	}

	@Test
	fun `Create a product with batch API and search it`() {
		val name = randomAlphanumeric(8)
		val productCreateRequest = ProductCreateRequest(
			newProduct = UpdatedProduct(
				name = name
			)
		)

		val productSearchRequest = ProductsSearchRequest.ByFilters(
			keyword = name
		)

		val createProductBatch = apiClient.createBatch(
			CreateBatchRequestWithIds(
				requests = mapOf(
					"test_product" to productCreateRequest
				)
			)
		)

		waitForIndexedProducts(productSearchRequest, 1)

		val createProductBatchResult = apiClient.getTypedBatch(
			GetEscapedBatchRequest(
				ticket = createProductBatch.ticket
			)
		)

		val createResponses = createProductBatchResult.responses
		require(createResponses != null)
		when (
			val productCreateResult =
				createResponses.first().toTypedResponse(ProductCreateResult::class.java)
		) {

			is TypedBatchResponse.Ok -> {
				Assertions.assertTrue(productCreateResult.value.id > 0)
			}
			is TypedBatchResponse.ApiError -> Assertions.fail("Api error is unexpected")
			is TypedBatchResponse.ParseError -> Assertions.fail("Parse error is unexpected")
			is TypedBatchResponse.NotExecuted -> Assertions.fail("Not executed error is not expected")
		}

		val searchProductBatch = apiClient.createBatch(
			CreateBatchRequestWithIds(
				requests = mapOf(
					"search_product" to productSearchRequest
				)
			)
		)

		// No way to predict when this query will be executed, let's simply wait
		awaitBatchExecution(searchProductBatch.ticket)

		val searchProductBatchResult = apiClient.getTypedBatch(
			GetEscapedBatchRequest(
				ticket = searchProductBatch.ticket
			)
		)

		val searchResponses = searchProductBatchResult.responses
		require(searchResponses != null)
		when (
			val productsSearchResult =
				searchResponses.first().toTypedResponse(ProductsSearchResult::class.java)
		) {

			is TypedBatchResponse.Ok -> {
				Assertions.assertTrue(productsSearchResult.value.count > 0)
			}
			is TypedBatchResponse.ApiError -> Assertions.fail("Api error is unexpected")
			is TypedBatchResponse.ParseError -> Assertions.fail("Parse error is unexpected")
			is TypedBatchResponse.NotExecuted -> Assertions.fail("Not executed error is not expected")
		}
	}

	private fun awaitBatchExecution(ticket: String) {
		var tries = 0

		do {
			val batchResult = apiClient.getTypedBatch(GetEscapedBatchRequest(ticket = ticket))
			if (batchResult.status == BatchStatus.COMPLETED) {
				return
			}
			tries++
			Thread.sleep(500L * tries)
		} while (tries < 10)

		return Assertions.fail("Failed to await for batch completion")
	}
}
