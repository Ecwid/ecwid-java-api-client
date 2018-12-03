package com.ecwid.apiclient.v3.httptransport

internal interface HttpTransport {
	fun makeHttpRequest(httpRequest: HttpRequest): HttpResponse
}
