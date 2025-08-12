package com.ecwid.apiclient.v3.converter

import com.ecwid.apiclient.v3.dto.profile.request.UpdatedShippingOption
import com.ecwid.apiclient.v3.dto.profile.result.FetchedShippingOption

fun FetchedShippingOption.toUpdated(): UpdatedShippingOption {
	return UpdatedShippingOption(
		id = id,
		appClientId = appClientId,
		title = title,
		titleTranslated = titleTranslated,
		description = description,
		descriptionTranslated = descriptionTranslated,
		enabled = enabled,
		orderBy = orderBy,
		fulfilmentType = fulfilmentType,
		minimumOrderSubtotal = minimumOrderSubtotal,
		destinationZone = destinationZone?.toUpdated(),
		deliveryTimeDays = deliveryTimeDays,
		ratesCalculationType = ratesCalculationType,
		locationId = locationId,
		flatRate = flatRate?.toUpdated(),
		ratesTable = ratesTable?.toUpdated(),
		shippingCostMarkup = shippingCostMarkup,
		pickupPreparationTimeHours = pickupPreparationTimeHours,
		pickupBusinessHours = pickupBusinessHours,
		pickupInstruction = pickupInstruction,
		pickupInstructionTranslated = pickupInstructionTranslated,
		scheduledPickup = scheduledPickup,
		pickupPrecisionType = pickupPrecisionType,
		type = type,
		carrier = carrier,
		carrierSettings = carrierSettings?.toUpdate(),
		carrierMethods = carrierMethods?.map { it.toUpdated() },
		businessHours = businessHours,
		businessHoursLimitationType = businessHoursLimitationType,
		allowSameDayDelivery = allowSameDayDelivery,
		availabilityPeriod = availabilityPeriod,
		customAvailabilityPeriodInDays = customAvailabilityPeriodInDays,
		scheduled = scheduled,
		scheduledTimePrecisionType = scheduledTimePrecisionType,
		timeSlotLengthInMinutes = timeSlotLengthInMinutes,
		cutoffTimeForSameDayDelivery = cutoffTimeForSameDayDelivery,
		fulfillmentTimeInMinutes = fulfillmentTimeInMinutes,
		blackoutDates = blackoutDates?.map { it.toUpdated() },
		estimatedShippingTimeAtCheckoutSettings = estimatedShippingTimeAtCheckoutSettings?.toUpdated(),
	)
}

fun FetchedShippingOption.Zone.toUpdated(): UpdatedShippingOption.Zone {
	return UpdatedShippingOption.Zone(
		id = id,
		name = name,
		countryCodes = countryCodes,
		stateOrProvinceCodes = stateOrProvinceCodes,
		postCodes = postCodes,
	)
}

fun FetchedShippingOption.FlatRate.toUpdated(): UpdatedShippingOption.FlatRate {
	return UpdatedShippingOption.FlatRate(
		rateType = rateType,
		rate = rate,
	)
}

fun FetchedShippingOption.TableRates.toUpdated(): UpdatedShippingOption.TableRates {
	return UpdatedShippingOption.TableRates(
		tableBasedOn = tableBasedOn,
		rates = rates?.map { it.toUpdated() },
	)
}

fun FetchedShippingOption.Rates.toUpdated(): UpdatedShippingOption.Rates {
	return UpdatedShippingOption.Rates(
		conditions = conditions?.toUpdated(),
		rate = rate?.toUpdated(),
	)
}

fun FetchedShippingOption.Conditions.toUpdated(): UpdatedShippingOption.Conditions {
	return UpdatedShippingOption.Conditions(
		subtotalFrom = subtotalFrom,
		subtotalTo = subtotalTo,
		discountedSubtotalFrom = discountedSubtotalFrom,
		discountedSubtotalTo = discountedSubtotalTo,
		weightFrom = weightFrom,
		weightTo = weightTo,
	)
}

fun FetchedShippingOption.Rate.toUpdated(): UpdatedShippingOption.Rate {
	return UpdatedShippingOption.Rate(
		perOrder = perOrder,
		percent = percent,
		perItem = perItem,
		perWeight = perWeight,
	)
}

fun FetchedShippingOption.BlackoutPeriodItem.toUpdated(): UpdatedShippingOption.BlackoutPeriodItem {
	return UpdatedShippingOption.BlackoutPeriodItem(
		fromDate = fromDate,
		toDate = toDate,
		repeatedAnnually = repeatedAnnually,
	)
}

fun FetchedShippingOption.EstimatedShippingTimeAtCheckoutSettings.toUpdated(): UpdatedShippingOption.EstimatedShippingTimeAtCheckoutSettings {
	return UpdatedShippingOption.EstimatedShippingTimeAtCheckoutSettings(
		estimatedDeliveryDateAtCheckoutEnabled = estimatedDeliveryDateAtCheckoutEnabled,
		estimatedTransitTimeInDays = estimatedTransitTimeInDays,
		fulfillmentTimeInDays = fulfillmentTimeInDays,
		shippingBusinessDays = shippingBusinessDays,
		deliveryDays = deliveryDays,
		cutoffTimeForSameDayPacking = cutoffTimeForSameDayPacking,
	)
}

fun FetchedShippingOption.CarrierSettings.toUpdate(): UpdatedShippingOption.CarrierSettings {
	return UpdatedShippingOption.CarrierSettings(
		defaultCarrierAccountEnabled = defaultCarrierAccountEnabled,
		defaultPostageDimensions = defaultPostageDimensions?.toUpdate(),
	)
}

fun FetchedShippingOption.DefaultPostageDimensions.toUpdate(): UpdatedShippingOption.DefaultPostageDimensions {
	return UpdatedShippingOption.DefaultPostageDimensions(
		height = height,
		length = length,
		width = width,
	)
}

fun FetchedShippingOption.CarrierMethod.toUpdated(): UpdatedShippingOption.CarrierMethod {
	return UpdatedShippingOption.CarrierMethod(
		id = id,
		name = name,
		enabled = enabled,
		orderBy = orderBy,
	)
}
