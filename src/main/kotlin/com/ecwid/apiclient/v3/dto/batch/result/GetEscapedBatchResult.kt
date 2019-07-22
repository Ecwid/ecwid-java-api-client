package com.ecwid.apiclient.v3.dto.batch.result

import com.google.gson.annotations.SerializedName

data class GetEscapedBatchResult(
        var status: BatchStatus,
        // TODO: remove this gson specific annotation
        @SerializedName("total_requests")
        var totalRequests: Int,
        // TODO: remove this gson specific annotation
        @SerializedName("completed_requests")
        var completedRequests: Int,
        var responses: List<EscapedSingleBatchResponse>?
)

data class EscapedSingleBatchResponse(
        var id: String,
        // TODO: remove this gson specific annotation
        @SerializedName("escaped_http_body")
        var escapedHttpBody: String?,
        // TODO: remove this gson specific annotation
        @SerializedName("http_status_code")
        var httpStatusCode: Int,
        // TODO: remove this gson specific annotation
        @SerializedName("http_status_line")
        var httpStatusLine: String,
        var status: BatchResponseStatus
)