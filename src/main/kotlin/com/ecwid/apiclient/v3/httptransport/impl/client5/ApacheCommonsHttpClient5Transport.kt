package com.ecwid.apiclient.v3.httptransport.impl.client5

import com.ecwid.apiclient.v3.httptransport.HttpRequest
import com.ecwid.apiclient.v3.httptransport.HttpResponse
import com.ecwid.apiclient.v3.httptransport.HttpTransport
import org.apache.hc.client5.http.classic.HttpClient
import org.apache.hc.client5.http.config.ConnectionConfig
import org.apache.hc.client5.http.config.RequestConfig
import org.apache.hc.client5.http.impl.classic.HttpClients
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder
import org.apache.hc.core5.http.Header
import java.io.Closeable
import java.io.IOException
import java.util.concurrent.TimeUnit

private const val DEFAULT_CONNECTION_TIMEOUT = 10_000L // 10 sec
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

open class ApacheCommonsHttpClient5Transport(
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
		defaultConnectionTimeout: Long = DEFAULT_CONNECTION_TIMEOUT,
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
		defaultConnectionTimeout: Long = DEFAULT_CONNECTION_TIMEOUT,
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

	override fun close() {
		if (httpClient is Closeable) {
			httpClient.close()
		}
	}

	companion object {

		private fun buildHttpClient(
			defaultConnectionTimeout: Long = DEFAULT_CONNECTION_TIMEOUT,
			defaultReadTimeout: Int = DEFAULT_READ_TIMEOUT,
			defaultMaxConnections: Int = DEFAULT_MAX_CONNECTIONS,
			defaultHeaders: List<Header> = emptyList(),
		): HttpClient {
			val connectionConfig = ConnectionConfig.custom()
				.setConnectTimeout(defaultConnectionTimeout, TimeUnit.SECONDS)
				.setSocketTimeout(defaultReadTimeout, TimeUnit.SECONDS)
				.build()

			val connectionManager = PoolingHttpClientConnectionManagerBuilder.create()
				.setMaxConnTotal(defaultMaxConnections)
				.setMaxConnPerRoute(defaultMaxConnections)
				.setDefaultConnectionConfig(connectionConfig)
				.build()

			val requestConfig = RequestConfig.custom()
				.setConnectionRequestTimeout(defaultConnectionTimeout, TimeUnit.SECONDS)
				.build()

			val httpClientBuilder = HttpClients.custom()
				.setConnectionManager(connectionManager)
				.setDefaultRequestConfig(requestConfig)
				.setRedirectStrategy(RemoveDisallowedHeadersRedirectStrategy())
			// TODO .setRetryHandler()
			// TODO .setServiceUnavailableRetryStrategy()
			if (defaultHeaders.isNotEmpty()) {
				httpClientBuilder.setDefaultHeaders(defaultHeaders)
			}
			return httpClientBuilder.build()
		}
	}
}

