package com.ecwid.apiclient.v3.dto.profile.request

import com.ecwid.apiclient.v3.dto.common.ApiUpdatedDTO
import com.ecwid.apiclient.v3.dto.profile.result.FetchedStoreProfile
import java.util.*

data class UpdatedShippingOption(
	val title: String? = null,
	val enabled: Boolean? = null,
	val orderBy: Int? = null,
	val destinationZone: Zone? = null,
	val businessHours: String? = null,
	val businessHoursLimitationType: String? = null,
	val blackoutDates: List<BlackoutDates>? = null,
	val scheduled: Boolean? = null,
	val scheduledPickup: Boolean? = null,
	val fulfillmentTimeInMinutes: Int? = null,
	val scheduledTimePrecisionType: String? = null,
	val deliveryTimeDays: String? = null,
	val timeSlotLengthInMinutes: Int? = null,
	val allowSameDayDelivery: Boolean? = null,
	val cutoffTimeForSameDayDelivery: String? = null,
	val availabilityPeriod: String? = null,
	val pickupInstruction: String? = null,
	val description: String? = null,
	val pickupPreparationTimeHours: Int? = null,
	val pickupBusinessHours: String? = null,
	val minimumOrderSubtotal: Int? = null,
	val flatRate: FlatRate? = null,
	val ratesTable: TableRatesDetails? = null,
	val estimatedShippingTimeAtCheckoutSettings: EstimatedShippingTimeAtCheckoutSettings? = null,
	val details: Details? = null,
) : ApiUpdatedDTO {
	override fun getModifyKind(): ApiUpdatedDTO.ModifyKind {
		return ApiUpdatedDTO.ModifyKind.ReadWrite(FetchedStoreProfile.ShippingOption::class)
	}
}

data class Zone(
	val name: String? = null,
	val countryCodes: List<String>? = null,
	val stateOrProvinceCodes: List<String>? = null,
	val postCodes: List<String>? = null,
	val geoPolygons: List<GeoPolygons>? = null,
)

data class GeoPolygons(
	val coordinates: List<List<Double>>? = null,
)

data class BlackoutDates(
	var fromDate: Date? = null,
	var toDate: Date? = null,
	var repeatedAnnually: Boolean? = null,
)

data class FlatRate(
	val rateType: String? = null,
	val rate: Double? = null,
)

data class TableRatesDetails(
	val tableBasedOn: String? = null,
	val rates: String? = null,
)

data class EstimatedShippingTimeAtCheckoutSettings(
	val estimatedDeliveryDateAtCheckoutEnabled: Boolean? = null,
	val estimatedTransitTimeInDays: List<Int>? = null,
	val fulfillmentTimeInDays: Int? = null,
	val cutoffTimeForSameDayPacking: String? = null,
	val shippingBusinessDays: List<String>? = null,
	val deliveryDays: List<String>? = null,
)

data class Details(
	val useDefaultAccount: Boolean? = null,
)
