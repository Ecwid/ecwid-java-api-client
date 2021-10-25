package com.ecwid.apiclient.v3.httptransport.impl

import com.ecwid.apiclient.v3.httptransport.HttpRequest
import com.ecwid.apiclient.v3.httptransport.HttpResponse
import org.apache.http.client.HttpClient

interface RateLimitRetryStrategy {
	fun makeHttpRequest(httpClient: HttpClient, httpRequest: HttpRequest): HttpResponse
}

