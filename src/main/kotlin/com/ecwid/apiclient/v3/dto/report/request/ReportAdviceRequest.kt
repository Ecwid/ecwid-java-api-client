package com.ecwid.apiclient.v3.dto.report.request

import com.ecwid.apiclient.v3.dto.report.enums.*
import com.ecwid.apiclient.v3.impl.RequestInfo
import com.ecwid.apiclient.v3.responsefields.ResponseFields

data class ReportAdviceRequest(
	override val reportType: ReportType = ReportType.allTraffic,
	override val startedFrom: Long? = null,
	override val endedAt: Long? = null,
	override val timeScaleValue: TimeScaleValue? = null,
	override val comparePeriod: ComparePeriod? = null,
	override val firstDayOfWeek: FirstDayOfWeek? = null,
	override val orderByMetric: String? = null,
	override val orderDirection: String? = null,
	override val limit: Int? = null,
	override val offset: Int? = null,
	override val responseFields: ResponseFields = ResponseFields.All,
	override val storefrontPlatform: StorefrontPlatform? = null,
) : AbstractReportRequest() {

	override fun toRequestInfo() = RequestInfo.createGetRequest(
		pathSegments = listOf(
			"reports",
			reportType.toString(),
			"tip"
		),
		params = toParams(),
		responseFields = ResponseFields.All
	)

}
