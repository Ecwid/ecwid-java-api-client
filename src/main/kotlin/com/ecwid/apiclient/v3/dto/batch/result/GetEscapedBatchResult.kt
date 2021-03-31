package com.ecwid.apiclient.v3.dto.batch.result

import com.ecwid.apiclient.v3.dto.common.ApiResultDTO

data class GetEscapedBatchResult(
		val status: BatchStatus = BatchStatus.QUEUED,
		val totalRequests: Int = 0,
		val completedRequests: Int = 0,
		val responses: List<EscapedSingleBatchResponse>? = null
) : ApiResultDTO

data class EscapedSingleBatchResponse(
		val id: String = "",
		val escapedHttpBody: String = "",
		val httpStatusCode: Int = 0,
		val httpStatusLine: String = "",
		val status: BatchResponseStatus = BatchResponseStatus.NOT_EXECUTED
) : ApiResultDTO

enum class BatchStatus {
	QUEUED,
	IN_PROGRESS,
	COMPLETED
}

enum class BatchResponseStatus {
	COMPLETED,
	FAILED,
	NOT_EXECUTED
}
