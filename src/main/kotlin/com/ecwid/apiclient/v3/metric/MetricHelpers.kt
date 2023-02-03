package com.ecwid.apiclient.v3.metric

import com.ecwid.apiclient.v3.httptransport.HttpResponse
import com.ecwid.apiclient.v3.impl.RequestInfo

internal fun RequestInfo.getFirstPathSegment() = this
	.pathSegments
	.firstOrNull() ?: ""

internal fun extractStatusFromHttpResponse(result: HttpResponse): String {
	return when (result) {
		is HttpResponse.Error -> result.statusCode.toString()
		is HttpResponse.Success -> 200.toString()
		is HttpResponse.TransportError -> "transport_error"
	}
}
