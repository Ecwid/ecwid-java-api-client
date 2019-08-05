package com.ecwid.apiclient.v3.dto.batch.result

import com.ecwid.apiclient.v3.dto.EcwidApiError
import com.ecwid.apiclient.v3.exception.JsonDeserializationException
import com.ecwid.apiclient.v3.impl.TypedBatchResponse
import com.ecwid.apiclient.v3.jsontransformer.GsonJsonTransformer

class GetTypedBatchResult(
        escapedBatchResult: GetEscapedBatchResult,
        jsonTransformer: GsonJsonTransformer
) {

    val status: BatchStatus = escapedBatchResult.status
    val totalRequests: Int = escapedBatchResult.totalRequests
    val completedRequests: Int = escapedBatchResult.completedRequests
    val responses: List<TypedSingleBatchResponse>? = escapedBatchResult.responses?.map { TypedSingleBatchResponse(it, jsonTransformer) }
}

class TypedSingleBatchResponse(
        escapedSingleBatchResponse: EscapedSingleBatchResponse,
        private val jsonTransformer: GsonJsonTransformer
) {

    val id: String = escapedSingleBatchResponse.id
    val escapedHttpBody: String = escapedSingleBatchResponse.escapedHttpBody
    val httpStatusCode: Int = escapedSingleBatchResponse.httpStatusCode
    val httpStatusLine: String = escapedSingleBatchResponse.httpStatusLine
    val status: BatchResponseStatus = escapedSingleBatchResponse.status


    fun <T> toTypedResponse(clazz: Class<T>): TypedBatchResponse<T> {
        return when (status) {
            BatchResponseStatus.COMPLETED -> {
                try {
                    TypedBatchResponse.Ok<T>(jsonTransformer.deserialize(escapedHttpBody, clazz)!!)
                } catch (jsonDeserializationException: JsonDeserializationException) {
                    TypedBatchResponse.ParseError<T>(jsonDeserializationException)
                }
            }
            BatchResponseStatus.FAILED -> {
                try {
                    val ecwidError = jsonTransformer.deserialize(escapedHttpBody, EcwidApiError::class.java)!!
                    TypedBatchResponse.ApiError<T>(ecwidError)
                } catch (jsonDeserializationException: JsonDeserializationException) {
                    TypedBatchResponse.ParseError<T>(jsonDeserializationException)
                }
            }
            BatchResponseStatus.NOT_EXECUTED -> {
                TypedBatchResponse.NotExecuted()
            }
        }
    }
}