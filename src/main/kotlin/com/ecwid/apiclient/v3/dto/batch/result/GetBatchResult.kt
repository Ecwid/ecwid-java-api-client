package com.ecwid.apiclient.v3.dto.batch.result

import com.google.gson.annotations.SerializedName

data class GetBatchResult(
        var status: String,
        // TODO: remove this gson specific annotation
        @SerializedName("total_requests")
        var totalRequests: Int,
        // TODO: remove this gson specific annotation
        @SerializedName("completed_requests")
        var completedRequests: Int,
        var responses: List<SingleBatchResponse>
)

data class SingleBatchResponse(
        var id: String,
        // TODO: remove this gson specific annotation
        @SerializedName("http_body")
        var httpBody: String?,
        // TODO: remove this gson specific annotation
        @SerializedName("http_status_code")
        var httpStatusCode: Int,
        // TODO: remove this gson specific annotation
        @SerializedName("http_status_line")
        var httpStatusLine: String,
        var status: BatchResponseStatus
)

enum class BatchResponseStatus {
    COMPLETED,
    FAILED,
    NOT_EXECUTED
}