package com.ecwid.apiclient.v3.dto.report.result

import com.ecwid.apiclient.v3.dto.common.ApiFetchedDTO
import com.ecwid.apiclient.v3.dto.report.enums.ComparePeriod
import com.ecwid.apiclient.v3.dto.report.enums.ReportType
import com.ecwid.apiclient.v3.dto.report.enums.TimeScaleValue
import com.google.gson.JsonObject

data class FetchedReportResponse(
	val reportType: ReportType = ReportType.allTraffic,
	val startedFrom: Long = 0,
	val endedAt: Long = 0,
	val timeScaleValue: TimeScaleValue? = null,
	val comparePeriod: ComparePeriod? = null,
	val aggregatedData: List<FetchedDataItem> = listOf(),
	val dataset: List<FetchedDataset>? = null,
	val comparePeriodAggregatedData: List<FetchedDataItem>? = null,
	val comparePeriodDataset: List<FetchedDataset>? = null,
) : ApiFetchedDTO {

	data class FetchedDataset(
		val orderBy: Int = 0,
		val datapointId: String = "",
		val data: List<FetchedDataItem> = listOf(),
		val startTimeStamp: Long? = null,
		val endTimeStamp: Long? = null,
		val percentage: Double? = null,
		val comparePeriodStartTimeStamp: Long? = null,
		val comparePeriodEndTimeStamp: Long? = null,
		val additionalData: JsonObject? = null,
	)

	data class FetchedDataItem(
		val dataId: String = "",
		val dataValue: Double = 0.0,
	)

	override fun getModifyKind() = ApiFetchedDTO.ModifyKind.ReadOnly

}
