package com.ecwid.apiclient.v3

import com.ecwid.apiclient.v3.dto.batch.request.CreateBatchRequest
import com.ecwid.apiclient.v3.dto.batch.request.GetTypedBatchRequest
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
	fun `Create category with batch API`() {

		val categoryCreateRequest = CategoryCreateRequest(
				newCategory = UpdatedCategory(
						name = "Category " + randomAlphanumeric(8)
				)
		)

		val createBatchResult = apiClient.createBatch(
				CreateBatchRequest(
						requests = mapOf("test_id" to categoryCreateRequest)
				)
		)

		TimeUnit.SECONDS.sleep(5)

		val getBatchResult = apiClient.getTypedBatch(
				GetTypedBatchRequest(
						ticket = createBatchResult.ticket
				)
		)

		val categoryCreateResult = getBatchResult.responses!!.first().toTypedResponse(CategoryCreateResult::class.java)
		when (categoryCreateResult) {

			is TypedBatchResponse.Ok -> {
				Assertions.assertTrue(categoryCreateResult.value.id > 0)
			}
			is TypedBatchResponse.ApiError -> Assertions.fail("Api error is unexpected")
			is TypedBatchResponse.ParseError -> Assertions.fail("Parse error is unexpected")
			is TypedBatchResponse.NotExecuted -> Assertions.fail("Not executed error is not expected")
		}
	}
}