package com.ecwid.apiclient.v3.metric

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.httptransport.HttpResponse
import com.ecwid.apiclient.v3.impl.RequestInfo
import io.prometheus.metrics.core.metrics.Histogram
import io.prometheus.metrics.model.snapshots.Unit

object RequestTimeMetric {
	private val metric = Histogram.builder()
		.name("ecwid_api_client_request_latency")
		.help("Ecwid API client request latency")
		.classicOnly()
		.classicUpperBounds(0.005, 0.01, 0.025, 0.05, 0.075, 0.1, 0.25, 0.5, 0.75, 1.0, 2.5, 5.0, 7.5, 10.0, 25.0, 50.0, 100.0, 200.0)
		.labelNames("request_type", "path", "method", "status")
		.unit(Unit.SECONDS)
		.register()

	fun observeRequest(
		apiRequest: ApiRequest,
		requestInfo: RequestInfo,
		requestTimeMs: Long,
		httpResponse: HttpResponse,
	) {
		metric
			.labelValues(
				apiRequest.javaClass.simpleName,
				requestInfo.getFirstPathSegment(),
				requestInfo.method.name,
				extractStatusFromHttpResponse(httpResponse),
			)
			.observe(Unit.millisToSeconds(requestTimeMs))
	}
}
