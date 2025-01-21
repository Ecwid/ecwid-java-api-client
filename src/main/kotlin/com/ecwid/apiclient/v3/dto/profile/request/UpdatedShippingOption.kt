package com.ecwid.apiclient.v3.dto.profile.request

import com.ecwid.apiclient.v3.dto.common.ApiUpdatedDTO
import com.ecwid.apiclient.v3.dto.profile.enums.*
import com.ecwid.apiclient.v3.dto.profile.result.FetchedShippingOption
import java.util.*

data class UpdatedShippingOption(
	val id: String? = null,
	val appClientId: String? = null,
	val title: String? = null,
	val titleTranslated: Map<String, String>? = null,
	val description: String? = null,
	val descriptionTranslated: Map<String, String>? = null,
	val enabled: Boolean? = null,
	val orderBy: Int? = null,
	val fulfilmentType: FulfilmentType? = null,
	val minimumOrderSubtotal: Double? = null,
	val destinationZone: Zone? = null,
	val ratesCalculationType: RatesCalculationType? = null,
	val locationId: String? = null,
	val flatRate: FlatRate? = null,
	val shippingCostMarkup: Double? = null,
	val ratesTable: TableRates? = null,
	val pickupInstruction: String? = null,
	val pickupInstructionTranslated: Map<String, String>? = null,
	val pickupPreparationTimeHours: Int? = null,
	val pickupBusinessHours: String? = null,
	val scheduledPickup: Boolean? = null,
	val type: String? = null,
	val carrier: String? = null,
	val carrierMethods: List<CarrierMethod>? = null,
	val carrierSettings: CarrierSettings? = null,
	val businessHours: BusinessHours? = null,
	val businessHoursLimitationType: ApiBusinessHoursLimitationType? = null,
	val allowSameDayDelivery: Boolean? = null,
	val deliveryTimeDays: String? = null,
	val availabilityPeriod: AvailabilityPeriodType? = null,
	val customAvailabilityPeriodInDays: Int? = null,
	val scheduled: Boolean? = null,
	val scheduledTimePrecisionType: ScheduledTimePrecisionType? = null,
	val timeSlotLengthInMinutes: Int? = null,
	val cutoffTimeForSameDayDelivery: String? = null,
	val fulfillmentTimeInMinutes: Int? = null,
	val blackoutDates: List<BlackoutPeriodItem>? = null,
	val estimatedShippingTimeAtCheckoutSettings: EstimatedShippingTimeAtCheckoutSettings? = null,
) : ApiUpdatedDTO {
	data class Zone(
		val id: String? = null,
		val name: String? = null,
		val countryCodes: List<String>? = null,
		val stateOrProvinceCodes: List<String>? = null,
		val postCodes: List<String>? = null,
	)

	data class FlatRate(
		val rateType: String? = null,
		val rate: Double? = null,
	)

	data class TableRates(
		val tableBasedOn: String? = null,
		val rates: List<Rates>? = null,
	)

	data class Rates(
		val conditions: Conditions? = null,
		val rate: Rate? = null,
	)

	data class Conditions(
		val subtotalFrom: Double? = null,
		val subtotalTo: Double? = null,
		val discountedSubtotalFrom: Double? = null,
		val discountedSubtotalTo: Double? = null,
		val weightFrom: Double? = null,
		val weightTo: Double? = null,
	)

	data class Rate(
		val perOrder: Double? = null,
		val percent: Double? = null,
		val perItem: Double? = null,
		val perWeight: Double? = null,
	)

	data class CarrierMethod(
		val id: String? = null,
		val name: String? = null,
		val enabled: Boolean? = null,
		val orderBy: Int? = null
	)

	data class BlackoutPeriodItem(
		val fromDate: Date? = null,
		val toDate: Date? = null,
		val repeatedAnnually: Boolean? = null,
	)

	data class EstimatedShippingTimeAtCheckoutSettings(
		val estimatedDeliveryDateAtCheckoutEnabled: Boolean? = null,
		val estimatedTransitTimeInDays: List<Int>? = null,
		val fulfillmentTimeInDays: List<Int>? = null,
		val shippingBusinessDays: List<String>? = null,
		val deliveryDays: List<String>? = null,
		val cutoffTimeForSameDayPacking: String? = null,
	)

	data class CarrierSettings(
		val defaultCarrierAccountEnabled: Boolean? = null,
		val defaultPostageDimensions: DefaultPostageDimensions? = null
	)

	data class DefaultPostageDimensions(
		val length: Double? = null,
		val width: Double? = null,
		val height: Double? = null
	)

	override fun getModifyKind(): ApiUpdatedDTO.ModifyKind {
		return ApiUpdatedDTO.ModifyKind.ReadWrite(FetchedShippingOption::class)
	}
}

typealias BusinessHours = Map<String, List<List<String>>>
