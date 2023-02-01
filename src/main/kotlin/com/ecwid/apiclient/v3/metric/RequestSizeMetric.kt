package com.ecwid.apiclient.v3.metric

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.httptransport.HttpRequest
import com.ecwid.apiclient.v3.httptransport.HttpResponse
import com.ecwid.apiclient.v3.httptransport.TransportHttpBody
import com.ecwid.apiclient.v3.impl.RequestInfo
import io.prometheus.client.Histogram
import java.util.concurrent.atomic.AtomicLong
import java.util.logging.Level
import java.util.logging.Logger

private val log = Logger.getLogger(RequestSizeMetric::class.qualifiedName)

object RequestSizeMetric {
	private val metric: Histogram = Histogram
		.build("ecwid_api_client_request_size_bytes", "Ecwid API client request size of parameters & body in bytes")
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
			50_000_000.0,
		)
		.labelNames("request_type", "endpoint", "method", "status")
		.register()

	fun observeRequest(
		apiRequest: ApiRequest,
		requestInfo: RequestInfo,
		size: AtomicLong,
		httpResponse: HttpResponse,
	) {
		metric
			.labels(
				apiRequest.buildMetricName(),
				requestInfo.getFirstPathSegment(),
				requestInfo.method.name,
				extractStatusFromHttpResponse(httpResponse),
			)
			.observe(size.toDouble())
	}

	fun makeHttpRequestCountable(httpRequest: HttpRequest): HttpRequestAndCounter {
		val parametersSizeInBytes = httpRequest.params.countSizeInBytes()
		val headersSizeInBytes = httpRequest.headers.countSizeInBytes()

		val counter = AtomicLong(parametersSizeInBytes + headersSizeInBytes)

		return HttpRequestAndCounter(
			httpRequest = wrapHttpRequestToCountable(httpRequest, counter),
			counter = counter,
		)
	}

	private fun Map<String, String>.countSizeInBytes() = entries
		.sumOf { it.key.length + it.value.length }
		.toLong()

	private fun wrapHttpRequestToCountable(httpRequest: HttpRequest, counter: AtomicLong): HttpRequest {
		return when (httpRequest) {
			is HttpRequest.HttpDeleteRequest -> httpRequest

			is HttpRequest.HttpGetRequest -> httpRequest

			is HttpRequest.HttpPostRequest -> httpRequest.copy(
				transportHttpBody = wrapTransportHttpBodyToCountable(
					httpBody = httpRequest.transportHttpBody,
					counter = counter,
				)
			)

			is HttpRequest.HttpPutRequest -> httpRequest.copy(
				transportHttpBody = wrapTransportHttpBodyToCountable(
					httpBody = httpRequest.transportHttpBody,
					counter = counter,
				)
			)
		}
	}

	private fun wrapTransportHttpBodyToCountable(
		httpBody: TransportHttpBody,
		counter: AtomicLong
	): TransportHttpBody {
		return when (httpBody) {
			is TransportHttpBody.ByteArrayBody -> httpBody.also {
				counter.addAndGet(httpBody.byteArray.size.toLong())
			}

			TransportHttpBody.EmptyBody -> httpBody

			is TransportHttpBody.InputStreamBody -> TransportHttpBody.InputStreamBody(
				stream = CountingInputStream(httpBody.stream, counter),
				mimeType = httpBody.mimeType,
			)

			is TransportHttpBody.LocalFileBody -> httpBody.also {
				try {
					counter.addAndGet(httpBody.file.length())
				} catch (e: Exception) {
					log.log(Level.WARNING, "Unable to get file [${httpBody.file.absolutePath}] size", e)
				}
			}
		}
	}

	data class HttpRequestAndCounter(
		val httpRequest: HttpRequest,
		val counter: AtomicLong,
	)
}
