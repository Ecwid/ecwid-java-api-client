package com.ecwid.apiclient.v3.dto.report.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.dto.report.enums.ReportType
import com.ecwid.apiclient.v3.impl.RequestInfo
import com.ecwid.apiclient.v3.responsefields.ResponseFields

data class ReportAdviceRequest(
	val reportType: ReportType = ReportType.allTraffic,
): ApiRequest {

	override fun toRequestInfo() = RequestInfo.createGetRequest(
			pathSegments = listOf(
				"reports",
				reportType.toString(),
				"tip"
			),
			params = emptyMap(),
			responseFields = ResponseFields.All
		)

}
