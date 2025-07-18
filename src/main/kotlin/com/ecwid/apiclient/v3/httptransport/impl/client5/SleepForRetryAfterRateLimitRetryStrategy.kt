package com.ecwid.apiclient.v3.httptransport.impl.client5

import com.ecwid.apiclient.v3.httptransport.HttpRequest
import com.ecwid.apiclient.v3.httptransport.HttpResponse
import com.ecwid.apiclient.v3.httptransport.TransportHttpBody
import org.apache.hc.client5.http.classic.HttpClient
import org.apache.hc.core5.http.ClassicHttpRequest
import java.io.IOException

class SleepForRetryAfterRateLimitRetryStrategy(
	private val defaultRateLimitRetryInterval: Long = DEFAULT_RATE_LIMIT_RETRY_INTERVAL_SECONDS,
	private val maxRateLimitRetryInterval: Long = MAX_RATE_LIMIT_RETRY_INTERVAL_SECONDS,
	private val defaultRateLimitAttempts: Int = DEFAULT_RATE_LIMIT_ATTEMPTS,
	private val onEverySecondOfWaiting: (Long) -> Unit = EMPTY_WAITING_REACTION,
	private val beforeEachRequestAttempt: () -> Unit = EMPTY_BEFORE_REQUEST_ACTION,
) : RateLimitRetryStrategy {

	override fun makeHttpRequest(httpClient: HttpClient, httpRequest: HttpRequest): HttpResponse {
		val request = httpRequest.toHttpUriRequest()
		return if (httpRequest.transportHttpBody is TransportHttpBody.InputStreamBody) {
			executeWithoutRetry(httpClient, request)
		} else {
			executeWithRetryOnRateLimited(httpClient, request)
		}
	}

	private fun executeWithoutRetry(httpClient: HttpClient, request: ClassicHttpRequest) =
		httpClient.execute(request) { response ->
			response.toApiResponse()
		}

	private fun executeWithRetryOnRateLimited(httpClient: HttpClient, request: ClassicHttpRequest): HttpResponse {
		return try {
			RateLimitedHttpClientWrapper(
                httpClient,
                defaultRateLimitRetryInterval,
                maxRateLimitRetryInterval,
                defaultRateLimitAttempts,
                onEverySecondOfWaiting,
				beforeEachRequestAttempt
            ).execute(request) { response ->
				response.toApiResponse()
			}
		} catch (e: IOException) {
            HttpResponse.TransportError(e)
		}
	}

}
