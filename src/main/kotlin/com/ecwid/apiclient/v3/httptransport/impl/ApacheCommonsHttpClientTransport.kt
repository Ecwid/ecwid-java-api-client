package com.ecwid.apiclient.v3.httptransport.impl

import com.ecwid.apiclient.v3.httptransport.HttpRequest
import com.ecwid.apiclient.v3.httptransport.HttpResponse
import com.ecwid.apiclient.v3.httptransport.HttpTransport
import org.apache.http.Header
import org.apache.http.client.HttpClient
import org.apache.http.client.config.RequestConfig
import org.apache.http.impl.client.HttpClientBuilder
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager
import java.io.IOException

private const val DEFAULT_CONNECTION_TIMEOUT = 10_000 // 10 sec
private const val DEFAULT_READ_TIMEOUT = 60_000 // 1 min

private const val DEFAULT_MAX_CONNECTIONS = 10

/**
 * Number of attempts to retry request if server responded with 429
 */
internal const val DEFAULT_RATE_LIMIT_ATTEMPTS = 2

/**
 * Number of seconds to wait until next attempt, if server didn't send Retry-After header
 */
internal const val DEFAULT_RATE_LIMIT_RETRY_INTERVAL_SECONDS = 10L

/**
 * Maximal delay in seconds before next attempt
 */
internal const val MAX_RATE_LIMIT_RETRY_INTERVAL_SECONDS = 60L

val EMPTY_WAITING_REACTION: (Long) -> Unit = { }
val EMPTY_BEFORE_REQUEST_ACTION: () -> Unit = { }

open class ApacheCommonsHttpClientTransport(
	private val httpClient: HttpClient = buildHttpClient(),
	private val rateLimitRetryStrategy: RateLimitRetryStrategy = SleepForRetryAfterRateLimitRetryStrategy(),
) : HttpTransport {

	constructor(
		httpClient: HttpClient,
		defaultRateLimitAttempts: Int = DEFAULT_RATE_LIMIT_ATTEMPTS,
		defaultRateLimitRetryInterval: Long = DEFAULT_RATE_LIMIT_RETRY_INTERVAL_SECONDS,
		maxRateLimitRetryInterval: Long = MAX_RATE_LIMIT_RETRY_INTERVAL_SECONDS,
		onEverySecondOfWaiting: (Long) -> Unit = EMPTY_WAITING_REACTION,
		beforeEachRequestAttempt: () -> Unit = EMPTY_BEFORE_REQUEST_ACTION,
	) : this(
		httpClient,
		rateLimitRetryStrategy = SleepForRetryAfterRateLimitRetryStrategy(
			defaultRateLimitAttempts = defaultRateLimitAttempts,
			defaultRateLimitRetryInterval = defaultRateLimitRetryInterval,
			maxRateLimitRetryInterval = maxRateLimitRetryInterval,
			onEverySecondOfWaiting = onEverySecondOfWaiting,
			beforeEachRequestAttempt = beforeEachRequestAttempt
		)
	)

	constructor(
		defaultConnectionTimeout: Int = DEFAULT_CONNECTION_TIMEOUT,
		defaultReadTimeout: Int = DEFAULT_READ_TIMEOUT,
		defaultMaxConnections: Int = DEFAULT_MAX_CONNECTIONS,
		defaultHeaders: List<Header> = emptyList(),
		rateLimitRetryStrategy: RateLimitRetryStrategy
	) : this(
		httpClient = buildHttpClient(
			defaultConnectionTimeout = defaultConnectionTimeout,
			defaultReadTimeout = defaultReadTimeout,
			defaultMaxConnections = defaultMaxConnections,
			defaultHeaders = defaultHeaders
		),
		rateLimitRetryStrategy = rateLimitRetryStrategy
	)

	constructor(
		defaultConnectionTimeout: Int = DEFAULT_CONNECTION_TIMEOUT,
		defaultReadTimeout: Int = DEFAULT_READ_TIMEOUT,
		defaultMaxConnections: Int = DEFAULT_MAX_CONNECTIONS,
		defaultRateLimitAttempts: Int = DEFAULT_RATE_LIMIT_ATTEMPTS,
		defaultRateLimitRetryInterval: Long = DEFAULT_RATE_LIMIT_RETRY_INTERVAL_SECONDS,
		maxRateLimitRetryInterval: Long = MAX_RATE_LIMIT_RETRY_INTERVAL_SECONDS,
		defaultHeaders: List<Header> = emptyList(),
		onEverySecondOfWaiting: (Long) -> Unit = EMPTY_WAITING_REACTION,
		beforeEachRequestAttempt: () -> Unit = EMPTY_BEFORE_REQUEST_ACTION,
	) : this(
		httpClient = buildHttpClient(
			defaultConnectionTimeout = defaultConnectionTimeout,
			defaultReadTimeout = defaultReadTimeout,
			defaultMaxConnections = defaultMaxConnections,
			defaultHeaders = defaultHeaders
		),
		rateLimitRetryStrategy = SleepForRetryAfterRateLimitRetryStrategy(
			defaultRateLimitAttempts = defaultRateLimitAttempts,
			defaultRateLimitRetryInterval = defaultRateLimitRetryInterval,
			maxRateLimitRetryInterval = maxRateLimitRetryInterval,
			onEverySecondOfWaiting = onEverySecondOfWaiting,
			beforeEachRequestAttempt = beforeEachRequestAttempt,
		)
	)

	override fun makeHttpRequest(httpRequest: HttpRequest): HttpResponse {
		return try {
			rateLimitRetryStrategy.makeHttpRequest(httpClient, httpRequest)
		} catch (e: IOException) {
			HttpResponse.TransportError(e)
		}
	}

	companion object {

		private fun buildHttpClient(
			defaultConnectionTimeout: Int = DEFAULT_CONNECTION_TIMEOUT,
			defaultReadTimeout: Int = DEFAULT_READ_TIMEOUT,
			defaultMaxConnections: Int = DEFAULT_MAX_CONNECTIONS,
			defaultHeaders: List<Header> = emptyList(),
		): HttpClient {
			val connectionManager = PoolingHttpClientConnectionManager().apply {
				maxTotal = defaultMaxConnections
				defaultMaxPerRoute = defaultMaxConnections
			}

			val requestConfig = RequestConfig.custom()
				.setConnectTimeout(defaultConnectionTimeout)
				.setConnectionRequestTimeout(defaultConnectionTimeout)
				.setSocketTimeout(defaultReadTimeout)
				.build()

			val httpClientBuilder = HttpClientBuilder.create()
				.setConnectionManager(connectionManager)
				.setDefaultRequestConfig(requestConfig)
			// TODO .setRetryHandler()
			// TODO .setServiceUnavailableRetryStrategy()
			if (defaultHeaders.isNotEmpty()) {
				httpClientBuilder.setDefaultHeaders(defaultHeaders)
			}
			return httpClientBuilder.build()
		}
	}
}
