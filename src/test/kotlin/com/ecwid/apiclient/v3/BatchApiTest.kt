package com.ecwid.apiclient.v3

import com.ecwid.apiclient.v3.dto.batch.request.CreateBatchRequest
import com.ecwid.apiclient.v3.dto.batch.request.GetBatchRequest
import com.ecwid.apiclient.v3.dto.category.request.CategoryCreateRequest
import com.ecwid.apiclient.v3.dto.category.request.UpdatedCategory
import com.ecwid.apiclient.v3.dto.category.result.CategoryCreateResult
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
	fun `Search first page of products`() {

		val productCreateRequest = CategoryCreateRequest(
				newCategory = UpdatedCategory(
						name = "Product " + randomAlphanumeric(8)
				)
		)

		val createBatchResult = apiClient.createBatch(
				CreateBatchRequest(
						requests = mapOf("123123" to productCreateRequest)
				)
		)

		TimeUnit.SECONDS.sleep(5)

		val getBatchResult = apiClient.getTypedBatch(GetBatchRequest(
				ticket = createBatchResult.ticket,
				escapedJson = false
		))

		val productCreateResult = getBatchResult.responses!!.first().toTypedResponse(CategoryCreateResult::class.java)
		Assertions.assertNotNull(productCreateRequest)
		Assertions.assertEquals(productCreateResult!!.javaClass, TypedBatchResponse.Ok::class.java)
		Assertions.assertTrue((productCreateResult as TypedBatchResponse.Ok<CategoryCreateResult>).value.id > 0)

	}
}