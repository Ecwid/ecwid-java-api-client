package com.ecwid.apiclient.v3.dto.batch.result

data class GetEscapedBatchResult(
		var status: BatchStatus = BatchStatus.QUEUED,
		var totalRequests: Int = 0,
		var completedRequests: Int = 0,
		var responses: List<EscapedSingleBatchResponse>? = null
)

data class EscapedSingleBatchResponse(
		var id: String = "",
		var escapedHttpBody: String = "",
		var httpStatusCode: Int = 0,
		var httpStatusLine: String = "",
		var status: BatchResponseStatus = BatchResponseStatus.NOT_EXECUTED
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
