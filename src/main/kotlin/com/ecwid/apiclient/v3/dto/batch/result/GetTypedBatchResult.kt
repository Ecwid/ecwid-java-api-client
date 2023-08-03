package com.ecwid.apiclient.v3.dto.batch.result

import com.ecwid.apiclient.v3.dto.common.ApiResultDTO
import com.ecwid.apiclient.v3.dto.common.EcwidApiError
import com.ecwid.apiclient.v3.exception.JsonDeserializationException
import com.ecwid.apiclient.v3.impl.TypedBatchResponse
import com.ecwid.apiclient.v3.jsontransformer.JsonTransformer

data class GetTypedBatchResult(
	val status: BatchStatus = BatchStatus.QUEUED,
	val totalRequests: Int = 0,
	val completedRequests: Int = 0,
	val responses: List<TypedSingleBatchResponse>? = null
) : ApiResultDTO {

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
	val id: String = "",
	val escapedHttpBody: String = "",
	val httpStatusCode: Int = 0,
	val httpStatusLine: String = "",
	val status: BatchResponseStatus = BatchResponseStatus.NOT_EXECUTED,
	val jsonTransformer: JsonTransformer
) : ApiResultDTO {

	internal constructor(
		escapedSingleBatchResponse: EscapedSingleBatchResponse,
		jsonTransformer: JsonTransformer
	) : this(
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
					val deserialized = jsonTransformer.deserialize(escapedHttpBody, clazz)
						?: throw JsonDeserializationException("Cannot deserialize response body to $clazz: $escapedHttpBody", null)
					TypedBatchResponse.Ok(deserialized)
				} catch (jsonDeserializationException: JsonDeserializationException) {
					TypedBatchResponse.ParseError(jsonDeserializationException)
				}
			}
			BatchResponseStatus.FAILED -> {
				@Suppress("SwallowedException")
				val ecwidApiError = try {
					jsonTransformer.deserialize(escapedHttpBody, EcwidApiError::class.java)
				} catch (jsonDeserializationException: JsonDeserializationException) {
					null
				}

				TypedBatchResponse.ApiError(
					httpStatusCode = httpStatusCode,
					httpStatusText = httpStatusLine,
					ecwidApiError = ecwidApiError,
				)
			}
			BatchResponseStatus.NOT_EXECUTED -> {
				TypedBatchResponse.NotExecuted()
			}
		}
	}
}
