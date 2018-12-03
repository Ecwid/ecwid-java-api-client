package com.ecwid.api.v3.httptransport

internal interface HttpTransport {
	fun makeHttpRequest(httpRequest: HttpRequest): HttpResponse
}
