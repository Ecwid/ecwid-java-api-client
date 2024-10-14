package com.ecwid.apiclient.v3.dto.report.request

import com.ecwid.apiclient.v3.dto.ApiRequest
import com.ecwid.apiclient.v3.dto.report.enums.*
import com.ecwid.apiclient.v3.responsefields.ResponseFields

abstract class AbstractReportRequest : ApiRequest {
	abstract val reportType: ReportType
	abstract val startedFrom: Long?
	abstract val endedAt: Long?
	abstract val timeScaleValue: TimeScaleValue?
	abstract val comparePeriod: ComparePeriod?
	abstract val firstDayOfWeek: FirstDayOfWeek?
	abstract val orderByMetric: String?
	abstract val orderDirection: String?
	abstract val limit: Int?
	abstract val offset: Int?
	abstract val responseFields: ResponseFields
	abstract val storefrontPlatform: StorefrontPlatform?

	protected fun toParams(): Map<String, String> {
		return mutableMapOf<String, String>().apply {
			startedFrom?.let { put("startedFrom", it.toString()) }
			endedAt?.let { put("endedAt", it.toString()) }
			timeScaleValue?.let { put("timeScaleValue", it.toString()) }
			comparePeriod?.let { put("comparePeriod", it.toString()) }
			firstDayOfWeek?.let { put("firstDayOfWeek", it.toString()) }
			orderByMetric?.let { put("orderByMetric", it) }
			orderDirection?.let { put("orderDirection", it) }
			limit?.let { put("limit", it.toString()) }
			offset?.let { put("offset", it.toString()) }
			storefrontPlatform?.let { put("storefrontPlatform", it.toString()) }
		}.toMap()
	}
}
