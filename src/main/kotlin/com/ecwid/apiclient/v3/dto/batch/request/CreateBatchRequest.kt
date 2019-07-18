package com.ecwid.apiclient.v3.dto.batch.request

data class SingleBatchRequest(
        var id: String?,
        var path: String,
        var method: String,
        var body: Any?
)

data class CreateBatchRequest(
        val requests: List<SingleBatchRequest>
)
