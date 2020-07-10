package com.ecwid.apiclient.v3.impl

import com.ecwid.apiclient.v3.ApiClientHelper
import com.ecwid.apiclient.v3.BatchApiClient
import com.ecwid.apiclient.v3.dto.EcwidApiError
import com.ecwid.apiclient.v3.dto.batch.request.CreateBatchRequest
import com.ecwid.apiclient.v3.dto.batch.request.CreateBatchRequestWithIds
import com.ecwid.apiclient.v3.dto.batch.request.GetEscapedBatchRequest
import com.ecwid.apiclient.v3.dto.batch.result.CreateBatchResult
import com.ecwid.apiclient.v3.dto.batch.result.GetEscapedBatchResult
import com.ecwid.apiclient.v3.dto.batch.result.GetTypedBatchResult
import com.ecwid.apiclient.v3.exception.JsonDeserializationException

internal class BatchApiClientImpl(
		private val apiClientHelper: ApiClientHelper
) : BatchApiClient {
	override fun createBatch(request: CreateBatchRequestWithIds) = apiClientHelper.makeRequest<CreateBatchResult>(request)
	override fun createBatch(request: CreateBatchRequest) = apiClientHelper.makeRequest<CreateBatchResult>(request)
	override fun getEscapedBatch(request: GetEscapedBatchRequest) = apiClientHelper.makeRequest<GetEscapedBatchResult>(request)
	override fun getTypedBatch(request: GetEscapedBatchRequest) = GetTypedBatchResult(getEscapedBatch(request), apiClientHelper.jsonTransformer)
}

sealed class TypedBatchResponse<T> {
	data class Ok<T>(val value: T) : TypedBatchResponse<T>()
	data class ApiError<T>(val ecwidApiError: EcwidApiError) : TypedBatchResponse<T>()
	data class ParseError<T>(val jsonDeserializationException: JsonDeserializationException) : TypedBatchResponse<T>()
	class NotExecuted<T> : TypedBatchResponse<T>()
}
