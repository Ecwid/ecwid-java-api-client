package com.ecwid.apiclient.v3.metric

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.impl.RequestInfo

internal fun ApiRequest.buildMetricName() = this
	.javaClass
	.simpleName
	.split("[A-Z]".toRegex()).joinToString("_") { it.lowercase() }

internal fun RequestInfo.getFirstPathSegment() = this
	.pathSegments
	.firstOrNull() ?: ""
