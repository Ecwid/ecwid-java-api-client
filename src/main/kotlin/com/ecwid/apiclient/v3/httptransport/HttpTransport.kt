package com.ecwid.apiclient.v3.httptransport

interface HttpTransport {
	fun makeHttpRequest(httpRequest: HttpRequest): HttpResponse
}
