package com.ecwid.apiclient.v3.metric

import io.prometheus.client.Counter

object RequestRetrySleepMetric {
	private val metric: Counter = Counter
		.build(
			"ecwid_api_client_retry_sleep_seconds",
			"Ecwid API client sleep during retries as result of rate limits (429 http)",
		)
		.register()

	fun inc() {
		metric.inc()
	}
}
