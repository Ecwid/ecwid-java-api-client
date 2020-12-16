package com.ecwid.apiclient.v3.dto.batch.result

import com.ecwid.apiclient.v3.dto.common.EcwidApiError
import com.ecwid.apiclient.v3.exception.JsonDeserializationException
import com.ecwid.apiclient.v3.impl.TypedBatchResponse
import com.ecwid.apiclient.v3.jsontransformer.JsonTransformer

data class GetTypedBatchResult(
		val status: BatchStatus,
		val totalRequests: Int,
		val completedRequests: Int,
		val responses: List<TypedSingleBatchResponse>?
) {

	internal constructor(
			escapedBatchResult: GetEscapedBatchResult,
			jsonTransformer: JsonTransformer
	) : this(
			status = escapedBatchResult.status,
			totalRequests = escapedBatchResult.totalRequests,
			completedRequests = escapedBatchResult.completedRequests,
			responses = escapedBatchResult.responses?.map { TypedSingleBatchResponse(it, jsonTransformer) }
	)

}

data class TypedSingleBatchResponse(
		val id: String,
		val escapedHttpBody: String,
		val httpStatusCode: Int,
		val httpStatusLine: String,
		val status: BatchResponseStatus,
		val jsonTransformer: JsonTransformer
) {

	internal constructor(
			escapedSingleBatchResponse: EscapedSingleBatchResponse,
			jsonTransformer: JsonTransformer
	): this(
			id = escapedSingleBatchResponse.id,
			escapedHttpBody = escapedSingleBatchResponse.escapedHttpBody,
			httpStatusCode = escapedSingleBatchResponse.httpStatusCode,
			httpStatusLine = escapedSingleBatchResponse.httpStatusLine,
			status = escapedSingleBatchResponse.status,
			jsonTransformer = jsonTransformer
	)

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
