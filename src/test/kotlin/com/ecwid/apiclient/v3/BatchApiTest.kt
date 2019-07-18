package com.ecwid.apiclient.v3

import com.ecwid.apiclient.v3.dto.batch.request.CreateBatchRequest
import com.ecwid.apiclient.v3.dto.batch.request.GetBatchRequest
import com.ecwid.apiclient.v3.dto.batch.request.SingleBatchRequest
import com.ecwid.apiclient.v3.dto.product.request.ProductCreateRequest
import com.ecwid.apiclient.v3.dto.product.request.ProductsSearchRequest
import com.ecwid.apiclient.v3.dto.product.request.UpdatedProduct
import com.ecwid.apiclient.v3.dto.product.result.ProductCreateResult
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
        val productSearchRequest = ProductsSearchRequest.ByFilters()

        val productCreateRequest = ProductCreateRequest(
                newProduct = UpdatedProduct(
                        name = "Product " + randomAlphanumeric(8)
                )
        )

        val batchSearchRequest = SingleBatchRequest(
                id = "test-request",
                path = "products",
                method = "GET",
                body = productSearchRequest
        )

        val batchCreateProductRequest = SingleBatchRequest(
                id = "test-create-products",
                path = "products",
                method = "POST",
                body = productCreateRequest

        )

        val createBatchResult = apiClient.createBatch(
                CreateBatchRequest(
                        requests = listOf(batchCreateProductRequest)
                )
        )

        TimeUnit.SECONDS.sleep(5)

        val getBatchResult = apiClient.getTypedBatch(GetBatchRequest(
                ticket = createBatchResult.ticket
        ))

        val productCreateResult = getBatchResult.responses.first().toTypedResponse(ProductCreateResult::class.java)
        Assertions.assertNotNull(productCreateRequest)
        Assertions.assertEquals(productCreateResult!!.javaClass, TypedBatchResponse.Ok::class.java)
        Assertions.assertTrue((productCreateResult as TypedBatchResponse.Ok<ProductCreateResult>).value.id > 0)

    }
}