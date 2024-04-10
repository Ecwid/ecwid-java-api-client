package com.ecwid.apiclient.v3.dto.report.result

import com.ecwid.apiclient.v3.dto.common.ApiFetchedDTO
import com.ecwid.apiclient.v3.dto.order.enums.FulfillmentType
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
	val itemCount: Int? = null,
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

		data class AdditionalInventoryData(
			val productId: Int = 0,
			val sku: String? = null,
			val imageUrl: String? = null,
			val thumbnailUrl: String? = null,
			val exampleOrder: String? = null,
		) : FetchedAdditionalData(AdditionalDataType.INVENTORY_PRODUCT)

		data class AdditionalProductData(
			val productName: String? = null,
			val productSmallThumbnailUrl: String? = null,
			val productUrl: String? = null,
			val productEditUrl: String? = null,
		) : FetchedAdditionalData(AdditionalDataType.PRODUCT)

		data class AdditionalCouponData(
			val couponName: String? = null,
		) : FetchedAdditionalData(AdditionalDataType.COUPONS)

		data class AdditionalAbandonedCartData(
			val autoAbandonedSalesRecovery: Boolean? = null,
		) : FetchedAdditionalData(AdditionalDataType.ABANDONED_CARTS)

		data class AdditionalShippingData(
			val shippingMethodName: String? = null,
			val fulfilmentType: FulfillmentType? = null,
		) : FetchedAdditionalData(AdditionalDataType.SHIPPING)

		data class AdditionalLandingData(
			val landingUrl: String? = null,
		) : FetchedAdditionalData(AdditionalDataType.LANDING)

		enum class AdditionalDataType {
			UTM,
			ORDERS,
			CUSTOMERS,
			PRODUCT,
			INVENTORY_PRODUCT,
			COUPONS,
			ABANDONED_CARTS,
			SHIPPING,
			LANDING,
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
