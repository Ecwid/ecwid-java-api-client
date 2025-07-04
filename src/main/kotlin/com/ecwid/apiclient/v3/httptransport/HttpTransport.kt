package com.ecwid.apiclient.v3.httptransport

import java.io.Closeable

interface HttpTransport : Closeable {
	fun makeHttpRequest(httpRequest: HttpRequest): HttpResponse
}
