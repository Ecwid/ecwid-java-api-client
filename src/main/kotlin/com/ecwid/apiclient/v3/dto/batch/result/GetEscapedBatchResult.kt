package com.ecwid.apiclient.v3.dto.batch.result

data class GetEscapedBatchResult(
		val status: BatchStatus = BatchStatus.QUEUED,
		val totalRequests: Int = 0,
		val completedRequests: Int = 0,
		val responses: List<EscapedSingleBatchResponse>? = null
)

data class EscapedSingleBatchResponse(
		val id: String = "",
		val escapedHttpBody: String = "",
		val httpStatusCode: Int = 0,
		val httpStatusLine: String = "",
		val status: BatchResponseStatus = BatchResponseStatus.NOT_EXECUTED
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
