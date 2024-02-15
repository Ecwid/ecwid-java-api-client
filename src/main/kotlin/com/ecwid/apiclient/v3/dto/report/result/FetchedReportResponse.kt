package com.ecwid.apiclient.v3.dto.report.result

import com.ecwid.apiclient.v3.dto.common.ApiFetchedDTO
import com.ecwid.apiclient.v3.dto.report.enums.ComparePeriod
import com.ecwid.apiclient.v3.dto.report.enums.ReportType
import com.ecwid.apiclient.v3.dto.report.enums.TimeScaleValue

data class FetchedReportResponse(
	val reportType: ReportType = ReportType.allTraffic,
	val startedFrom: Long = 0,
	val endedAt: Long = 0,
	val timeScaleValue: TimeScaleValue? = null,
	val comparePeriod: ComparePeriod? = null,
	val firstDayOfWeek: String? = null,
	val orderByMetric: String? = null,
	val orderDirection: String? = null,
	val limit: Int? = null,
	val offset: Int? = null,
	val aggregatedData: List<FetchedDataItem> = listOf(),
	val dataset: List<FetchedDataset>? = null,
	val comparePeriodAggregatedData: List<FetchedDataItem>? = null,
	val comparePeriodDataset: List<FetchedDataset>? = null,
	val additionalData: FetchedAdditionalData? = null,
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
		val additionalData: FetchedAdditionalData? = null,
	)

	data class FetchedDataItem(
		val dataId: String = "",
		val dataValue: Double = 0.0,
	)

	sealed class FetchedAdditionalData(
		val type: AdditionalDataType,
	) {
		data class AdditionalUtmData(
			val utmList: List<FetchedUtmDataItem> = emptyList(),
		) : FetchedAdditionalData(AdditionalDataType.UTM)

		data class AdditionalOrdersData(
			val ordersCount: Int = 0,
		) : FetchedAdditionalData(AdditionalDataType.ORDERS)

		data class AdditionalCustomerData(
			val customerData: FetchedCustomerData = FetchedCustomerData(),
		) : FetchedAdditionalData(AdditionalDataType.CUSTOMERS)

		enum class AdditionalDataType {
			UTM,
			ORDERS,
			CUSTOMERS,
		}
	}

	data class FetchedUtmDataItem(
		val utmSource: String = "",
		val utmMedium: String = "",
		val utmCampaign: String = "",
	)

	data class FetchedCustomerData(
		val customerId: Int? = null,
		val customerEmail: String? = null,
		val customerPhone: String? = null,
		val customerName: String = "",
	)

	override fun getModifyKind() = ApiFetchedDTO.ModifyKind.ReadOnly

}
