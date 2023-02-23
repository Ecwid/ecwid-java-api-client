package com.ecwid.apiclient.v3.dto.report.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.dto.report.enums.ComparePeriod
import com.ecwid.apiclient.v3.dto.report.enums.FirstDayOfWeek
import com.ecwid.apiclient.v3.dto.report.enums.TimeScaleValue
import com.ecwid.apiclient.v3.impl.RequestInfo

data class ReportRequest(
	val reportType: String,
	val startedFrom: Long? = null,
	val endedAt: Long? = null,
	val timeScaleValue: TimeScaleValue? = null,
	val comparePeriod: ComparePeriod? = null,
	val firstDayOfWeek: FirstDayOfWeek? = null,
) : ApiRequest {

	override fun toRequestInfo() = RequestInfo.createGetRequest(
		pathSegments = listOf(
			"reports",
			reportType
		),
		params = toParams()
	)

	private fun toParams(): Map<String, String> {
		return mutableMapOf<String, String>().apply {
			startedFrom?.let { put("startedFrom", it.toString()) }
			endedAt?.let { put("endedAt", it.toString()) }
			timeScaleValue?.let { put("timeScaleValue", it.alias) }
			comparePeriod?.let { put("comparePeriod", it.alias) }
			firstDayOfWeek?.let { put("firstDayOfWeek", it.alias) }
		}.toMap()
	}



}
