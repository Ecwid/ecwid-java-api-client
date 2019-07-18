package com.ecwid.apiclient.v3.impl

import com.ecwid.apiclient.v3.BatchApiClient
import com.ecwid.apiclient.v3.dto.EcwidApiError
import com.ecwid.apiclient.v3.dto.batch.request.CreateBatchRequest
import com.ecwid.apiclient.v3.dto.batch.request.GetBatchRequest
import com.ecwid.apiclient.v3.dto.batch.request.SingleBatchRequest
import com.ecwid.apiclient.v3.dto.batch.result.*
import com.ecwid.apiclient.v3.dto.product.request.ProductCreateRequest
import com.ecwid.apiclient.v3.exception.JsonDeserializationException
import com.ecwid.apiclient.v3.httptransport.HttpBody
import com.ecwid.apiclient.v3.jsontransformer.JsonTransformer

internal class BatchApiClientImpl(
        private val apiClientHelper: ApiClientHelper
) : BatchApiClient {
    override fun createBatch(request: CreateBatchRequest) = apiClientHelper.makePostRequest<CreateBatchResult>(
            endpoint = request.toEndpoint(),
            params = mapOf(),
            httpBody = HttpBody.StringBody(
                    body = apiClientHelper.serializeJson(request.requests.mapToExactRequests()),
                    mimeType = MIME_TYPE_APPLICATION_JSON
            )
    )

    override fun getTypedBatch(request: GetBatchRequest): GetTypedBatchResult {
        val response = getEscapedBatch(request)
        return GetTypedBatchResult(response, object : JsonTransformer {
            override fun serialize(src: Any?) = apiClientHelper.serializeJson(src)
            override fun <V> deserialize(json: String, clazz: Class<V>) = apiClientHelper.deserializeJson(json, clazz)
        })
    }

    override fun getBatch(request: GetBatchRequest) = apiClientHelper.makeGetRequest<GetBatchResult>(
            endpoint = request.toEndpoint(),
            params = mapOf(
                    "ticket" to request.ticket,
                    "escapedJson" to "false"
            )
    )

    override fun getEscapedBatch(request: GetBatchRequest) = apiClientHelper.makeGetRequest<GetEscapedBatchResult>(
            endpoint = request.toEndpoint(),
            params = mapOf(
                    "ticket" to request.ticket,
                    "escapedJson" to "true"
            )
    )

}

sealed class TypedBatchResponse<T> {
    data class Ok<T>(val value: T) : TypedBatchResponse<T>()
    data class ApiError<T>(val ecwidApiError: EcwidApiError) : TypedBatchResponse<T>()
    data class ParseError<T>(val jsonDeserializationException: JsonDeserializationException) : TypedBatchResponse<T>()
}

private fun GetBatchRequest.toEndpoint() = "batch"
private fun CreateBatchRequest.toEndpoint() = "batch"

// TODO: eliminate this code
private fun List<SingleBatchRequest>.mapToExactRequests() = map {
    val body = it.body ?: return@map it
    when (body) {
        is ProductCreateRequest -> {
            it.copy(
                    body = body.newProduct
            )
        }
        else -> {
            return@map it
        }
    }
}