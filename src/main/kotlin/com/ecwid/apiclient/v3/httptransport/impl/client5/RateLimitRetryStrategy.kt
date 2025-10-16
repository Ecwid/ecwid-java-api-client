package com.ecwid.apiclient.v3.httptransport.impl.client5

import com.ecwid.apiclient.v3.httptransport.HttpRequest
import com.ecwid.apiclient.v3.httptransport.HttpResponse
import org.apache.hc.client5.http.classic.HttpClient

interface RateLimitRetryStrategy {
	fun makeHttpRequest(httpClient: HttpClient, httpRequest: HttpRequest): HttpResponse
}

