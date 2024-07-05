package com.ecwid.apiclient.v3.metric

import io.prometheus.metrics.core.metrics.Counter

object RequestRetrySleepMetric {
	private val metric: Counter = Counter.builder()
		.name("ecwid_api_client_retry_sleep_seconds_total")
		.help("Ecwid API client sleep during retries as result of rate limits (429 http)")
		.register()

	fun inc() {
		metric.inc()
	}
}
