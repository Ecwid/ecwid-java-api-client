package com.ecwid.apiclient.v3.dto.batch.result

data class GetEscapedBatchResult(
		var status: BatchStatus,
		var totalRequests: Int,
		var completedRequests: Int,
		var responses: List<EscapedSingleBatchResponse>?
)

data class EscapedSingleBatchResponse(
		var id: String,
		var escapedHttpBody: String?,
		var httpStatusCode: Int,
		var httpStatusLine: String,
		var status: BatchResponseStatus
)

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