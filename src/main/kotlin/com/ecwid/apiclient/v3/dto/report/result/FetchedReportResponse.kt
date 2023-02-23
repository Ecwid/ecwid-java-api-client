package com.ecwid.apiclient.v3.dto.report.result

import com.ecwid.apiclient.v3.dto.common.ApiFetchedDTO
import com.ecwid.apiclient.v3.dto.report.enums.ComparePeriod

data class FetchedReportResponse(
	val reportType: String,
	val startedFrom: Long,
	val endedAt: Long,
	val timeScaleValue: String? = null,
	val comparePeriod: ComparePeriod? = null,
	val aggregatedData: List<DataItem>,
	val dataset: List<Dataset>? = null,
) : ApiFetchedDTO {

	data class Dataset(
		val orderBy: Int,
		val datapointId: String,
		val data: List<DataItem>,
		val startTimeStamp: Long? = null,
		val endTimeStamp: Long? = null,
		val percentage: Double? = null,
		val comparePeriodStartTimeStamp: Long? = null,
		val comparePeriodEndTimeStamp: Long? = null,
	)

	data class DataItem(
		val dataId: String,
		val dataValue: Double?,
	)

	override fun getModifyKind() = ApiFetchedDTO.ModifyKind.ReadOnly

}
