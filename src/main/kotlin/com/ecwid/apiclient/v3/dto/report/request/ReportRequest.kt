package com.ecwid.apiclient.v3.dto.report.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.dto.report.enums.ComparePeriod
import com.ecwid.apiclient.v3.dto.report.enums.FirstDayOfWeek
import com.ecwid.apiclient.v3.dto.report.enums.ReportType
import com.ecwid.apiclient.v3.dto.report.enums.TimeScaleValue
import com.ecwid.apiclient.v3.impl.RequestInfo

data class ReportRequest(
	val reportType: ReportType = ReportType.allTraffic,
	val startedFrom: Long? = null,
	val endedAt: Long? = null,
	val timeScaleValue: TimeScaleValue? = null,
	val comparePeriod: ComparePeriod? = null,
	val firstDayOfWeek: FirstDayOfWeek? = null,
) : ApiRequest {

	override fun toRequestInfo() = RequestInfo.createGetRequest(
		pathSegments = listOf(
			"reports",
			reportType.toString(),
		),
		params = toParams()
	)

	private fun toParams(): Map<String, String> {
		return mutableMapOf<String, String>().apply {
			startedFrom?.let { put("startedFrom", it.toString()) }
			endedAt?.let { put("endedAt", it.toString()) }
			timeScaleValue?.let { put("timeScaleValue", it.toString()) }
			comparePeriod?.let { put("comparePeriod", it.toString()) }
			firstDayOfWeek?.let { put("firstDayOfWeek", it.toString()) }
		}.toMap()
	}



}
