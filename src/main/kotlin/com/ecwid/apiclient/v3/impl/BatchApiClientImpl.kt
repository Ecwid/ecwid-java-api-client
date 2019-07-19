package com.ecwid.apiclient.v3.impl

import com.ecwid.apiclient.v3.BatchApiClient
import com.ecwid.apiclient.v3.dto.EcwidApiError
import com.ecwid.apiclient.v3.dto.batch.request.CreateBatchRequest
import com.ecwid.apiclient.v3.dto.batch.request.GetBatchRequest
import com.ecwid.apiclient.v3.dto.batch.result.CreateBatchResult
import com.ecwid.apiclient.v3.dto.batch.result.GetBatchResult
import com.ecwid.apiclient.v3.dto.batch.result.GetEscapedBatchResult
import com.ecwid.apiclient.v3.dto.batch.result.GetTypedBatchResult
import com.ecwid.apiclient.v3.exception.JsonDeserializationException

internal class BatchApiClientImpl(
		private val apiClientHelper: ApiClientHelper
) : BatchApiClient {
	override fun createBatch(request: CreateBatchRequest) = apiClientHelper.makeRequest<CreateBatchResult>(request)

	override fun getTypedBatch(request: GetBatchRequest): GetTypedBatchResult {
		val response = getEscapedBatch(request)
		return GetTypedBatchResult(response, apiClientHelper.jsonTransformer)
	}

	override fun getBatch(request: GetBatchRequest) = apiClientHelper.makeRequest<GetBatchResult>(request.copy(escapedJson = false))
	override fun getEscapedBatch(request: GetBatchRequest) = apiClientHelper.makeRequest<GetEscapedBatchResult>(request.copy(escapedJson = true))

}

sealed class TypedBatchResponse<T> {
	data class Ok<T>(val value: T) : TypedBatchResponse<T>()
	data class ApiError<T>(val ecwidApiError: EcwidApiError) : TypedBatchResponse<T>()
	data class ParseError<T>(val jsonDeserializationException: JsonDeserializationException) : TypedBatchResponse<T>()
}
