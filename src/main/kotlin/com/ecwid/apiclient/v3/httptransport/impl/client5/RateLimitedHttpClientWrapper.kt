package com.ecwid.apiclient.v3.httptransport.impl.client5

import com.ecwid.apiclient.v3.metric.RequestRetrySleepMetric
import org.apache.hc.client5.http.classic.HttpClient
import org.apache.hc.core5.http.ClassicHttpRequest
import org.apache.hc.core5.http.ClassicHttpResponse
import org.apache.hc.core5.http.io.HttpClientResponseHandler
import org.apache.hc.core5.http.io.entity.EntityUtils
import java.io.IOException
import java.util.concurrent.TimeUnit
import java.util.logging.Logger

private val log = Logger.getLogger(RateLimitedHttpClientWrapper::class.qualifiedName)

private const val SC_TOO_MANY_REQUESTS = 429

/**
 * Wrapper for httpClient, which retries requests if faces 429 error response.
 * Respects server's Retry-After header if provided.
 */
open class RateLimitedHttpClientWrapper(
	private val httpClient: HttpClient,
	private val defaultRateLimitRetryInterval: Long = DEFAULT_RATE_LIMIT_RETRY_INTERVAL_SECONDS,
	private val maxRateLimitRetryInterval: Long = MAX_RATE_LIMIT_RETRY_INTERVAL_SECONDS,
	private val totalAttempts: Int = DEFAULT_RATE_LIMIT_ATTEMPTS,
	private val onEverySecondOfWaiting: (Long) -> Unit = { },
	private val beforeEachRequestAttempt: () -> Unit = { },
) {

	@Throws(IOException::class)
	fun <T> execute(request: ClassicHttpRequest, responseHandler: HttpClientResponseHandler<T>): T {
		return executeWithRetry(request, totalAttempts, responseHandler)
	}

	private fun <T> executeWithRetry(
		request: ClassicHttpRequest,
		attemptsLeft: Int,
		responseHandler: HttpClientResponseHandler<T>
	): T {
		beforeEachRequestAttempt()
		return httpClient.execute(request) { response ->
			if (response.code == SC_TOO_MANY_REQUESTS && attemptsLeft > 0) {
				process429(
					request,
					response,
					attemptsLeft - 1, // decrement attempts reminder
					responseHandler
				)
			} else if (response.code == SC_TOO_MANY_REQUESTS && attemptsLeft <= 0) {
				log.warning("Request ${request.uri.path} rate-limited: no more attempts.")
				responseHandler.handleResponse(response)
			} else {
				responseHandler.handleResponse(response)
			}
		}
	}

	private fun <T> process429(
		request: ClassicHttpRequest,
		response: ClassicHttpResponse,
		attemptsLeft: Int,
		responseHandler: HttpClientResponseHandler<T>
	): T {
		// server must inform how long to wait
		val waitInterval = response.getFirstHeader("Retry-After")?.value?.toLong()
			?: defaultRateLimitRetryInterval
		return if (waitInterval <= maxRateLimitRetryInterval) {
			// return used http connection to pool before retry
			EntityUtils.consume(response.entity)
			// if server requested acceptable time, we'll wait
			log.info("Request ${request.uri.path} rate-limited: waiting $waitInterval seconds...")
			waitSeconds(waitInterval, onEverySecondOfWaiting)
			log.info("Retrying ${request.uri.path} after $waitInterval-s pause...")
			executeWithRetry(request, attemptsLeft, responseHandler)
		} else {
			// too long to wait - let's return the original error
			log.warning("Request ${request.uri.path} rate-limited: too long to wait ($waitInterval).")
			responseHandler.handleResponse(response)
		}
	}

	private fun waitSeconds(waitInterval: Long, onEverySecondOfWaiting: (Long) -> Unit) {
		for (second in 1..waitInterval) {
			TimeUnit.SECONDS.sleep(1)
			onEverySecondOfWaiting(second)
			RequestRetrySleepMetric.inc()
		}
	}
}
