package com.ecwid.apiclient.v3

import com.ecwid.apiclient.v3.dto.batch.request.CreateBatchRequest
import com.ecwid.apiclient.v3.dto.batch.request.GetTypedBatchRequest
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
import java.util.concurrent.TimeUnit

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
				CreateBatchRequest(
						requests = mapOf(
								"test_product" to productCreateRequest
						)
				)
		)

		waitForProductCount(productSearchRequest, 1)

		val createProductBatchResult = apiClient.getTypedBatch(
				GetTypedBatchRequest(
						ticket = createProductBatch.ticket
				)
		)

		val createResponses = createProductBatchResult.responses
		require(createResponses != null)
		val productCreateResult = createResponses.first().toTypedResponse(ProductCreateResult::class.java)
		when (productCreateResult) {

			is TypedBatchResponse.Ok -> {
				Assertions.assertTrue(productCreateResult.value.id > 0)
			}
			is TypedBatchResponse.ApiError -> Assertions.fail("Api error is unexpected")
			is TypedBatchResponse.ParseError -> Assertions.fail("Parse error is unexpected")
			is TypedBatchResponse.NotExecuted -> Assertions.fail("Not executed error is not expected")
		}


		val searchProductBatch = apiClient.createBatch(
				CreateBatchRequest(
						requests = mapOf(
								"search_product" to productSearchRequest
						)
				)
		)

		// No way to predict when this query will be executed, let's simply wait
		TimeUnit.SECONDS.sleep(5)

		val searchProductBatchResult = apiClient.getTypedBatch(
				GetTypedBatchRequest(
						ticket = searchProductBatch.ticket
				)
		)

		val searchResponses = searchProductBatchResult.responses
		require(searchResponses != null)
		val productsSearchResult = searchResponses.first().toTypedResponse(ProductsSearchResult::class.java)
		when (productsSearchResult) {

			is TypedBatchResponse.Ok -> {
				Assertions.assertTrue(productsSearchResult.value.count > 0)
			}
			is TypedBatchResponse.ApiError -> Assertions.fail("Api error is unexpected")
			is TypedBatchResponse.ParseError -> Assertions.fail("Parse error is unexpected")
			is TypedBatchResponse.NotExecuted -> Assertions.fail("Not executed error is not expected")
		}
	}
}