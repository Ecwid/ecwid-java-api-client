package com.ecwid.apiclient.v3.metric

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.httptransport.HttpResponse
import com.ecwid.apiclient.v3.impl.RequestInfo
import io.prometheus.client.Histogram

object ResponseSizeMetric {
	private val metric: Histogram = Histogram
		.build("ecwid_api_client_response_size_bytes", "Ecwid API client response size in bytes")
		.buckets(
			100.0,
			500.0,
			1_000.0,
			5_000.0,
			10_000.0,
			50_000.0,
			100_000.0,
			500_000.0,
			1_000_000.0,
			5_000_000.0,
			10_000_000.0,
			25_000_000.0,
			50_000_000.0,
			100_000_000.0,
		)
		.labelNames("request_type", "endpoint", "method", "status")
		.register()

	fun observeResponse(
		apiRequest: ApiRequest,
		requestInfo: RequestInfo,
		httpResponse: HttpResponse,
	) {
		metric
			.labels(
				apiRequest.javaClass.simpleName,
				requestInfo.getFirstPathSegment(),
				requestInfo.method.name,
				extractStatusFromHttpResponse(httpResponse),
			)
			.observe(httpResponse.responseBytes.size.toDouble())
	}
}
